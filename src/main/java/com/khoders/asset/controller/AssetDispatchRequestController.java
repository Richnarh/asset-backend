package com.khoders.asset.controller;

import com.khoders.asset.dto.AssetDispatchRequestDto;
import com.khoders.asset.dto.VendorDto;
import com.khoders.asset.entities.AssetDispatchRequest;
import com.khoders.asset.entities.Vendor;
import com.khoders.asset.mapper.AssetDispatchRequestMapper;
import com.khoders.asset.mapper.VendorMapper;
import com.khoders.asset.services.AssetDispatchRequestService;
import com.khoders.asset.services.VendorService;
import com.khoders.asset.utils.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/dispatch-request")
public class AssetDispatchRequestController {
    @Autowired
    private AssetDispatchRequestService requestService;
    @Autowired
    private AssetDispatchRequestMapper mapper;

    @PostMapping
    public ResponseEntity<AssetDispatchRequest> create(@RequestBody AssetDispatchRequestDto dto) {
        try {
            AssetDispatchRequest entity = mapper.toEntity(dto);
            AssetDispatchRequest dispatchRequest = requestService.save(entity);
            if (dispatchRequest == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.created(Msg.CREATED, mapper.toDto(dispatchRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<AssetDispatchRequest>> list() {
        List<AssetDispatchRequest> dispatchRequestList = requestService.dispatchRequestList();
        List<AssetDispatchRequestDto> dtoList = new LinkedList<>();
        dispatchRequestList.forEach(request -> {
            dtoList.add(mapper.toDto(request));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{dispatchRequestId}")
    public ResponseEntity<Vendor> findSingle(@PathVariable(value = "dispatchRequestId") String dispatchRequestId) {
        try {
            AssetDispatchRequest dispatchRequest = requestService.findById(dispatchRequestId);
            if (dispatchRequest == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new AssetDispatchRequestDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(dispatchRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
    }

    @PutMapping
    public ResponseEntity<AssetDispatchRequest> update(@RequestBody AssetDispatchRequestDto dto) {
        try {
            AssetDispatchRequest dispatchRequest = requestService.findById(dto.getId());
            if (dispatchRequest == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            AssetDispatchRequest entity = mapper.toEntity(dto);
            AssetDispatchRequest request = requestService.save(entity);
            if (request == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.ok(Msg.UPDATED, mapper.toDto(request));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @DeleteMapping("/{dispatchRequestId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "dispatchRequestId") String dispatchRequestId) {
        try {
            if (requestService.delete(dispatchRequestId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Asset Dispatch Request", false);
    }
}