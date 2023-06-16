package com.khoders.asset.mapper.accounting;

import com.khoders.asset.dto.accounting.BillDto;
import com.khoders.asset.dto.accounting.BillItemDto;
import com.khoders.asset.dto.accounting.PaymentDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.entities.BusinessClient;
import com.khoders.entities.accounting.Account;
import com.khoders.entities.accounting.Bill;
import com.khoders.entities.accounting.BillItem;
import com.khoders.entities.accounting.Invoice;
import com.khoders.entities.accounting.Payment;
import com.khoders.entities.constants.PaymentType;
import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class BillExtractMapper {
    @Autowired
    private AppService appService;

    public Bill toEntity(BillDto dto) throws Exception {
        Bill bill = new Bill();
        if (dto.getId() != null) {
            bill.setId(dto.getId());
        }
        bill.setBillDate(DateUtil.parseLocalDate(dto.getBillDate(), Pattern._yyyyMMdd));
        bill.setBalanceOverDue(dto.getBalanceOverDue());
        bill.setMemo(dto.getMemo());
        if (dto.getReceiptNo() == null) {
            throw new DataNotFoundException("Bill Receipt is Required");
        }
        bill.setReceiptNo(dto.getReceiptNo());
        bill.setTotalAmount(dto.getTotalAmount());
        if (dto.getBusinessClientId() == null) {
            throw new DataNotFoundException("Please Specify Valid Client");
        }
        BusinessClient businessClient = appService.findById(BusinessClient.class, dto.getBusinessClientId());
        if (businessClient != null) {
            bill.setBusinessClient(businessClient);
        }
        bill.setBillItemList(toEntity(dto.getBillItemList()));
        return bill;
    }

    public List<BillItem> toEntity(List<BillItemDto> itemDtoList) throws Exception {
        List<BillItem> billItemList = new LinkedList<>();
        for (BillItemDto dto : itemDtoList) {
            BillItem billItem = new BillItem();
            if (dto.getId() != null) {
                billItem.setId(dto.getId());
            }
            billItem.setProduct(dto.getProduct());
            billItem.setQuantity(dto.getQuantity());
            billItem.setUnitPrice(dto.getUnitPrice());
            billItem.setTotalAmount(dto.getQuantity() * dto.getUnitPrice());
            if (dto.getAccountId() == null) {
                throw new DataNotFoundException("Please Specify a Valid Account");
            }
            Account account = appService.findById(Account.class, dto.getAccountId());
            if (account != null) {
                billItem.setAccount(account);
            }
            billItemList.add(billItem);
        }
        return billItemList;
    }

    public BillDto toDto(Bill bill) {
        BillDto dto = new BillDto();
        if (bill.getId() == null) return null;
        dto.setId(bill.getId());
        dto.setBillDate(DateUtil.parseLocalDateString(bill.getBillDate(), Pattern.ddMMyyyy));
        dto.setMemo(bill.getMemo());
        dto.setBalanceOverDue(bill.getBalanceOverDue());
        dto.setReceiptNo(bill.getReceiptNo());
        dto.setTotalAmount(bill.getTotalAmount());
        dto.setValueDate(DateUtil.parseLocalDateString(bill.getValueDate(), Pattern.ddMMyyyy));
        if (bill.getBusinessClient() != null) {
            dto.setBusinessClientId(bill.getBusinessClient().getId());
            dto.setBusinessClient(bill.getBusinessClient().getEmailAddress());
        }
        dto.setBillItemList(toDto(bill.getBillItemList()));
        return dto;
    }

    public List<BillItemDto> toDto(List<BillItem> billItemList) {
        List<BillItemDto> dtoList = new LinkedList<>();
        for (BillItem billItem : billItemList) {
            BillItemDto dto = new BillItemDto();
            if (billItem.getId() == null) return null;
            dto.setId(billItem.getId());
            dto.setProduct(billItem.getProduct());
            dto.setTotalAmount(billItem.getTotalAmount());
            dto.setUnitPrice(billItem.getUnitPrice());
            dto.setValueDate(DateUtil.parseLocalDateString(billItem.getValueDate(), Pattern.ddMMyyyy));
            dto.setQuantity(billItem.getQuantity());
            if (billItem.getBill() != null) {
                dto.setBillId(billItem.getBill().getId());
                dto.setBill(billItem.getBill().getReceiptNo());
            }
            if (billItem.getAccount() != null) {
                dto.setAccountId(billItem.getAccount().getId());
                dto.setAccountName(billItem.getAccount().getAccountName());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    public Payment toEntity(PaymentDto dto) throws Exception {
        Payment payment = new Payment();
        if (dto.getId() != null) {
            payment.setId(dto.getId());
        }
        if (dto.getBillId() == null) {
            throw new DataNotFoundException("Please Specify Valid BillId");
        }
        if (dto.getAccountId() == null) {
            throw new DataNotFoundException("Please Specify Valid AccountId");
        }
        if (dto.getPaymentType().equals(PaymentType.BILL_PAYMENT.name())) {
            Bill bill = appService.findById(Bill.class, dto.getBillId());
            if (bill != null) {
                payment.setBill(bill);
            }
        }
        if (dto.getPaymentType().equals(PaymentType.INVOICE_PAYMENT.name())) {
            Invoice invoice = appService.findById(Invoice.class, dto.getInvoiceId());
            if (invoice != null) {
                payment.setInvoice(invoice);
            }
        }

        Account account = appService.findById(Account.class, dto.getAccountId());
        if (account != null) {
            payment.setAccount(account);
        }
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(PaymentMethod.valueOf(dto.getPaymentMethod()));
        payment.setPaymentStatus(PaymentStatus.valueOf(dto.getPaymentStatus()));
        payment.setPaymentType(PaymentType.valueOf(dto.getPaymentType()));
        payment.setPaidDate(DateUtil.parseLocalDate(dto.getPaidDate(), Pattern._yyyyMMdd));
        payment.setReferenceNo(dto.getReferenceNo());
        payment.setAmountRemaining(dto.getAmountRemaining());

        return payment;
    }

    public PaymentDto toDto(Payment payment) {
        PaymentDto dto = new PaymentDto();
        if (payment.getId() == null) return null;
        dto.setId(payment.getId());
        if (payment.getAccount() != null) {
            dto.setAccountId(payment.getAccount().getId());
            dto.setAccountName(payment.getAccount().getAccountName());
        }
        if (payment.getBill() != null) {
            dto.setBillId(payment.getBill().getId());
            dto.setBill(payment.getReferenceNo());
        }
        dto.setAmount(payment.getAmount());
        dto.setPaidDate(DateUtil.parseLocalDateString(payment.getPaidDate(), Pattern.ddMMyyyy));
        dto.setPaymentMethod(payment.getPaymentMethod().getLabel());
        dto.setPaymentStatus(payment.getPaymentStatus().getLabel());
        dto.setValueDate(DateUtil.parseLocalDateString(payment.getValueDate(), Pattern.ddMMyyyy));
        dto.setPaymentType(payment.getPaymentType().name());
        return dto;
    }
}
