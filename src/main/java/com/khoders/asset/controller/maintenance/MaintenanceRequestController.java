package com.khoders.asset.controller.maintenance;

import com.khoders.asset.dto.maintenance.MaintenanceRequestDto;
import com.khoders.asset.entities.maintenance.MaintenanceRequest;
import com.khoders.asset.mapper.maintenance.MaintenanceRequestMapper;
import com.khoders.asset.services.MaintenanceRequestService;
import com.khoders.asset.utils.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/maintenance-request")
public class MaintenanceRequestController {
    @Autowired
    private MaintenanceRequestService requestService;
    @Autowired
    private MaintenanceRequestMapper mapper;

    @PostMapping
    public ResponseEntity<MaintenanceRequest> create(@RequestBody MaintenanceRequestDto dto) {
        try {
            MaintenanceRequest entity = mapper.toEntity(dto);
            MaintenanceRequest request = requestService.save(entity);
            if (request == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.created(Msg.CREATED, mapper.toDto(request));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @PutMapping
    public ResponseEntity<MaintenanceRequest> update(@RequestBody MaintenanceRequestDto dto) {
        try {
            MaintenanceRequest request = requestService.findById(dto.getId());
            if (request == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            MaintenanceRequest entity = mapper.toEntity(dto);
            MaintenanceRequest maintenanceRequest = requestService.save(request);
            if (maintenanceRequest == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.ok(Msg.UPDATED, mapper.toDto(maintenanceRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<MaintenanceRequest>> list() {
        List<MaintenanceRequest> requestList = requestService.maintenanceRequestList();
        List<MaintenanceRequestDto> dtoList = new LinkedList<>();
        requestList.forEach(request -> {
            dtoList.add(mapper.toDto(request));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<MaintenanceRequest> findSingle(@PathVariable(value = "requestId") String requestId) {
        try {
            MaintenanceRequest request = requestService.findById(requestId);
            if (request == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new MaintenanceRequestDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(request));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "requestId") String requestId) {
        try {
            if (requestService.delete(requestId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Maintenance Request", false);
    }
}
