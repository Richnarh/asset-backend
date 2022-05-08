package com.khoders.asset.controller;

import com.khoders.asset.dto.BusinessClientDto;
import com.khoders.asset.entities.BusinessClient;
import com.khoders.asset.mapper.VendorMapper;
import com.khoders.asset.services.BusinessClientService;
import com.khoders.resource.spring.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/vendor")
public class BusinessClientController {
    @Autowired
    private BusinessClientService clientService;
    @Autowired
    private VendorMapper mapper;

    @PostMapping
    public ResponseEntity<BusinessClient> create(@RequestBody BusinessClientDto dto) {
        try {
            BusinessClient entity = mapper.toEntity(dto);
            BusinessClient businessClient = clientService.save(entity);
            if (businessClient == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.created(Msg.CREATED, mapper.toDto(businessClient));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<BusinessClient>> list() {
        List<BusinessClient> vendorList = clientService.vendors();
        List<BusinessClientDto> dtoList = new LinkedList<>();
        vendorList.forEach(vendor -> {
            dtoList.add(mapper.toDto(vendor));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{businessClientId}")
    public ResponseEntity<BusinessClient> findSingle(@PathVariable(value = "businessClientId") String businessClientId) {
        try {
            BusinessClient businessClient = clientService.findById(businessClientId);
            if (businessClient == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new BusinessClientDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(businessClient));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
    }

    @PutMapping
    public ResponseEntity<BusinessClient> update(@RequestBody BusinessClientDto dto) {
        try {
            BusinessClient businessClient = clientService.findById(dto.getId());
            if (businessClient == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            BusinessClient entity = mapper.toEntity(dto);
            BusinessClient client = clientService.save(entity);
            if (client == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.ok(Msg.UPDATED, mapper.toDto(client));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @DeleteMapping("/{businessClientId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "businessClientId") String businessClientId) {
        try {
            if (clientService.delete(businessClientId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Vendor", false);
    }
}
