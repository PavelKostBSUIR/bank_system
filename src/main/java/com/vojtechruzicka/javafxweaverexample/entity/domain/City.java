package com.vojtechruzicka.javafxweaverexample.entity.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class City {
    @Id
    @NonNull
    @NotBlank
    private String name;
}
