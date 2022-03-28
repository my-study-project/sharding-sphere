package com.js.controller;


import com.alibaba.fastjson.JSONObject;
import com.js.mapper.UserMapper;
import com.js.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/api/order")
@Slf4j
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询订单
     */
    @GetMapping("/list")
    public ResponseEntity<List<User>> list() {
        return ResponseEntity.ok().body(userMapper.find(new User()));
    }

    /**
     * 插入订单
     */
    @PostMapping("/save")
    public ResponseEntity<List<User>> save(@RequestBody User order) {
        // 这里只是简单的演示，正式代码记得不要直接传实体对象，要传递VO对象进行转换，并且要做参数校验。
        log.debug("[插入订单]>>>> order={}", JSONObject.toJSONString(order));
        boolean ret = userMapper.insert(order) > 0;
        return ret ? ResponseEntity.ok().body(userMapper.find(new User())) : ResponseEntity.badRequest().body(new ArrayList<>());
    }
}
