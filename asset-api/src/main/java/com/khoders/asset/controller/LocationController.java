package com.khoders.asset.controller;

import com.khoders.asset.dto.LocationDto;
import com.khoders.asset.exceptions.BadDataException;
import com.khoders.asset.exceptions.InternalErrException;
import com.khoders.asset.mapper.LocationMapper;
import com.khoders.asset.services.LocationService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.entities.Location;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@Tag(name = "Location - Endpoint")
@RequestMapping(ApiEndpoint.LOCATION_ENDPOINT)
public class LocationController {
    @Autowired
    private LocationService locationService;
    @Autowired
    private LocationMapper mapper;

    @PostMapping
    public ResponseEntity<Location> create(@RequestBody LocationDto dto) throws Exception {
        try {
            Location entity = mapper.toEntity(dto);
            Location location = locationService.save(entity);
            if (location == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            return ApiResponse.created(Msg.CREATED, mapper.toDto(location));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Location>> list() {
        List<Location> locationList = locationService.locations();
        List<LocationDto> dtoList = new LinkedList<>();
        locationList.forEach(location -> {
            dtoList.add(mapper.toDto(location));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<Location> findSingle(@PathVariable(value = "locationId") String locationId) {
        try {
            Location location = locationService.findById(locationId);
            if (location == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new LocationDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(location));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
    }

    @PutMapping
    public ResponseEntity<Location> update(@RequestBody LocationDto dto) throws Exception {
        try {
            Location loc = locationService.findById(dto.getId());
            if (loc == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            Location entity = mapper.toEntity(dto);
            Location location = locationService.save(entity);
            if (location == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            return ApiResponse.ok(Msg.UPDATED, mapper.toDto(location));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "locationId") String locationId) {
        try {
            if (locationService.delete(locationId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Location", false);
    }
}
