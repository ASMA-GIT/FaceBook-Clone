/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.facebook.facebook;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.sql.Timestamp;
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
 * Notification generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`notification`")
public class Notification implements Serializable {

    private Integer id;
    private int senderId;
    private int receiverId;
    private Integer notifyTypeId;
    private boolean isRead;
    private Timestamp notifyTime;
    private NotifyType notifyType;
    private Users usersBySenderId;
    private Users usersByReceiverId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`sender_id`", nullable = false, scale = 0, precision = 10)
    public int getSenderId() {
        return this.senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    @Column(name = "`receiver_id`", nullable = false, scale = 0, precision = 10)
    public int getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    @Column(name = "`notify_type_id`", nullable = true, scale = 0, precision = 10)
    public Integer getNotifyTypeId() {
        return this.notifyTypeId;
    }

    public void setNotifyTypeId(Integer notifyTypeId) {
        this.notifyTypeId = notifyTypeId;
    }

    @Column(name = "`is_read`", nullable = false)
    public boolean isIsRead() {
        return this.isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    @Column(name = "`notify_time`", nullable = false)
    public Timestamp getNotifyTime() {
        return this.notifyTime;
    }

    public void setNotifyTime(Timestamp notifyTime) {
        this.notifyTime = notifyTime;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`notify_type_id`", referencedColumnName = "`id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`notification_ibfk_1`"))
    @Fetch(FetchMode.JOIN)
    public NotifyType getNotifyType() {
        return this.notifyType;
    }

    public void setNotifyType(NotifyType notifyType) {
        if(notifyType != null) {
            this.notifyTypeId = notifyType.getId();
        }

        this.notifyType = notifyType;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`sender_id`", referencedColumnName = "`id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`notification_ibfk_2`"))
    @Fetch(FetchMode.JOIN)
    public Users getUsersBySenderId() {
        return this.usersBySenderId;
    }

    public void setUsersBySenderId(Users usersBySenderId) {
        if(usersBySenderId != null) {
            this.senderId = usersBySenderId.getId();
        }

        this.usersBySenderId = usersBySenderId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`receiver_id`", referencedColumnName = "`id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`notification_ibfk_3`"))
    @Fetch(FetchMode.JOIN)
    public Users getUsersByReceiverId() {
        return this.usersByReceiverId;
    }

    public void setUsersByReceiverId(Users usersByReceiverId) {
        if(usersByReceiverId != null) {
            this.receiverId = usersByReceiverId.getId();
        }

        this.usersByReceiverId = usersByReceiverId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        final Notification notification = (Notification) o;
        return Objects.equals(getId(), notification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}