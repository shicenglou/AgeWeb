package com.age.security.model;

import lombok.Data;

import java.util.List;

@Data
public class MiniUser {
    private String id;
    private String username;
    private String phone;
    private String password;
    private List<String> permissionValueList;
}
