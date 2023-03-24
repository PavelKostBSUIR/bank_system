package com.vojtechruzicka.javafxweaverexample.repo;

import com.vojtechruzicka.javafxweaverexample.entity.domain.AccountPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountPlanRepository extends JpaRepository<AccountPlan, Long> {
}
