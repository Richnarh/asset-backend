package com.khoders.asset.services;

import com.khoders.asset.dto.Sql;
import com.khoders.asset.dto.accounting.InvoiceDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.accounting.InvoiceExtractMapper;
import com.khoders.entities.accounting.Invoice;
import com.khoders.entities.accounting.InvoiceItem;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class InvoiceService {
    @Autowired
    private AppService appService;
    @Autowired
    private InvoiceExtractMapper extractMapper;
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public InvoiceDto saveInvoice(InvoiceDto dto) throws Exception {
        if (dto.getId() != null) {
            Invoice invoice = appService.findById(Invoice.class, dto.getId());
            if (invoice == null) {
                throw new DataNotFoundException("Invoice with ID: " + dto.getId() + " Not Found");
            }
        }
        Invoice invoice = extractMapper.toEntity(dto);
        if (appService.save(invoice) != null) {
            for (InvoiceItem invoiceItem : invoice.getInvoiceItemList()) {
                invoiceItem.setInvoice(invoice);
                appService.save(invoiceItem);
            }
        }
        return extractMapper.toDto(invoice);
    }

    public List<InvoiceDto> invoiceList() throws Exception {
        List<InvoiceItem> invoiceItemList;
        List<InvoiceDto> dtoList = new LinkedList<>();
        List<Invoice> invoiceList = appService.findAll(Invoice.class);
        if (invoiceList.isEmpty()) {
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        for (Invoice invoice : invoiceList) {
            SqlParameterSource param = new MapSqlParameterSource(InvoiceItem._invoiceId, invoice.getId());
            invoiceItemList = jdbc.query(Sql.INVOICE_ITEM_BY_INVOICE_ID, param, BeanPropertyRowMapper.newInstance(InvoiceItem.class));
            invoice.setInvoiceItemList(invoiceItemList);
            invoiceList = new LinkedList<>();
            invoiceList.add(invoice);
        }
        for (Invoice invoice : invoiceList) {
            dtoList.add(extractMapper.toDto(invoice));
        }
        return dtoList;
    }

    public InvoiceDto findById(String invoiceId) throws Exception {
        List<InvoiceItem> invoiceItemList;
        Invoice invoice = appService.findById(Invoice.class, invoiceId);
        if (invoice == null) {
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        SqlParameterSource param = new MapSqlParameterSource(InvoiceItem._invoiceId, invoice.getId());
        invoiceItemList = jdbc.query(Sql.INVOICE_ITEM_BY_INVOICE_ID, param, BeanPropertyRowMapper.newInstance(InvoiceItem.class));
        invoice.setInvoiceItemList(invoiceItemList);
        return extractMapper.toDto(invoice);
    }

    public boolean delete(String invoiceId) throws Exception {
        return appService.deleteById(Invoice.class, invoiceId);
    }
}
