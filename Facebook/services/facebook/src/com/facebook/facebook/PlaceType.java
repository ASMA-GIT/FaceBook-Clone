/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.facebook.facebook;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * PlaceType generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`place_type`", uniqueConstraints = {
            @UniqueConstraint(name = "`city_type`", columnNames = {"`city_type`"})})
public class PlaceType implements Serializable {

    private Integer id;
    private String cityType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`city_type`", nullable = true, length = 30)
    public String getCityType() {
        return this.cityType;
    }

    public void setCityType(String cityType) {
        this.cityType = cityType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceType)) return false;
        final PlaceType placeType = (PlaceType) o;
        return Objects.equals(getId(), placeType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}