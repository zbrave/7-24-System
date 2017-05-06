package tr.edu.yildiz.ce.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tr.edu.yildiz.ce.dao.ActivationDAO;

import tr.edu.yildiz.ce.entity.Activation;
import tr.edu.yildiz.ce.model.UserRoleInfo;

import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.dao.UserRoleDAO;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.UserInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class UserController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private ActivationDAO activationDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String saveUser(Model model, //
			@ModelAttribute("userForm") @Validated UserInfo userInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			model.addAttribute("signupMsg", "Hatalı giriş!");
			System.out.println("Hata!");
		}
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(userInfo.getUsername().getBytes("ISO-8859-1"), "UTF-8");
			userInfo.setUsername(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Username cannot converted.");
			e.printStackTrace();
		}
		if (!userInfo.getPassword().equals(userInfo.getPasswordConf())) {
			model.addAttribute("signupMsg", "Parola eşleşmedi.");
			System.out.println("Parola eşleşmedi.");
			return "loginPage";
		}
		if (this.userDAO.findLoginUser(userInfo.getUsername()) != null) {
			model.addAttribute("signupMsg", "Kullanıcı mevcut.");
			System.out.println("Kullanıcı mevcut.");
			return "loginPage";
		}
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		System.out.println(userInfo.getUsername()+userInfo.getPassword()+userInfo.getEmail());
		this.userDAO.saveUser(userInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("signupMsg", "Hesabınızı mailize gelen aktivasyon linki ile aktive edin.");

//		return "redirect:/deptList";
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public String activate(Model model, @RequestParam String code,	final RedirectAttributes redirectAttributes) {
		Activation act = activationDAO.findActivationWithCode(code);
		if (act != null) {
			UserInfo user = userDAO.findLoginUserInfo(act.getUsername());
			user.setEnabled(true);
			userDAO.saveUser(user);
			System.out.println(act.getId()+act.getUsername());
			activationDAO.deleteActivation(act.getId());
			UserRoleInfo user2 = new UserRoleInfo();
			user2.setUserInfo(user);
			user2.setRole("USER");
			userRoleDAO.saveUserRole(user2);
			redirectAttributes.addFlashAttribute("signupMsg", "Hesabınız aktif edildi.");
			return "redirect:/login";
		}
		else {
			return "redirect:/403";
		}
	}
}
