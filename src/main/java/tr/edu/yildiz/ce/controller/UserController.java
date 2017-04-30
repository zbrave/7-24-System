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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tr.edu.yildiz.ce.dao.UserDAO;
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
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String saveUser(Model model, //
			@ModelAttribute("userForm") @Validated UserInfo userInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			model.addAttribute("signupMsgError", "Hatalı giriş!");
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
			model.addAttribute("signupMsgError", "Parola eşleşmedi.");
			System.out.println("Parola eşleşmedi.");
			return "loginPage";
		}
		if (this.userDAO.findLoginUser(userInfo.getUsername()) != null) {
			model.addAttribute("signupMsgError", "Kullanıcı mevcut.");
			System.out.println("Kullanıcı mevcut.");
			return "loginPage";
		}
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		System.out.println(userInfo.getUsername()+userInfo.getPassword()+userInfo.getEmail());
		this.userDAO.saveUser(userInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("signupMsgSuccess", "Kullanıcı eklendi.");

//		return "redirect:/deptList";
		return "redirect:/login";
	}
}
