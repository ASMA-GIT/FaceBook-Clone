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

import com.facebook.facebook.Feedback;


/**
 * ServiceImpl object for domain model class Feedback.
 *
 * @see Feedback
 */
@Service("facebook.FeedbackService")
@Validated
@EntityService(entityClass = Feedback.class, serviceId = "facebook")
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackServiceImpl.class);


    @Autowired
    @Qualifier("facebook.FeedbackDao")
    private WMGenericDao<Feedback, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<Feedback, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public Feedback create(Feedback feedbackInstance) {
        LOGGER.debug("Creating a new Feedback with information: {}", feedbackInstance);

        Feedback feedbackInstanceCreated = this.wmGenericDao.create(feedbackInstance);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(feedbackInstanceCreated);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Feedback getById(Integer feedbackId) {
        LOGGER.debug("Finding Feedback by id: {}", feedbackId);
        return this.wmGenericDao.findById(feedbackId);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Feedback findById(Integer feedbackId) {
        LOGGER.debug("Finding Feedback by id: {}", feedbackId);
        try {
            return this.wmGenericDao.findById(feedbackId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Feedback found with id: {}", feedbackId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public List<Feedback> findByMultipleIds(List<Integer> feedbackIds, boolean orderedReturn) {
        LOGGER.debug("Finding Feedbacks by ids: {}", feedbackIds);

        return this.wmGenericDao.findByMultipleIds(feedbackIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "facebookTransactionManager")
    @Override
    public Feedback update(Feedback feedbackInstance) {
        LOGGER.debug("Updating Feedback with information: {}", feedbackInstance);

        this.wmGenericDao.update(feedbackInstance);
        this.wmGenericDao.refresh(feedbackInstance);

        return feedbackInstance;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public Feedback partialUpdate(Integer feedbackId, Map<String, Object>feedbackInstancePatch) {
        LOGGER.debug("Partially Updating the Feedback with id: {}", feedbackId);

        Feedback feedbackInstance = getById(feedbackId);

        try {
            ObjectReader feedbackInstanceReader = this.objectMapper.reader().forType(Feedback.class).withValueToUpdate(feedbackInstance);
            feedbackInstance = feedbackInstanceReader.readValue(this.objectMapper.writeValueAsString(feedbackInstancePatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", feedbackInstancePatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        feedbackInstance = update(feedbackInstance);

        return feedbackInstance;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public Feedback delete(Integer feedbackId) {
        LOGGER.debug("Deleting Feedback with id: {}", feedbackId);
        Feedback deleted = this.wmGenericDao.findById(feedbackId);
        if (deleted == null) {
            LOGGER.debug("No Feedback found with id: {}", feedbackId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), Feedback.class.getSimpleName(), feedbackId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public void delete(Feedback feedbackInstance) {
        LOGGER.debug("Deleting Feedback with {}", feedbackInstance);
        this.wmGenericDao.delete(feedbackInstance);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<Feedback> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Feedbacks");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<Feedback> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Feedbacks");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service facebook for table Feedback to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service facebook for table Feedback to {} format", options.getExportType());
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