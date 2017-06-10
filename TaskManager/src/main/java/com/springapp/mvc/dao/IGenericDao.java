package com.springapp.mvc.dao;

import java.util.List;

public interface IGenericDao<T> {
    public void add(T model);
    public void update(T model);
    public void removeById(int id);
    public T getById(int id);
    public List<T> getAll();
}
