package life.xnfxzypt.community.controller;

import life.xnfxzypt.community.dto.PaginationDTO;
import life.xnfxzypt.community.model.User;
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
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新发布");
        }
        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
