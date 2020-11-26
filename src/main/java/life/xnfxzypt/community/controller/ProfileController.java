package life.xnfxzypt.community.controller;

import life.xnfxzypt.community.dto.PaginationDTO;
import life.xnfxzypt.community.model.User;
import life.xnfxzypt.community.service.LikeService;
import life.xnfxzypt.community.service.NotificationService;
import life.xnfxzypt.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private LikeService likeService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
            @PathVariable(name="action") String action,
            Model model,
            @RequestParam(name ="page",defaultValue = "1") Integer page,
            @RequestParam(name ="size",defaultValue = "5") Integer size){

        User user=(User)request.getSession().getAttribute("user");
        if(user ==null){
            model.addAttribute("error","用户未登录");
            return "redirect:/";
        }

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的发布");
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        }else if("replies".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "replies");
            model.addAttribute("pagination", paginationDTO);
            model.addAttribute("sectionName", "最新回复");
        }else if("like".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "like");
            int  likeCount=likeService.findUserLikeCount(user.getId());
            model.addAttribute("pagination", paginationDTO);
            model.addAttribute("likeCount",likeCount);
            model.addAttribute("sectionName", "获得的赞");
        }

        return "profile";
    }
}
