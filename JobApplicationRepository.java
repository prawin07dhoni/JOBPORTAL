package com.jobportalbackend.repository;
import com.jobportalbackend.entity.JobApplication;

public interface JobApplicationRepository {
	/**
	 * @param jobAppId
	 * @return True if the application was successfully cancelled
	 */
	boolean cancel(int jobAppId);

	/**
	 * @param jobseekerId
	 * @param jobId
	 * @param resumeFlag
	 * @param resumePath
	 * @return The newly created job application
	 */
	JobApplication apply(int jobseekerId, int jobId, boolean resumeFlag, String resumePath);

	/**
	 * @param jobAppId
	 * @return Required job application
	 */
	JobApplication getJobApplication(int jobAppId);

	/**
	 * @param state
	 * @return Modified job application
	 */
	JobApplication modifyJobApplicationStatus(int jobAppId, int state);
	
	/**
	 * @param ja
	 * @return Updated job application
	 */
	JobApplication updateApplication(JobApplication ja);

}

}
