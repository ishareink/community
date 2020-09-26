package life.xnfxzypt.community.service;

import life.xnfxzypt.community.mapper.UserMapper;
import life.xnfxzypt.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {

       User dbUser = userMapper.findByAccountId(user.getAccount_id());
       if(dbUser==null){
           //insert account
           user.setGmt_create(System.currentTimeMillis());
           user.setGmt_modified(user.getGmt_create());
           userMapper.insert(user);
       }
       else{
           //update account
           dbUser.setGmt_modified(System.currentTimeMillis());
           dbUser.setAvatar_url(user.getAvatar_url());
           dbUser.setName(user.getName());
           dbUser.setToken(user.getToken());
           userMapper.update(dbUser);
       }
    }
}
