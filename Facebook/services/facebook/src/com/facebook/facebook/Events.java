/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.facebook.facebook;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.sql.Date;
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
 * Events generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`events`")
public class Events implements Serializable {

    private Integer id;
    private String image;
    private int userId;
    private String eventTitle;
    private String content;
    private Date startDate;
    private Date endDate;
    private Timestamp createdAt;
    private Users users;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`image`", nullable = true, length = 2048)
    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "`user_id`", nullable = false, scale = 0, precision = 10)
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "`event_title`", nullable = false, length = 30)
    public String getEventTitle() {
        return this.eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    @Column(name = "`content`", nullable = true, length = 80)
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "`start_date`", nullable = false)
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "`end_date`", nullable = true)
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "`created_at`", nullable = false)
    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`user_id`", referencedColumnName = "`id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`events_ibfk_1`"))
    @Fetch(FetchMode.JOIN)
    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        if(users != null) {
            this.userId = users.getId();
        }

        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Events)) return false;
        final Events events = (Events) o;
        return Objects.equals(getId(), events.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}