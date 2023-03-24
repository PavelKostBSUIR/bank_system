package com.vojtechruzicka.javafxweaverexample.entity.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AccountPlan", uniqueConstraints = {
        @UniqueConstraint(name = "uc_accountplan_accountcode", columnNames = {"accountCode"})
})
@Getter
@Setter
@ToString
public class AccountPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @NotBlank
    private String accountName;
    @NonNull
    @NotNull
    private Long accountCode;
    @NonNull
    @NotNull
    private Boolean isActive;
}
