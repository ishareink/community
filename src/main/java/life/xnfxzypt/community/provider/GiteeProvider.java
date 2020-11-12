package life.xnfxzypt.community.provider;

import com.alibaba.fastjson.JSON;
import life.xnfxzypt.community.dto.AccessTokenDTO;
import life.xnfxzypt.community.dto.GiteeUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GiteeProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()){
            String string = null;
            if (response.body() != null) {
                string = response.body().string();
            }
            String accessToken = JSON.parseObject(string).getString("access_token");
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GiteeUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?")
                .header("Authorization","token "+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()){
            String string=null;
            if (response.body() != null)
            string=response.body().string();
            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);
            return giteeUser;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
