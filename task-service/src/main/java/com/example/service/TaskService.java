package com.example.service;

import com.example.model.Task;
import com.example.model.TaskStatus;

import java.util.List;

public interface TaskService {
    Task createTask(Task task, String requestRole) throws Exception;
    Task getTaskById(Long id) throws Exception;
    List<Task> getAllTasks(TaskStatus taskStatus) throws Exception;
    Task updateTask(Task task, Long id, Long userId) throws Exception;
    void deleteTask(Long id) throws Exception;
    Task assignedToUser(Long userId, Long taskId) throws Exception;
    List<Task> assignedUsersTasks(Long userId, TaskStatus status) throws Exception;
    Task completeTask(Long taskId) throws Exception;

}
