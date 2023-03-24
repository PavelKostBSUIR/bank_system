package com.vojtechruzicka.javafxweaverexample.repo;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Account;
import com.vojtechruzicka.javafxweaverexample.entity.domain.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
