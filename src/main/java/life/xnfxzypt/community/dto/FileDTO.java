package life.xnfxzypt.community.dto;

/**
 * Created by codedrinker on 2019/6/26.
 */

public class FileDTO {

    private Integer success;
    private String message;
    private String url;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
