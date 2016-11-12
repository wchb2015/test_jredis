package org.jredisDemo.dao;

import org.jredisDemo.model.User;

import java.util.List;

public interface IUserDao {

    boolean add(User user);

    /**
     * 批量新增 使用pipeline方式
     *
     * @param list
     * @return
     */
    boolean add(List<User> list);

    int delete(String key);

    int delete(List<String> keys);

    boolean update(User user);

    User get(String keyId);

}
