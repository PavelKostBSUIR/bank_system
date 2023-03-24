package com.vojtechruzicka.javafxweaverexample.entity.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "Client", uniqueConstraints = {
        @UniqueConstraint(name = "uc_client_surname_name_secondname", columnNames = {"surname", "name", "secondName"}),
        @UniqueConstraint(name = "uc_client_passportseries", columnNames = {"passportSeries", "passportNumber", "passportId"}),
        @UniqueConstraint(name = "uc_client_passportid", columnNames = {"passportId"})
})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NonNull
    @NotBlank
    @Pattern(regexp = "^[A-\u042F\u0401][\u0430-\u044F\u0451]++$", message = "Invalid surname")
    private String surname;
    @Column(nullable = false)
    @NonNull
    @NotBlank
    @Pattern(regexp = "^[A-\u042F\u0401][\u0430-\u044F\u0451]++$")
    private String name;
    @Column(nullable = false)
    @NonNull
    @NotBlank
    @Pattern(regexp = "^[A-\u042F\u0401][\u0430-\u044F\u0451]++$")
    private String secondName;
    @NotNull
    private Date date;
    @NotBlank
    private String passportSeries;
    @NotBlank
    private String passportNumber;
    @NotBlank
    private String issuer;
    @NotNull
    private Date issueDate;
    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{14}$")
    private String passportId;
    @NotBlank
    private String birthplace;
    @NotNull
    @ManyToOne
    private City residentialCity;
    @NotBlank
    private String actualResidentialAddress;
    private String homePhoneNumber;
    private String mobilePhoneNumber;
    private String email;
    private String workPlace;
    private String post;
    @NotBlank
    private String residentialAddress;
    @ManyToOne
    @NotNull
    private MaritalStatus maritalStatus;
    @ManyToOne
    @NotNull
    private Citizenship citizenship;
    @NotNull
    @ManyToOne
    private Disability disability;
    @NotNull
    private Boolean retiree;
    private Double income;
}
