package com.khoders.asset.controller;

import com.khoders.asset.dto.VendorDto;
import com.khoders.asset.entities.Vendor;
import com.khoders.asset.mapper.VendorMapper;
import com.khoders.asset.services.VendorService;
import com.khoders.asset.utils.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/vendor")
public class VendorController {
    @Autowired
    private VendorService vendorService;
    @Autowired
    private VendorMapper mapper;

    @PostMapping
    public ResponseEntity<Vendor> create(@RequestBody VendorDto dto) {
        try {
            Vendor entity = mapper.toEntity(dto);
            Vendor vendor = vendorService.save(entity);
            if (vendor == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.created(Msg.CREATED, mapper.toDto(vendor));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Vendor>> list() {
        List<Vendor> vendorList = vendorService.vendors();
        List<VendorDto> dtoList = new LinkedList<>();
        vendorList.forEach(vendor -> {
            dtoList.add(mapper.toDto(vendor));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<Vendor> findSingle(@PathVariable(value = "vendorId") String vendorId) {
        try {
            Vendor vendor = vendorService.findById(vendorId);
            if (vendor == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new VendorDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(vendor));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
    }

    @PutMapping
    public ResponseEntity<Vendor> update(@RequestBody VendorDto dto) {
        try {
            Vendor vendor = vendorService.findById(dto.getId());
            if (vendor == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            Vendor entity = mapper.toEntity(dto);
            Vendor ven = vendorService.save(entity);
            if (ven == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.ok(Msg.UPDATED, mapper.toDto(ven));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @DeleteMapping("/{vendorId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "vendorId") String vendorId) {
        try {
            if (vendorService.delete(vendorId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Vendor", false);
    }
}
