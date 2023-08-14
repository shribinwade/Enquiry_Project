package in.shrihari.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.shrihari.binding.DashboardResponse;
import in.shrihari.binding.EnquiryForm;
import in.shrihari.binding.EnquirySearchCriteria;
import in.shrihari.entity.StudentEnqEntity;
import in.shrihari.entity.UserDetailsEntity;
import in.shrihari.repo.StudentEnqRepo;
import in.shrihari.repo.UserDtlsRepo;
import in.shrihari.service.EnquiryService;

@Controller
public class EnquiryController {
	@Autowired
	private EnquiryService enqService;

	@Autowired
	StudentEnqRepo studentEnqRepo;

	@Autowired
	private HttpSession session;

	private void initForm(Model model) {

		// get Courses for drop down
		List<String> courses = enqService.getCourseName();

		// get enq status for drop down

		List<String> enqStatuses = enqService.getEnqStatus();

		// create binding class obj
		EnquiryForm formObj = new EnquiryForm();

		// set data in model obj
		model.addAttribute("courseNames", courses);

		model.addAttribute("enqStatusNames", enqStatuses);

		model.addAttribute("formObj", formObj);
	}

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

		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		// get Courses for drop down
		List<String> courses = enqService.getCourseName();

		// get enq status for drop down

		List<String> enqStatuses = enqService.getEnqStatus();

		// create binding class obj
		EnquiryForm formObj = new EnquiryForm();

		// set data in model obj
		model.addAttribute("courseNames", courses);

		model.addAttribute("enqStatusNames", enqStatuses);

		model.addAttribute("formObj", formObj);

		return "add-enquiry";
	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {

		// get Courses for drop down
		List<String> courses = enqService.getCourseName();

		// get enq status for drop down

		List<String> enqStatuses = enqService.getEnqStatus();
		// set data in model obj
		model.addAttribute("courseNames", courses);

		model.addAttribute("enqStatusNames", enqStatuses);

		boolean status = enqService.saveEnquiry(formObj);
		if (status) {
			model.addAttribute("succMsg", "Enquiry added");

		} else {
			model.addAttribute("errMsg", "Problem Occured");
		}

		return "add-enquiry";
	}

	@GetMapping("/enquires")
	public String viewEnquiriesPage(Model model) {

		initForm(model);
		List<StudentEnqEntity> enquiries = enqService.getEnquiries();
		model.addAttribute("enquiries", enquiries);
		return "view-enquiries";
	}

	@GetMapping("/filter-enquiries")
	public String getFilteredEnqs(@RequestParam String cname, @RequestParam String status, @RequestParam String mode,
			Model model) {
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setEnqStatus(status);
		criteria.setClassMode(mode);
		Integer userId = (Integer) session.getAttribute("userId");

		List<StudentEnqEntity> filteredEnqs = enqService.getFilteredEnqs(criteria, userId);
		model.addAttribute("enq", filteredEnqs);
		return "filter-enquiries-page";
	}

	@GetMapping("/edit")
	public String editEnquiry(@RequestParam Integer enquiryId, Model model) {
		StudentEnqEntity entity = studentEnqRepo.findById(enquiryId).get();
		LocalDate dateCreated = entity.getDateCreated();
		entity.setDateCreated(dateCreated);

		EnquiryForm formObj = new EnquiryForm();
		// get Courses for drop down
		List<String> courses = enqService.getCourseName();

		// get enq status for drop down

		List<String> enqStatuses = enqService.getEnqStatus();
		// set data in model obj
		model.addAttribute("courseNames", courses);

		model.addAttribute("enqStatusNames", enqStatuses);

		BeanUtils.copyProperties(entity, formObj);

		model.addAttribute("formObj", formObj);
		return "add-enquiry";
	}
}
