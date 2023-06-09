/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.facebook.facebook.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.wavemaker.commons.InvalidInputException;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.commons.file.model.Downloadable;
import com.wavemaker.runtime.data.annotations.EntityService;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;

import com.facebook.facebook.LikedPost;


/**
 * ServiceImpl object for domain model class LikedPost.
 *
 * @see LikedPost
 */
@Service("facebook.LikedPostService")
@Validated
@EntityService(entityClass = LikedPost.class, serviceId = "facebook")
public class LikedPostServiceImpl implements LikedPostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LikedPostServiceImpl.class);


    @Autowired
    @Qualifier("facebook.LikedPostDao")
    private WMGenericDao<LikedPost, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<LikedPost, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public LikedPost create(LikedPost likedPost) {
        LOGGER.debug("Creating a new LikedPost with information: {}", likedPost);

        LikedPost likedPostCreated = this.wmGenericDao.create(likedPost);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(likedPostCreated);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public LikedPost getById(Integer likedpostId) {
        LOGGER.debug("Finding LikedPost by id: {}", likedpostId);
        return this.wmGenericDao.findById(likedpostId);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public LikedPost findById(Integer likedpostId) {
        LOGGER.debug("Finding LikedPost by id: {}", likedpostId);
        try {
            return this.wmGenericDao.findById(likedpostId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No LikedPost found with id: {}", likedpostId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public List<LikedPost> findByMultipleIds(List<Integer> likedpostIds, boolean orderedReturn) {
        LOGGER.debug("Finding LikedPosts by ids: {}", likedpostIds);

        return this.wmGenericDao.findByMultipleIds(likedpostIds, orderedReturn);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public LikedPost getByPostIdAndLikedUserId(int postId, int likedUserId) {
        Map<String, Object> postIdAndLikedUserIdMap = new HashMap<>();
        postIdAndLikedUserIdMap.put("postId", postId);
        postIdAndLikedUserIdMap.put("likedUserId", likedUserId);

        LOGGER.debug("Finding LikedPost by unique keys: {}", postIdAndLikedUserIdMap);
        return this.wmGenericDao.findByUniqueKey(postIdAndLikedUserIdMap);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "facebookTransactionManager")
    @Override
    public LikedPost update(LikedPost likedPost) {
        LOGGER.debug("Updating LikedPost with information: {}", likedPost);

        this.wmGenericDao.update(likedPost);
        this.wmGenericDao.refresh(likedPost);

        return likedPost;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public LikedPost partialUpdate(Integer likedpostId, Map<String, Object>likedPostPatch) {
        LOGGER.debug("Partially Updating the LikedPost with id: {}", likedpostId);

        LikedPost likedPost = getById(likedpostId);

        try {
            ObjectReader likedPostReader = this.objectMapper.reader().forType(LikedPost.class).withValueToUpdate(likedPost);
            likedPost = likedPostReader.readValue(this.objectMapper.writeValueAsString(likedPostPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", likedPostPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        likedPost = update(likedPost);

        return likedPost;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public LikedPost delete(Integer likedpostId) {
        LOGGER.debug("Deleting LikedPost with id: {}", likedpostId);
        LikedPost deleted = this.wmGenericDao.findById(likedpostId);
        if (deleted == null) {
            LOGGER.debug("No LikedPost found with id: {}", likedpostId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), LikedPost.class.getSimpleName(), likedpostId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public void delete(LikedPost likedPost) {
        LOGGER.debug("Deleting LikedPost with {}", likedPost);
        this.wmGenericDao.delete(likedPost);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<LikedPost> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all LikedPosts");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<LikedPost> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all LikedPosts");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service facebook for table LikedPost to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service facebook for table LikedPost to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}