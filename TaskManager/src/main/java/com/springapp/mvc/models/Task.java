package com.springapp.mvc.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "created_by")
    private String recentlySharedTo;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tasks")
    private Set<User> users = new HashSet<User>();

    public Task(String title) {
        this.title = title;
    }

    public Task() {
    }

    public void addManager(User manager){
        users.add(manager);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecentlySharedTo() {
        return recentlySharedTo;
    }

    public void setRecentlySharedTo(String recentlySharedTo) {
        this.recentlySharedTo = recentlySharedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != task.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", recentlySharedTo='" + recentlySharedTo + '\'' +
                  ", users=" + users +
                '}';
    }
}
