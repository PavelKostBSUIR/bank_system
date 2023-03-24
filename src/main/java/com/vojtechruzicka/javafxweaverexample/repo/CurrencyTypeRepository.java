package com.vojtechruzicka.javafxweaverexample.repo;

import com.vojtechruzicka.javafxweaverexample.entity.domain.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyTypeRepository extends JpaRepository<CurrencyType, String> {
}
