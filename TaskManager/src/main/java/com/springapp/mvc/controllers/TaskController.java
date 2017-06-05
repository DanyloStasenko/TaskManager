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


    @RequestMapping("/tasks/share")
    public String shareWith(@ModelAttribute("task") Task task){

        String shareWith = task.getRecentlySharedTo();
        task.setRecentlySharedTo("Shared with " + shareWith);

        taskService.updateTask(task);
        com.springapp.mvc.models.User user = userService.getUserByName(shareWith);

        user.getTasks().add(task);
        userService.updateUser(user);

        return "redirect:/tasks";
    }


    @RequestMapping("/share/{id}")
    public String shareTask(@PathVariable("id") int id, Model model){

        model.addAttribute("task", taskService.getTaskById(id));

        return "share";
    }

    @RequestMapping(value = "/tasks/add", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("task") Task task){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String name = user.getUsername();

        if (task.getId() == 0){
            com.springapp.mvc.models.User manager = userService.getUserByName(name);
            taskService.addTask(task);

            manager.getTasks().add(task);
            userService.updateUser(manager);
        }

        else {
            com.springapp.mvc.models.User manager = userService.getUserByName(name);
            task.addManager(manager);
            taskService.updateTask(task);
        }

        return "redirect:/tasks";
    }

    @RequestMapping("/remove/{id}")
    public String removeTask(@PathVariable("id") int id){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String name = user.getUsername();

        Task task = taskService.getTaskById(id);
        com.springapp.mvc.models.User manager = userService.getUserByName(name);
        manager.getTasks().remove(task);

        userService.updateUser(manager);
        taskService.removeTaskById(id);

        return "redirect:/tasks";
    }

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
