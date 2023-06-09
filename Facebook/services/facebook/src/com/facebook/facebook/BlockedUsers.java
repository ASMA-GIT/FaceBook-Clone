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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * BlockedUsers generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`blocked_users`")
public class BlockedUsers implements Serializable {

    private Integer blockId;
    private int userId;
    private int blockedUserId;
    private Users usersByUserId;
    private Users usersByBlockedUserId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`block_id`", nullable = false, scale = 0, precision = 10)
    public Integer getBlockId() {
        return this.blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    @Column(name = "`user_id`", nullable = false, scale = 0, precision = 10)
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "`blocked_user_id`", nullable = false, scale = 0, precision = 10)
    public int getBlockedUserId() {
        return this.blockedUserId;
    }

    public void setBlockedUserId(int blockedUserId) {
        this.blockedUserId = blockedUserId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`user_id`", referencedColumnName = "`id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`blocked_users_ibfk_1`"))
    @Fetch(FetchMode.JOIN)
    public Users getUsersByUserId() {
        return this.usersByUserId;
    }

    public void setUsersByUserId(Users usersByUserId) {
        if(usersByUserId != null) {
            this.userId = usersByUserId.getId();
        }

        this.usersByUserId = usersByUserId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`blocked_user_id`", referencedColumnName = "`id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`blocked_users_ibfk_2`"))
    @Fetch(FetchMode.JOIN)
    public Users getUsersByBlockedUserId() {
        return this.usersByBlockedUserId;
    }

    public void setUsersByBlockedUserId(Users usersByBlockedUserId) {
        if(usersByBlockedUserId != null) {
            this.blockedUserId = usersByBlockedUserId.getId();
        }

        this.usersByBlockedUserId = usersByBlockedUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockedUsers)) return false;
        final BlockedUsers blockedUsers = (BlockedUsers) o;
        return Objects.equals(getBlockId(), blockedUsers.getBlockId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBlockId());
    }
}