package com.khoders.asset.controller.accounting;

import com.khoders.asset.dto.accounting.BillDto;
import com.khoders.asset.services.AccountService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.spring.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.BILL_ENDPOINT)
public class BillBillItemController {
    @Autowired private AccountService accountService;

    @PostMapping
    public ResponseEntity<BillDto> create(@RequestBody BillDto dto){
        BillDto bill = accountService.saveBill(dto);
        return ApiResponse.created(Msg.CREATED, bill);
    }
    @PutMapping
    public ResponseEntity<BillDto> update(@RequestBody BillDto dto){
        BillDto bill = accountService.saveBill(dto);
        return ApiResponse.created(Msg.UPDATED, bill.getId());
    }
    @GetMapping("/list")
    public ResponseEntity<List<BillDto>> list(){
        List<BillDto> dtoList = accountService.billList();
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }
    @GetMapping("/{billId}")
    public ResponseEntity<BillDto> findById(@PathVariable(value = "billId") String billId){
        BillDto itemDto = accountService.findById(billId);
        return ApiResponse.ok(Msg.RECORD_FOUND, itemDto);
    }
}
