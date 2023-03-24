package com.vojtechruzicka.javafxweaverexample.entity.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.*;



@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class MaritalStatus {
    @Id
    @NonNull
    @NotBlank
    private String name;

}
