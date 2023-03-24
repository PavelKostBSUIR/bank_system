package com.vojtechruzicka.javafxweaverexample.backController;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Account;
import com.vojtechruzicka.javafxweaverexample.service.AccountService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public void post(Account account) {
        accountService.add(account);
    }

    public List<Account> getAll() {
        return accountService.getAll();
    }

    public void fill(Long id, Double sum) {
        accountService.fill(id, sum);
    }

    public void takeOff(Long id, Double sum) {
        accountService.takeOff(id, sum);
    }
}
