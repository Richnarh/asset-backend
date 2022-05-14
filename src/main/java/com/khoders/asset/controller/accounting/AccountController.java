package com.khoders.asset.controller.accounting;

import com.khoders.asset.dto.accounting.AccountDto;
import com.khoders.asset.services.AccountService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.spring.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.ACCOUNT_ENDPOINT)
public class AccountController {
    @Autowired private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto dto){
        AccountDto itemDto = accountService.saveAccount(dto);
       return ApiResponse.created(Msg.CREATED, itemDto);
    }
    @PutMapping
    public ResponseEntity<AccountDto> update(@RequestBody AccountDto dto){
        return ApiResponse.ok(Msg.UPDATED, accountService.saveAccount(dto).getId());
    }
    @GetMapping("/list")
    public ResponseEntity<List<AccountDto>> list(){
        return ApiResponse.ok(Msg.RECORD_FOUND, accountService.accountList());
    }
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> findById(@PathVariable("accountId") String accountId){
        return ApiResponse.ok(Msg.RECORD_FOUND, accountService.findById(accountId));
    }

}
