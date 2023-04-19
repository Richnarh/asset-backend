package com.khoders.asset.controller.accounting;

import com.khoders.asset.dto.accounting.PaymentDto;
import com.khoders.asset.exceptions.InternalErrException;
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
@Tag(name = "Invoice Payment - Endpoint")
@RequestMapping(ApiEndpoint.INVOICE_PAYMENT_ENDPOINT)
public class InvoicePaymentController {
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
    public ResponseEntity<PaymentDto> findByInvoice(@PathVariable(value = "invoiceId") String invoiceId, @PathVariable(value = "type") String type) {
        List<PaymentDto> dtoList = accountService.find(invoiceId, type);
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Object> delete(@PathVariable("paymentId") String paymentId) throws Exception {
        try {
            if (accountService.deletePayment(paymentId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
        return ApiResponse.error("Could Not Delete Payment", false);
    }
}
