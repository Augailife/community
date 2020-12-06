package com.zhao.community.provider;

import com.alibaba.fastjson.JSON;
import com.zhao.community.dto.AccessTokenDTO;
import com.zhao.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class GithubProvider {
public String getAccessToken(AccessTokenDTO accessTokenDTO){
    MediaType mediaType = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token=string.split("&")[0].split("=")[1];//先按照&符号分割一次，分割后得到数组取第一个后再按照=号分割一次取第二个。
            return token;
    } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

}
public GithubUser getUser(String accessToken){
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url("https://api.github.com/user?access_token="+accessToken)
            .build();
    try {
        Response response = client.newCall(request).execute();
        String s= response.body().string();
        GithubUser githubUser = JSON.parseObject(s, GithubUser.class);//返回json字符串，找到同名信息后注入GithubUser对象
        return githubUser;
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;

}
}
