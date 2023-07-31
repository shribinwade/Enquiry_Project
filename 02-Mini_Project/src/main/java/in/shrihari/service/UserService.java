package in.shrihari.service;

import in.shrihari.binding.LoginForm;
import in.shrihari.binding.SignUpForm;
import in.shrihari.binding.UnlockForm;

public interface UserService {

	public String Login(LoginForm form);

	public boolean signUp(SignUpForm form);

	public boolean unlock(UnlockForm form);

	public String forgotPws(String email);

}
