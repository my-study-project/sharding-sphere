package com.js.pojo;

import lombok.Data;

/**
 * (User)实体类
 *
 * @author 渡劫 dujie
 * @since 2021-08-17 21:27:53
 */
@Data
public class User {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    private String userNameEncrypt;
    /**
     * 密码
     */
    private String password;
    private String passwordEncrypt;
    /**
     * 性别
     */
    private String gender;

}