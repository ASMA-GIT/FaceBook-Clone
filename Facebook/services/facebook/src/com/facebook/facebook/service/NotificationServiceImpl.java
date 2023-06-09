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

import com.facebook.facebook.Notification;


/**
 * ServiceImpl object for domain model class Notification.
 *
 * @see Notification
 */
@Service("facebook.NotificationService")
@Validated
@EntityService(entityClass = Notification.class, serviceId = "facebook")
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);


    @Autowired
    @Qualifier("facebook.NotificationDao")
    private WMGenericDao<Notification, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<Notification, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public Notification create(Notification notification) {
        LOGGER.debug("Creating a new Notification with information: {}", notification);

        Notification notificationCreated = this.wmGenericDao.create(notification);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(notificationCreated);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Notification getById(Integer notificationId) {
        LOGGER.debug("Finding Notification by id: {}", notificationId);
        return this.wmGenericDao.findById(notificationId);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Notification findById(Integer notificationId) {
        LOGGER.debug("Finding Notification by id: {}", notificationId);
        try {
            return this.wmGenericDao.findById(notificationId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Notification found with id: {}", notificationId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public List<Notification> findByMultipleIds(List<Integer> notificationIds, boolean orderedReturn) {
        LOGGER.debug("Finding Notifications by ids: {}", notificationIds);

        return this.wmGenericDao.findByMultipleIds(notificationIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "facebookTransactionManager")
    @Override
    public Notification update(Notification notification) {
        LOGGER.debug("Updating Notification with information: {}", notification);

        this.wmGenericDao.update(notification);
        this.wmGenericDao.refresh(notification);

        return notification;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public Notification partialUpdate(Integer notificationId, Map<String, Object>notificationPatch) {
        LOGGER.debug("Partially Updating the Notification with id: {}", notificationId);

        Notification notification = getById(notificationId);

        try {
            ObjectReader notificationReader = this.objectMapper.reader().forType(Notification.class).withValueToUpdate(notification);
            notification = notificationReader.readValue(this.objectMapper.writeValueAsString(notificationPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", notificationPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        notification = update(notification);

        return notification;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public Notification delete(Integer notificationId) {
        LOGGER.debug("Deleting Notification with id: {}", notificationId);
        Notification deleted = this.wmGenericDao.findById(notificationId);
        if (deleted == null) {
            LOGGER.debug("No Notification found with id: {}", notificationId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), Notification.class.getSimpleName(), notificationId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public void delete(Notification notification) {
        LOGGER.debug("Deleting Notification with {}", notification);
        this.wmGenericDao.delete(notification);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<Notification> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Notifications");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<Notification> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Notifications");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service facebook for table Notification to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service facebook for table Notification to {} format", options.getExportType());
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