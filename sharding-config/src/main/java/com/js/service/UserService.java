package com.js.service;

import com.js.mapper.UserMapper;
import com.js.entity.User;

import java.util.List;

public interface UserService {

    UserMapper getUserMapper();
    /** 根据id查询 */
    User findById(Long id);

    /**条件查询**/
    List<User> find(User User);

    /** 插入新数据 */
    int insert(User User);

    /**批量插入**/
    int batchInsert(List<User> list);

    /**根据id修改数据**/
    int updateById(User User);

    /**批量修改暂时不好用**/
    int updateBatch(List<User> list);

    /**根据id删除**/
    int deleteById(Long id);

    /**通过条件删除数据**/
    int deleteByEntity(User User);

    /** 通过ID批量删除数据除数据  **/
    int deleteByIds(List<Long> list);

    /**条件查询总数**/
    int countByEntity(User User);
}