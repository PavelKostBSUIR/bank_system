package com.vojtechruzicka.javafxweaverexample.service;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Account;
import com.vojtechruzicka.javafxweaverexample.entity.domain.Contract;
import com.vojtechruzicka.javafxweaverexample.repo.AccountPlanRepository;
import com.vojtechruzicka.javafxweaverexample.repo.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final AccountService accountService;
    private final AccountPlanRepository accountPlanRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository, AccountService accountService, AccountPlanRepository accountPlanRepository) {
        this.contractRepository = contractRepository;
        this.accountService = accountService;
        this.accountPlanRepository = accountPlanRepository;
    }

    @Transactional
    public Boolean revoke(Long id) {
        Contract contract = contractRepository.getOne(id);
        if (contract.getDeposit().getRevocable()) {
            contract.setRevoked(true);
            accountService.transaction(2L, contract.getCurrentAccount().getId(), contract.getSum());
            contractRepository.save(contract);
            return true;
        }
        return false;
    }

    @Transactional
    public Contract add(Contract contract) {
        Account account;
        if (contract.getDeposit().getRevocable()) {
            account = accountService.add(new Account(accountPlanRepository.findById(2L).get()));
        } else {
            account = accountService.add(new Account(accountPlanRepository.findById(3L).get()));
        }
        account.setClient(contract.getClient());
        contract.setPercentAccount(account);
        account = accountService.add(new Account(accountPlanRepository.findById(1L).get()));
        account.setClient(contract.getClient());
        accountService.transaction(1L, account.getId(), contract.getSum());
        accountService.transaction(account.getId(), 2L, contract.getSum());
        contract.setCurrentAccount(account);
        return contractRepository.save(contract);
    }

    public List<Contract> getAll() {
        return contractRepository.findAll();
    }


}
