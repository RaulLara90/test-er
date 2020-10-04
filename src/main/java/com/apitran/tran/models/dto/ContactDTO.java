package com.apitran.tran.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class ContactDTO {

    @NotNull
    private String contactName;
    @NotNull
    private String phone;
}
