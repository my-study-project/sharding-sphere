package com.js.mapper;


import com.js.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2021-08-17 21:27:55
 */
@Mapper
public interface UserMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User findById(@Param("id") Long id);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    List<User> find(User user);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    Integer insert(User user);

    /**
     * 批量新增
     *
     * @param users 实例对象的集合
     * @return 影响行数
     */
    Integer batchInsert(@Param("users") List<User> users);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    Integer updateById(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteById(@Param("id") Long id);

    /**
     * 通过条件删除数据
     *
     * @param user 删除条件
     * @return 影响行数
     */
    Integer deleteByEntity(User user);

    /**
     * 通过ID批量删除数据除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteByIds(@Param("list") List<Long> list);

    /**
     * 条件查询总数
     *
     * @return 数据总数
     */
    Integer countByEntity(User user);

}