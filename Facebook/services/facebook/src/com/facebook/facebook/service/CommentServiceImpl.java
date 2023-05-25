/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.facebook.facebook.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.IOException;
import java.io.OutputStream;
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

import com.facebook.facebook.Comment;


/**
 * ServiceImpl object for domain model class Comment.
 *
 * @see Comment
 */
@Service("facebook.CommentService")
@Validated
@EntityService(entityClass = Comment.class, serviceId = "facebook")
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);


    @Autowired
    @Qualifier("facebook.CommentDao")
    private WMGenericDao<Comment, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<Comment, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public Comment create(Comment commentInstance) {
        LOGGER.debug("Creating a new Comment with information: {}", commentInstance);

        Comment commentInstanceCreated = this.wmGenericDao.create(commentInstance);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(commentInstanceCreated);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Comment getById(Integer commentId) {
        LOGGER.debug("Finding Comment by id: {}", commentId);
        return this.wmGenericDao.findById(commentId);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Comment findById(Integer commentId) {
        LOGGER.debug("Finding Comment by id: {}", commentId);
        try {
            return this.wmGenericDao.findById(commentId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Comment found with id: {}", commentId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public List<Comment> findByMultipleIds(List<Integer> commentIds, boolean orderedReturn) {
        LOGGER.debug("Finding Comments by ids: {}", commentIds);

        return this.wmGenericDao.findByMultipleIds(commentIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "facebookTransactionManager")
    @Override
    public Comment update(Comment commentInstance) {
        LOGGER.debug("Updating Comment with information: {}", commentInstance);

        this.wmGenericDao.update(commentInstance);
        this.wmGenericDao.refresh(commentInstance);

        return commentInstance;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public Comment partialUpdate(Integer commentId, Map<String, Object>commentInstancePatch) {
        LOGGER.debug("Partially Updating the Comment with id: {}", commentId);

        Comment commentInstance = getById(commentId);

        try {
            ObjectReader commentInstanceReader = this.objectMapper.reader().forType(Comment.class).withValueToUpdate(commentInstance);
            commentInstance = commentInstanceReader.readValue(this.objectMapper.writeValueAsString(commentInstancePatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", commentInstancePatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        commentInstance = update(commentInstance);

        return commentInstance;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public Comment delete(Integer commentId) {
        LOGGER.debug("Deleting Comment with id: {}", commentId);
        Comment deleted = this.wmGenericDao.findById(commentId);
        if (deleted == null) {
            LOGGER.debug("No Comment found with id: {}", commentId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), Comment.class.getSimpleName(), commentId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public void delete(Comment commentInstance) {
        LOGGER.debug("Deleting Comment with {}", commentInstance);
        this.wmGenericDao.delete(commentInstance);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<Comment> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Comments");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<Comment> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Comments");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service facebook for table Comment to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service facebook for table Comment to {} format", options.getExportType());
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