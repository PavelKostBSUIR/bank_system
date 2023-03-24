package com.vojtechruzicka.javafxweaverexample.entity.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Citizenship {
    @Id
    @NonNull
    @NotBlank
    private String name;

}
