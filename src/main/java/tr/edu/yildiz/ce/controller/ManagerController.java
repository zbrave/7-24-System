package tr.edu.yildiz.ce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.model.ComplaintInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class ManagerController {

	@Autowired
	private ComplaintDAO complaintDAO;
	
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String managerPage(Model model ) {
		List<ComplaintInfo> list = this.complaintDAO.listComplaintInfos();
		model.addAttribute("complaintInfos", list);
		return "manager";
	}
}
