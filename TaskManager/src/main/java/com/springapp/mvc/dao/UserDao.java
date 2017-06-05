package com.springapp.mvc.dao;

import com.springapp.mvc.models.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    protected SessionFactory sessionFactory;

    public void add(User user){
        getSession().persist(user);
        logger.info(user + " added");
    }

    public void update(User user){
        getSession().update(user);
        logger.info(user + " updated");
    }

    public void removeUserById(int id){
        Session session = getSession();
        User user = (User) session.load(User.class, new Integer(id));
        if (user != null){
            session.delete(user);
            logger.info(user + " removed");
        }
        else {
            logger.info("Can't remove user. User is null");
        }
    }

    @SuppressWarnings("unchecked")
    public User getUserById(int id){
        logger.info("Getting model by id: " + id);
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("id", id));
        return (User)criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<User> getUsersList(){
        logger.info("Getting criteria list");
        Criteria criteria =  getSession().createCriteria(User.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public void removeByUsername(String username) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, new String(username));
        if (user != null){
            session.delete(user);
        }
        logger.info("User deleted " + user);
    }

    public User getUserByUsername(String username) {
        Session session = this.sessionFactory.openSession();
        User user = (User) session.load(User.class, new String(username));
        logger.info("User loaded" + user);
        session.close();
        return user;
    }

    public Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }
}