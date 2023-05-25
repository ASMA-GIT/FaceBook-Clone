/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.facebook.facebook;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Gender generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`Gender`")
public class Gender implements Serializable {

    private Integer id;
    private String genderName;

    @Id
    @Column(name = "`id`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`gender_name`", nullable = false, length = 10)
    public String getGenderName() {
        return this.genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gender)) return false;
        final Gender gender = (Gender) o;
        return Objects.equals(getId(), gender.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}