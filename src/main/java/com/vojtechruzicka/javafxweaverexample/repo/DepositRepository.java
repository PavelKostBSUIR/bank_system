package com.vojtechruzicka.javafxweaverexample.repo;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Deposit;
import com.vojtechruzicka.javafxweaverexample.entity.domain.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
  //  Deposit findById(Long )
}
