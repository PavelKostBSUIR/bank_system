package com.vojtechruzicka.javafxweaverexample.entity.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractNumber;
    @NonNull
    @ManyToOne
    @NotNull
    private Deposit deposit;
    @NonNull
    @NotNull
    private Date startDate;
    @NonNull
    @NotNull
    private Date endDate;
    @NonNull
    @NotNull
    private Double sum;
    @ManyToOne
    @NotNull
    private Client client;
    @OneToOne
    @NonNull
    @NotNull
    private Account currentAccount;
    @OneToOne
    @NonNull
    @NotNull
    private Account percentAccount;
    @NonNull
    @NotNull
    private Boolean revoked = false;
}
