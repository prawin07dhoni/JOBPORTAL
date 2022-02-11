package com.jobportalbackend.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jobportalbackend.repository.AdminRepository;
import com.jobportalbackend.repository.InterestedRepository;
import com.jobportalbackend.repository.JobPostingRepository;
import com.jobportalbackend.repository.JobSeekerRepository;
import com.jobportalbackend.entity.Admin;
import com.jobportalbackend.entity.JobPosting;
import com.jobportalbackend.entity.JobSeeker;

/**
 * @author rahul
 *
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/admin")

public class AdminController {

	
	
	 

		@Autowired
		AdminRepository adminRepo;
		
		@Autowired
		JobPostingRepository jobRepo;
		
		@Autowired
		InterestedRepository interestedRepo;
		
		@Autowired
		JobSeekerRepository jobSeekerRepo;

		@RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
		public String showJobSeeker(@PathVariable("id") int id, Model model){
			
			Admin admin = adminRepo.getCompany(id);
			
			model.addAttribute("admin", admin);
			return "companyprofile"; 
		}
		
		@RequestMapping(value = "/showjob", method = RequestMethod.GET)
		public String showJob(@RequestParam("cid") String cid, @RequestParam("jobId") String jobId, Model model) {
			
			
			JobPosting p1 = jobRepo.getJobPosting(Integer.parseInt(jobId));
			Admin admin = adminRepo.getCompany(Integer.parseInt(cid));
			model.addAttribute("job", p1);
			model.addAttribute("admin", admin);
			return "jobprofile";
		}
		
		@RequestMapping(value = "/showapplicants", method = RequestMethod.GET)
		public String showJobApplicants(@RequestParam("jobId") String jobId, Model model) {
			
			
			JobPosting p1 = jobRepo.getJobPosting(Integer.parseInt(jobId));
			model.addAttribute("job", p1);
			return "jobprofile";
		}
		
		/**
		 * @param adminId
		 * @param state
		 * @return List of jobs posted by the company
		 */
		@RequestMapping(value = "/getjobs", method = RequestMethod.GET)
		public String getJobs(@RequestParam("adminId") String adminId, Model model) {
			List<?> companyJobPostings = new ArrayList<String>();
			companyJobPostings = adminRepo.getJobsByCompany(Integer.parseInt(adminId));
			Admin admin = adminRepo.getCompany(Integer.parseInt(adminId));
			
			model.addAttribute("jobs", companyJobPostings);
			model.addAttribute("admin", admin);
			
			return "companyjobs";
		}

	}
	
