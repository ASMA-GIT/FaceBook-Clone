/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.facebook.facebook.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.commons.file.model.Downloadable;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;

import com.facebook.facebook.Comment;

/**
 * Service object for domain model class {@link Comment}.
 */
public interface CommentService {

    /**
     * Creates a new Comment. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Comment if any.
     *
     * @param commentInstance Details of the Comment to be created; value cannot be null.
     * @return The newly created Comment.
     */
    Comment create(@Valid Comment commentInstance);


	/**
     * Returns Comment by given id if exists.
     *
     * @param commentId The id of the Comment to get; value cannot be null.
     * @return Comment associated with the given commentId.
	 * @throws EntityNotFoundException If no Comment is found.
     */
    Comment getById(Integer commentId);

    /**
     * Find and return the Comment by given id if exists, returns null otherwise.
     *
     * @param commentId The id of the Comment to get; value cannot be null.
     * @return Comment associated with the given commentId.
     */
    Comment findById(Integer commentId);

	/**
     * Find and return the list of Comments by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param commentIds The id's of the Comment to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return Comments associated with the given commentIds.
     */
    List<Comment> findByMultipleIds(List<Integer> commentIds, boolean orderedReturn);


    /**
     * Updates the details of an existing Comment. It replaces all fields of the existing Comment with the given commentInstance.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Comment if any.
     *
     * @param commentInstance The details of the Comment to be updated; value cannot be null.
     * @return The updated Comment.
     * @throws EntityNotFoundException if no Comment is found with given input.
     */
    Comment update(@Valid Comment commentInstance);


    /**
     * Partially updates the details of an existing Comment. It updates only the
     * fields of the existing Comment which are passed in the commentInstancePatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Comment if any.
     *
     * @param commentId The id of the Comment to be deleted; value cannot be null.
     * @param commentInstancePatch The partial data of Comment which is supposed to be updated; value cannot be null.
     * @return The updated Comment.
     * @throws EntityNotFoundException if no Comment is found with given input.
     */
    Comment partialUpdate(Integer commentId, Map<String, Object> commentInstancePatch);

    /**
     * Deletes an existing Comment with the given id.
     *
     * @param commentId The id of the Comment to be deleted; value cannot be null.
     * @return The deleted Comment.
     * @throws EntityNotFoundException if no Comment found with the given id.
     */
    Comment delete(Integer commentId);

    /**
     * Deletes an existing Comment with the given object.
     *
     * @param commentInstance The instance of the Comment to be deleted; value cannot be null.
     */
    void delete(Comment commentInstance);

    /**
     * Find all Comments matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Comments.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<Comment> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all Comments matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Comments.
     *
     * @see Pageable
     * @see Page
     */
    Page<Comment> findAll(String query, Pageable pageable);

    /**
     * Exports all Comments matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
     */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

    /**
     * Exports all Comments matching the given input query to the given exportType format.
     *
     * @param options The export options provided by the user; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @param outputStream The output stream of the file for the exported data to be written to.
     *
     * @see DataExportOptions
     * @see Pageable
     * @see OutputStream
     */
    void export(DataExportOptions options, Pageable pageable, OutputStream outputStream);

    /**
     * Retrieve the count of the Comments in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the Comment.
     */
    long count(String query);

    /**
     * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return Paginated data with included fields.
     *
     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
    Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);


}