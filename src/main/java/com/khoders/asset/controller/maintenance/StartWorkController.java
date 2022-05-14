package com.khoders.asset.controller.maintenance;

import com.khoders.asset.dto.maintenance.OccurrenceDto;
import com.khoders.asset.dto.maintenance.StartWorkDto;
import com.khoders.asset.services.OccurrenceService;
import com.khoders.asset.services.StartWorkService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.spring.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.STARTWORK_ENDPOINT)
public class StartWorkController {
    @Autowired private StartWorkService workService;

    @PostMapping
    public ResponseEntity<StartWorkDto> save(@RequestBody StartWorkDto dto){
        return ApiResponse.created(Msg.CREATED, workService.toEntity(dto));
    }
    @PutMapping
    public ResponseEntity<StartWorkDto> update(@RequestBody StartWorkDto dto){
        return ApiResponse.ok(Msg.UPDATED, workService.toEntity(dto).getId());
    }
    @GetMapping("/list")
    public ResponseEntity<List<StartWorkDto>> list(){
        List<StartWorkDto> dtoList = workService.startWorkList();
        if (dtoList.isEmpty()){
            ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }
    @GetMapping("/{startWorkId}")
    public ResponseEntity<StartWorkDto> findById(@PathVariable("startWorkId") String startWorkId){
        StartWorkDto dto = workService.findById(startWorkId);
        if (dto == null){
            ApiResponse.ok(Msg.RECORD_NOT_FOUND, null);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dto);
    }
    @DeleteMapping("/{startWorkId}")
    public ResponseEntity<?> delete(@PathVariable("startWorkId") String startWorkId){
        try {
            if (workService.delete(startWorkId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete StartWork", false);
    }
}
