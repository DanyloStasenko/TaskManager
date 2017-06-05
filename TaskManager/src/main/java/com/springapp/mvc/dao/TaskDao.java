package com.springapp.mvc.dao;

import com.springapp.mvc.models.Task;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskDao{
    private static final Logger logger = LoggerFactory.getLogger(TaskDao.class);

    @Autowired
    protected SessionFactory sessionFactory;

    public void add(Task task){
        getSession().persist(task);
        logger.info(task + " added");
    }

    public void update(Task task){
        getSession().update(task);
        logger.info(task + " updated");
    }

    public void merge(Task task){
        getSession().merge(task);
        logger.info(task + " merged");
    }

    public void removeById(int id){
        Session session = this.sessionFactory.getCurrentSession();
        Task task = (Task) session.load(Task.class, new Integer(id));
        if (task != null){
            session.delete(task);
        }
        logger.info("Task deleted" + task);
    }

    public Task getTaskById(int id){
        Task task = (Task) getSession().load(Task.class, new Integer(id));
        logger.info("Task loaded" + task);

        return task;
    }

    @SuppressWarnings("unchecked")
    public List<Task> getTasksList(){
        /*Session session = sessionFactory.openSession();
        List<Task> tasksList = session.createQuery("from Task").list();

        for(Task task : tasksList)
        {
            logger.info("Domain list: " + task);
        }

        return tasksList;*/

        logger.info("Getting list of tasks");
        Criteria criteria = getSession().createCriteria(Task.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }
}