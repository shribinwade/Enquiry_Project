package in.shrihari.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.shrihari.binding.LoginForm;
import in.shrihari.binding.SignUpForm;
import in.shrihari.binding.UnlockForm;
import in.shrihari.entity.UserDetailsEntity;
import in.shrihari.repo.UserDtlsRepo;
import in.shrihari.utils.EmailUtils;
import in.shrihari.utils.PwdUtils;

@Service
public class UserServiceImpl implements UserService {
   
	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private EmailUtils emailutils;

	@Override
	public String Login(LoginForm form) {
		
		UserDetailsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(),form.getPwd());
		if(entity == null) {
			return "Invalid Credentials or Email not found ";
		}
		if(entity.accStatus.equals("LOCKED")) {
			return "Your account is locked please unlocked than retry ";
		}
		
		//Create Session and store user data in session
		session.setAttribute("userId", entity.getUserId());
		
			return "success";	
		
	}

	@Override
	public boolean signUp(SignUpForm form) {

		//check email exist
		UserDetailsEntity user = userDtlsRepo.findByEmail(form.getEmail());
		if(user!=null) {
			return false;
		}
		
		// TODO:copy data from binding object to entity obj
		UserDetailsEntity entity = new UserDetailsEntity();
		BeanUtils.copyProperties(form, entity);

		// TODO generate random pwd
		String generateRandomPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(generateRandomPwd);

		// TODo: set acc status as locked
		entity.setAccStatus("LOCKED");
		// TODO: insert record
		userDtlsRepo.save(entity);

		// TODO:send email to unlock the account

		String to = form.getEmail();
		String subject = "Unlock Your Account ";
	
		StringBuffer body = new StringBuffer("");
		body.append("<h1>Use temporary password to unlock your account</h1>");
		body.append("Temporay password: " + generateRandomPwd);
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click here to unlock your Account/>");
		
		boolean sendEmail = emailutils.sendEmail(to, subject, body.toString());
		if(sendEmail) {
			return true;
		}else {
		return false;
		}
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {
		// TODO Auto-generated method stub
		UserDetailsEntity entity = userDtlsRepo.findByEmail(form.getEmail());
		
		if(entity.getPwd().equals(form.getTempPwd())) {
			entity.setAccStatus("UnLocked");
			entity.setPwd(form.getNewPwd());
			userDtlsRepo.save(entity);
			return true;
		}else {
			return false;

		}
		
	}


	

	@Override
	public String forgotPwd(String email) {
		// TODO Auto-generated method stub
		
		//cheking email is valid or not
		UserDetailsEntity entity = userDtlsRepo.findByEmail(email);

		if(entity==null) {
			System.out.println("failed");
			return "Email is invalid";
			
		}
		String Subject="Recover Passsword";
		String body="Your Password: "+ entity.getPwd();
		emailutils.sendEmail(email, Subject, body);
		System.out.println("true");
		
		return "success";
	}

	

}
