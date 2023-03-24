package com.vojtechruzicka.javafxweaverexample.service;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Account;
import com.vojtechruzicka.javafxweaverexample.entity.domain.Contract;
import com.vojtechruzicka.javafxweaverexample.entity.domain.Deposit;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class EndBankDayService {
    private final ContractService contractService;
    private final AccountService accountService;

    public EndBankDayService(ContractService contractService, AccountService accountService) {
        this.contractService = contractService;
        this.accountService = accountService;
    }

    public Date endBankDay(Date currentDate) {
        List<Contract> contracts = contractService.getAll();
        for (Contract contract : contracts) {
            if (!contract.getRevoked()) {
                Deposit deposit = contract.getDeposit();
                if (contract.getEndDate().toString().equals(currentDate.toString())) {
                    accountService.transaction(2L, contract.getCurrentAccount().getId(), contract.getSum());
                    if (deposit.getRevocable()) {
                        accountService.transaction(2L, contract.getPercentAccount().getId(), calculateNewSum(contract.getSum(), deposit.getPercent(), 1L));
                    }
                    if (!deposit.getRevocable()) {
                        accountService.transaction(2L, contract.getPercentAccount().getId(), calculateNewSum(contract.getSum(), deposit.getPercent(), deposit.getTerm() * 30));
                    }
                } else if (currentDate.before(contract.getEndDate()) && (contract.getStartDate().toString().equals(currentDate.toString()) || currentDate.after(contract.getStartDate()))) {
                    if (deposit.getRevocable()) {
                        if (calculateDayDifference(currentDate, contract.getStartDate()) != 0)
                            accountService.transaction(2L, contract.getPercentAccount().getId(), calculateNewSum(contract.getSum(), deposit.getPercent(), 1L));
                    }
                }
            }
            List<Account> accounts = accountService.getAll();
            for (Account account : accounts) {
                accountService.calculateSaldo(account.getId());
            }
        }
        String dt = currentDate.toString();  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date
        return Date.valueOf(dt);
    }

    private Double calculateNewSum(Double sum, Double percent, Long days) {
        double finalSum = sum;
        finalSum = finalSum * percent * days / 365.0 / 100.0;

        return finalSum;
    }

    private Long calculateDayDifference(Date date1, Date date2) {
        long timeDifference = date1.getTime() - date2.getTime();
        return (long) (timeDifference / (1000.0 * 60 * 60 * 24)) % 365;
    }
}
