package com.khoders.asset.controller.lookups;

import com.khoders.asset.entities.constants.*;
import com.khoders.resource.enums.ClientType;
import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.enums.Title;
import com.khoders.resource.spring.ApiResponse;
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
    @GetMapping("/title")
    public ResponseEntity<List<Title>> title() {
        return ApiResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(Title.values()));
    }
    @GetMapping("/roles")
    public ResponseEntity<List<UserRole>> userRoles() {
        return ApiResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(UserRole.values()));
    }
    @GetMapping("/client-types")
    public ResponseEntity<List<ClientType>> clientType() {
        return ApiResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(ClientType.values()));
    }
    @GetMapping("/file-types")
    public ResponseEntity<List<FileType>> fileType() {
        return ApiResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(FileType.values()));
    }
    @GetMapping("/payment-status")
    public ResponseEntity<List<PaymentStatus>> paymentStatus() {
        return ApiResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(PaymentStatus.values()));
    }
    @GetMapping("/payment-method")
    public ResponseEntity<List<PaymentMethod>> paymentMethod() {
        return ApiResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(PaymentMethod.values()));
    }
}
