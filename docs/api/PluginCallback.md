# 回调方法

::: warning 警告
本文档适用于 WAuxiliary v1.2.6 版本
:::

## 插件加载

```java
void onLoad();
```

## 插件卸载

```java
void onUnLoad();
```

## 监听收到消息

```java
void onHandleMsg(Object msgInfoBean);
```

## 单击发送按钮

```java
boolean onClickSendBtn(String text);
```

## 监听成员变动

```java
void onMemberChange(String type, String groupWxid, String userWxid, String userName);
```

## 监听好友申请

```java
void onNewFriend(String wxid, String ticket, int scene);
```
