package life.xnfxzypt.community.controller;

import life.xnfxzypt.community.dto.FileDTO;
import life.xnfxzypt.community.provider.QiniuCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileController {
    @Autowired
    private QiniuCloudProvider qiniuCloudProvider;



    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        FileDTO result = new FileDTO();
        if (file.isEmpty()) {
            result.setSuccess(0);
            result.setMessage("文件为空，请重新上传");
            return result;
        }
        try {
            String url = qiniuCloudProvider.upload(file);
            result.setSuccess(1);
            result.setMessage("文件上传成功");
            result.setUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
