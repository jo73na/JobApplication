package com.joe.jobms.job;

import java.util.List;
import com.joe.jobms.job.dto.JobDTO;

public interface JobService {
    public List<JobDTO> findAll();
    public void createJob(Job job);
    JobDTO getJobById(Long id);
    Boolean deleteJobById(Long id);
    Boolean updateJob(Long id, Job job);
}
