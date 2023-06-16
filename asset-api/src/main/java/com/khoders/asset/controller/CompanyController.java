package com.khoders.asset.controller;

import com.khoders.asset.dto.AssetTransferDto;
import com.khoders.asset.dto.CompanyDto;
import com.khoders.asset.exceptions.BadDataException;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.exceptions.InternalErrException;
import com.khoders.asset.mapper.CompanyMapper;
import com.khoders.asset.services.CompanyService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.entities.AssetTransfer;
import com.khoders.entities.Company;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@Tag(name = "Company - Endpoint")
@RequestMapping(ApiEndpoint.COMPANY_ENDPOINT)
public class CompanyController {
    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyMapper mapper;

    @PostMapping
    public ResponseEntity<Company> saveCompany(@RequestBody CompanyDto dto) throws Exception {
        try {
            Company entity = mapper.toEntity(dto);
            Company company = companyService.saveCompany(entity);
            if (company == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            return ApiResponse.created("Company Created Successfully", mapper.toDto(company));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<AssetTransfer> updateTransfer(@RequestBody AssetTransferDto dto) throws Exception {
        try {
            Company company = companyService.findById(dto.getId());
            if (company == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            Company com = companyService.saveCompany(company);
            if (com == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            return ApiResponse.ok(Msg.UPDATED, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
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

    @GetMapping("/{companyId}")
    public ResponseEntity<Company> findSingle(@PathVariable(value = "companyId") String companyId) throws Exception {
        try {
            Company company = companyService.findById(companyId);
            if (company == null) {
                throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(company));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
    }
}
