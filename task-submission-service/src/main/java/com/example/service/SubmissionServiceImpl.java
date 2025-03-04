package com.example.service;

import com.example.model.Submission;
import com.example.model.dto.TaskDto;
import com.example.repository.SubmissionRepository;
import org.bouncycastle.pqc.crypto.ExhaustedPrivateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TaskService taskService;

    @Override
    public Submission submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception {
        TaskDto task = taskService.getTasksById(taskId,jwt);
        if(task != null) {
            Submission submission = new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGithubLink(githubLink);
            submission.setSubmissionTime(LocalDateTime.now());
            return submissionRepository.save(submission);
        }
        throw new Exception("Task not found with id " + taskId);
    }

    @Override
    public Submission getTaskSubmissionById(Long submission) throws Exception {
        return submissionRepository.findById(submission).orElseThrow(()-> new ExhaustedPrivateKeyException("Task submission not found with id: "+submission));
    }

    @Override
    public List<Submission> getAllTaskSubmissions() throws Exception {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionByTaskId(Long taskId) throws Exception {
        return submissionRepository.findByTaskId(taskId);
    }

    @Override
    public Submission acceptDeclineSubmission(Long id, String status) throws Exception {
        Submission submission = getTaskSubmissionById(id);
        submission.setStatus(status);
        if(status.equals("ACCEPT")) {
            taskService.completeTask(submission.getTaskId());
        }

        return submissionRepository.save(submission);
    }
}
