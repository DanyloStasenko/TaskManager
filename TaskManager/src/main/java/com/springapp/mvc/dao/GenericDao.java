package com.springapp.mvc.dao;

import com.springapp.mvc.models.Model;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDao<T extends Model> implements IGenericDao<T>{
    private static final Logger logger = LoggerFactory.getLogger(GenericDao.class);

    @Autowired
    protected SessionFactory sessionFactory;

    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    public GenericDao() {
        final ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.clazz = (Class<T>) ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public void add(T model){
        getSession().persist(model);
        logger.info(model + " added");
    }

    public void update(T model){
        getSession().update(model);
        logger.info(model + " updated");
    }

    public void removeById(int id){
        Session session = getSession();
        T model = (T) session.load(clazz, new Integer(id));
        if (model != null){
            session.delete(model);
            logger.info(model + " removed");
        }
        else {
            logger.info("Can't remove model. Model is null");
        }
    }

    @SuppressWarnings("unchecked")
    public T getById(int id){
        logger.info("Getting model by id: " + id);
        Criteria criteria = getSession().createCriteria(this.clazz);
        criteria.add(Restrictions.eq("id", id));
        return (T)criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll(){
        logger.info("Getting criteria list");
        Criteria criteria =  getSession().createCriteria(this.clazz);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }
}
