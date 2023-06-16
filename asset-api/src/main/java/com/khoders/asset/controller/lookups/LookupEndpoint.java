package com.khoders.asset.controller.lookups;

import com.khoders.asset.dto.LookupItem;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.entities.constants.AccountType;
import com.khoders.entities.constants.ApprovalStatus;
import com.khoders.entities.constants.AssetStatus;
import com.khoders.entities.constants.DebitCredit;
import com.khoders.entities.constants.FileType;
import com.khoders.entities.constants.SocialMediaType;
import com.khoders.entities.constants.Status;
import com.khoders.entities.constants.TaskPriority;
import com.khoders.entities.constants.UserRole;
import com.khoders.resource.enums.ClientType;
import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.enums.Title;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Lookup - Endpoint")
@RequestMapping(ApiEndpoint.LOOKUP_ENDPOINT)
public class LookupEndpoint {
    @Autowired
    private LookupSetup lookupSetup;

    // Enums
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

    @GetMapping("/Social-media-type")
    public ResponseEntity<List<SocialMediaType>> socialMediaType() {
        return ApiResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(SocialMediaType.values()));
    }

    @GetMapping("/account-type")
    public ResponseEntity<List<AccountType>> accountCategory() {
        return ApiResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(AccountType.values()));
    }

    @GetMapping("/debit-credit")
    public ResponseEntity<List<DebitCredit>> debitCredit() {
        return ApiResponse.ok(Msg.RECORD_FOUND, LookupSetup.PrepareEnum(DebitCredit.values()));
    }

    // Entities
    @GetMapping("/categories")
    public ResponseEntity<List<LookupItem>> category() {
        return ApiResponse.ok(Msg.RECORD_FOUND, lookupSetup.categories());
    }

    @GetMapping("/departments")
    public ResponseEntity<List<LookupItem>> department() {
        return ApiResponse.ok(Msg.RECORD_FOUND, lookupSetup.department());
    }

    @GetMapping("/locations")
    public ResponseEntity<List<LookupItem>> location() {
        return ApiResponse.ok(Msg.RECORD_FOUND, lookupSetup.location());
    }

    @GetMapping("/employees")
    public ResponseEntity<List<LookupItem>> employees() {
        return ApiResponse.ok(Msg.RECORD_FOUND, lookupSetup.employees());
    }

    @GetMapping("/business-clients")
    public ResponseEntity<List<LookupItem>> businessClient() {
        return ApiResponse.ok(Msg.RECORD_FOUND, lookupSetup.businessClient());
    }

    @GetMapping("/invoice-types")
    public ResponseEntity<List<LookupItem>> invoiceType() {
        return ApiResponse.ok(Msg.RECORD_FOUND, lookupSetup.invoiceType());
    }

    @GetMapping("/request-types")
    public ResponseEntity<List<LookupItem>> requestType() {
        return ApiResponse.ok(Msg.RECORD_FOUND, lookupSetup.invoiceType());
    }

    @GetMapping("/companies")
    public ResponseEntity<List<LookupItem>> companies() {
        return ApiResponse.ok(Msg.RECORD_FOUND, lookupSetup.companies());
    }
}
