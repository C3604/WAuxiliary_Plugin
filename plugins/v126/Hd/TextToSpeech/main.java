import org.json.JSONObject;

import java.io.File;

import me.hd.wauxv.plugin.api.callback.PluginCallBack;

boolean onClickSendBtn(String text) {
    if (text.startsWith("#tts ")) {
        String str = text.substring(5);
        String voice = "2"; // 音色
        String apikey = ""; // 密钥
        String api = "https://www.yx520.ltd/API/wzzyy/silk.php?text=" + str + "&voice=" + voice + "&apikey=" + apikey;
        get(api, null, new PluginCallBack.HttpCallback() {
            public void onSuccess(int respCode, String respContent) {
                JSONObject json = new JSONObject(respContent);
                String code = json.getString("code");
                if (code.equals("0")) {
                    String url = json.getString("url");
                    String path = cacheDir + "/voice.silk";
                    download(url, path, null, new PluginCallBack.DownloadCallback() {
                        public void onSuccess(File file) {
                            sendVoice(getTargetTalker(), file.getAbsolutePath());
                            file.delete();
                        }
                    });
                }
            }
        });
        return true;
    }
    return false;
}
