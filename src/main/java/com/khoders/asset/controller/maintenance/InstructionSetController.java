package com.khoders.asset.controller.maintenance;

import com.khoders.asset.dto.accounting.InvoiceDto;
import com.khoders.asset.dto.maintenance.InstructionSetDto;
import com.khoders.asset.services.InstructionSetService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.spring.ApiResponse;
import com.khoders.resource.utilities.Msg;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Instruction Set - Endpoint")
@RequestMapping(ApiEndpoint.INSTRUCTION_SET_ENDPOINT)
public class InstructionSetController {
    @Autowired private InstructionSetService instructionSetService;

    @PostMapping
    public ResponseEntity<InstructionSetDto> save(@RequestBody InstructionSetDto dto){
        InstructionSetDto instructionSetDto = instructionSetService.save(dto);
        return ApiResponse.created(Msg.CREATED, instructionSetDto);
    }
    @PutMapping
    public ResponseEntity<InstructionSetDto> update(@RequestBody InstructionSetDto dto){
        InstructionSetDto instructionSetDto = instructionSetService.save(dto);
        return ApiResponse.ok(Msg.UPDATED, instructionSetDto);
    }
    @GetMapping("/list")
    public ResponseEntity<List<InstructionSetDto>> list(){
        List<InstructionSetDto> dtoList = instructionSetService.instructionSetList();
        if (dtoList.isEmpty()){
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, new InstructionSetDto());
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }
    @GetMapping("/{instructionSetId}")
    public ResponseEntity<InstructionSetDto> findById(@PathVariable(value = "instructionSetId") String instructionSetId){
        InstructionSetDto itemDto = instructionSetService.findById(instructionSetId);
        return ApiResponse.ok(Msg.RECORD_FOUND, itemDto);
    }
    @DeleteMapping("/{instructionSetId}")
    public ResponseEntity<?> delete(@PathVariable("instructionSetId") String instructionSetId){
        try {
            if (instructionSetService.delete(instructionSetId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Invoice", false);
    }
}
