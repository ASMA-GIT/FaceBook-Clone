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

import com.facebook.facebook.HobbieType;
import com.facebook.facebook.Hobbies;
import com.facebook.facebook.service.HobbieTypeService;


/**
 * Controller object for domain model class HobbieType.
 * @see HobbieType
 */
@RestController("facebook.HobbieTypeController")
@Api(value = "HobbieTypeController", description = "Exposes APIs to work with HobbieType resource.")
@RequestMapping("/facebook/HobbieType")
public class HobbieTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HobbieTypeController.class);

    @Autowired
	@Qualifier("facebook.HobbieTypeService")
	private HobbieTypeService hobbieTypeService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new HobbieType instance.")
    @PostMapping
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public HobbieType createHobbieType(@RequestBody HobbieType hobbieType) {
		LOGGER.debug("Create HobbieType with information: {}" , hobbieType);

		hobbieType = hobbieTypeService.create(hobbieType);
		LOGGER.debug("Created HobbieType with information: {}" , hobbieType);

	    return hobbieType;
	}

    @ApiOperation(value = "Returns the HobbieType instance associated with the given id.")
    @GetMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public HobbieType getHobbieType(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting HobbieType with id: {}" , id);

        HobbieType foundHobbieType = hobbieTypeService.getById(id);
        LOGGER.debug("HobbieType details with id: {}" , foundHobbieType);

        return foundHobbieType;
    }

    @ApiOperation(value = "Updates the HobbieType instance associated with the given id.")
    @PutMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public HobbieType editHobbieType(@PathVariable("id") Integer id, @RequestBody HobbieType hobbieType) {
        LOGGER.debug("Editing HobbieType with id: {}" , hobbieType.getId());

        hobbieType.setId(id);
        hobbieType = hobbieTypeService.update(hobbieType);
        LOGGER.debug("HobbieType details with id: {}" , hobbieType);

        return hobbieType;
    }
    
    @ApiOperation(value = "Partially updates the HobbieType instance associated with the given id.")
    @PatchMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public HobbieType patchHobbieType(@PathVariable("id") Integer id, @RequestBody @MapTo(HobbieType.class) Map<String, Object> hobbieTypePatch) {
        LOGGER.debug("Partially updating HobbieType with id: {}" , id);

        HobbieType hobbieType = hobbieTypeService.partialUpdate(id, hobbieTypePatch);
        LOGGER.debug("HobbieType details after partial update: {}" , hobbieType);

        return hobbieType;
    }

    @ApiOperation(value = "Deletes the HobbieType instance associated with the given id.")
    @DeleteMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteHobbieType(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting HobbieType with id: {}" , id);

        HobbieType deletedHobbieType = hobbieTypeService.delete(id);

        return deletedHobbieType != null;
    }

    @GetMapping(value = "/hobbieName/{hobbieName}" )
    @ApiOperation(value = "Returns the matching HobbieType with given unique key values.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public HobbieType getByHobbieName(@PathVariable("hobbieName") String hobbieName) {
        LOGGER.debug("Getting HobbieType with uniques key HobbieName");
        return hobbieTypeService.getByHobbieName(hobbieName);
    }

    /**
     * @deprecated Use {@link #findHobbieTypes(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of HobbieType instances matching the search criteria.")
    @PostMapping(value = "/search")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<HobbieType> searchHobbieTypesByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering HobbieTypes list by query filter:{}", (Object) queryFilters);
        return hobbieTypeService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of HobbieType instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @GetMapping
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<HobbieType> findHobbieTypes(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering HobbieTypes list by filter:", query);
        return hobbieTypeService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of HobbieType instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @PostMapping(value="/filter", consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<HobbieType> filterHobbieTypes(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering HobbieTypes list by filter", query);
        return hobbieTypeService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @GetMapping(value = "/export/{exportType}", produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportHobbieTypes(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return hobbieTypeService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @PostMapping(value = "/export", consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportHobbieTypesAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = HobbieType.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> hobbieTypeService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of HobbieType instances matching the optional query (q) request param.")
	@GetMapping(value = "/count")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countHobbieTypes( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting HobbieTypes");
		return hobbieTypeService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@PostMapping(value = "/aggregations")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getHobbieTypeAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return hobbieTypeService.getAggregatedValues(aggregationInfo, pageable);
    }

    @GetMapping(value="/{id:.+}/hobbieses")
    @ApiOperation(value = "Gets the hobbieses instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Hobbies> findAssociatedHobbieses(@PathVariable("id") Integer id, Pageable pageable) {

        LOGGER.debug("Fetching all associated hobbieses");
        return hobbieTypeService.findAssociatedHobbieses(id, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service HobbieTypeService instance
	 */
	protected void setHobbieTypeService(HobbieTypeService service) {
		this.hobbieTypeService = service;
	}

}