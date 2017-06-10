package com.springapp.mvc.dao;

import com.springapp.mvc.models.Task;

import java.util.List;

public interface ITaskDao {
    public void add(Task task);
    public void update(Task task);
    public void removeById(int id);
    public Task getById(int id);
    public List<Task> getAll();
}
