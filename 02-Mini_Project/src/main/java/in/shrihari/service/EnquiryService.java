package in.shrihari.service;

import java.util.List;

import in.shrihari.binding.DashboardResponse;
import in.shrihari.binding.EnquiryForm;


public interface EnquiryService {

	
	public List<String> getCourseName();
	
	public List<String> getEnqStatus();
	
	public DashboardResponse getDashboardResponse(Integer userId); 
	
	public boolean saveEnquiry (EnquiryForm form);
}
