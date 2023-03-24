package com.vojtechruzicka.javafxweaverexample.entity.dto;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Citizenship;
import com.vojtechruzicka.javafxweaverexample.entity.domain.City;
import com.vojtechruzicka.javafxweaverexample.entity.domain.Disability;
import com.vojtechruzicka.javafxweaverexample.entity.domain.MaritalStatus;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class UpdateClientDto {
    @NonNull
    private String surname;
    @NonNull
    private String name;
    @NonNull
    private String secondName;
    @NonNull
    private Date date;
    @NonNull
    private String passportSeries;
    @NonNull
    private String passportNumber;
    @NonNull
    private String issuer;
    @NonNull
    private Date issueDate;
    @NonNull
    private String passportId;
    @NonNull
    private String birthplace;
    @NonNull
    private City residentialCity;
    @NonNull
    private String actualResidentialAddress;
    @NonNull
    private String homePhoneNumber;
    @NonNull
    private String mobilePhoneNumber;
    @NonNull
    private String email;
    @NonNull
    private String workPlace;
    @NonNull
    private String post;
    @NonNull
    private String residentialAddress;
    @NonNull
    private MaritalStatus maritalStatus;
    @NonNull
    private Citizenship citizenship;
    @NonNull
    private Disability disability;
    @NonNull
    private boolean retiree;
    @NonNull
    private Double income;
    @NonNull
    private boolean conscripted;
}
