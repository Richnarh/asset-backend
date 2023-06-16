package com.khoders.asset.controller.accounting;

import com.khoders.asset.dto.accounting.AccountDto;
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
@Tag(name = "Chat of Account - Endpoint")
@RequestMapping(ApiEndpoint.ACCOUNT_ENDPOINT)
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto dto) throws Exception {
        AccountDto itemDto = accountService.saveAccount(dto);
        return ApiResponse.created(Msg.CREATED, itemDto);
    }

    @PutMapping
    public ResponseEntity<AccountDto> update(@RequestBody AccountDto dto) throws Exception {
        return ApiResponse.ok(Msg.UPDATED, accountService.saveAccount(dto).getId());
    }

    @GetMapping("/list")
    public ResponseEntity<List<AccountDto>> list() throws Exception {
        return ApiResponse.ok(Msg.RECORD_FOUND, accountService.accountList());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> findById(@PathVariable("accountId") String accountId) throws Exception {
        AccountDto dto = accountService.findAccount(accountId);
        return ApiResponse.ok(Msg.RECORD_FOUND, dto);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<?> delete(@PathVariable("accountId") String accountId) {
        try {
            if (accountService.delete(accountId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete account", false);
    }
}
