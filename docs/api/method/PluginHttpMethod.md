# 网络方法

::: warning 警告
本文档适用于 WAuxiliary v1.2.6 版本
:::

## get

```java
void get(String url, Map<String, String> headerMap, PluginCallBack.HttpCallback callback);

void get(String url, Map<String, String> headerMap, long timeout, PluginCallBack.HttpCallback callback);
```

## post

```java
void post(String url, Map<String, String> paramMap, Map<String, String> headerMap, PluginCallBack.HttpCallback callback);

void post(String url, Map<String, String> paramMap, Map<String, String> headerMap, long timeout, PluginCallBack.HttpCallback callback);
```

## download

```java
void download(String url, String path, Map<String, String> headerMap, PluginCallBack.DownloadCallback callback);

void download(String url, String path, Map<String, String> headerMap, long timeout, PluginCallBack.DownloadCallback callback);
```
