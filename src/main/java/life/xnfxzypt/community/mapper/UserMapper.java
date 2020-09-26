package life.xnfxzypt.community.mapper;

import life.xnfxzypt.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified},#{avatar_url})")
    void insert(User user);

    @Select("select * from user Where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user Where id=#{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user Where account_id=#{account_id}")
    User findByAccountId(@Param("account_id") String account_id);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmt_modified},avatar_url=#{avatar_url} Where id=#{id}")
    void update(User user);
}
