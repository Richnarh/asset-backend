package com.khoders.asset.controller.accounting;

import com.khoders.asset.dto.accounting.PaymentDto;
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
@Tag(name = "Bill Payment - Endpoint")
@RequestMapping(ApiEndpoint.BILL_PAYMENT_ENDPOINT)
public class BillPaymentController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<PaymentDto> save(@RequestBody PaymentDto dto) throws Exception {
        PaymentDto paymentDto = accountService.savePayment(dto);
        return ApiResponse.created(Msg.CREATED, paymentDto);
    }

    @PutMapping
    public ResponseEntity<PaymentDto> update(@RequestBody PaymentDto dto) throws Exception {
        PaymentDto paymentDto = accountService.savePayment(dto);
        return ApiResponse.ok(Msg.UPDATED, paymentDto);
    }

    @GetMapping("/{type}")
    public ResponseEntity<PaymentDto> findByBill(@PathVariable(value = "billId") String billId, @PathVariable(value = "type") String type) {
        List<PaymentDto> dtoList = accountService.find(billId, type);
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Object> delete(@PathVariable("paymentId") String paymentId) {
        try {
            accountService.deletePayment(paymentId);
            return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
    }
}
