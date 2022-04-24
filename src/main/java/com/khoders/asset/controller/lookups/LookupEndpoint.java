package com.khoders.asset.controller.lookups;

import com.khoders.asset.entities.constants.ApprovalStatus;
import com.khoders.asset.entities.constants.AssetStatus;
import com.khoders.asset.entities.constants.Status;
import com.khoders.asset.entities.constants.TaskPriority;
import com.khoders.asset.utils.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/data")
public class LookupEndpoint {
    @GetMapping("/approval-status")
    public ResponseEntity<List<ApprovalStatus>> approvalStatus() {
        return ApiResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(ApprovalStatus.values()));
    }
    @GetMapping("/asset-status")
    public ResponseEntity<List<AssetStatus>> assetStatus() {
        return ApiResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(AssetStatus.values()));
    }
    @GetMapping("/status")
    public ResponseEntity<List<AssetStatus>> status() {
        return ApiResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(Status.values()));
    }
    @GetMapping("/task-priority")
    public ResponseEntity<List<TaskPriority>> taskPriority() {
        return ApiResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(TaskPriority.values()));
    }
}
