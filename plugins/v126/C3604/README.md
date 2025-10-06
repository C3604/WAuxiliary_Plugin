# C3604 插件开发指南

欢迎来到 WAuxiliary Plugin 开发世界！这是您的专用开发目录，您可以在此创建各种有趣的微信辅助插件。

## 📋 目录

- [项目概述](#项目概述)
- [开发环境](#开发环境)
- [插件结构](#插件结构)
- [核心 API](#核心-api)
- [开发模式](#开发模式)
- [示例插件](#示例插件)
- [最佳实践](#最佳实践)
- [调试技巧](#调试技巧)
- [发布流程](#发布流程)

## 🎯 项目概述

WAuxiliary Plugin 是一个微信辅助插件框架，支持：
- 📱 **消息处理**：自动回复、消息转发、内容分析
- 🤖 **智能交互**：AI 聊天、语音合成、图像生成
- 🎵 **媒体功能**：音乐播放、视频分享、图片处理
- 👥 **群组管理**：自动加群、成员管理、群规维护
- 🔧 **实用工具**：定时任务、数据统计、系统集成

## 🛠 开发环境

### 必需文件
每个插件必须包含以下三个文件：

```
YourPlugin/
├── info.prop      # 插件信息配置
├── main.java      # 主程序文件
└── readme.md      # 插件说明文档
```

### 文件模板

#### info.prop
```properties
name = 您的插件名称
author = C3604
version = 20250101
```

#### main.java
```java
// 导入必要的包
import org.json.JSONObject;
import me.hd.wauxv.plugin.api.callback.PluginCallBack;

// 插件主逻辑
void onHandleMsg(Object msgInfoBean) {
    // 消息处理逻辑
}

boolean onClickSendBtn(String text) {
    // 发送按钮点击处理
    return false; // 返回 true 表示已处理
}
```

#### readme.md
```markdown
# 插件名称

## 功能描述
简要描述插件功能

## 使用方法
- 命令格式：`/命令 参数`
- 触发条件：收到消息/点击发送按钮
- 预期结果：描述插件执行后的效果

## 配置说明
如有配置项，请详细说明
```

## 🔌 核心 API

### 消息处理方法

#### 发送消息
参考：[PluginMsgMethod.md](../../../docs/api/method/PluginMsgMethod.md)

#### 媒体分享
参考：[PluginMediaMsgMethod.md](../../../docs/api/method/PluginMediaMsgMethod.md)

### 联系人管理
参考：[PluginContactMethod.md](../../../docs/api/method/PluginContactMethod.md)

### HTTP 请求
参考：[PluginHttpMethod.md](../../../docs/api/method/PluginHttpMethod.md)

### 配置管理
参考：[PluginConfigMethod.md](../../../docs/api/method/PluginConfigMethod.md)

### 实用工具
参考：[PluginOtherMethod.md](../../../docs/api/method/PluginOtherMethod.md)

## 🎨 开发模式

### 1. 消息监听模式
参考：[AutoReply/main.java](../../Hd/AutoReply/main.java)

### 2. 命令触发模式
参考：[DemoPlugin/main.java](../../Hd/DemoPlugin/main.java)

### 3. 群成员变动监听
参考：[JoinAndLeftGroupTips/main.java](../../Hd/JoinAndLeftGroupTips/main.java)

### 4. 好友申请处理
参考：[AutoAgreeFriend/main.java](../../Hd/AutoAgreeFriend/main.java)

## 📚 示例插件

### 基础自动回复
参考：[AutoReply/main.java](../../Hd/AutoReply/main.java)

### 艾特自动回复
参考：[AutoAtReply/main.java](../../Hd/AutoAtReply/main.java)

### HTTP API 调用
参考：[MusicPlugin/main.java](../../Hd/MusicPlugin/main.java)

### 文件下载处理
参考：[TextToSpeech/main.java](../../Hd/TextToSpeech/main.java)

### 图像生成
参考：[TextToImg/main.java](../../Hd/TextToImg/main.java)

### AI 聊天
参考：[OpenAiChat/main.java](../../Hd/OpenAiChat/main.java)

## ✨ 最佳实践

### 1. 错误处理
参考：[MusicPlugin/main.java](../../Hd/MusicPlugin/main.java) 中的 HTTP 回调处理

### 2. 配置管理
参考：[OpenAiChat/main.java](../../Hd/OpenAiChat/main.java) 中的配置读取

### 3. 资源清理
参考：[TextToSpeech/main.java](../../Hd/TextToSpeech/main.java) 中的临时文件处理

### 4. 性能优化
参考：[OpenAiChat/main.java](../../Hd/OpenAiChat/main.java) 中的消息缓存机制

## 🐛 调试技巧

### 1. 日志调试
参考：[PluginOtherMethod.md](../../../docs/api/method/PluginOtherMethod.md) 中的 `log()` 方法

### 2. 状态检查
参考：[PluginStruct.md](../../../docs/api/PluginStruct.md) 中的消息结构说明

### 3. 配置验证
参考：[PluginConfigMethod.md](../../../docs/api/method/PluginConfigMethod.md) 中的配置读取方法

## 🚀 发布流程

### 1. 测试检查
- [ ] 插件功能正常
- [ ] 错误处理完善
- [ ] 资源清理到位
- [ ] 配置项合理
- [ ] 文档完整

### 2. 版本管理
参考：[DemoPlugin/info.prop](../../Hd/DemoPlugin/info.prop)

### 3. 文档完善
参考：[DemoPlugin/readme.md](../../Hd/DemoPlugin/readme.md)

### 4. 提交代码
使用 Git 命令提交您的插件代码到版本控制系统

## 📖 参考资源

### 官方文档
- [API 文档](../docs/api/) - 完整的 API 参考
- [示例插件](../Hd/) - 各种类型的插件示例

### 常用 API
- [消息方法](../docs/api/method/PluginMsgMethod.md)
- [联系人方法](../docs/api/method/PluginContactMethod.md)
- [HTTP 方法](../docs/api/method/PluginHttpMethod.md)
- [媒体方法](../docs/api/method/PluginMediaMsgMethod.md)

### 开发工具
- `log()` - 调试日志
- `toast()` - 用户提示
- `notify()` - 系统通知
- `cacheDir` - 缓存目录

## 🎉 开始开发

现在您已经了解了 WAuxiliary Plugin 的开发流程，可以开始创建您的第一个插件了！

建议从简单的自动回复插件开始，逐步学习更复杂的功能。记住：
- 遵循代码规范
- 添加错误处理
- 完善文档说明
- 及时清理资源

祝您开发愉快！🚀