package com.luna.post.service.impl;

import com.luna.common.dto.constant.ResultCode;
import com.luna.post.config.LoginInterceptor;
import com.luna.post.dto.CommentDTO;
import com.luna.post.entity.Register;
import com.luna.post.entity.User;
import com.luna.post.entity.UserException;
import com.luna.post.mapper.CommentMapper;
import com.luna.post.mapper.PostMapper;
import com.luna.post.mapper.RegisterMapper;
import com.luna.post.mapper.UserMapper;
import com.luna.post.service.CommentService;
import com.luna.post.entity.Comment;

import com.luna.post.utils.DO2DTOUtil;
import com.luna.redis.util.RedisHashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: luna
 * @CreateTime: 2021-05-27 17:20:27
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper  commentMapper;

    @Resource
    private PostMapper     postMapper;

    @Resource
    private RegisterMapper registerMapper;

    @Resource
    private UserMapper     userMapper;

    @Resource
    private RedisHashUtil  redisHashUtil;

    @Override
    public Comment getById(Long id) {
        return commentMapper.getById(id);
    }

    @Override
    public Comment getByEntity(Comment comment) {
        return commentMapper.getByEntity(comment);
    }

    @Override
    public List<CommentDTO> listByEntity(Comment comment) {
        List<Comment> comments = commentMapper.listByEntity(comment);
        List<CommentDTO> collect = comments.stream().map(tempComment -> {
            // 这条评论的用户名
            User user = userMapper.getById(Long.valueOf(tempComment.getUserId()));
            Register register = registerMapper.getByEntity(new Register(user.getId()));
            return DO2DTOUtil.comment2CommentDTO(tempComment, user.getName(), user.getCreateTime(),
                register.getPhoto());
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public PageInfo<Comment> listPageByEntity(int page, int pageSize, Comment comment) {
        PageHelper.startPage(page, pageSize);
        List<Comment> list = commentMapper.listByEntity(comment);
        return new PageInfo<Comment>(list);
    }

    @Override
    public PageInfo<Comment> listPage(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Comment> list = commentMapper.listByEntity(new Comment());
        return new PageInfo<Comment>(list);
    }

    @Override
    public List<Comment> listByIds(List<Long> ids) {
        return commentMapper.listByIds(ids);
    }

    @Override
    public int insert(String sessionKey, Comment comment) {
        if (sessionKey == null) {
            throw new UserException(ResultCode.PARAMETER_INVALID, "用户不存在");
        }

        User user = (User)redisHashUtil.get(LoginInterceptor.sessionKey + ":" + sessionKey, sessionKey);

        comment.setUserId(String.valueOf(user.getId()));
        return commentMapper.insert(comment);
    }

    @Override
    public int insertBatch(List<Comment> list) {
        return commentMapper.insertBatch(list);
    }

    @Override
    public int update(Comment comment) {
        return commentMapper.update(comment);
    }

    @Override
    public int updateBatch(List<Comment> list) {
        return commentMapper.updateBatch(list);
    }

    @Override
    public int deleteById(Long id) {
        return commentMapper.deleteById(id);
    }

    @Override
    public int deleteByEntity(Comment comment) {
        return commentMapper.deleteByEntity(comment);
    }

    @Override
    public int deleteByIds(List<Long> list) {
        return commentMapper.deleteByIds(list);
    }

    @Override
    public int countAll() {
        return commentMapper.countAll();
    }

    @Override
    public int countByEntity(Comment comment) {
        return commentMapper.countByEntity(comment);
    }

}
