package in.shrihari.service;

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
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private EmailUtils emailutils;

	@Override
	public String Login(LoginForm form) {
		// TODO
		return null;
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
	public boolean unlock(UnlockForm form) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String forgotPws(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
