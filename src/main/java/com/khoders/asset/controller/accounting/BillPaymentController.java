package com.khoders.asset.controller.accounting;

import com.khoders.asset.dto.accounting.BillPaymentDto;
import com.khoders.asset.services.AccountService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.spring.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.BILL_PAYMENT_ENDPOINT)
public class BillPaymentController {
    @Autowired private AccountService accountService;

    @PostMapping
    public ResponseEntity<BillPaymentDto> save(@RequestBody BillPaymentDto dto){
        BillPaymentDto paymentDto = accountService.saveBillPayment(dto);
        return ApiResponse.created(Msg.CREATED, paymentDto);
    }
    @PutMapping
    public ResponseEntity<BillPaymentDto> update(@RequestBody BillPaymentDto dto){
        BillPaymentDto paymentDto = accountService.saveBillPayment(dto);
        return ApiResponse.ok(Msg.UPDATED, paymentDto);
    }
    @GetMapping
    public ResponseEntity<BillPaymentDto> findByBill(@PathVariable(value = "billId") String billId){
        List<BillPaymentDto> dtoList = accountService.findByBill(billId);
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }
    @DeleteMapping("/{billPaymentId}")
    public ResponseEntity<Object> delete(@PathVariable("billPaymentId") String billPaymentId){
        try {
            if (accountService.deletePayment(billPaymentId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete BillPayment", false);
    }
}
