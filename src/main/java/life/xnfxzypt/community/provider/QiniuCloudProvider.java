package life.xnfxzypt.community.provider;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import life.xnfxzypt.community.config.QiniuConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QiniuCloudProvider {

    //注入七牛云配置类
    @Autowired
    private QiniuConfig qiNiuConfig;


    /**
     * 上传文件
     * @param multipartFile
     * @throws IOException
     */
    public String upload(MultipartFile multipartFile) throws IOException {
        //判断文件是否存在
        byte[] bytes=multipartFile.getBytes();
        String name=multipartFile.getOriginalFilename();
        int i = name.lastIndexOf(".");
        String key=name.substring(0,i);
        String rUrl=qiNiuConfig.url+key;
        //创建上传manager && upToken
        qiNiuConfig.cfg.useHttpsDomains = false;
        UploadManager uploadManager = new UploadManager(qiNiuConfig.cfg);
        String upToken=qiNiuConfig.auth.uploadToken(qiNiuConfig.bucket);

        try {
            //上传
            Response response = uploadManager.put(bytes, key, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
        }
        return rUrl;


    }
    /**
     * 判断同名图片是否存在
     * @param key
     * @return
     */
    private boolean isImageExist(String key){

        List<FileInfo[]> list=getImageList();
        for (FileInfo[] fileInfos : list) {
            for (FileInfo fileInfo : fileInfos) {
                if (fileInfo.key.equals(key))
                    return false;
            }
        }
        return true;
    }
    /**
     * 获取文件列表
     * @return
     */
    private List<FileInfo[]> getImageList(){
        //创建bucketManage
        BucketManager bucketManager = new BucketManager(qiNiuConfig.auth, qiNiuConfig.cfg);

        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";


        List<FileInfo[]> list=new ArrayList<>();
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(qiNiuConfig.bucket, prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            list.add(items);
        }
        return list;
    }
    /**
     * 获取图片列表
     * @return 图片url和key
     */
    public List<Map<String,String>> getKeyMap(){
        List<Map<String,String>> keys=new ArrayList<>();
        String url=qiNiuConfig.url;

        List<FileInfo[]> imageList = getImageList();
        for (FileInfo[] fileInfos : imageList) {
            for (FileInfo info : fileInfos) {
                Map<String,String> keyMap=new HashMap<>();
                keyMap.put("src",url+info.key);
                keyMap.put("key",info.key);
                keys.add(keyMap);
            }
        }
        return keys;
    }


    /**
     * 删除
     * @param image_name
     * @return
     */
    public boolean deleteKeys(String image_name){
        //获取bucketManage
        BucketManager bucketManager = new BucketManager(qiNiuConfig.auth, qiNiuConfig.cfg);

        try {
            //单次批量请求的文件数量不得超过1000
            String[] keyList = new String[]{
                    image_name
            };
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(qiNiuConfig.bucket, keyList);
            Response response = bucketManager.batch(batchOperations);

            //获取返回json对象
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);

            for (int i = 0; i < keyList.length; i++) {
                BatchStatus status = batchStatusList[i];
                return status.code == 200;
            }
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
            return false;
        }
        return false;
    }

}
