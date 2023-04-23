package com.khoders.asset.controller.accounting;

import com.khoders.asset.dto.accounting.ExpenseDto;
import com.khoders.asset.services.ExpenseService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Expense - Endpoint")
@RequestMapping(ApiEndpoint.EXPENSE_ENDPOINT)
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDto> save(@RequestBody ExpenseDto dto) throws Exception {
        ExpenseDto expenseDto = expenseService.save(dto);
        return ApiResponse.created(Msg.CREATED, expenseDto);
    }

    @PutMapping
    public ResponseEntity<ExpenseDto> update(@RequestBody ExpenseDto dto) throws Exception {
        ExpenseDto expenseDto = expenseService.save(dto);
        return ApiResponse.created(Msg.UPDATED, expenseDto.getId());
    }

    @GetMapping("/list")
    public ResponseEntity<List<ExpenseDto>> findAll() throws Exception {
        List<ExpenseDto> dtoList = expenseService.expenseList();
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDto> findById(@PathVariable(value = "expenseId") String expenseId) throws Exception {
        ExpenseDto itemDto = expenseService.findById(expenseId);
        return ApiResponse.ok(Msg.RECORD_FOUND, itemDto);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<?> delete(@PathVariable("expenseId") String expenseId) {
        try {
            if (expenseService.delete(expenseId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Expense", false);
    }
}
