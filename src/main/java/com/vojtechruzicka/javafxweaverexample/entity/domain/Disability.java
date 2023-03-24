package com.vojtechruzicka.javafxweaverexample.entity.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Disability {
    @Id
    @NonNull
    @NotBlank
    private String name;
}
