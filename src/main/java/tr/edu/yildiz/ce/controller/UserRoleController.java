package tr.edu.yildiz.ce.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
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
import tr.edu.yildiz.ce.dao.UserRoleDAO;
import tr.edu.yildiz.ce.model.UserRoleInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class UserRoleController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
	public String saveUserRole(Model model, //
			@ModelAttribute("userRoleForm") @Validated UserRoleInfo userRoleInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			

		if (result.hasErrors()) {
			model.addAttribute("userRoleMsgError", "Eklenemedi.");
			return "userRoleEditPage";
		}
		System.out.println(userRoleInfo.getRole());
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(userRoleInfo.getRole().getBytes("ISO-8859-1"), "UTF-8");
			userRoleInfo.setRole(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("UserRole cannot converted.");
			e.printStackTrace();
		}
		userRoleInfo.setUserInfo(userDAO.findUserInfo( userRoleInfo.getUserId()));
		this.userRoleDAO.saveUserRole(userRoleInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("userRoleMsgSuccess", "Başarıyla eklendi.");

//		return "redirect:/deptList";
		return "redirect:/userRoleEdit";
	}
}