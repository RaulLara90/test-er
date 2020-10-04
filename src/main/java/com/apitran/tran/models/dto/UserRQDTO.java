package com.apitran.tran.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRQDTO {
    @NotNull(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Last name is mandatory")
    private String lastName;
    @NotNull(message = "Phone is mandatory")
    private String phone;
}
