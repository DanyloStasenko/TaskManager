package com.springapp.mvc.controllers;

import com.springapp.mvc.models.Task;
import com.springapp.mvc.models.User;
import com.springapp.mvc.services.TaskService;
import com.springapp.mvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


    /**
     * Redirecting user to registration-page
     */
    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new com.springapp.mvc.models.User());
        return "register";
    }


    /**
     * Adding new user if username and password are not empty. Available for unauthorized users.
     */
    @RequestMapping(value = "/register/adduser", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") com.springapp.mvc.models.User user){

        if (!user.getUsername().isEmpty() && !user.getPassword().isEmpty()){
            userService.addUser(new com.springapp.mvc.models.User(user.getUsername(), user.getPassword()));
        }
        return "redirect:/tasks";
    }


    /**
     *  Getting [username] to share with and continue sharing
     */
    @RequestMapping("/share/{id}")
    public String shareTask(@PathVariable("id") int id, Model model){

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByName(loggedInUser.getName());

        // checking whether the user has the rights to share this task
        if (!taskService.getTaskById(id).getUsers().contains(manager)){
            return "redirect:/tasks";
        }

        model.addAttribute("task", taskService.getTaskById(id));
        return "share";
    }


    /**
     *  Sharing this task with user.
     * 'Sharing' means making task available to manage.
     *  and changing task's status to 'shared with [username]'
     */
    @RequestMapping("/tasks/share")
    public String shareWith(@ModelAttribute("task") Task task){

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByName(loggedInUser.getName());

        // Changing status
        String shareWith = task.getStatus();
        task.setStatus("Shared with " + shareWith + " by " + manager.getUsername());
        taskService.updateTask(task);

        // Making available to manage
        User user = userService.getUserByName(shareWith);
        user.getTasks().add(task);
        userService.updateUser(user);

        return "redirect:/tasks";
    }


    /**
     *  Implementation of adding and editing tasks
     */
    @RequestMapping(value = "/tasks/add", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("task") Task task){

        // Getting user who's created this task
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User taskCreator = userService.getUserByName(loggedInUser.getName());

        // Adding task
        if (task.getId() == 0){
            taskService.addTask(task);

            // making this task available to manage for it's creator
            taskCreator.getTasks().add(task);
            userService.updateUser(taskCreator);
        }

        // Updating task
        else {
            taskService.updateTask(task);
        }

        return "redirect:/tasks";
    }


    /**
     *  Implementation of removing tasks by ID
     */
    @RequestMapping("/remove/{id}")
    public String removeTask(@PathVariable("id") int id){

        // Getting user who's trying to remove this task
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByName(loggedInUser.getName());

        // Task must be shared with this user, otherwise user shouldn't remove this task
        if (taskService.getTaskById(id).getUsers().contains(manager)) {

            Task task = taskService.getTaskById(id);

            // Unplugging this task from all users in database
            List<com.springapp.mvc.models.User> users = userService.getUsersList();
            for (com.springapp.mvc.models.User currentUser : users) {
                if (currentUser.getTasks().contains(task)) {
                    currentUser.getTasks().remove(task);
                    userService.updateUser(currentUser);
                }
            }

            // Removing the task is available only after it's unplugged from other users
            taskService.removeTaskById(id);
        }

        return "redirect:/tasks";
    }


    /**
     *  Creating page to edit the task.
     *  This page is the same as 'tasks', but here's the form to edit task instead of create new one
     */
    @RequestMapping("edit/{id}")
    public String editTask(@PathVariable("id") int id, Model model){

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByName(loggedInUser.getName());

        // Removing available to manage tasks from 'allTasks' to avoid duplicated tasks on the page
        List<Task> allTasks = taskService.getTasksList();
        allTasks.removeIf(task -> task.getUsers().contains(manager));

        // Removing not available to manage tasks from 'availableToManage' list
        List<Task> availableToManage = taskService.getTasksList();
        availableToManage.removeIf(task -> !task.getUsers().contains(manager));

        model.addAttribute("tasks", allTasks);
        model.addAttribute("managing", availableToManage);
        model.addAttribute("task", this.taskService.getTaskById(id));

        return "tasks";
    }


    /**
     *  Getting page with all tasks.
     *
     *  Here are 2 lists, depends from user:
     *   - List with not available to manage tasks
     *   - List with available to manage tasks
     *
     *  User can also create new tasks from this page
     */
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
	public String getAllTasks(ModelMap model) {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByName(loggedInUser.getName());

        // Removing available to manage tasks from 'allTasks' to avoid duplicated tasks on the page
        List<Task> allTasks = taskService.getTasksList();
        allTasks.removeIf(task -> task.getUsers().contains(manager));

        // Removing not available to manage tasks from 'availableToManage' list
        List<Task> availableToManage = taskService.getTasksList();
        availableToManage.removeIf(task -> !task.getUsers().contains(manager));

        model.addAttribute("task", new Task());
        model.addAttribute("tasks", allTasks);
        model.addAttribute("managing", availableToManage);

        return "tasks";
    }
}
