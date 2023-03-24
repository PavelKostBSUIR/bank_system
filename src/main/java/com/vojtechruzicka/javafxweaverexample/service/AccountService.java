package com.vojtechruzicka.javafxweaverexample.service;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Account;
import com.vojtechruzicka.javafxweaverexample.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account add(Account account) {
        account.setAccountNumber(generateAccountNumber());
        return accountRepository.save(account);
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Transactional
    public void fill(Long id, Double sum) {
        Account account = accountRepository.getOne(id);
        if (account.getAccountPlan().getIsActive()) {
            account.setDebet(account.getDebet() + sum);
        } else {
            account.setCredit(account.getCredit() + sum);
        }
    }

    @Transactional
    public void takeOff(Long id, Double sum) {
        Account account = accountRepository.getOne(id);
        if (account.getAccountPlan().getIsActive()) {
            account.setCredit(account.getCredit() + sum);
        } else {
            account.setDebet(account.getDebet() + sum);
        }
    }

    @Transactional
    public void transaction(Long idFrom, Long idTo, Double sum) {
        takeOff(idFrom, sum);
        fill(idTo, sum);
    }

    @Transactional
    public void calculateSaldo(Long id) {
        Account account = accountRepository.findById(id).get();
        double newSaldo;
        if (account.getAccountPlan().getIsActive()) {
            newSaldo = account.getSaldo() + account.getDebet() - account.getCredit();
        } else {
            newSaldo = account.getSaldo() + account.getCredit() - account.getDebet();
        }
        account.setSaldo(newSaldo);
        account.setDebet((double) 0);
        account.setCredit((double) 0);
        accountRepository.save(account);
    }

    private Long generateAccountNumber() {
        return (long) (Math.random() * 8_999_999_999_999L + 1_000_000_000_000L);
    }
}
