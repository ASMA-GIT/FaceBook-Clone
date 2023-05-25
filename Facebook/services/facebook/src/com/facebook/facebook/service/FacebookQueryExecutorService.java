/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.facebook.facebook.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.export.ExportOptions;

import com.facebook.facebook.models.query.*;

public interface FacebookQueryExecutorService {

    Page<GetLoggedUserPostsResponse> executeGetLoggedUserPosts(String loggedUserId, Pageable pageable);

    void exportGetLoggedUserPosts(String loggedUserId, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

}