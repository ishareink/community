package life.xnfxzypt.community.controller;

import life.xnfxzypt.community.dto.CommentDTO;
import life.xnfxzypt.community.dto.QuestionDTO;
import life.xnfxzypt.community.enums.CommentTypeEnum;
import life.xnfxzypt.community.model.User;
import life.xnfxzypt.community.service.CommentService;
import life.xnfxzypt.community.service.LikeService;
import life.xnfxzypt.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id, Model model,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        //帖子实体
        QuestionDTO questionDTO=questionService.getById(id,user==null?-1:user.getId());

        //类似主题帖子推送
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        //评论or回复实体
        List<CommentDTO> comments=commentService.listByTargetId(id, CommentTypeEnum.QUESTION,user==null?-1:user.getId());

        //增加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
