package com.springapp.mvc.services;

import com.springapp.mvc.dao.UserDao;
import com.springapp.mvc.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Component
public class UserService {

    @Autowired(required = true)
    @Qualifier(value = "userDao")
    private UserDao userDao;

    public void addUser(User user) {
        if (!user.getUsername().isEmpty() && !user.getPassword().isEmpty() && !user.getEmail().isEmpty()){
            if (!getUsersList().contains(user)){
                userDao.add(user);
            }
        }
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public List<User> getUsersList() {
        List<User> list = userDao.getAll();
        return list;
    }

    public User getUserByName(String username) {
        User user = userDao.getUserByUsername(username);
        return user;
    }

    public User getUserByEmail(String email) {
        List<User> allUsers = getUsersList();
        for (User currentUser : allUsers) {
            if (currentUser.getEmail() != null){
                if (currentUser.getEmail().equals(email)){
                    return currentUser;
                }
            }
        }
        return null;
    }
}
