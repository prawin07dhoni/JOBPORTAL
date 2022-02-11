package com.jobportalbackend.controller;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobportalbackend.repository.AdminRepository;
import com.jobportalbackend.repository.JobPostingRepository;
import com.jobportalbackend.entity.Admin;
import com.jobportalbackend.entity.JobPosting;

/**
 * @author Rajesh
 *
 */
@Controller
@RequestMapping("/JobPosting")

public class JobPostingController {
	@Autowired
	JobPostingRepository jobRepo;

	@Autowired
	AdminRepository adminRepo;

	/**
	 * @param cid
	 * @param model
	 * @return homepage view
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showHomePage(@RequestParam("cid") String cid, Model model) {
		System.out.println(cid);
		
		Admin admin = adminRepo.getAdmin(Integer.parseInt(cid));
		model.addAttribute("cid", cid);
		model.addAttribute("admin", admin);
		return "postjob";
	}

	/**
	 * @param title
	 * @param description
	 * @param responsibilities
	 * @param location
	 * @param salary
	 * @param cid
	 * @param model
	 * @return JobPosting
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String createJobPosting(@RequestParam("title") String title, @RequestParam("description") String description,
			@RequestParam("responsibilities") String responsibilities, @RequestParam("location") String location,
			@RequestParam("salary") String salary, @RequestParam("cid") String cid, Model model) {
		JobPosting j = new JobPosting();
		j.setTitle(title);
		j.setDescription(description);
		j.setResponsibilities(responsibilities);
		j.setLocation(location);
		j.setSalary(salary);
		j.setKeywords(title + " " + description + " " + responsibilities + " " + location);

		try {
			System.out.println("0");

			JobPosting p1 = jobRepo.createJobPosting(j, Integer.parseInt(cid));
			System.out.println("ashay");

			model.addAttribute("job", p1);
			Admin admin = adminRepo.getAdmin(Integer.parseInt(cid));
			model.addAttribute("admin", admin);
			return "jobprofile";

		} catch (Exception e) {
			HttpHeaders httpHeaders = new HttpHeaders();
			Map<String, Object> message = new HashMap<String, Object>();
			Map<String, Object> response = new HashMap<String, Object>();
			message.put("code", "400");
			message.put("msg", "another passenger with the phone number  already exists.");
			response.put("BadRequest", message);
			JSONObject json_test = new JSONObject(response);
			String json_resp = json_test.toString();

			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			return "error";
		}

	}

	/**
	 * @param id
	 * @param model
	 * @return Message view for deleted javadocs
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteJobPosting(@PathVariable("id") int id, Model model) {

		if (jobRepo.deleteJobPosting(id)) {
			String message = "Job Posting with JobID " + id + " is deleted successfully";
			model.addAttribute("message", message);
			return "message";
		} else {
			return "error";
		}
	}

	@RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
	public String showUpdatePage(@PathVariable("id") int id, @RequestParam("cid") String cid, Model model) {
		System.out.println(cid);
		System.out.println(id);
		
		Admin admin = adminRepo.getAdmin(Integer.parseInt(cid));
		JobPosting jp = jobRepo.getJobPosting(id);
		model.addAttribute("job", jp);
		model.addAttribute("admin", admin);
		return "updatejob";
	}
	
	/**
	 * @param id
	 * @param state
	 * @param title
	 * @param description
	 * @param responsibilities
	 * @param location
	 * @param salary
	 * @param model
	 * @return Updated job view
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public String updateJobPosting(@PathVariable("id") int id, @RequestParam("state") String state,
			@RequestParam("title") String title, @RequestParam("description") String description,
			@RequestParam("responsibilities") String responsibilities, @RequestParam("location") String location,
			@RequestParam("salary") String salary, @RequestParam("cid") String cid, Model model) {
		// TODO routing
		JobPosting job = jobRepo.getJobPosting(id);
		if (job != null) {
			job.setjobId(id);
			job.setDescription(description);
			job.setState(Integer.parseInt(state));
			job.setTitle(title);
			job.setLocation(location);
			job.setResponsibilities(responsibilities);
			JobPosting p1 = jobRepo.updateJobPosting(job);

			model.addAttribute("job", p1);
			Admin admin = adminRepo.getAdmin(Integer.parseInt(cid));
			model.addAttribute("admin", admin);
			return "jobprofile";
		}
		return "error";

	}
	
	/**
	 * @param jobId
	 * @param state
	 * @return true if the state has been modified
	 */
	@RequestMapping(value = "/modifyjobstate", method = RequestMethod.POST)
	public String modifyJobState(@RequestParam("jobId") String jobId, @RequestParam("state") String state) {
		JobPosting jp = jobRepo.getJobPosting(Integer.parseInt(jobId));
		jp.setState(Integer.parseInt(state));
		jp = jobRepo.updateJobPosting(jp);
		if(jp==null){
			return "Error";
		}
		return "modified";
	}

}
