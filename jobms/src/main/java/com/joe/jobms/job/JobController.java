package com.joe.jobms.job;

import com.joe.jobms.job.dto.JobDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
@Autowired
    private JobService jobService;

 @GetMapping
    public ResponseEntity<List<JobDTO>> findAll(){

     List<JobDTO> all= jobService.findAll();
     return new ResponseEntity<>(all,HttpStatus.OK);

 }
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        JobDTO jobDTO = jobService.getJobById(id);
        if(jobDTO !=null){
            return new ResponseEntity<>(jobDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
     return new ResponseEntity<>("Job added successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        Boolean job= jobService.deleteJobById(id);
        if(job){
            return new ResponseEntity<>("Job is deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>( "Job was not found",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJobById(@PathVariable Long id,@RequestBody Job job){
       Boolean updateStatus= jobService.updateJob(id,job);

        if(updateStatus){
            return new ResponseEntity<>("Job is updated", HttpStatus.OK);
        }
        return new ResponseEntity<>( "Job was not found",HttpStatus.NOT_FOUND);
    }


}
