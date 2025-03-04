package com.example.service;

import com.example.mapper.TaskMapper;
import com.example.model.Task;
import com.example.model.TaskStatus;
import com.example.model.dto.TaskDto;
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

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public Task createTask(TaskDto taskDto, String requestRole) throws Exception {
        if(!requestRole.equals("ROLE_ADMIN")){
            throw new Exception("only admin can create task");
        }

        taskDto.setCreatedTime(LocalDateTime.now());
        Task task = taskMapper.toEntity(taskDto);
        task.setStatus(TaskStatus.PENDING);

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
    public Task updateTask(TaskDto taskDto, Long id, Long userId) throws Exception {
        Task existingTask = getTaskById(id);
        if(taskDto.getTitle() != null){
            existingTask.setTitle(taskDto.getTitle());
        }

        if(taskDto.getDescription() != null){
            existingTask.setDescription(taskDto.getDescription());
        }

        if(taskDto.getStatus() != null){
            existingTask.setStatus(taskDto.getStatus());
        }

        if(taskDto.getImage() != null){
            existingTask.setImage(taskDto.getImage());
        }

        if(taskDto.getDeadline() != null){
            existingTask.setDeadline(taskDto.getDeadline());
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
