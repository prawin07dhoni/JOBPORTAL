package com.jobportalbackend.repository;
import com.jobportalbackend.entity.JobPosting;
public interface JobPostingRepository {

	     /**
		 * @param job
		 * @param cid
		 * @return New JobPosting
		 * @throws Exception 
		 */
		JobPosting createJobPosting(JobPosting job, int cid);
		
		/**
		 * @param id
		 * @return Requested JobPosting
		 */
		JobPosting getJobPosting(int id);

		/**
		 * @param id
		 * @return True if JobPosting is Deleted
		 */
		boolean deleteJobPosting(int id);

		/**
		 * @param job
		 * @return Updated Job Posting
		 */
		JobPosting updateJobPosting(JobPosting job);


}
