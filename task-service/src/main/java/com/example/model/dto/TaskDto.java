package com.example.model.dto;
import com.example.model.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255, message = "Tiêu đề không được vượt quá 255 ký tự")
    private String title;

    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String description;

    @Size(max = 500, message = "URL ảnh không được vượt quá 500 ký tự")
    private String image;

//    @NotNull(message = "Người được giao không được để trống")
//    private Long assignedUserId;

//    private List<String> tags = new ArrayList<>();

    //    @NotNull(message = "Trạng thái không được để trống")
//    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @FutureOrPresent(message = "Hạn chót phải là ngày hiện tại hoặc tương lai")
    private LocalDateTime deadline;

    private LocalDateTime createdTime;

}

