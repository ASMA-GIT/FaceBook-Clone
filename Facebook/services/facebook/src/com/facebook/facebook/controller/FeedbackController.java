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

import com.facebook.facebook.Feedback;
import com.facebook.facebook.service.FeedbackService;


/**
 * Controller object for domain model class Feedback.
 * @see Feedback
 */
@RestController("facebook.FeedbackController")
@Api(value = "FeedbackController", description = "Exposes APIs to work with Feedback resource.")
@RequestMapping("/facebook/Feedback")
public class FeedbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
	@Qualifier("facebook.FeedbackService")
	private FeedbackService feedbackService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new Feedback instance.")
    @PostMapping
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Feedback createFeedback(@RequestBody Feedback feedbackInstance) {
		LOGGER.debug("Create Feedback with information: {}" , feedbackInstance);

		feedbackInstance = feedbackService.create(feedbackInstance);
		LOGGER.debug("Created Feedback with information: {}" , feedbackInstance);

	    return feedbackInstance;
	}

    @ApiOperation(value = "Returns the Feedback instance associated with the given id.")
    @GetMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Feedback getFeedback(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting Feedback with id: {}" , id);

        Feedback foundFeedback = feedbackService.getById(id);
        LOGGER.debug("Feedback details with id: {}" , foundFeedback);

        return foundFeedback;
    }

    @ApiOperation(value = "Updates the Feedback instance associated with the given id.")
    @PutMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Feedback editFeedback(@PathVariable("id") Integer id, @RequestBody Feedback feedbackInstance) {
        LOGGER.debug("Editing Feedback with id: {}" , feedbackInstance.getId());

        feedbackInstance.setId(id);
        feedbackInstance = feedbackService.update(feedbackInstance);
        LOGGER.debug("Feedback details with id: {}" , feedbackInstance);

        return feedbackInstance;
    }
    
    @ApiOperation(value = "Partially updates the Feedback instance associated with the given id.")
    @PatchMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Feedback patchFeedback(@PathVariable("id") Integer id, @RequestBody @MapTo(Feedback.class) Map<String, Object> feedbackInstancePatch) {
        LOGGER.debug("Partially updating Feedback with id: {}" , id);

        Feedback feedbackInstance = feedbackService.partialUpdate(id, feedbackInstancePatch);
        LOGGER.debug("Feedback details after partial update: {}" , feedbackInstance);

        return feedbackInstance;
    }

    @ApiOperation(value = "Deletes the Feedback instance associated with the given id.")
    @DeleteMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteFeedback(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting Feedback with id: {}" , id);

        Feedback deletedFeedback = feedbackService.delete(id);

        return deletedFeedback != null;
    }

    /**
     * @deprecated Use {@link #findFeedbacks(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Feedback instances matching the search criteria.")
    @PostMapping(value = "/search")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Feedback> searchFeedbacksByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Feedbacks list by query filter:{}", (Object) queryFilters);
        return feedbackService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Feedback instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @GetMapping
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Feedback> findFeedbacks(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Feedbacks list by filter:", query);
        return feedbackService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Feedback instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @PostMapping(value="/filter", consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Feedback> filterFeedbacks(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Feedbacks list by filter", query);
        return feedbackService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @GetMapping(value = "/export/{exportType}", produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportFeedbacks(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return feedbackService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @PostMapping(value = "/export", consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportFeedbacksAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = Feedback.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> feedbackService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of Feedback instances matching the optional query (q) request param.")
	@GetMapping(value = "/count")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countFeedbacks( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting Feedbacks");
		return feedbackService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@PostMapping(value = "/aggregations")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getFeedbackAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return feedbackService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service FeedbackService instance
	 */
	protected void setFeedbackService(FeedbackService service) {
		this.feedbackService = service;
	}

}