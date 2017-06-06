package com.springapp.mvc.services;

import com.springapp.mvc.dao.TaskDao;
import com.springapp.mvc.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Component
public class TaskService{

    @Autowired
    private TaskDao taskDao;

    public void addTask(Task task) {
        taskDao.add(task);
    }

    public void updateTask(Task task) {
        taskDao.update(task);
    }

    public void removeTaskById(int id) {
        taskDao.removeById(id);
    }

    public Task getTaskById(int id) {
      return taskDao.getTaskById(id);
    }

    public List<Task> getTasksList() {return taskDao.getTasksList();
    }
}

