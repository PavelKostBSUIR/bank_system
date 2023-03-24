package com.vojtechruzicka.javafxweaverexample.entity.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountNumber;
    @ManyToOne
    @NonNull
    @NotNull
    AccountPlan accountPlan;
    @NonNull
    @NotNull
    private Double debet = (double) 0;
    @NonNull
    @NotNull
    private Double credit = (double) 0;
    @NonNull
    @NotNull
    private Double saldo = (double) 0;
    @ManyToOne
    private Client client;

}
