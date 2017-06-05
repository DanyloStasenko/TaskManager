package com.springapp.mvc.services;

import com.springapp.mvc.dao.UserDao;
import com.springapp.mvc.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Component
public class UserService {

    @Autowired
    private UserDao userDao;

    public void addUser(User user) {
        userDao.add(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void removeUserById(String id) {
        userDao.removeByUsername(id);
    }

    public User getUserById(int id) {
        User user = userDao.getUserById(id);
        return user;
    }

    public List<User> getUsersList() {
        List<User> list = userDao.getUsersList();
        return list;
    }

    public User getUserByName(String username) {
        User user = userDao.getUserByUsername(username);
        return user;
    }
}
