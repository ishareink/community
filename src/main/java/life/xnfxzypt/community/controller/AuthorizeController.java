package life.xnfxzypt.community.controller;

import life.xnfxzypt.community.dto.AccessTokenDTO;
import life.xnfxzypt.community.dto.GiteeUser;
import life.xnfxzypt.community.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);

        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser user =giteeProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
