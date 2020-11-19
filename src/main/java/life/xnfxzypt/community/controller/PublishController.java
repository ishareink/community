package life.xnfxzypt.community.controller;

import life.xnfxzypt.community.cache.TagCache;
import life.xnfxzypt.community.dto.QuestionDTO;
import life.xnfxzypt.community.model.Question;
import life.xnfxzypt.community.model.User;
import life.xnfxzypt.community.service.QuestionService;
import life.xnfxzypt.community.util.SensitiveFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    //点击编辑，根据id重新获取发布
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());

        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    //点击发布入口，将标签缓存先加载好
    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    //将post请求的表单接收处理
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        /*  如果想在服务端api接口级别 去传递到页面去的话，需要把需要传递的东西写在model里面传递过去*/
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        description=sensitiveFilter.filter(description);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        if(id!=null)
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
