package life.xnfxzypt.community.controller;

import life.xnfxzypt.community.dto.AccessTokenDTO;
import life.xnfxzypt.community.dto.GiteeUser;
import life.xnfxzypt.community.mapper.UserMapper;
import life.xnfxzypt.community.model.User;
import life.xnfxzypt.community.provider.GiteeProvider;
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
public class AuthorizeController {//认证的controller
    @Autowired
    private GiteeProvider giteeProvider;

    @Value("${gitee.client_id}")
    private String clientId;

    @Value("${gitee.client_secret}")
    private String clientSecret;

    @Value("${gitee.redirect_uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);

        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser giteeUser =giteeProvider.getUser(accessToken);
        System.out.println("username——>"+giteeUser.getName());
        if(giteeUser!=null&&giteeUser.getId()!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(giteeUser.getName());
            user.setAccount_id(String.valueOf(giteeUser.getId()));
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            user.setAvatar_url(giteeUser.getAvatar_url());
            userMapper.insert(user);
            //登陆成功，写cookies和session
            response.addCookie(new Cookie("token",token));
            //redirect——跳转
            return "redirect:/";
        }else{
            //登陆失败,重新登录
            return "redirect:/";
        }
    }
}
