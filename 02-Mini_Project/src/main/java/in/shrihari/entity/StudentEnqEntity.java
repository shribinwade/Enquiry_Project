package in.shrihari.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name="ALT_STUDENT_ENQURIES")
@Data
public class StudentEnqEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enqId;
	
	private String studentName;
	
	private Long studentPhno;
	
	private String classMode;
	
	private String courseName;
	
	private String enqStatus;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate dateCreated;
	
	@UpdateTimestamp
	private LocalDate lastUpdated;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserDetailsEntity user;
	
	
	
}
