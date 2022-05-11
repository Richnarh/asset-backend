package com.khoders.asset.mapper.accounting;

import com.khoders.asset.dto.accounting.BillDto;
import com.khoders.asset.dto.accounting.BillItemDto;
import com.khoders.asset.dto.accounting.BillPaymentDto;
import com.khoders.asset.entities.BusinessClient;
import com.khoders.asset.entities.accounting.Account;
import com.khoders.asset.entities.accounting.Bill;
import com.khoders.asset.entities.accounting.BillItem;
import com.khoders.asset.entities.accounting.BillPayment;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.enums.PaymentStatus;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class BillExtractMapper {
    @Autowired private CrudBuilder builder;

    public Bill toEntity(BillDto dto){
        Bill bill = new Bill();
        if (dto.getId() !=null){
            bill.setId(dto.getId());
        }
        bill.setBillDate(DateUtil.parseLocalDate(dto.getBillDate(), Pattern._yyyyMMdd));
        bill.setBalanceOverDue(dto.getBalanceOverDue());
        bill.setMemo(dto.getMemo());
        if (dto.getReceiptNo() == null){
            throw new DataNotFoundException("Bill Receipt is Required");
        }
        bill.setReceiptNo(dto.getReceiptNo());
        bill.setTotalAmount(dto.getTotalAmount());
        if (dto.getBusinessClientId() == null){
            throw new DataNotFoundException("Please Specify Valid Client");
        }
        BusinessClient businessClient = builder.simpleFind(BusinessClient.class, dto.getBusinessClientId());
        if (businessClient != null){
            bill.setBusinessClient(businessClient);
        }
        bill.setBillItemList(toEntity(dto.getBillItemList()));
        return bill;
    }

    public List<BillItem> toEntity(List<BillItemDto> itemDtoList){
        List<BillItem> billItemList = new LinkedList<>();
         for(BillItemDto dto: itemDtoList){
            BillItem billItem = new BillItem();
            if (dto.getId() != null){
                billItem.setId(dto.getId());
            }
            billItem.setProduct(dto.getProduct());
            billItem.setQuantity(dto.getQuantity());
            billItem.setUnitPrice(dto.getUnitPrice());
            billItem.setTotalAmount(dto.getQuantity()*dto.getUnitPrice());
            if (dto.getAccountId() == null) {
                throw new DataNotFoundException("Please Specify a Valid Account");
            }
            Account account = builder.simpleFind(Account.class, dto.getAccountId());
            if (account != null){
                billItem.setAccount(account);
            }
            billItemList.add(billItem);
        }
        return billItemList;
    }

    public BillDto toDto(Bill bill){
        BillDto dto = new BillDto();
        if (bill.getId() == null) return null;
        dto.setId(bill.getId());
        dto.setBillDate(DateUtil.parseLocalDateString(bill.getBillDate(), Pattern.ddMMyyyy));
        dto.setMemo(bill.getMemo());
        dto.setBalanceOverDue(bill.getBalanceOverDue());
        dto.setReceiptNo(bill.getReceiptNo());
        dto.setTotalAmount(bill.getTotalAmount());
        dto.setValueDate(DateUtil.parseLocalDateString(bill.getValueDate(), Pattern.ddMMyyyy));
        if (bill.getBusinessClient() != null){
            dto.setBusinessClientId(bill.getBusinessClient().getId());
            dto.setBusinessClient(bill.getBusinessClient().getEmailAddress());
        }
        dto.setBillItemList(toDto(bill.getBillItemList()));
        return dto;
    }

    public List<BillItemDto> toDto(List<BillItem> billItemList){
        List<BillItemDto> dtoList = new LinkedList<>();
        for (BillItem billItem:billItemList){
            BillItemDto dto = new BillItemDto();
            if (billItem.getId() == null) return null;
            dto.setId(billItem.getId());
            dto.setProduct(billItem.getProduct());
            dto.setTotalAmount(billItem.getTotalAmount());
            dto.setUnitPrice(billItem.getUnitPrice());
            dto.setValueDate(DateUtil.parseLocalDateString(billItem.getValueDate(), Pattern.ddMMyyyy));
            dto.setQuantity(billItem.getQuantity());
            if (billItem.getBill() != null){
                dto.setBillId(billItem.getBill().getId());
                dto.setBill(billItem.getBill().getReceiptNo());
            }
            if (billItem.getAccount() != null){
                dto.setAccountId(billItem.getAccount().getId());
                dto.setAccountName(billItem.getAccount().getAccountCode());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    public BillPayment toEntity(BillPaymentDto dto){
        BillPayment billPayment = new BillPayment();
        if (dto.getId() != null){
            billPayment.setId(dto.getId());
        }
        if (dto.getBillId() == null){
            throw new DataNotFoundException("Please Specify Valid BillId");
        }
        if (dto.getAccountId() == null){
            throw new DataNotFoundException("Please Specify Valid AccountId");
        }
        Bill bill = builder.simpleFind(Bill.class, dto.getBillId());
        if (bill != null){
            billPayment.setBill(bill);
        }
        Account account = builder.simpleFind(Account.class, dto.getAccountId());
        if (account != null){
            billPayment.setAccount(account);
        }
        billPayment.setAmount(dto.getAmount());
        try {
            billPayment.setPaymentMethod(PaymentMethod.valueOf(dto.getPaymentMethod()));
        }catch (Exception ignored){}
        try {
            billPayment.setPaymentStatus(PaymentStatus.valueOf(dto.getPaymentStatus()));
        }catch (Exception ignored){}
        billPayment.setPaidDate(DateUtil.parseLocalDate(dto.getPaidDate(), Pattern._yyyyMMdd));
        billPayment.setReferenceNo(dto.getReferenceNo());
        billPayment.setAmountRemaining(dto.getAmountRemaining());

        return billPayment;
    }

    public BillPaymentDto toDto(BillPayment billPayment){
        BillPaymentDto dto = new BillPaymentDto();
        if (billPayment.getId() == null) return null;
        dto.setId(billPayment.getId());
        if (billPayment.getAccount() != null){
            dto.setAccountId(billPayment.getAccount().getId());
            dto.setAccountName(billPayment.getAccount().getAccountName());
        }
        if (billPayment.getBill() != null){
            dto.setBillId(billPayment.getBill().getId());
            dto.setBill(billPayment.getReferenceNo());
        }
        dto.setAmount(billPayment.getAmount());
        dto.setPaidDate(DateUtil.parseLocalDateString(billPayment.getPaidDate(), Pattern.ddMMyyyy));
        dto.setPaymentMethod(billPayment.getPaymentMethod().getLabel());
        dto.setPaymentStatus(billPayment.getPaymentStatus().getLabel());
        dto.setValueDate(DateUtil.parseLocalDateString(billPayment.getValueDate(), Pattern.ddMMyyyy));
        return dto;
    }
}
