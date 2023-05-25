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

import com.facebook.facebook.Notification;
import com.facebook.facebook.NotifyType;


/**
 * ServiceImpl object for domain model class NotifyType.
 *
 * @see NotifyType
 */
@Service("facebook.NotifyTypeService")
@Validated
@EntityService(entityClass = NotifyType.class, serviceId = "facebook")
public class NotifyTypeServiceImpl implements NotifyTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyTypeServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("facebook.NotificationService")
    private NotificationService notificationService;

    @Autowired
    @Qualifier("facebook.NotifyTypeDao")
    private WMGenericDao<NotifyType, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<NotifyType, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public NotifyType create(NotifyType notifyType) {
        LOGGER.debug("Creating a new NotifyType with information: {}", notifyType);

        NotifyType notifyTypeCreated = this.wmGenericDao.create(notifyType);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(notifyTypeCreated);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public NotifyType getById(Integer notifytypeId) {
        LOGGER.debug("Finding NotifyType by id: {}", notifytypeId);
        return this.wmGenericDao.findById(notifytypeId);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public NotifyType findById(Integer notifytypeId) {
        LOGGER.debug("Finding NotifyType by id: {}", notifytypeId);
        try {
            return this.wmGenericDao.findById(notifytypeId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No NotifyType found with id: {}", notifytypeId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public List<NotifyType> findByMultipleIds(List<Integer> notifytypeIds, boolean orderedReturn) {
        LOGGER.debug("Finding NotifyTypes by ids: {}", notifytypeIds);

        return this.wmGenericDao.findByMultipleIds(notifytypeIds, orderedReturn);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public NotifyType getByNotiType(String notiType) {
        Map<String, Object> notiTypeMap = new HashMap<>();
        notiTypeMap.put("notiType", notiType);

        LOGGER.debug("Finding NotifyType by unique keys: {}", notiTypeMap);
        return this.wmGenericDao.findByUniqueKey(notiTypeMap);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "facebookTransactionManager")
    @Override
    public NotifyType update(NotifyType notifyType) {
        LOGGER.debug("Updating NotifyType with information: {}", notifyType);

        this.wmGenericDao.update(notifyType);
        this.wmGenericDao.refresh(notifyType);

        return notifyType;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public NotifyType partialUpdate(Integer notifytypeId, Map<String, Object>notifyTypePatch) {
        LOGGER.debug("Partially Updating the NotifyType with id: {}", notifytypeId);

        NotifyType notifyType = getById(notifytypeId);

        try {
            ObjectReader notifyTypeReader = this.objectMapper.reader().forType(NotifyType.class).withValueToUpdate(notifyType);
            notifyType = notifyTypeReader.readValue(this.objectMapper.writeValueAsString(notifyTypePatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", notifyTypePatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        notifyType = update(notifyType);

        return notifyType;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public NotifyType delete(Integer notifytypeId) {
        LOGGER.debug("Deleting NotifyType with id: {}", notifytypeId);
        NotifyType deleted = this.wmGenericDao.findById(notifytypeId);
        if (deleted == null) {
            LOGGER.debug("No NotifyType found with id: {}", notifytypeId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), NotifyType.class.getSimpleName(), notifytypeId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public void delete(NotifyType notifyType) {
        LOGGER.debug("Deleting NotifyType with {}", notifyType);
        this.wmGenericDao.delete(notifyType);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<NotifyType> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all NotifyTypes");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<NotifyType> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all NotifyTypes");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service facebook for table NotifyType to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service facebook for table NotifyType to {} format", options.getExportType());
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
    public Page<Notification> findAssociatedNotifications(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated notifications");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("notifyType.id = '" + id + "'");

        return notificationService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service NotificationService instance
     */
    protected void setNotificationService(NotificationService service) {
        this.notificationService = service;
    }

}