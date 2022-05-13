package com.khoders.asset.controller.accounting;

import com.khoders.asset.dto.accounting.InvoiceDto;
import com.khoders.asset.services.InvoiceService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.spring.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.INVOICE_ENDPOINT)
public class InvoiceController {
    @Autowired private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceDto> save(@RequestBody InvoiceDto dto){
        InvoiceDto invoiceDto = invoiceService.saveInvoice(dto);
        return ApiResponse.created(Msg.CREATED, invoiceDto);
    }
    @PutMapping
    public ResponseEntity<InvoiceDto> update(@RequestBody InvoiceDto dto){
        InvoiceDto invoiceDto = invoiceService.saveInvoice(dto);
        return ApiResponse.created(Msg.UPDATED, invoiceDto.getId());
    }
    @GetMapping("/list")
    public ResponseEntity<List<InvoiceDto>> findAll(){
        List<InvoiceDto> dtoList = invoiceService.invoiceList();
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }
    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDto> findById(@PathVariable(value = "invoiceId") String invoiceId){
        InvoiceDto itemDto = invoiceService.findById(invoiceId);
        return ApiResponse.ok(Msg.RECORD_FOUND, itemDto);
    }
}
