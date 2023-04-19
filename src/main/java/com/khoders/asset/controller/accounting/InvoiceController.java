package com.khoders.asset.controller.accounting;

import com.khoders.asset.dto.accounting.InvoiceDto;
import com.khoders.asset.exceptions.InternalErrException;
import com.khoders.asset.services.InvoiceService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Invoice - Endpoint")
@RequestMapping(ApiEndpoint.INVOICE_ENDPOINT)
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    @Operation(summary = "Save Invoice")
    public ResponseEntity<InvoiceDto> save(@RequestBody InvoiceDto dto) throws Exception {
        InvoiceDto invoiceDto = invoiceService.saveInvoice(dto);
        return ApiResponse.created(Msg.CREATED, invoiceDto);
    }

    @PutMapping
    public ResponseEntity<InvoiceDto> update(@RequestBody InvoiceDto dto) throws Exception {
        InvoiceDto invoiceDto = invoiceService.saveInvoice(dto);
        return ApiResponse.created(Msg.UPDATED, invoiceDto.getId());
    }

    @GetMapping("/list")
    public ResponseEntity<List<InvoiceDto>> findAll() throws Exception {
        List<InvoiceDto> dtoList = invoiceService.invoiceList();
        if (dtoList.isEmpty()) {
            ApiResponse.ok(Msg.RECORD_NOT_FOUND, new InvoiceDto());
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDto> findById(@PathVariable(value = "invoiceId") String invoiceId) throws Exception {
        InvoiceDto itemDto = invoiceService.findById(invoiceId);
        return ApiResponse.ok(Msg.RECORD_FOUND, itemDto);
    }

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<?> delete(@PathVariable("invoiceId") String invoiceId) throws Exception {
        try {
            if (invoiceService.delete(invoiceId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
        return ApiResponse.error("Could Not Delete Invoice", false);
    }
}
