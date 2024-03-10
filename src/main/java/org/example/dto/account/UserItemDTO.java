package org.example.dto.account;

import lombok.Data;

@Data
public class UserItemDTO {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;
}
