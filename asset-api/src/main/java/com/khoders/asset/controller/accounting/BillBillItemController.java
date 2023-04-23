package com.khoders.asset.controller.accounting;

import com.khoders.asset.dto.accounting.BillDto;
import com.khoders.asset.services.AccountService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Bill & Bill Item - Endpoint")
@RequestMapping(ApiEndpoint.BILL_ENDPOINT)
public class BillBillItemController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<BillDto> create(@RequestBody BillDto dto) throws Exception {
        BillDto bill = accountService.saveBill(dto);
        return ApiResponse.created(Msg.CREATED, bill);
    }

    @PutMapping
    public ResponseEntity<BillDto> update(@RequestBody BillDto dto) throws Exception {
        BillDto bill = accountService.saveBill(dto);
        return ApiResponse.ok(Msg.UPDATED, bill.getId());
    }

    @GetMapping("/list")
    public ResponseEntity<List<BillDto>> list() {
        List<BillDto> dtoList = accountService.billList();
        if (dtoList.isEmpty()) {
            ApiResponse.ok(Msg.RECORD_NOT_FOUND, new BillDto());
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{billId}")
    public ResponseEntity<BillDto> findById(@PathVariable(value = "billId") String billId) {
        BillDto itemDto = accountService.findById(billId);
        return ApiResponse.ok(Msg.RECORD_FOUND, itemDto);
    }

    @DeleteMapping("/{billId}")
    public ResponseEntity<?> delete(@PathVariable("billId") String billId) {
        try {
            if (accountService.deleteBill(billId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Bill", false);
    }
}
