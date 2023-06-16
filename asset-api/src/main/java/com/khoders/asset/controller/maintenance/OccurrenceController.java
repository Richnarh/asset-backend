package com.khoders.asset.controller.maintenance;

import com.khoders.asset.dto.maintenance.OccurrenceDto;
import com.khoders.asset.services.OccurrenceService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Occurrence - Endpoint")
@RequestMapping(ApiEndpoint.OCCURRENCE_ENDPOINT)
public class OccurrenceController {
    @Autowired
    private OccurrenceService occurrenceService;

    @PostMapping
    public ResponseEntity<OccurrenceDto> save(@RequestBody OccurrenceDto dto) throws Exception {
        return ApiResponse.created(Msg.CREATED, occurrenceService.toEntity(dto));
    }

    @PutMapping
    public ResponseEntity<OccurrenceDto> update(@RequestBody OccurrenceDto dto) throws Exception {
        return ApiResponse.ok(Msg.UPDATED, occurrenceService.toEntity(dto).getId());
    }

    @GetMapping("/list")
    public ResponseEntity<List<OccurrenceDto>> list() {
        List<OccurrenceDto> dtoList = occurrenceService.occurrenceList();

        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{occurrenceId}")
    public ResponseEntity<OccurrenceDto> findById(@PathVariable("occurrenceId") String occurrenceId) {
        OccurrenceDto dto = occurrenceService.findById(occurrenceId);
        if (dto == null) {
            ApiResponse.ok(Msg.RECORD_NOT_FOUND, null);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dto);
    }

    @DeleteMapping("/{occurrenceId}")
    public ResponseEntity<?> delete(@PathVariable("occurrenceId") String occurrenceId) {
        try {
            if (occurrenceService.delete(occurrenceId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Occurrence", false);
    }
}
