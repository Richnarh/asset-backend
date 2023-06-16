package com.khoders.asset.services;

import com.khoders.asset.dto.Sql;
import com.khoders.asset.dto.accounting.AccountDto;
import com.khoders.asset.dto.accounting.BillDto;
import com.khoders.asset.dto.accounting.PaymentDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.accounting.AccountMapper;
import com.khoders.asset.mapper.accounting.BillExtractMapper;
import com.khoders.entities.accounting.Account;
import com.khoders.entities.accounting.Bill;
import com.khoders.entities.accounting.BillItem;
import com.khoders.entities.accounting.Payment;
import com.khoders.entities.constants.PaymentType;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AppService appService;
    @Autowired
    private BillExtractMapper extractMapper;
    @Autowired
    private AccountMapper mapper;
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    
    public BillDto saveBill(BillDto dto) throws Exception {
        if (dto.getId() != null) {
            Bill bill = appService.findById(Bill.class, dto.getId());
            if (bill == null) {
                throw new DataNotFoundException("Bill with ID: " + dto.getId() + " Not Found");
            }
        }
        Bill bill = extractMapper.toEntity(dto);
        if (appService.save(bill) != null) {
            for (BillItem billItem : bill.getBillItemList()) {
                billItem.setBill(bill);
                appService.save(billItem);
            }
        }
        return extractMapper.toDto(bill);
    }

    public List<BillDto> billList() {
        List<BillDto> dtoList = new LinkedList<>();

        List<Bill> billList = appService.findAll(Bill.class);
        if (billList != null && !billList.isEmpty()) {
            try {
                for (Bill bill : billList) {
                    SqlParameterSource param = new MapSqlParameterSource(BillItem._billId, bill.getId());
                    List<BillItem> billItemList = jdbc.query(Sql.BILL_ITEM_BY_BILL_ID, param, BeanPropertyRowMapper.newInstance(BillItem.class));
                    bill.setBillItemList(billItemList);
                    billList = new LinkedList<>();
                    billList.add(bill);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Bill bill : billList) {
                dtoList.add(extractMapper.toDto(bill));
            }
            return dtoList;
        }
        return Collections.emptyList();
    }

    public BillDto findById(String billId) {
        List<BillItem> billItemList = new LinkedList<>();;

        Bill bill = appService.load(Bill.class, billId);

        SqlParameterSource param = new MapSqlParameterSource(BillItem._billId, bill.getId());
        billItemList = jdbc.query(Sql.BILL_ITEM_BY_BILL_ID, param, BeanPropertyRowMapper.newInstance(BillItem.class));
        bill.setBillItemList(billItemList);
        return extractMapper.toDto(bill);
    }

    public AccountDto saveAccount(AccountDto dto) throws Exception {
        Account account = null;
        if (dto.getId() != null) {
            account = appService.findById(Account.class, dto.getId());
            if (account == null) {
                throw new DataNotFoundException("Account with ID: " + dto.getId() + " Not Found");
            }
        }
        account = mapper.toEntity(dto);
        if (appService.save(account) != null) {
            return mapper.tDto(account);
        }
        return null;
    }

    public AccountDto findAccount(String accountId) throws Exception {
        Account account = appService.findById(Account.class, accountId);
        if (account == null) {
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        return mapper.tDto(account);
    }

    public List<AccountDto> accountList() throws Exception {
        List<Account> accounts = appService.findAll(Account.class);
        if (accounts.isEmpty()) {
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        List<AccountDto> dtoList = new LinkedList<>();
        for (Account account : accounts) {
            dtoList.add(mapper.tDto(account));
        }
        return dtoList;
    }

    public PaymentDto savePayment(PaymentDto dto) throws Exception {
        if (dto.getId() != null) {
            Payment payment = appService.findById(Payment.class, dto.getId());
            if (payment == null) {
                throw new DataNotFoundException("Payment with ID: " + dto.getId() + " Not Found");
            }
        }
        Payment payment = extractMapper.toEntity(dto);
        if (appService.save(payment) != null) {
            return extractMapper.toDto(payment);
        }
        return null;
    }

    // Payment
    public List<PaymentDto> find(String billOrInvoice, String type) {
        List<Payment> paymentList;
        List<PaymentDto> dtoList = new LinkedList<>();
        SqlParameterSource param = null;

        if ((PaymentType.BILL_PAYMENT == PaymentType.valueOf(type))) {
            param = new MapSqlParameterSource(Payment._billId, billOrInvoice);
        } else {
            param = new MapSqlParameterSource(Payment._invoiceId, billOrInvoice);
        }
        paymentList = jdbc.query(Sql.BILL_ITEM_BY_BILL_ID, param, BeanPropertyRowMapper.newInstance(Payment.class));

        for (Payment payment : paymentList) {
            dtoList.add(extractMapper.toDto(payment));
        }
        return dtoList;
    }

    public boolean delete(String accountId) throws Exception {
        return appService.deleteById(Account.class, accountId);
    }
    public boolean deleteBill(String billId) throws Exception {
        return appService.deleteById(Bill.class, billId);
    }
    public boolean deletePayment(String paymentId) throws Exception {
        return appService.deleteById(Payment.class, paymentId);
    }
}
