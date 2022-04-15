package com.khoders.asset.controller;

import com.khoders.asset.dto.AssetTransferDto;
import com.khoders.asset.dto.CompanyDto;
import com.khoders.asset.entities.AssetTransfer;
import com.khoders.asset.entities.Company;
import com.khoders.asset.mapper.CompanyMapper;
import com.khoders.asset.services.CompanyService;
import com.khoders.asset.utils.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/company")
public class CompanyController {
    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyMapper mapper;

    @PostMapping("/create")
    public ResponseEntity<Company> saveCompany(@RequestBody CompanyDto dto) {
        try {
            Company entity = mapper.toEntity(dto);
            Company company = companyService.saveCompany(entity);
            if (company == null) {
                return ApiResponse.error(Msg.setMsg("Unknown Error"), null);
            }
            return ApiResponse.created(Msg.setMsg("Company Created Successfully"), mapper.toDto(company));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<AssetTransfer> updateTransfer(@RequestBody AssetTransferDto dto) {
        try {
            Company company = companyService.findById(dto.getId());
            if (company == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            Company com = companyService.saveCompany(company);
            if (com == null) {
                return ApiResponse.error("Unknown Error", null);
            }
            return ApiResponse.created(Msg.UPDATED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Company>> companies() {
        List<Company> compList = companyService.companyList();
        List<CompanyDto> dtoList = new LinkedList<>();
        compList.forEach(company -> {
            dtoList.add(mapper.toDto(company));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok("Records Found", dtoList);
    }

}
