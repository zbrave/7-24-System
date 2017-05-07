package tr.edu.yildiz.ce.controller;

import java.security.Principal;
import java.util.List;

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

import tr.edu.yildiz.ce.dao.BanDAO;
import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.dao.MailSend;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.SupporterDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.dao.UserRoleDAO;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.SupporterInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class SupporterController {
	
	@Autowired
	private ComplaintDAO complaintDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SupporterDAO supporterDAO;
	
	@Autowired
	private LocationDAO locationDAO;
	
	@Autowired
	private SupportTypeDAO supportTypeDAO;

	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private BanDAO banDAO;
	
	@Autowired
	private MailSend mailSend;
	
	@RequestMapping(value = "/supporter", method = RequestMethod.GET)
	public String supporterPage(Model model, Principal principal) {
		
		List<ComplaintInfo> list = complaintDAO.listComplaintInfosForSupport(userDAO.findLoginUserInfo(principal.getName()).getId());
		for (ComplaintInfo l : list) {
			l.setLocationInfo(locationDAO.findLocationInfo(l.getLocationId()));
			l.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(l.getSupportTypeId()));
			l.setComplainantUserInfo(userDAO.findUserInfo(l.getComplainantUserId()));
		}
		model.addAttribute("complaintInfos", list);
		return "supporter";
	}
	
	@RequestMapping(value = "/saveSupporter", method = RequestMethod.POST)
	public String saveSupporter(Model model, //
			@ModelAttribute("supporterForm") @Validated SupporterInfo supporterInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			model.addAttribute("supMsgError", "Hatalı giriş.");
			System.out.println("Hata!");
			return "supporterEditor";
		}
		
		this.supporterDAO.saveSupporter(supporterInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("supMsgSuccess", "Destek birimi eklendi.");

//		return "redirect:/deptList";
		return "redirect:/supporterEdit";
	}
}
