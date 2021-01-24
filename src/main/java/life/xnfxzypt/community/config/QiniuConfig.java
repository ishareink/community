package life.xnfxzypt.community.config;


import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;
@Component
public class QiniuConfig {
    //公钥
    private final String accessKey="NshmuSQbn4pCpRRmAO4jY6KXf67cMBMYlcgr5JYi";
    //私钥
    private final String secretKey="8xgiyuEc6KFUGagJ7daCtfAVzOMQSScq5UknD0dH";
    //七牛域名
    public final String url="http://qiniusave.ishare.ink/";
    //空间名称
    public final String bucket = "isharecode";

    public final Configuration cfg = new Configuration(Region.region2());


    public final Auth auth = Auth.create(accessKey, secretKey);
}
