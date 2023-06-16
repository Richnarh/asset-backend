package com.khoders.asset.mapper.accounting;

import com.khoders.asset.dto.accounting.InvoiceDto;
import com.khoders.asset.dto.accounting.InvoiceItemDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.entities.accounting.Account;
import com.khoders.entities.accounting.Invoice;
import com.khoders.entities.accounting.InvoiceItem;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class InvoiceExtractMapper {
    @Autowired
    private AppService appService;

    public Invoice toEntity(InvoiceDto dto) throws Exception {
        Invoice invoice = new Invoice();
        if (dto.getId() != null) {
            invoice.setId(dto.getId());
        }
        if (dto.getInvoiceNo() == null) {
            invoice.setInvoiceNo(SystemUtils.generateRefNo());
        } else {
            invoice.setInvoiceNo(dto.getInvoiceNo());
        }
        invoice.setBalanceOverDue(dto.getBalanceOverDue());
        invoice.setMemo(dto.getMemo());
        invoice.setDueDate(DateUtil.parseLocalDate(dto.getDueDate(), Pattern._yyyyMMdd));
        invoice.setCustomerNotes(dto.getCustomerNotes());
        invoice.setTermsCondition(dto.getTermsCondition());
        if (dto.getBusinessClientId() == null) {
            throw new DataNotFoundException("Specify Valid ClientId");
        }
        invoice.setInvoiceItemList(toEntity(dto.getInvoiceItemList()));
        return invoice;
    }

    public List<InvoiceItem> toEntity(List<InvoiceItemDto> itemDtoList) throws Exception {
        List<InvoiceItem> invoiceItemList = new LinkedList<>();
        for (InvoiceItemDto dto : itemDtoList) {
            InvoiceItem invoiceItem = new InvoiceItem();
            if (dto.getId() != null) {
                invoiceItem.setId(dto.getId());
            }
            invoiceItem.setQuantity(dto.getQuantity());
            invoiceItem.setProduct(dto.getProduct());
            invoiceItem.setUnitPrice(dto.getUnitPrice());
            if (dto.getAccountId() == null) {
                throw new DataNotFoundException("Specify Valid AccountId");
            }
            Account account = appService.findById(Account.class, dto.getAccountId());
            if (account != null) {
                invoiceItem.setAccount(account);
            }
            invoiceItemList.add(invoiceItem);
        }
        return invoiceItemList;
    }

    public List<InvoiceItemDto> toDto(List<InvoiceItem> invoiceItemList) {
        List<InvoiceItemDto> itemDtoList = new LinkedList<>();
        for (InvoiceItem invoiceItem : invoiceItemList) {
            InvoiceItemDto dto = new InvoiceItemDto();
            if (invoiceItem.getId() == null) return null;
            dto.setId(invoiceItem.getId());
            dto.setQuantity(invoiceItem.getQuantity());
            dto.setUnitPrice(invoiceItem.getUnitPrice());
            dto.setTotalAmount(invoiceItem.getTotalAmount());
            dto.setProduct(invoiceItem.getProduct());
            if (invoiceItem.getInvoice() != null) {
                dto.setInvoiceId(invoiceItem.getInvoice().getId());
                dto.setInvoice(invoiceItem.getInvoice().getInvoiceNo());
            }
            if (invoiceItem.getAccount() != null) {
                dto.setAccountId(invoiceItem.getAccount().getId());
                dto.setAccountName(invoiceItem.getAccount().getAccountName());
            }
            itemDtoList.add(dto);
        }
        return itemDtoList;
    }

    public InvoiceDto toDto(Invoice invoice) {
        InvoiceDto dto = new InvoiceDto();
        if (invoice.getId() == null) {
            return null;
        }
        dto.setId(invoice.getId());
        dto.setInvoiceNo(invoice.getInvoiceNo());
        dto.setDueDate(DateUtil.parseLocalDateString(invoice.getDueDate(), Pattern.ddMMyyyy));
        dto.setBalanceOverDue(invoice.getBalanceOverDue());
        dto.setCustomerNotes(invoice.getCustomerNotes());
        dto.setMemo(invoice.getMemo());
        dto.setTermsCondition(invoice.getTermsCondition());
        dto.setTotalAmount(invoice.getTotalAmount());
        if (invoice.getBusinessClient() != null) {
            dto.setBusinessClientId(invoice.getBusinessClient().getId());
            dto.setBusinessClient(invoice.getBusinessClient().getEmailAddress());
        }
        dto.setInvoiceItemList(toDto(invoice.getInvoiceItemList()));
        return dto;
    }
}
