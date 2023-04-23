package com.khoders.asset.controller;

import com.khoders.asset.dto.maintenance.RequestTypeDto;
import com.khoders.asset.exceptions.BadDataException;
import com.khoders.asset.mapper.maintenance.RequestTypeMapper;
import com.khoders.asset.services.RequestTypeService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.entities.maintenance.RequestType;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@Tag(name = "Request Type - Endpoint")
@RequestMapping(ApiEndpoint.REQUEST_TYPE_ENDPOINT)
public class RequestTypeController {
    @Autowired
    private RequestTypeService typeService;
    @Autowired
    private RequestTypeMapper mapper;

    @PostMapping
    public ResponseEntity<RequestTypeDto> create(@RequestBody RequestTypeDto dto) throws Exception {
        try {
            RequestType entity = mapper.toEntity(dto);
            RequestType requestType = typeService.save(entity);
            if (requestType == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            return ApiResponse.created(Msg.CREATED, mapper.toDto(requestType));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InterruptedException(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<RequestTypeDto>> list() {
        List<RequestType> requestTypeList = typeService.requestTypes();
        List<RequestTypeDto> dtoList = new LinkedList<>();
        requestTypeList.forEach(dep -> {
            dtoList.add(mapper.toDto(dep));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{requestTypeId}")
    public ResponseEntity<RequestType> findSingle(@PathVariable(value = "requestTypeId") String requestTypeId) {
        try {
            RequestType requestType = typeService.findById(requestTypeId);
            if (requestType == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new RequestType());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(requestType));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
    }

    @PutMapping
    public ResponseEntity<RequestType> update(@RequestBody RequestTypeDto dto) throws Exception {
        try {
            RequestType requestType = typeService.findById(dto.getId());
            if (requestType == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            RequestType request = typeService.save(requestType);
            if (request == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            return ApiResponse.ok(Msg.UPDATED, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InterruptedException(e.getMessage());
        }
    }

    @DeleteMapping("/{requestTypeId}")
    public ResponseEntity<Object> deleteAssetTransfer(@PathVariable(value = "requestTypeId") String requestTypeId) {
        try {
            if (typeService.delete(requestTypeId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Department", false);
    }
}
