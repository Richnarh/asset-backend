package com.khoders.asset.controller.maintenance;

import com.khoders.asset.dto.maintenance.StartWorkDto;
import com.khoders.asset.services.StartWorkService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Start Work - Endpoint")
@RequestMapping(ApiEndpoint.START_WORK_ENDPOINT)
public class StartWorkController {
    @Autowired
    private StartWorkService workService;

    @PostMapping
    public ResponseEntity<StartWorkDto> save(@RequestBody StartWorkDto dto) throws Exception {
        return ApiResponse.created(workService.toEntity(dto));
    }

    @PutMapping
    public ResponseEntity<StartWorkDto> update(@RequestBody StartWorkDto dto) throws Exception {
        return ApiResponse.ok(Msg.UPDATED, workService.toEntity(dto).getId());
    }

    @GetMapping("/list")
    public ResponseEntity<List<StartWorkDto>> list() throws Exception {
        List<StartWorkDto> dtoList = workService.startWorkList();
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{startWorkId}")
    public ResponseEntity<StartWorkDto> findById(@PathVariable("startWorkId") String startWorkId) throws Exception {
        StartWorkDto dto = workService.findById(startWorkId);
        return ApiResponse.ok(Msg.RECORD_FOUND, dto);
    }

    @DeleteMapping("/{startWorkId}")
    public ResponseEntity<?> delete(@PathVariable("startWorkId") String startWorkId) {
        try {
            if (workService.delete(startWorkId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete StartWork", false);
    }
}
