package in.shrihari.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.shrihari.binding.LoginForm;
import in.shrihari.binding.SignUpForm;
import in.shrihari.binding.UnlockForm;
import in.shrihari.service.UserService;
import in.shrihari.service.UserServiceImpl;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/signup")
	public String signuppage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignUpForm(@ModelAttribute("user") SignUpForm form, Model model) {

		boolean status = userService.signUp(form);

		if (status) {
			model.addAttribute("succMsg", "Account created, Check Your Email");

		} else {
			model.addAttribute("errMsg", "Choose Unique Email");
		}

		return "signup";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {

		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {

		System.out.println(loginForm);

		String loginstatus = userService.Login(loginForm);
		if (loginstatus.contains("success")) {
			//redirect req to dashboard method
			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg", loginstatus);
		return "login";

	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockForm unlockFormObj = new UnlockForm();

		unlockFormObj.setEmail(email);

		model.addAttribute("unlock", unlockFormObj);

		return "unlock";
	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {
		System.out.println(unlock);

		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			boolean unlockAccount = userService.unlockAccount(unlock);

			if (unlockAccount) {
				model.addAttribute("succMsg", "Login account Unlocked,");
			} else {
				model.addAttribute("errMsg", "Password is incorrect");
			}
		} else {
			model.addAttribute("errMsg", "Password not match Retry");
		}

		return "unlock";
	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {
		
		return "forgotPwd";
	}
	
	@PostMapping("/forgotPwd")
	public String forgotPwdReset(@RequestParam("email") String email ,Model model) {
		System.out.println(email);
		String forgotPwd = userService.forgotPwd(email);
		if(forgotPwd.contains("success")) {
			model.addAttribute("succMsg","Please check your email");
		}else {
			model.addAttribute("errMsg",forgotPwd);

		}
		
		return "forgotPwd";	
	}
}
