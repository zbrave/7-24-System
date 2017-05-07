package tr.edu.yildiz.ce.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import tr.edu.yildiz.ce.dao.BanDAO;
import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.dao.MailSend;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.SupporterDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.dao.UserRoleDAO;
import tr.edu.yildiz.ce.model.BanInfo;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.LocationInfo;
import tr.edu.yildiz.ce.model.SupportTypeInfo;
import tr.edu.yildiz.ce.model.SupporterInfo;
import tr.edu.yildiz.ce.model.UserInfo;
import tr.edu.yildiz.ce.model.UserRoleInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class MainController {

	/*	@Autowired
	private UniValidator uniValidator;*/
	
	@Autowired
	private LocationDAO locationDAO;
	
	@Autowired
	private SupportTypeDAO supportTypeDAO;
	
	@Autowired
	private SupporterDAO supporterDAO;
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private ComplaintDAO complaintDAO;
	
	@Autowired
	private BanDAO banDAO;
	
	@Autowired
	private MailSend mailSend;
    
	
	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("title", "7-24 Servisi Sistemi");
		return "welcomePage";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model) {
		List<LocationInfo> list = locationDAO.listLocationInfos();
		model.addAttribute("locationInfos", list);
		List<SupportTypeInfo> list2 = supportTypeDAO.listSupportTypeInfos();
		model.addAttribute("supportTypeInfos", list2);
		List<UserRoleInfo> list3 = userRoleDAO.listUserRoleInfos();
		model.addAttribute("userRoleInfos", list3);
		List<SupporterInfo> list4 = supporterDAO.listSupporterInfos();
		for (SupporterInfo l : list4) {
			l.setLocationInfo(locationDAO.findLocationInfo(l.getLocationId()));
			l.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(l.getSupportTypeId()));
			l.setUserInfo(userDAO.findUserInfo(l.getUserId()));
		}
		model.addAttribute("supporterInfos", list4);
		List<UserInfo> list5 = userDAO.listUserInfos();
		model.addAttribute("userInfos", list5);
		LocationInfo mod = new LocationInfo();
		model.addAttribute("locationForm", mod);
		return "adminPage";
	}
	
	@RequestMapping(value = "/supporterEdit", method = RequestMethod.GET)
	public String supporterEditPage(Model model) {
		
		List<LocationInfo> listLoc = locationDAO.listLocationInfos();
		model.addAttribute("locationInfos", listLoc);
		
		List<SupporterInfo> list = supporterDAO.listSupporterInfos();
		model.addAttribute("supporterInfos", list);
		for (SupporterInfo l : list) {
			l.setLocationInfo(locationDAO.findLocationInfo(l.getLocationId()));
			l.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(l.getSupportTypeId()));
			l.setUserInfo(userDAO.findUserInfo(l.getUserId()));
		}
		List<SupportTypeInfo> list2 = supportTypeDAO.listSupportTypeInfos();
		model.addAttribute("supportTypeInfos", list2);
		
		List<UserInfo> list3 = userDAO.listUserInfos();
		model.addAttribute("userInfos", list3);
		
		return "supporterEditor";
	}
	
	@RequestMapping(value = "/supporterTypeEdit", method = RequestMethod.GET)
	public String supporterTypeEditPage(Model model) {
		
		
		List<SupportTypeInfo> list2 = supportTypeDAO.listSupportTypeInfos();
		model.addAttribute("supportTypeInfos", list2);
		
		return "supportTypeEditor";
	}
	
	@RequestMapping(value = "/locationEdit", method = RequestMethod.GET)
	public String locationEditPage(Model model) {
		
		List<LocationInfo> listLoc = locationDAO.listLocationInfos();
		model.addAttribute("locationInfos", listLoc);
		
		return "location";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String ban(Model model) {
		
		List<UserInfo> user = userDAO.listUserInfos();
		for (UserInfo u : user) {
			u.setBanned(banDAO.isBanned(u.getId()));
		}
		model.addAttribute("users", user);
		
		return "users";
	}
	
	@RequestMapping(value = "/banUser", method = RequestMethod.GET)
	public String banUser(Model model, @RequestParam(value = "id") Integer id) {
		
		UserInfo user = userDAO.findUserInfo(id);
		
		return this.userBanForm(model, user);
	}
	
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String report(Model model ) {
		Long avgProcess = complaintDAO.avgTimeForProcess();
		model.addAttribute("avgProcess", avgProcess);
		
		List<SupportTypeInfo> supType = supportTypeDAO.listSupportTypeInfos();
		for(SupportTypeInfo s : supType) {
			s.setAvgTime(complaintDAO.avgTimeForComplaintBySupportType(s.getId()));
			s.setTotal(complaintDAO.numOfComplaintBySupportType(s.getId()));
			s.setActive(complaintDAO.numOfActiveComplaintBySupportType(s.getId()));
			s.setWait(complaintDAO.numOfWaitingComplaintBySupportType(s.getId()));
		}
		model.addAttribute("supTypeAvgProcess", supType);
		
		Integer numTotal = complaintDAO.numOfProcess();
		model.addAttribute("total", numTotal);
		
		return "report";
	}
	
	@RequestMapping(value = "/reportComplaintsPdf", method = RequestMethod.GET)
    public ModelAndView downloadExcel() {
        // create some sample data
        List<ComplaintInfo> listComplaints= this.complaintDAO.listComplaintInfos();
 
        // return a view which will be resolved by an excel view resolver
        return new ModelAndView("complaintPdfView", "listComplaints", listComplaints);
    }
	
	@RequestMapping(value = "/userBanForm", method = RequestMethod.GET)
	public String userBanForm(Model model, UserInfo user) {
		
		List<BanInfo> bans = banDAO.banHistory(user.getId());
		model.addAttribute("bans", bans);
		model.addAttribute("user", user);
		return "userBanForm";
	}
	
	@RequestMapping(value = "/userRoleEdit", method = RequestMethod.GET)
	public String userRoleEditPage(Model model) {
		
		List<UserRoleInfo> list3 = userRoleDAO.listUserRoleInfos();
		model.addAttribute("userRoleInfos", list3);
		
		List<UserInfo> list5 = userDAO.listUserInfos();
		model.addAttribute("userInfos", list5);
		
		return "userRoleEditPage";
	}
	
	@RequestMapping(value = "/complaint", method = RequestMethod.GET)
	public String complaintPage(Model model,Principal principal) {
		
		UserInfo user = userDAO.findLoginUserInfo(principal.getName());
		model.addAttribute("userInfo", user);
		
		List<LocationInfo> list = locationDAO.listLocationInfos();
		model.addAttribute("locationInfos", list);
		
		List<SupportTypeInfo> list2 = supportTypeDAO.listSupportTypeInfos();
		model.addAttribute("supportTypeInfos", list2);
		
		return "complaintPage";
	}
	
	@RequestMapping(value="/getLocationList",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getLocationList() {
		String res = "<option id= value=>Üst konum seçin.</option>";
		List<LocationInfo> list = locationDAO.listLocationInfos();
		for (LocationInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getDescription()+"</option>");
		}
		return res;
	}
	
	@RequestMapping(value="/getUserList",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getUserList() {
		String res = "<option id= value=>Kullanıcı seçin.</option>";
		List<UserInfo> list = userDAO.listUserInfos();
		for (UserInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getUsername()+"</option>");
		}
		return res;
	}
	
	@RequestMapping(value="/getUserRoleList",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getUserRoleList() {
		String res = "<option id= value=>Rol seçin.</option>";
		List<UserRoleInfo> list = userRoleDAO.listUserRoleInfos();
		for (UserRoleInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getRole()+"</option>");
		}
		return res;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model ) {
		return "loginPage";
	}

	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutSuccessfulPage";
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {

		// After user login successfully.
		UserInfo user = userDAO.findLoginUserInfo(principal.getName());
		model.addAttribute("userInfo", user);
		List<LocationInfo> list = locationDAO.listLocationInfos();
		model.addAttribute("locationInfos", list);
		List<SupportTypeInfo> list2 = supportTypeDAO.listSupportTypeInfos();
		model.addAttribute("supportTypeInfos", list2);
		//		model.addAttribute("message", userName);
		return "userInfoPage";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			model.addAttribute("message", "Hi " + principal.getName()
			+ "<br> You do not have permission to access this page!");
		} else {
			model.addAttribute("msg",
					"You do not have permission to access this page!");
		}
		return "403Page";
	}
}