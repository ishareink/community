package life.xnfxzypt.community.service;

import life.xnfxzypt.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @author coolsen
 * @version 1.0.1
 * @ClassName LikeService.java
 * @Description 点赞Service
 * @createTime 22/11/2020 4:01 PM
 */

@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param userId     :点赞的人
     * @param entityType
     * @param entityId
     * @Description: 对实体点赞, 对相应的用户加赞
     * @return: void
     * @Date 22/11/2020
     **/
    public void like(Long userId, int entityType, int entityId,Long entityUserId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey= RedisKeyUtil.getEntityLikeKey(entityType,entityId);
                String userLikeKey=RedisKeyUtil.getUserLikeKey(entityUserId);

                boolean isMember=operations.opsForSet().isMember(entityLikeKey,userId);
                operations.multi();
                if(isMember){
                    operations.opsForSet().remove(entityLikeKey,userId);
                    operations.opsForValue().decrement(userLikeKey);
                }else{
                    operations.opsForSet().add(entityLikeKey,userId);
                    operations.opsForValue().increment(userLikeKey);
                }

                return operations.exec();
            }
        });
    }

    /**
     * @param entityType
     * @param entityId
     * @Description: 查询某实体（帖子，评论等）点赞数量
     * @return: long
     * @Date 22/11/2020
     **/
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    /**
     * @param entityType
     * @param entityId
     * @param userId
     * @Description:查询某人对某实体的点赞状态
     * @return: int
     * @Date 22/11/2020
     **/
    public int findEntityLikeStatus(int entityType, int entityId, Long userId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        //此处返回int，是为了进行扩展。比如扩展踩，为止2.等等情况
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }

    /**
     * @param userId
     * @Description: 查询某个用户获得赞，用于在个人主页查看收获了多少赞
     * @return: int
     * @Date 22/11/2020
     **/
    public int findUserLikeCount(Long userId) {
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count == null ? 0 : count.intValue();// count.intValue()数据的整数形式;
    }
}
