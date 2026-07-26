package com.project.jobms;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping()
    public ResponseEntity<List<Job>> findAll() {
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping()
    public ResponseEntity<String> createJob(@RequestBody Job job) {
        jobService.createJob(job);
        return ResponseEntity.ok("Job added successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        if(job != null) {
            return ResponseEntity.ok(job);
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id) {
        boolean deleted = jobService.deleteJobById(id);
        if(deleted) {
            return ResponseEntity.ok("Job successfully deleted");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateJobById(@PathVariable Long id, @RequestBody Job updateJob) {
        boolean jobUpdated = jobService.updateJobById(id, updateJob);
        if(jobUpdated) {
            return ResponseEntity.ok("Job updated successfully");
        }

        return ResponseEntity.notFound().build();
    }

}
