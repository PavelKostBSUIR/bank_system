package com.vojtechruzicka.javafxweaverexample.repo;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Client;
import com.vojtechruzicka.javafxweaverexample.entity.domain.Disability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisabilityRepository extends JpaRepository<Disability, String> {
}
