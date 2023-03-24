package com.vojtechruzicka.javafxweaverexample.repo;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Contract;
import com.vojtechruzicka.javafxweaverexample.entity.domain.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
