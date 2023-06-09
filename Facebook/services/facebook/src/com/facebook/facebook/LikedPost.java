/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.facebook.facebook;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * LikedPost generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`liked_post`", uniqueConstraints = {
            @UniqueConstraint(name = "`unique_like`", columnNames = {"`post_id`", "`liked_user_id`"})})
public class LikedPost implements Serializable {

    private Integer id;
    private int postId;
    private int likedUserId;
    private Users users;
    private Post post;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`post_id`", nullable = false, scale = 0, precision = 10)
    public int getPostId() {
        return this.postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Column(name = "`liked_user_id`", nullable = false, scale = 0, precision = 10)
    public int getLikedUserId() {
        return this.likedUserId;
    }

    public void setLikedUserId(int likedUserId) {
        this.likedUserId = likedUserId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`liked_user_id`", referencedColumnName = "`id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`liked_post_ibfk_1`"))
    @Fetch(FetchMode.JOIN)
    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        if(users != null) {
            this.likedUserId = users.getId();
        }

        this.users = users;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`post_id`", referencedColumnName = "`id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`liked_post_ibfk_2`"))
    @Fetch(FetchMode.JOIN)
    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        if(post != null) {
            this.postId = post.getId();
        }

        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikedPost)) return false;
        final LikedPost likedPost = (LikedPost) o;
        return Objects.equals(getId(), likedPost.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}