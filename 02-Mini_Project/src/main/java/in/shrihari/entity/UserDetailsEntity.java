package in.shrihari.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "ALT_USER_DTLS")
public class UserDetailsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer UserID;
	
	public String  name;
	
	public String  email;
	
	public Long phno;
	
	public String  pwd;
	
	public String  accStatus;

}
