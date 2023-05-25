/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.facebook.facebook.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.commons.wrapper.StringWrapper;
import com.wavemaker.runtime.commons.file.manager.ExportedFileManager;
import com.wavemaker.runtime.commons.file.model.Downloadable;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.tools.api.core.annotations.MapTo;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.facebook.facebook.SavedPost;
import com.facebook.facebook.service.SavedPostService;


/**
 * Controller object for domain model class SavedPost.
 * @see SavedPost
 */
@RestController("facebook.SavedPostController")
@Api(value = "SavedPostController", description = "Exposes APIs to work with SavedPost resource.")
@RequestMapping("/facebook/SavedPost")
public class SavedPostController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavedPostController.class);

    @Autowired
	@Qualifier("facebook.SavedPostService")
	private SavedPostService savedPostService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new SavedPost instance.")
    @PostMapping
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public SavedPost createSavedPost(@RequestBody SavedPost savedPost) {
		LOGGER.debug("Create SavedPost with information: {}" , savedPost);

		savedPost = savedPostService.create(savedPost);
		LOGGER.debug("Created SavedPost with information: {}" , savedPost);

	    return savedPost;
	}

    @ApiOperation(value = "Returns the SavedPost instance associated with the given id.")
    @GetMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public SavedPost getSavedPost(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting SavedPost with id: {}" , id);

        SavedPost foundSavedPost = savedPostService.getById(id);
        LOGGER.debug("SavedPost details with id: {}" , foundSavedPost);

        return foundSavedPost;
    }

    @ApiOperation(value = "Updates the SavedPost instance associated with the given id.")
    @PutMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public SavedPost editSavedPost(@PathVariable("id") Integer id, @RequestBody SavedPost savedPost) {
        LOGGER.debug("Editing SavedPost with id: {}" , savedPost.getId());

        savedPost.setId(id);
        savedPost = savedPostService.update(savedPost);
        LOGGER.debug("SavedPost details with id: {}" , savedPost);

        return savedPost;
    }
    
    @ApiOperation(value = "Partially updates the SavedPost instance associated with the given id.")
    @PatchMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public SavedPost patchSavedPost(@PathVariable("id") Integer id, @RequestBody @MapTo(SavedPost.class) Map<String, Object> savedPostPatch) {
        LOGGER.debug("Partially updating SavedPost with id: {}" , id);

        SavedPost savedPost = savedPostService.partialUpdate(id, savedPostPatch);
        LOGGER.debug("SavedPost details after partial update: {}" , savedPost);

        return savedPost;
    }

    @ApiOperation(value = "Deletes the SavedPost instance associated with the given id.")
    @DeleteMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteSavedPost(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting SavedPost with id: {}" , id);

        SavedPost deletedSavedPost = savedPostService.delete(id);

        return deletedSavedPost != null;
    }

    @GetMapping(value = "/postId-savedUserId" )
    @ApiOperation(value = "Returns the matching SavedPost with given unique key values.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public SavedPost getByPostIdAndSavedUserId(@RequestParam("postId") Integer postId, @RequestParam("savedUserId") Integer savedUserId) {
        LOGGER.debug("Getting SavedPost with uniques key PostIdAndSavedUserId");
        return savedPostService.getByPostIdAndSavedUserId(postId, savedUserId);
    }

    /**
     * @deprecated Use {@link #findSavedPosts(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of SavedPost instances matching the search criteria.")
    @PostMapping(value = "/search")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<SavedPost> searchSavedPostsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering SavedPosts list by query filter:{}", (Object) queryFilters);
        return savedPostService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of SavedPost instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @GetMapping
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<SavedPost> findSavedPosts(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering SavedPosts list by filter:", query);
        return savedPostService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of SavedPost instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @PostMapping(value="/filter", consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<SavedPost> filterSavedPosts(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering SavedPosts list by filter", query);
        return savedPostService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @GetMapping(value = "/export/{exportType}", produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportSavedPosts(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return savedPostService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @PostMapping(value = "/export", consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportSavedPostsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = SavedPost.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> savedPostService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of SavedPost instances matching the optional query (q) request param.")
	@GetMapping(value = "/count")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countSavedPosts( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting SavedPosts");
		return savedPostService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@PostMapping(value = "/aggregations")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getSavedPostAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return savedPostService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service SavedPostService instance
	 */
	protected void setSavedPostService(SavedPostService service) {
		this.savedPostService = service;
	}

}