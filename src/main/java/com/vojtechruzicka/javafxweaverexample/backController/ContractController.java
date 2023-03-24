package com.vojtechruzicka.javafxweaverexample.backController;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Contract;
import com.vojtechruzicka.javafxweaverexample.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ContractController {
    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }


    public Contract post(Contract contract) {
        return contractService.add(contract);
    }

    public List<Contract> getAll() {
        return contractService.getAll();
    }

    public Boolean revoke(Long id) {
        return contractService.revoke(id);
    }
}
