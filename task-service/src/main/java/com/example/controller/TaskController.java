package com.example.controller;

import com.example.model.Task;
import com.example.model.TaskStatus;
import com.example.model.dto.TaskDto;
import com.example.model.dto.UserDto;
import com.example.service.TaskService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Task> createTask(@Validated @RequestBody TaskDto task,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto userDto = userService.getUserProfile(jwt);
        Task createdTask = taskService.createTask(task, userDto.getRole());

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTasksById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto userDto = userService.getUserProfile(jwt);
        Task createdTask = taskService.getTaskById(id);

        return new ResponseEntity<>(createdTask, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Task>> getAssignedUserTask(@RequestParam(required = false)TaskStatus status,
                                                         @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto userDto = userService.getUserProfile(jwt);
        List<Task> tasks = taskService.assignedUsersTasks(userDto.getId(), status);

        return new ResponseEntity<>(tasks, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user = userService.getUserProfile(jwt);

        List<Task> tasks= taskService.getAllTasks(status);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}/user/{userId}/assigned")
    public ResponseEntity<Task> assignedTaskToUser(
            @PathVariable Long id,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user = userService.getUserProfile(jwt);

        Task tasks= taskService.assignedToUser(userId,id);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @Validated @RequestBody TaskDto taskDto,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user = userService.getUserProfile(jwt);

        Task tasks= taskService.updateTask(taskDto,id,user.getId());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(
            @PathVariable Long id) throws Exception {
        Task tasks= taskService.completeTask(id);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id) throws Exception {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
