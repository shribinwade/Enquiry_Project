package in.shrihari.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.shrihari.entity.CoursesEntity;
import in.shrihari.entity.EnqStatusEntity;
import in.shrihari.repo.CourseRepo;
import in.shrihari.repo.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		courseRepo.deleteAll();
		
		CoursesEntity c1 = new CoursesEntity();
		c1.setCourseName("Java");
		CoursesEntity c2 = new CoursesEntity();
		c2.setCourseName("Spring Boot");
		CoursesEntity c3 = new CoursesEntity();
		c3.setCourseName("Hibernate");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3));
		
		statusRepo.deleteAll();
		
		EnqStatusEntity s1 = new EnqStatusEntity();
		s1.setStatusName("New");
		EnqStatusEntity s2 = new EnqStatusEntity();
		s2.setStatusName("Enrolled");
		EnqStatusEntity s3 = new EnqStatusEntity();
		s3.setStatusName("Lost");
		
		statusRepo.saveAll(Arrays.asList(s1,s2,s3));
	}

}
