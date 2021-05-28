package com.luna.post.utils;

import com.alibaba.fastjson.JSON;
import com.luna.common.date.DateUtil;
import com.luna.post.dto.PostDTO;
import com.luna.post.dto.ShowUserDTO;
import com.luna.post.entity.Post;
import com.luna.post.entity.Register;
import com.luna.post.entity.User;

import java.util.Date;

/**
 * @author luna
 * 2021/5/28
 */
public class DO2DTOUtil {

    public static ShowUserDTO user2ShowUserDTO(User user, Register register) {
        if (user == null) {
            return null;
        }
        ShowUserDTO showUserDTO = new ShowUserDTO();
        showUserDTO.setId(user.getId());
        showUserDTO.setName(user.getName());
        showUserDTO.setPassword(user.getPassword());
        showUserDTO.setLoginTime(
            user.getLoginTime() != null ? DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, user.getLoginTime()) : "");
        showUserDTO.setAdmin("1".equals(user.getAdmin()) ? "管理员" : "客户");
        showUserDTO.setCreateTime(DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, user.getCreateTime()));
        if (register == null) {
            return showUserDTO;
        }
        showUserDTO.setSex("1".equals(register.getSex()) ? "男" : "女");
        showUserDTO.setAge(register.getAge());
        showUserDTO.setEmail(register.getEmail());
        showUserDTO.setPhoto(register.getPhoto());
        return showUserDTO;
    }

    public static PostDTO postDO2PostDTO(Post post, String username, Date lastComment, Integer readCount) {
        if (post == null) {
            return null;
        }

        PostDTO postDTO = new PostDTO();
        postDTO.setUsername(username);
        postDTO.setLastComment(
            lastComment != null ? DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, post.getCreateTime()) : "");
        postDTO.setPostCommentSize(readCount != null ? readCount : 0);
        postDTO.setId(post.getId());
        postDTO.setUserId(post.getUserId());
        postDTO.setPostTitle(post.getPostTitle());
        postDTO.setPostText(post.getPostText());
        postDTO.setPostPageViews(post.getPostPageViews());
        postDTO.setPostAudio(post.getPostAudio());
        postDTO.setCreateTime(
            post.getCreateTime() != null ? DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, post.getCreateTime()) : "");
        postDTO.setModifiedTime(
            post.getModifiedTime() != null ? DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, post.getModifiedTime()) : "");
        postDTO.setVersion(post.getVersion());
        return postDTO;
    }
}