package life.xnfxzypt.community.service;

import life.xnfxzypt.community.dto.QuestionDTO;
import life.xnfxzypt.community.mapper.QuesstionMapper;
import life.xnfxzypt.community.mapper.UserMapper;
import life.xnfxzypt.community.model.Question;
import life.xnfxzypt.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuesstionMapper quesstionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> questions =quesstionMapper.list();
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for(Question question:questions){
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//内置的工具，快速得把对象1属性拷贝到2
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
    //当一个请求需要组装user和question的时候，需要一个中间层去做这件事
}
