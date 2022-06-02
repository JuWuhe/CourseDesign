package com.course.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
    public static final String SESSION_KEY = "login_user";
    private Integer userId;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;

    private String information;

    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getInformation(){
        return information;
    }
}
