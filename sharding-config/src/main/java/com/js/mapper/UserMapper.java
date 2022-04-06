package com.js.mapper;

import com.js.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author 渡劫
 * @since 2022-04-06 09:53:14
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
     * @param User 实例对象
     * @return 对象列表
     */
    List<User> find(User User);

    /**
     * 新增数据
     *
     * @param User 实例对象
     * @return 影响行数
     */
    int insert(User User);

    /**
     * 批量新增
     *
     * @param Users 实例对象的集合
     * @return 影响行数
     */
    int batchInsert(@Param("Users") List<User> Users);

    /**
     * 修改数据
     *
     * @param User 实例对象
     * @return 影响行数
     */
    int updateById(User User);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 通过条件删除数据
     *
     * @param User 删除条件
     * @return 影响行数
     */
    int deleteByEntity(User User);

    /**
     * 通过ID批量删除数据除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    int deleteByIds(@Param("list") List<Long> list);

    /**
     * 条件查询总数
     *
     * @return 数据总数
     */
    int countByEntity(User User);

    /**
     * 批量修改
     *
     * @return 修改成功的条数
     */
    int updateBatch(@Param("list") List<User> list);

}