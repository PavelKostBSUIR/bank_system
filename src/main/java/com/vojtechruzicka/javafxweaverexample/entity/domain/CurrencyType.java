package com.vojtechruzicka.javafxweaverexample.entity.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class CurrencyType {
    @Id
    @NonNull
    private String name;
}
