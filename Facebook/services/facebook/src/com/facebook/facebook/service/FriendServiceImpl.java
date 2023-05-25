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

import com.facebook.facebook.Friend;


/**
 * ServiceImpl object for domain model class Friend.
 *
 * @see Friend
 */
@Service("facebook.FriendService")
@Validated
@EntityService(entityClass = Friend.class, serviceId = "facebook")
public class FriendServiceImpl implements FriendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendServiceImpl.class);


    @Autowired
    @Qualifier("facebook.FriendDao")
    private WMGenericDao<Friend, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<Friend, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public Friend create(Friend friend) {
        LOGGER.debug("Creating a new Friend with information: {}", friend);

        Friend friendCreated = this.wmGenericDao.create(friend);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(friendCreated);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Friend getById(Integer friendId) {
        LOGGER.debug("Finding Friend by id: {}", friendId);
        return this.wmGenericDao.findById(friendId);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Friend findById(Integer friendId) {
        LOGGER.debug("Finding Friend by id: {}", friendId);
        try {
            return this.wmGenericDao.findById(friendId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Friend found with id: {}", friendId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public List<Friend> findByMultipleIds(List<Integer> friendIds, boolean orderedReturn) {
        LOGGER.debug("Finding Friends by ids: {}", friendIds);

        return this.wmGenericDao.findByMultipleIds(friendIds, orderedReturn);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Friend getByUser1IdAndUser2Id(int user1Id, int user2Id) {
        Map<String, Object> user1IdAndUser2IdMap = new HashMap<>();
        user1IdAndUser2IdMap.put("user1Id", user1Id);
        user1IdAndUser2IdMap.put("user2Id", user2Id);

        LOGGER.debug("Finding Friend by unique keys: {}", user1IdAndUser2IdMap);
        return this.wmGenericDao.findByUniqueKey(user1IdAndUser2IdMap);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "facebookTransactionManager")
    @Override
    public Friend update(Friend friend) {
        LOGGER.debug("Updating Friend with information: {}", friend);

        this.wmGenericDao.update(friend);
        this.wmGenericDao.refresh(friend);

        return friend;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public Friend partialUpdate(Integer friendId, Map<String, Object>friendPatch) {
        LOGGER.debug("Partially Updating the Friend with id: {}", friendId);

        Friend friend = getById(friendId);

        try {
            ObjectReader friendReader = this.objectMapper.reader().forType(Friend.class).withValueToUpdate(friend);
            friend = friendReader.readValue(this.objectMapper.writeValueAsString(friendPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", friendPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        friend = update(friend);

        return friend;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public Friend delete(Integer friendId) {
        LOGGER.debug("Deleting Friend with id: {}", friendId);
        Friend deleted = this.wmGenericDao.findById(friendId);
        if (deleted == null) {
            LOGGER.debug("No Friend found with id: {}", friendId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), Friend.class.getSimpleName(), friendId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "facebookTransactionManager")
    @Override
    public void delete(Friend friend) {
        LOGGER.debug("Deleting Friend with {}", friend);
        this.wmGenericDao.delete(friend);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<Friend> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Friends");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager")
    @Override
    public Page<Friend> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Friends");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service facebook for table Friend to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "facebookTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service facebook for table Friend to {} format", options.getExportType());
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