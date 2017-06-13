package com.springapp.mvc.controllers;

import com.springapp.mvc.helpers.AuthHelper;
import com.springapp.mvc.models.Task;
import com.springapp.mvc.services.TaskService;
import com.springapp.mvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/")
public class TaskController {

    @Autowired(required = true)
    @Qualifier(value = "taskService")
    private TaskService taskService;

    @Autowired(required = true)
    @Qualifier(value = "authHelper")
    private AuthHelper authHelper;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    private UserService userService;

    /**
     * Creating page for users
     */
    @RequestMapping("/users")
    public String users(Model model){
        model.addAttribute("users", userService.getUsersList());
        return "users";
    }

    /**
     * Creating page for sharing
     */
    @RequestMapping("/share/{id}")
    public String shareTask(@PathVariable("id") int id, Model model){
        if (isManageAllowed(taskService.getTaskById(id))){
            model.addAttribute("task", taskService.getTaskById(id));
            return "share";
        } else {
            model.addAttribute("message", "Not enough rights to share this task");
            return "error";
        }
    }

    /**
     * Sharing this task with user
     */
    @RequestMapping("/tasks/share")
    public String shareWith(@ModelAttribute("task") Task task){
        taskService.shareTask(task);
        return "redirect:/tasks";
    }

    /**
     * Creating page for editing
     */
    @RequestMapping("edit/{id}")
    public String editTask(@PathVariable("id") int id, Model model){
        if (isManageAllowed(taskService.getTaskById(id))){
            model.addAttribute("tasks", taskService.getNotAvailableToManageTasks());
            model.addAttribute("managing", taskService.getAvailableToManageTasks());
            model.addAttribute("task", taskService.getTaskById(id));
            return "tasks";
        } else {
            model.addAttribute("message", "Not enough rights to edit this task");
            return "error";
        }
    }

    /**
     * Adding and editing tasks
     */
    @RequestMapping(value = "/tasks/add", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("task") Task task){
        if (task.getId() == 0){
            taskService.addTask(task);
        } else {
            taskService.updateTask(task);
        }
        return "redirect:/tasks";
    }

    /**
     * Removing
     */
    @RequestMapping("/remove/{id}")
    public String removeTask(@PathVariable("id") int id, Model model){
        if (isManageAllowed(taskService.getTaskById(id))){
            taskService.removeTaskById(id);
            return "redirect:/tasks";
        } else {
            model.addAttribute("message", "Not enough rights to remove this task");
            return "error";
        }
    }

    /**
     * Creating page with tasks list
     */
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
	public String getAllTasks(ModelMap model) {

        model.addAttribute("task", new Task());
        model.addAttribute("tasks", taskService.getNotAvailableToManageTasks());
        model.addAttribute("managing", taskService.getAvailableToManageTasks());

        return "tasks";
    }

    /**
     * Checking if current user has enough rights to manage this task
     */
    private boolean isManageAllowed(Task task){
        return task.getManagers().contains(authHelper.getLoggedInUser());
    }
}
