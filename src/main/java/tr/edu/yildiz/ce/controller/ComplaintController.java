package tr.edu.yildiz.ce.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.LocationInfo;
import tr.edu.yildiz.ce.model.SupportTypeInfo;
import tr.edu.yildiz.ce.model.UserInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class ComplaintController {

	@Autowired
	private ComplaintDAO complaintDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private LocationDAO locationDAO;
	
	@Autowired
	private SupportTypeDAO supportTypeDAO;
	
	@RequestMapping(value = "/saveComplaint", method = RequestMethod.POST)
	public String saveComplaint(Model model, //
			@ModelAttribute("complaintForm") @Validated ComplaintInfo complaintInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			model.addAttribute("compMsg", "Hatalı giriş.");
			System.out.println("Hata!");
		}
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(complaintInfo.getComplaintText().getBytes("ISO-8859-1"), "UTF-8");
			complaintInfo.setComplaintText(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Dept name cannot converted.");
			e.printStackTrace();
		}
		System.out.println(complaintInfo.getLocationId()+" "+complaintInfo.getSupportTypeId()+" "+complaintInfo.getComplainantUserId()+" "+complaintInfo.getComplaintText());
		this.complaintDAO.recordComplaint(complaintInfo.getLocationId(), complaintInfo.getSupportTypeId(), complaintInfo.getComplainantUserId(), complaintInfo.getComplaintText());

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("compMsg", "Şikayet gönderildi.");

//		return "redirect:/deptList";
		return "redirect:/userInfo";
	}
	
	@RequestMapping(value = "/endComplaint", method = RequestMethod.GET)
	public String endComplaint(Model model,Principal principal, @RequestParam("id") Integer id) {
		UserInfo user = userDAO.findLoginUserInfo(principal.getName());
		if(id==1){
			return "manager";
		}
		model.addAttribute("userInfo", user);
		model.addAttribute("comp", this.complaintDAO.findComplaintInfo(id));
//		return "redirect:/deptList";
		return "endComplaint";
	}
	
	@RequestMapping(value = "/endedComplaint", method = RequestMethod.POST)
	public String endedComplaint(Model model, //
			@ModelAttribute("complaintForm") @Validated ComplaintInfo complaintInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			model.addAttribute("compMsg", "Hatalı giriş.");
			System.out.println("Hata!");
		}
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(complaintInfo.getResponseText().getBytes("ISO-8859-1"), "UTF-8");
			complaintInfo.setResponseText(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Complaint name cannot converted.");
			e.printStackTrace();
		}
		System.out.println("detay: "+complaintInfo.getId()+" "+complaintInfo.getSupportUserId()+" "+complaintInfo.getResponseText());
		this.complaintDAO.endComplaint(complaintInfo.getId(),complaintInfo.getSupportUserId(),complaintInfo.getResponseText());

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("compMsg", "Şikayet kapatıldı.");

//		return "redirect:/deptList";
		return "redirect:/supporter";
	}
	
	@RequestMapping(value = "/transferComplaint", method = RequestMethod.GET)
	public String transferComplaint(Model model,Principal principal, @RequestParam("id") Integer id) {
		UserInfo user = userDAO.findLoginUserInfo(principal.getName());
		model.addAttribute("userInfo", user);
		model.addAttribute("comp", this.complaintDAO.findComplaintInfo(id));
		List<LocationInfo> list = locationDAO.listLocationInfos();
		model.addAttribute("locationInfos", list);
		List<SupportTypeInfo> list2 = supportTypeDAO.listSupportTypeInfos();
		model.addAttribute("supportTypeInfos", list2);
//		return "redirect:/deptList";
		return "transferComplaint";
	}
	
	@RequestMapping(value = "/transferedComplaint", method = RequestMethod.POST)
	public String transferedComplaint(Model model, //
			@ModelAttribute("complaintForm") @Validated ComplaintInfo complaintInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			model.addAttribute("compMsg", "Hatalı giriş.");
			System.out.println("Hata!");
		}
		String decodedToUTF8;
		String decodedToUTF82;
		try {
			decodedToUTF8 = new String(complaintInfo.getResponseText().getBytes("ISO-8859-1"), "UTF-8");
			decodedToUTF82 = new String(complaintInfo.getComplaintText().getBytes("ISO-8859-1"), "UTF-8");
			complaintInfo.setResponseText(decodedToUTF8);
			complaintInfo.setComplaintText(decodedToUTF82);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Complaint name cannot converted.");
			e.printStackTrace();
		}
		System.out.println("detay: "+complaintInfo.getId()+" "+complaintInfo.getLocationId()+" "+complaintInfo.getSupportTypeId()+" "+complaintInfo.getSupportUserId()+" "+complaintInfo.getComplaintText()+" "+complaintInfo.getResponseText()+" "+complaintInfo.isEnded());
		this.complaintDAO.transferComplaint(complaintInfo.getId(),complaintInfo.getSupportUserId(),complaintInfo.getResponseText(),complaintInfo.getLocationId(),complaintInfo.getSupportTypeId(),complaintInfo.getComplaintText(),complaintInfo.isEnded());

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("compMsg", "Şikayet yönlendirildi.");

//		return "redirect:/deptList";
		return "redirect:/supporter";
	}
	
	
}
