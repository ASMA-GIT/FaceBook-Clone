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
import org.springframework.context.annotation.Lazy;
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

import com.facebook.facebook.Post;
import com.facebook.facebook.PostType;


/**
 * ServiceImpl object for domain model class PostType.
 *
 * @see PostType
 */
@Service("facebook.PostTypeService")
@Validated
@EntityService(entityClass = PostType.class, serviceId = "facebook")
public class PostTypeServiceImpl implements PostTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostTypeServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("facebook.PostService")
    private PostService postService;

    @Autowired
    @Qualifier("facebook.PostTypeDao")
    private WMGenericDao<PostType, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<PostType, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public PostType create(PostType postTypeInstance) {
        LOGGER.debug("Creating a new PostType with information: {}", postTypeInstance);

        PostType postTypeInstanceCreated = this.wmGenericDao.create(postTypeInstance);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(postTypeInstanceCreated);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public PostType getById(Integer posttypeId) {
        LOGGER.debug("Finding PostType by id: {}", posttypeId);
        return this.wmGenericDao.findById(posttypeId);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public PostType findById(Integer posttypeId) {
        LOGGER.debug("Finding PostType by id: {}", posttypeId);
        try {
            return this.wmGenericDao.findById(posttypeId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No PostType found with id: {}", posttypeId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public List<PostType> findByMultipleIds(List<Integer> posttypeIds, boolean orderedReturn) {
        LOGGER.debug("Finding PostTypes by ids: {}", posttypeIds);

        return this.wmGenericDao.findByMultipleIds(posttypeIds, orderedReturn);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public PostType getByPostType(String postType) {
        Map<String, Object> postTypeMap = new HashMap<>();
        postTypeMap.put("postType", postType);

        LOGGER.debug("Finding PostType by unique keys: {}", postTypeMap);
        return this.wmGenericDao.findByUniqueKey(postTypeMap);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "facebookTransactionManager")
    @Override
    public PostType update(PostType postTypeInstance) {
        LOGGER.debug("Updating PostType with information: {}", postTypeInstance);

        this.wmGenericDao.update(postTypeInstance);
        this.wmGenericDao.refresh(postTypeInstance);

        return postTypeInstance;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public PostType partialUpdate(Integer posttypeId, Map<String, Object>postTypeInstancePatch) {
        LOGGER.debug("Partially Updating the PostType with id: {}", posttypeId);

        PostType postTypeInstance = getById(posttypeId);

        try {
            ObjectReader postTypeInstanceReader = this.objectMapper.reader().forType(PostType.class).withValueToUpdate(postTypeInstance);
            postTypeInstance = postTypeInstanceReader.readValue(this.objectMapper.writeValueAsString(postTypeInstancePatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", postTypeInstancePatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        postTypeInstance = update(postTypeInstance);

        return postTypeInstance;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public PostType delete(Integer posttypeId) {
        LOGGER.debug("Deleting PostType with id: {}", posttypeId);
        PostType deleted = this.wmGenericDao.findById(posttypeId);
        if (deleted == null) {
            LOGGER.debug("No PostType found with id: {}", posttypeId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), PostType.class.getSimpleName(), posttypeId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public void delete(PostType postTypeInstance) {
        LOGGER.debug("Deleting PostType with {}", postTypeInstance);
        this.wmGenericDao.delete(postTypeInstance);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<PostType> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all PostTypes");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<PostType> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all PostTypes");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service facebook for table PostType to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service facebook for table PostType to {} format", options.getExportType());
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

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<Post> findAssociatedPosts(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated posts");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("postType.id = '" + id + "'");

        return postService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service PostService instance
     */
    protected void setPostService(PostService service) {
        this.postService = service;
    }

}