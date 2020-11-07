package life.xnfxzypt.community.mapper;

import life.xnfxzypt.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}