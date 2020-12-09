package com.zhao.community.controller;

import com.zhao.community.dto.AccessTokenDTO;
import com.zhao.community.dto.GithubUser;
import com.zhao.community.mapper.UserMapper;
import com.zhao.community.model.User;
import com.zhao.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class OauthController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecreat;
    @Value("${github.redirect.url}")
    private  String redirectUri;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse) {
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecreat);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null){
            User user=new User();
            user.setAccountId(String.valueOf(githubUser.getId()));//客戶端账户id（固定）,使用valueof将Long类型的转化为Integer类型的
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);//使用UUID随机生成一个16位的不重复的令牌
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setTouXiang(githubUser.getAvatar_url());
            userMapper.insert(user);//以上将获取到的gitHub用户信息封装，并放入数据库中
            httpServletResponse.addCookie(new Cookie("token",token));//将随机生成的令牌放入cookie中去

//            httpServletRequest.getSession().setAttribute("user", githubUser);//将用户信息存入session域中
            return "redirect:/";
//            因为redirect是重定向，后面应该写正规的地址栏地址，在此处和web时不同，web重定向是将/发送给浏览器解析，会解析到项目路径
//            在此处是直接用服务器解析，会解析到项目路径（封装了一下）
        }
        return "redirect:/";
    }
}
