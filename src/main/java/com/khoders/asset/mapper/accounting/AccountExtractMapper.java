package com.khoders.asset.mapper.accounting;

import com.khoders.asset.dto.accounting.BillDto;
import com.khoders.asset.dto.accounting.BillItemDto;
import com.khoders.asset.entities.BusinessClient;
import com.khoders.asset.entities.accounting.Account;
import com.khoders.asset.entities.accounting.Bill;
import com.khoders.asset.entities.accounting.BillItem;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class AccountExtractMapper {
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
}
