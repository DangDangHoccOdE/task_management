package com.example.mapper;

import com.example.model.Task;
import com.example.model.dto.TaskDto;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDto toDto(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getImage(),
//                task.getTags(),
                task.getStatus(),
                task.getDeadline(),
                task.getCreatedTime()
        );
    }

    public Task toEntity(TaskDto taskDto) {
        if (taskDto == null) {
            return null;
        }
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setImage(taskDto.getImage());
//        task.setTags(taskDto.getTags());
        task.setStatus(taskDto.getStatus());
        task.setDeadline(taskDto.getDeadline());
        task.setCreatedTime(taskDto.getCreatedTime());
        return task;
    }
}

