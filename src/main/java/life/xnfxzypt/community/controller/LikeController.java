package life.xnfxzypt.community.controller;

import life.xnfxzypt.community.model.User;
import life.xnfxzypt.community.service.LikeService;
import life.xnfxzypt.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author coolsen
 * @version 1.0.0
 * @ClassName LikeController.java
 * @Description 点赞 Controller
 * @createTime 5/9/2020 4:30 PM
 */

@Controller
public class LikeController  {



    @Autowired
    private LikeService likeService;


    @RequestMapping(path = "/like",method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, Long entityId,Long entityUserId,
                       HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        //点赞
        likeService.like(user.getId(), entityType, entityId,entityUserId);
        //点赞数量
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        //点赞状态
        int likeStatus = likeService.findEntityLikeStatus(entityType, entityId, user.getId());
        //返回的结果
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);
        return CommunityUtil.getJSONString(0, null, map);
    }
}
