package tr.edu.yildiz.ce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.model.ComplaintInfo;

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
	
	@RequestMapping(value = "/supporter", method = RequestMethod.GET)
	public String supporterPage(Model model, Principal principal) {
		
		List<ComplaintInfo> list = complaintDAO.listComplaintInfosForSupport(userDAO.findLoginUserInfo(principal.getName()).getId());
		System.out.println(userDAO.findLoginUserInfo(principal.getName()).getId()+list.size());
		model.addAttribute("complaintInfos", list);
		return "supporter";
	}
}
