package com.vojtechruzicka.javafxweaverexample.entity.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @NotBlank
    private String name;
    @NonNull
    @NotNull
    private Boolean revocable;
    @NonNull
    @NotNull
    private Double percent;
    @NonNull
    @NotNull
    private Long term;
    @NonNull
    @NotNull
    private Double minSum;
    @NonNull
    @NotNull
    private Double maxSum;
    @ManyToOne
    @NonNull
    @NotNull
    private CurrencyType currencyType;
}
