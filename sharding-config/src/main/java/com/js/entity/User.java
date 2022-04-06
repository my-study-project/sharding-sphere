package com.js.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (User)实体类
 *
 * @author 渡劫 dujie
 * @since 2022-04-06 09:53:12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 城市
     */
    private Integer cityId;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 密码
     */
    private String password;

}