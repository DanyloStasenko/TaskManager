package com.springapp.mvc.services;

import com.springapp.mvc.dao.TaskDao;
import com.springapp.mvc.helpers.AuthHelper;
import com.springapp.mvc.models.Task;
import com.springapp.mvc.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Component
public class TaskService{

    @Autowired(required = true)
    @Qualifier(value = "taskDao")
    private TaskDao taskDao;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "authHelper")
    private AuthHelper authHelper;

    /**
     * Adding task and setting it's creator as manager
     */
    public void addTask(Task task) {
        User taskCreator = authHelper.getLoggedInUser();

        taskDao.add(task);
        taskCreator.getTasks().add(task);
        userService.updateUser(taskCreator);
    }

    /**
     * Updating
     */
    public void updateTask(Task task) {
        taskDao.update(task);
    }

    /**
     * Removing
     */
    public void removeTaskById(int id) {
        Task taskToDelete = taskDao.getById(id);
        List<User> users = userService.getUsersList();

        // Unplugging task from all managers before deleting
        for (User user : users) {
            if (user.getTasks().contains(taskToDelete)) {
                user.getTasks().remove(taskToDelete);
                userService.updateUser(user);
            }
        }
        taskDao.removeById(id);
    }

    /**
     * Getting task
     */
    public Task getTaskById(int id) {
        return taskDao.getById(id);
    }

    /**
     * Sharing task and changing it's status
     */
    public void shareTask(Task task){

        User manager = authHelper.getLoggedInUser();

        // Changing status
        String shareWith = task.getStatus();
        task.setStatus("Shared with " + shareWith + " by " + manager.getUsername());
        updateTask(task);

        // Making available to manage
        User user = userService.getUserByEmail(shareWith);
        user.getTasks().add(task);
        userService.updateUser(user);
    }

    /**
     * Getting all tasks
     */
    public List<Task> getTasksList() {
        return taskDao.getAll();
    }

    /**
     * Removing available to manage tasks from list
     */
    public List<Task> getNotAvailableToManageTasks() {
        List<Task> notAvailableToManage = getTasksList();
        notAvailableToManage.removeIf(task -> task.getManagers().contains(authHelper.getLoggedInUser()));

        return notAvailableToManage;
    }

    /**
     * Removing not available to manage tasks from list
     */
    public List<Task> getAvailableToManageTasks() {
        List<Task> availableToManage = getTasksList();
        availableToManage.removeIf(task -> !task.getManagers().contains(authHelper.getLoggedInUser()));

        return availableToManage;
    }
}
