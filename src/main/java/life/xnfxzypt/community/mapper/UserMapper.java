package life.xnfxzypt.community.mapper;

import life.xnfxzypt.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified})")
    public void insert(User user);

    @Select("select * from user Where token=#{token}")
    User findByToken(@Param("token") String token);
}
