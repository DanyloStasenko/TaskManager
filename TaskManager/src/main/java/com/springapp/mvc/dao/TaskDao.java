package com.springapp.mvc.dao;

import com.springapp.mvc.models.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskDao extends GenericDao<Task> implements ITaskDao{

}