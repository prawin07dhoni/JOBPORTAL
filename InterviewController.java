package com.jobportalbackend.controller;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jobportalbackend.repository.InterviewRepository;
import com.jobportalbackend.repository.JobApplicationRepository;
import com.jobportalbackend.entity.Admin;
import com.jobportalbackend.entity.Interview;
import com.jobportalbackend.entity.JobApplication;
import com.jobportalbackend.entity.JobSeeker;
import com.jobportalbackend.mail.EmailServiceImpl;

/**
 * @author Avdeep
 *
 */
@RestController

public class InterviewController {
	@Autowired
	InterviewRepository interviewRepo;

	@Autowired
	JobApplicationRepository jobAppRepo;

	@Autowired
	EmailServiceImpl emailService;

	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping(value = "/createinterview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> createInterview(@RequestParam("appId") String appId,
			@RequestParam("location") String location, @RequestParam("datetime") String datetime) {
		System.out.println("started");
		JobApplication ja = jobAppRepo.getJobApplication(Integer.parseInt(appId));
		JobSeeker jobSeeker = ja.getJobSeeker();
		ja.setInterviewFlag(true);
		ja.setInterviewLocation(location);
		ja.setInterviewTime(Date.valueOf(datetime));
		ja.setInterviewAccepted(false);
		jobAppRepo.updateApplication(ja);
		String verificationUrl = "http://localhost:8080/acceptinterview?jobseekerid=" + ja.getAppId();
		System.out.println("interview created");
		emailService.sendSimpleMessage(jobSeeker.getEmailId(), "Interview call",
				"Hi " + jobSeeker.getFirstName() + " " + jobSeeker.getLastName()
						+ ", \nYou have been selected to interview for the position at time " + datetime
						+ " at VENUE : " + location
						+ ".\n If you are intereseted in it please click on the following link : \n" + verificationUrl);
		return ResponseEntity.ok(ja);
	}

	@RequestMapping(value = "/acceptinterview", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> acceptinterview(@RequestParam("appId") String appId) {
		System.out.println("started");
		JobApplication ja = jobAppRepo.getJobApplication(Integer.parseInt(appId));
		JobSeeker jobSeeker = ja.getJobSeeker();
		ja.setInterviewAccepted(false);
		jobAppRepo.updateApplication(ja);
		Admin admin = ja.getJobPosting().getAdmin();

		emailService.sendSimpleMessage(admin.getCompanyUser(), "Interview Response", jobSeeker.getFirstName() + " "
				+ jobSeeker.getLastName() + " has decide to move forward with the interview process");
		return ResponseEntity.ok(ja);
	}

}
