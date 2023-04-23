package com.khoders.asset.controller.accounting;

import com.khoders.asset.dto.accounting.JournalDto;
import com.khoders.asset.exceptions.InternalErrException;
import com.khoders.asset.services.accounting.JournalService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Journal - Endpoint")
@RequestMapping(ApiEndpoint.JOURNAL_ENDPOINT)
public class JournalController {
    @Autowired
    private JournalService journalService;

    @PostMapping
    public ResponseEntity<JournalDto> create(@RequestBody JournalDto dto) throws Exception {
        JournalDto itemDto = journalService.save(dto);
        return ApiResponse.created(Msg.CREATED, itemDto);
    }

    @PutMapping
    public ResponseEntity<JournalDto> update(@RequestBody JournalDto dto) throws Exception {
        return ApiResponse.ok(Msg.UPDATED, journalService.save(dto).getId());
    }

    @GetMapping("/list")
    public ResponseEntity<List<JournalDto>> list() {
        try {
            return ApiResponse.ok(Msg.RECORD_FOUND, journalService.journalList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
    }

    @GetMapping("/{journalId}")
    public ResponseEntity<JournalDto> findById(@PathVariable("journalId") String journalId) {
        JournalDto dto = journalService.findById(journalId);
        return ApiResponse.ok(Msg.RECORD_FOUND, dto);
    }

    @DeleteMapping("/{journalId}")
    public ResponseEntity<?> delete(@PathVariable("accountId") String accountId) throws Exception {
        try {
            if (journalService.delete(accountId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
        return ApiResponse.error("Could Not Delete journal", false);
    }
}
