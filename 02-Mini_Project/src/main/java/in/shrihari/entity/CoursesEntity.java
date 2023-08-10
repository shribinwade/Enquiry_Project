package in.shrihari.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="AIT_COURSES")
@Data
public class CoursesEntity {
	
	@Id
	@GeneratedValue()
	private Integer courseId;
	
	private String courseName;
	
    
}
