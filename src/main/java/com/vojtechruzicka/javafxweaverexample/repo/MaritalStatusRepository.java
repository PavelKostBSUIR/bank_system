package com.vojtechruzicka.javafxweaverexample.repo;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Client;
import com.vojtechruzicka.javafxweaverexample.entity.domain.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaritalStatusRepository extends JpaRepository<MaritalStatus, String> {
}
