package in.shrihari.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "ALT_USER_DTLS")
public class UserDetailsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer userId;
	
	public String  name;
	
	public String  email;
	
	public Long phno;
	
	public String  pwd;
	
	public String  accStatus;

	@OneToMany(mappedBy="user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<StudentEnqEntity> enquiries;
}
