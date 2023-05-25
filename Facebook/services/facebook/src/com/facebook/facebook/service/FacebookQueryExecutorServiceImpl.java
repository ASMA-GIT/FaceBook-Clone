/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.facebook.facebook.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wavemaker.runtime.data.dao.query.WMQueryExecutor;
import com.wavemaker.runtime.data.export.ExportOptions;
import com.wavemaker.runtime.data.model.QueryProcedureInput;

import com.facebook.facebook.models.query.*;

@Service
public class FacebookQueryExecutorServiceImpl implements FacebookQueryExecutorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookQueryExecutorServiceImpl.class);

    @Autowired
    @Qualifier("facebookWMQueryExecutor")
    private WMQueryExecutor queryExecutor;

    @Transactional(value = "facebookTransactionManager", readOnly = true)
    @Override
    public Page<GetLoggedUserPostsResponse> executeGetLoggedUserPosts(String loggedUserId, Pageable pageable) {
        Map<String, Object> params = new HashMap<>(1);

        params.put("logged_user_id", loggedUserId);

        return queryExecutor.executeNamedQuery("getLoggedUserPosts", params, GetLoggedUserPostsResponse.class, pageable);
    }

    @Transactional(value = "facebookTransactionManager", timeout = 300, readOnly = true)
    @Override
    public void exportGetLoggedUserPosts(String loggedUserId, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream) {
        Map<String, Object> params = new HashMap<>(1);

        params.put("logged_user_id", loggedUserId);

        QueryProcedureInput<GetLoggedUserPostsResponse> queryInput = new QueryProcedureInput<>("getLoggedUserPosts", params, GetLoggedUserPostsResponse.class);

        queryExecutor.exportNamedQueryData(queryInput, exportOptions, pageable, outputStream);
    }

}