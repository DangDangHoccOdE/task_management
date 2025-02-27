package com.example.service;

import com.example.model.Task;
import com.example.model.TaskStatus;
import com.example.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task, String requestRole) throws Exception {
        if(!requestRole.equals("ROLE_ADMIN")){
            throw new Exception("only admin can create task");
        }

        task.setStatus(TaskStatus.PENDING);
        task.setCreatedTime(LocalDateTime.now());

        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepository.findById(id).orElseThrow(() -> new Exception("task not found with id: " + id));
    }

    @Override
    public List<Task> getAllTasks(TaskStatus taskStatus) throws Exception {
        List<Task> allTasks = taskRepository.findAll();
        List<Task> filteredTasks = allTasks.stream().filter(
                task -> taskStatus==null || task.getStatus().name().equalsIgnoreCase(taskStatus.toString())
        ).collect(Collectors.toList());
        return filteredTasks;
    }

    @Override
    public Task updateTask(Task task, Long id, Long userId) throws Exception {
        Task existingTask = getTaskById(id);
        if(task.getTitle() != null){
            existingTask.setTitle(task.getTitle());
        }

        if(task.getDescription() != null){
            existingTask.setDescription(task.getDescription());
        }

        if(task.getStatus() != null){
            existingTask.setStatus(task.getStatus());
        }

        if(task.getImage() != null){
            existingTask.setImage(task.getImage());
        }

        if(task.getDeadline() != null){
            existingTask.setDeadline(task.getDeadline());
        }
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) throws Exception {
        getTaskById(id);
        taskRepository.deleteById(id);
    }

    @Override
    public Task assignedToUser(Long userId, Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUsersTasks(Long userId, TaskStatus status) throws Exception {
        List<Task> allTasks = taskRepository.findByAssignedUserId(userId);

        List<Task> filteredTasks = allTasks.stream().filter(
                task -> status==null || task.getStatus().name().equalsIgnoreCase(status.toString())
        ).collect(Collectors.toList());
        return filteredTasks;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);

        return taskRepository.save(task);
    }
}
