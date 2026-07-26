package com.project.jobms;

import com.project.jobms.dto.JobWithCompanyDto;
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
    public ResponseEntity<List<JobWithCompanyDto>> findAll() {
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping()
    public ResponseEntity<String> createJob(@RequestBody Job job) {
        jobService.createJob(job);
        return ResponseEntity.ok("Job added successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobWithCompanyDto> getJobById(@PathVariable Long id) {
        var jobWithCompanyDto = jobService.getJobById(id);
        if(jobWithCompanyDto != null) {
            return ResponseEntity.ok(jobWithCompanyDto);
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
