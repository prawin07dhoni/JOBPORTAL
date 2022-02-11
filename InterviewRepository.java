package com.jobportalbackend.repository;
import java.util.List;

import com.jobportalbackend.entity.Interview;

public interface InterviewRepository {
    
	public Interview createInterview(int jobseekerid,String company, String location, String datetime, String flag);
	
	public String acceptInterview(int jobseekerid);
	
	public List<Interview> getAllInterviews(int jobseekerid);

}
