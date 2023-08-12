package in.shrihari.service;

import java.util.List;

import in.shrihari.binding.DashboardResponse;
import in.shrihari.binding.EnquiryForm;
import in.shrihari.binding.EnquirySearchCriteria;
import in.shrihari.entity.StudentEnqEntity;


public interface EnquiryService {

	
	public List<String> getCourseName();
	
	public List<String> getEnqStatus();
	
	public List<StudentEnqEntity> getEnquiries();
	
	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria, Integer userId);
	
	public DashboardResponse getDashboardResponse(Integer userId); 
	
	public boolean saveEnquiry (EnquiryForm form);
	
}
