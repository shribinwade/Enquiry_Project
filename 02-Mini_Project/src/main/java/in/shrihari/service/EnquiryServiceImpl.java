package in.shrihari.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.shrihari.binding.DashboardResponse;
import in.shrihari.binding.EnquiryForm;
import in.shrihari.binding.EnquirySearchCriteria;
import in.shrihari.entity.CoursesEntity;
import in.shrihari.entity.EnqStatusEntity;
import in.shrihari.entity.StudentEnqEntity;
import in.shrihari.entity.UserDetailsEntity;
import in.shrihari.repo.CourseRepo;
import in.shrihari.repo.EnqStatusRepo;
import in.shrihari.repo.StudentEnqRepo;
import in.shrihari.repo.UserDtlsRepo;

@Component
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private StudentEnqRepo enqRepo;

	@Autowired
	private CourseRepo coursesRepo;

	@Autowired
	private EnqStatusRepo statusRepo;

	@Autowired
	private HttpSession session;

	@Override
	public DashboardResponse getDashboardResponse(Integer userId) {

		DashboardResponse response = new DashboardResponse();

		Optional<UserDetailsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {

			UserDetailsEntity userEntity = findById.get();

			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();

			Integer size = enquiries.size();

			Integer enrolledCnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			Integer lostCnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			response.setTotalEnquriesCnt(size);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);
		}

		return response;
	}

	@Override
	public List<String> getCourseName() {

		List<CoursesEntity> findAll = coursesRepo.findAll();

		List<String> names = new ArrayList<>();

		for (CoursesEntity entity : findAll) {
			names.add(entity.getCourseName());
		}

		return names;
	}

	@Override
	public List<String> getEnqStatus() {
		// TODO Auto-generated method stub
		List<EnqStatusEntity> findAll = statusRepo.findAll();

		List<String> statusList = new ArrayList<>();
		for (EnqStatusEntity entity : findAll) {
			statusList.add(entity.getStatusName());
		}

		return statusList;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm form) {

		StudentEnqEntity entity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, entity);

		Integer userId = (Integer) session.getAttribute("userId");
		UserDetailsEntity userEntity = userDtlsRepo.findById(userId).get();

		entity.setUser(userEntity);

		enqRepo.save(entity);

		return true;
	}

	@Override
	public List<StudentEnqEntity> getEnquiries() {
		// TODO Auto-generated method stub
		Integer userId = (Integer) session.getAttribute("userId");
		Optional<UserDetailsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {
			UserDetailsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			return enquiries;
		}
		return null;
	}

	@Override
	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria, Integer userId) {
	
		Optional<UserDetailsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {
			UserDetailsEntity userDtlsEntity = findById.get();	
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			
			//filter logic
			if(null != criteria.getCourseName() && !"".equals(criteria.getCourseName())) 
			{
				enquiries = enquiries.stream()
				.filter(e -> e.getCourseName().equals(criteria.getCourseName()))
				.collect(Collectors.toList());
							
			}
			if(null != criteria.getEnqStatus() && !"".equals(criteria.getEnqStatus())) {
				enquiries = enquiries.stream()
				.filter(e -> e.getEnqStatus().equals(criteria.getEnqStatus()))
				.collect(Collectors.toList());
				
						
			}
			if(null != criteria.getClassMode() && !"".equals(criteria.getClassMode())) {
				enquiries =	enquiries.stream()
				.filter(e -> e.getClassMode().equals(criteria.getClassMode()))
				.collect(Collectors.toList());	
				
			}
			return enquiries;
		}
	
		return null;
	}

}
