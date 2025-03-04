package com.example.controller;

import com.example.model.Submission;
import com.example.model.dto.UserDto;
import com.example.service.SubmissionService;
import com.example.service.TaskService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @PostMapping("")
    public ResponseEntity<Submission> submitTask(
            @RequestParam Long task_id,
            @RequestParam String githubLink,
            @RequestHeader("Authorization") String jwt
    )throws Exception{
        UserDto userDto = userService.getUserProfiles(jwt);
        Submission submission = submissionService.submitTask(task_id,githubLink,userDto.getId(),jwt);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    )throws Exception{
        UserDto userDto = userService.getUserProfiles(jwt);
        Submission submission = submissionService.getTaskSubmissionById(id);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Submission>> getAllSubmissionsById(
            @PathVariable Long submissionId,
            @RequestHeader("Authorization") String jwt
    )throws Exception{
        UserDto userDto = userService.getUserProfiles(jwt);
        List<Submission> submission = submissionService.getAllTaskSubmissions();
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Submission>> getTaskSubmissionsByTaskId(
            @PathVariable Long taskId,
            @RequestHeader("Authorization") String jwt
    )throws Exception{
        UserDto userDto = userService.getUserProfiles(jwt);
        List<Submission> submission = submissionService.getTaskSubmissionByTaskId(taskId);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> acceptOrDeclineSubmission(
            @PathVariable Long id,
            @RequestParam("status") String status,
            @RequestHeader("Authorization") String jwt
    )throws Exception{
        UserDto userDto = userService.getUserProfiles(jwt);
        Submission submission = submissionService.acceptDeclineSubmission(id,status);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }
}
