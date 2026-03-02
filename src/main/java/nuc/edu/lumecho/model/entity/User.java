package nuc.edu.lumecho.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String account;     //登录账号
    private String phone;       //手机号
    private String password;
    private String username;    //用户名
    private String email;
    private String bio;         //简介
    private Integer status;     //状态
    private String role;        //角色
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime deletedAt;
}
