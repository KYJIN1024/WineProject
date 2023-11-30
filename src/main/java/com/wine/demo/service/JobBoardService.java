package com.wine.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wine.demo.entity.JobEntity;
import com.wine.demo.entity.ProducerEntity;
import com.wine.demo.repository.JobBoardRepository;
import com.wine.demo.repository.JobLikeRepository;
import com.wine.demo.repository.UserRepository;

@Service
public class JobBoardService {
	
	@Autowired
    private JobBoardRepository jobBoardRepository;

	@Autowired
	private JobLikeRepository jobLikeRepository;
	 
	@Autowired
	private UserRepository userRepository; 
	
	

	public List<JobEntity> getAllJobBoards() {
	    return jobBoardRepository.findAll();
	}
	
	public Optional<JobEntity> getjobBoardById(Integer id) {
	        return jobBoardRepository.findById(id);
	    }

	public JobEntity saveJobBoard(JobEntity jobEntity) {
	        return jobBoardRepository.save(jobEntity);
	    }
	
	public Page<JobEntity> getAllJobBoards(Pageable pageable) {
	    return jobBoardRepository.findAll(pageable);
	    }
	 
	public String saveImage(MultipartFile file) throws IOException {
	        String directoryPath = "D:\\uploaded_files";
	        File dir = new File(directoryPath);

	        // 디렉토리 확인 및 생성
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }

	        // 파일 확장자 확인
	        String originalFileName = file.getOriginalFilename();
	        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();

	        // 파일 저장
	        String savedFileName = UUID.randomUUID() + "." + extension;
	        File targetFile = new File(dir, savedFileName);

	        file.transferTo(targetFile);

	        return directoryPath + "\\" + savedFileName;
	    } 
	 
	public void updateJobBoard(Integer id, JobEntity updatedJob) {
	        Optional<JobEntity> jobOpt = jobBoardRepository.findById(id);

	        if (jobOpt.isPresent()) {
	        	JobEntity existingJob = jobOpt.get();
	            
	            existingJob.setJobboardtitle(updatedJob.getJobboardtitle());
	            existingJob.setJobboardcontent(updatedJob.getJobboardcontent());
	           
	            jobBoardRepository.save(existingJob);
	        } else {
	            throw new RuntimeException("Job not found!");
	        }
	    }
	    
	public void deleteJobBoard(Integer id) {
	    	jobBoardRepository.deleteById(id);
	    }
	
}
