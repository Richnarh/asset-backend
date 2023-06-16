package com.khoders.asset.controller.maintenance;

import com.khoders.asset.dto.maintenance.MaintenanceTaskDto;
import com.khoders.asset.services.MaintenanceTaskService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Maintenance Task - Endpoint")
@RequestMapping(ApiEndpoint.MAINTENANCE_TASK_ENDPOINT)
public class MaintenanceTaskController {
    @Autowired
    private MaintenanceTaskService taskService;

    @PostMapping
    public ResponseEntity<MaintenanceTaskDto> save(@RequestBody MaintenanceTaskDto dto) throws Exception {
        return ApiResponse.created(Msg.CREATED, taskService.toEntity(dto));
    }

    @PutMapping
    public ResponseEntity<MaintenanceTaskDto> update(@RequestBody MaintenanceTaskDto dto) throws Exception {
        return ApiResponse.ok(Msg.UPDATED, taskService.toEntity(dto).getId());
    }

    @GetMapping("/list")
    public ResponseEntity<List<MaintenanceTaskDto>> list() {
        List<MaintenanceTaskDto> dtoList = taskService.maintenanceTaskList();
        if (dtoList.isEmpty()) {
            ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{maintenanceTaskId}")
    public ResponseEntity<MaintenanceTaskDto> findById(@PathVariable("maintenanceTaskId") String maintenanceTaskId) {
        MaintenanceTaskDto dto = taskService.findById(maintenanceTaskId);
        if (dto == null) {
            ApiResponse.ok(Msg.RECORD_NOT_FOUND, null);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dto);
    }

    @DeleteMapping("/{maintenanceTaskId}")
    public ResponseEntity<?> delete(@PathVariable("maintenanceTaskId") String maintenanceTaskId) {
        try {
            if (taskService.delete(maintenanceTaskId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Occurrence", false);
    }
}
