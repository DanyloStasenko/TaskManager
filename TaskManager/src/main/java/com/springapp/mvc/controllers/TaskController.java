package com.springapp.mvc.controllers;

import com.springapp.mvc.models.Task;
import com.springapp.mvc.services.TaskService;
import com.springapp.mvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
public class TaskController {

    @Autowired(required = true)
    @Qualifier(value = "taskService")
    private TaskService taskService;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    private UserService userService;

    /** Changing task 'status' to 'shared with [username]'
     * and sharing this task with user
     * ('sharing' means making task available to manage) */
    @RequestMapping("/tasks/share")
    public String shareWith(@ModelAttribute("task") Task task){

        User manager = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String name = manager.getUsername();

        // Updating
        String shareWith = task.getStatus();
        task.setStatus("Shared with " + shareWith + " by " + name);
        taskService.updateTask(task);

        // Sharing
        com.springapp.mvc.models.User user = userService.getUserByName(shareWith);
        user.getTasks().add(task);
        userService.updateUser(user);

        return "redirect:/tasks";
    }

    /** Getting 'username' to share with */
    @RequestMapping("/share/{id}")
    public String shareTask(@PathVariable("id") int id, Model model){
        model.addAttribute("task", taskService.getTaskById(id));
        return "share";
    }

    /** Implementation of adding and editing tasks */
    @RequestMapping(value = "/tasks/add", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("task") Task task){

        // Getting user who created this task
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.springapp.mvc.models.User taskCreator = userService.getUserByName(user.getUsername());

        // Adding task
        if (task.getId() == 0){
            taskService.addTask(task);

            // making this task available to manage for user
            taskCreator.getTasks().add(task);
            userService.updateUser(taskCreator);
        }

        // Updating task
        else {
            taskService.updateTask(task);
        }

        return "redirect:/tasks";
    }

    /** Implementation of removing task by id */
    @RequestMapping("/remove/{id}")
    public String removeTask(@PathVariable("id") int id){

        // Getting user who is trying to remove this task
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.springapp.mvc.models.User manager = userService.getUserByName(user.getUsername());

        // Task must be shared with this user, otherwise user shouldn't remove this task
        Task task = taskService.getTaskById(id);
        if (manager.getTasks().contains(task)){

            // Deleting this task from all users
            List<com.springapp.mvc.models.User> users = userService.getUsersList();
            for (com.springapp.mvc.models.User currentUser : users) {
                if (currentUser.getTasks().contains(task)){
                    currentUser.getTasks().remove(task);
                    userService.updateUser(currentUser);
                }
            }
        }

        // Removing task after it is unplugged to other users
        taskService.removeTaskById(id);

        return "redirect:/tasks";
    }

    /** Getting page to edit the task.
     * This page looks like main page but user is editing task, instead of adding */
    @RequestMapping("edit/{id}")
    public String editTask(@PathVariable("id") int id, Model model){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String name = user.getUsername();

        com.springapp.mvc.models.User manager = userService.getUserByName(name);

        List<Task> allTasks = taskService.getTasksList();
        List<Task> availableToManage = taskService.getTasksList();

        allTasks.removeIf(task -> task.getUsers().contains(manager));
        availableToManage.removeIf(task -> !task.getUsers().contains(manager));

        model.addAttribute("task", this.taskService.getTaskById(id));
        model.addAttribute("tasks", allTasks);
        model.addAttribute("managing", availableToManage);

        return "tasks";
    }

    /** Getting page to manage the tasks.
     * By default, user can create new tasks from this page */
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
	public String getAllTasks(ModelMap model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String name = user.getUsername();

        com.springapp.mvc.models.User manager = userService.getUserByName(name);

        List<Task> nonManageTasks = taskService.getTasksList();
        List<Task> availableToManage = taskService.getTasksList();

        nonManageTasks.removeIf(task -> task.getUsers().contains(manager));
        availableToManage.removeIf(task -> !task.getUsers().contains(manager));

        model.addAttribute("task", new Task());
        model.addAttribute("tasks", nonManageTasks);
        model.addAttribute("managing", availableToManage);

        return "tasks";
    }

}
