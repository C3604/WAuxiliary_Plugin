// 微信群管理插件
// 功能：限定群组响应、踢人管理

// ==================== 配置部分 ====================
// 允许管理的群组列表（多个群组用逗号分隔，留空表示允许所有群组）
String allowedGroupsConfig = "";

// 调试模式（true=开启，false=关闭）
boolean debugMode = false;

// 全局变量
List<String> allowedGroups = new ArrayList<>();

// 插件加载时初始化
void onLoad() {
    log("微信群管理插件加载中...");
    loadConfig();
    initializePlugin();
    log("微信群管理插件加载完成");
}

// 插件卸载时清理
void onUnLoad() {
    log("微信群管理插件卸载");
}

// 初始化插件
void initializePlugin() {
    // 从配置中加载允许的群组列表
    if (!allowedGroupsConfig.isEmpty()) {
        String[] groups = allowedGroupsConfig.split(",");
        for (String group : groups) {
            String trimmedGroup = group.trim();
            if (!trimmedGroup.isEmpty()) {
                allowedGroups.add(trimmedGroup);
            }
        }
    }
    
    logInfo("允许管理的群组数量: " + allowedGroups.size());
    if (debugMode) {
        logInfo("调试模式已启用");
    }
}

// 消息处理回调
void onHandleMsg(Object msgInfoBean) {
    if (msgInfoBean.isSend()) return;
    if (!msgInfoBean.isText()) return;
    
    String content = msgInfoBean.getContent();
    String talker = msgInfoBean.getTalker();
    String sender = msgInfoBean.getSendTalker();
    
    // 检查是否为群聊消息
    if (!isGroupChat(talker)) return;
    
    // 检查是否在允许的群组列表中
    if (!isAllowedGroup(talker)) {
        logDebug("群组 " + talker + " 不在允许列表中，忽略消息");
        return;
    }
    
    logDebug("处理群组消息: " + talker + " 发送者: " + sender + " 内容: " + content);
    
    // 处理踢人命令
    if (content.contains("@") && content.contains("#b")) {
        handleKickCommand(content, talker, sender);
    }
}

// 检查是否为群聊
boolean isGroupChat(String talker) {
    return talker.contains("@chatroom");
}

// 检查是否为允许的群组
boolean isAllowedGroup(String groupWxid) {
    if (allowedGroups.isEmpty()) {
        logDebug("未配置允许的群组，允许所有群组");
        return true;
    }
    return allowedGroups.contains(groupWxid);
}


// 处理踢人命令
void handleKickCommand(String content, String groupWxid, String sender) {
    try {
        // 解析命令格式: @用户名 #b 原因
        String[] parts = content.split("#b");
        if (parts.length < 2) {
            logError("命令格式错误: " + content);
            sendText(groupWxid, "❌ 命令格式错误，正确格式：@用户名 #b [原因]");
            return;
        }
        
        String mentionPart = parts[0].trim();
        String reason = parts[1].trim();
        
        // 踢人原因可以为空，如果为空则使用默认原因
        if (reason.isEmpty()) {
            reason = "违反群规";
        }
        
        // 提取被@的用户
        String targetUser = extractMentionedUser(mentionPart);
        if (targetUser == null) {
            logError("无法提取被@的用户: " + mentionPart);
            sendText(groupWxid, "❌ 无法识别被@的用户，请确保@的是群成员");
            return;
        }
        
        // 获取用户昵称
        String userName = getFriendName(targetUser, groupWxid);
        if (userName == null || userName.isEmpty()) {
            userName = targetUser;
        }
        
        // 检查是否尝试踢出自己
        if (targetUser.equals(getLoginWxid())) {
            sendText(groupWxid, "❌ 不能踢出自己");
            return;
        }
        
        // 检查是否尝试踢出发送者
        if (targetUser.equals(sender)) {
            sendText(groupWxid, "❌ 不能踢出命令发送者");
            return;
        }
        
        logInfo("准备踢出用户: " + userName + " (" + targetUser + ") 原因: " + reason);
        
        // 执行踢人操作
        kickUserFromGroup(groupWxid, targetUser, userName, reason);
        
    } catch (Exception e) {
        logError("处理踢人命令时发生异常: " + e.getMessage());
        sendText(groupWxid, "❌ 处理踢人命令时发生错误");
    }
}

// 提取被@的用户
String extractMentionedUser(String mentionPart) {
    // 查找@符号后的用户名
    int atIndex = mentionPart.lastIndexOf("@");
    if (atIndex == -1) return null;
    
    String afterAt = mentionPart.substring(atIndex + 1).trim();
    // 移除可能的空格和特殊字符
    afterAt = afterAt.replaceAll("\\s+", "");
    
    // 如果包含@chatroom，说明是群聊中的用户
    if (afterAt.contains("@")) {
        return afterAt;
    }
    
    // 否则需要从群成员列表中查找匹配的用户
    return findUserInGroup(afterAt);
}

// 在群成员中查找用户
String findUserInGroup(String userName) {
    logDebug("查找用户: " + userName);
    
    // 方法1：如果用户名就是wxid格式
    if (userName.contains("@")) {
        return userName;
    }
    
    // 方法2：尝试从群成员列表中模糊匹配
    // 注意：这个方法需要群成员列表，但API可能有限制
    try {
        // 这里可以尝试其他方法获取用户信息
        // 比如通过消息历史或其他API
        logDebug("尝试通过昵称查找用户: " + userName);
        
        // 暂时返回null，实际使用时需要根据具体情况实现
        return null;
    } catch (Exception e) {
        logError("查找用户时发生异常: " + e.getMessage());
        return null;
    }
}


// 执行踢人操作
void kickUserFromGroup(String groupWxid, String userWxid, String userName, String reason) {
    try {
        // 检查用户是否在群中
        List<String> members = getGroupMemberList(groupWxid);
        if (members == null || !members.contains(userWxid)) {
            sendText(groupWxid, "❌ 用户 " + userName + " 不在群中");
            return;
        }
        
        // 执行踢人
        delChatroomMember(groupWxid, userWxid);
        
        // 发送踢人消息
        String kickMessage = userName + "被踢出群组，原因：" + reason;
        sendText(groupWxid, kickMessage);
        
        logInfo("成功踢出用户: " + userName + " 原因: " + reason);
        
    } catch (Exception e) {
        logError("踢人操作失败: " + e.getMessage());
        sendText(groupWxid, "❌ 踢人操作失败: " + e.getMessage());
    }
}

// 发送按钮点击回调
boolean onClickSendBtn(String text) {
    return false;
}

// 工具方法：日志记录
void logInfo(String message) {
    log("[GroupManager INFO] " + message);
}

void logError(String message) {
    log("[GroupManager ERROR] " + message);
}

void logDebug(String message) {
    if (debugMode) {
        log("[GroupManager DEBUG] " + message);
    }
}

