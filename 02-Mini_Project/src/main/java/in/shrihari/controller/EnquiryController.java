package in.shrihari.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.shrihari.binding.DashboardResponse;
import in.shrihari.binding.EnquiryForm;
import in.shrihari.service.EnquiryService;

@Controller
public class EnquiryController {
	@Autowired
	private EnquiryService enqService;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

	Integer userId = (Integer) session.getAttribute("userId");
//
		DashboardResponse dashboardData = enqService.getDashboardResponse(userId);
		
		model.addAttribute("dashboardData",dashboardData);
		
		return "dashboard";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		
		//get Courses for drop down
		List<String> courses = enqService.getCourseName();
		
		//get enq status for drop down
		
		List<String> enqStatuses = enqService.getEnqStatus();
		
		//create binding class obj
		EnquiryForm formObj = new EnquiryForm();
		
		//set data in model obj
		model.addAttribute("courseNames",courses);
		
		model.addAttribute("enqStatusNames",enqStatuses);
		
		model.addAttribute("formObj",formObj);

		
		return "add-enquiry";
	}

	
	
	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {
		
		System.out.println(formObj);
		boolean status = enqService.saveEnquiry(formObj);
		if(status) {
			model.addAttribute("succMsg","Enquiry added");
			
		}else {
			model.addAttribute("errMsg","Problem Occured");
		}
		
		return "add-enquiry";
	}
	
	
	@GetMapping("/enquires")
	public String viewEnquiriesPage(Model model) {
		return "view-enquiries";
	}

}
