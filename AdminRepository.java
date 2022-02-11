package com.jobportalbackend.repository;

import java.util.List;

import com.jobportalbackend.entity.Admin;

/**
 * @author Kalyan
 *
 */
public interface AdminRepository {
	
	
	/**
	 * @param emailid
	 * @return password for the given emailId
	 */
	public List<String> PasswordLookUp(String emailid);
	
	/**
	 * @param adm
	 * @return Created company
	 * @throws Exception
	 */
	public Admin createCompany(Admin adm) throws Exception;

	/**
	 * @param js
	 * @return Updated company
	 */
	public Admin updateCompany(Admin js);

	/**
	 * @param id
	 * @return Company
	 */
	public Admin getCompany(int id);
	
	/**
	 * @param c
	 */

	public void verify(Admin c);
	
	/**
	 * @param companyId
	 * @param state
	 * @return List of jobs according to the state
	 */
	public List<?> getJobsByCompany(int companyId);

	/**
	 * @param emailid
	 * @return
	 */
	public List<Integer> getCompanyIdFromEmail(String emailid);
	
	

}