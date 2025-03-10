package com.example.model.dto;

import com.example.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;

    private String title;

    private String description;

    private String image;

//    @NotNull(message = "Người được giao không được để trống")
//    private Long assignedUserId;

    private List<String> tags = new ArrayList<>();

    //    @NotNull(message = "Trạng thái không được để trống")
//    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDateTime deadline;

    private LocalDateTime createdTime;

}


