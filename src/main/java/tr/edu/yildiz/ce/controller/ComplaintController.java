package tr.edu.yildiz.ce.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

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
import org.springframework.web.bind.annotation.ResponseBody;
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
	public String saveComplaint(Model model, HttpServletRequest request, //
			@ModelAttribute("complaintForm") @Validated ComplaintInfo complaintInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) throws IOException, ServletException {
			
		if (result.hasErrors()) {
			model.addAttribute("compMsgError", "Hatalı giriş.");
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
		System.out.println(complaintInfo.getLocationId()+" "+complaintInfo.getSupportTypeId()+" "+complaintInfo.getComplainantUserId()+" "+complaintInfo.getComplaintText()+" "+complaintInfo.getFile());
		this.complaintDAO.recordComplaint(complaintInfo.getLocationId(), complaintInfo.getSupportTypeId(), complaintInfo.getComplainantUserId(), complaintInfo.getComplaintText(), complaintInfo.getParentId());

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("compMsgSuccess", "Şikayet gönderildi.");

//		return "redirect:/deptList";
		return "redirect:/complaint";
	}
	
	@RequestMapping(value = "/endComplaint", method = RequestMethod.GET)
	public String endComplaint(Model model,Principal principal, @RequestParam("id") Integer id) {
		UserInfo user = userDAO.findLoginUserInfo(principal.getName());
		if(id == null){
			return "manager";
		}
		model.addAttribute("userInfo", user);
		ComplaintInfo comp = this.complaintDAO.findComplaintInfo(id);
		comp.setComplainantUserInfo(userDAO.findUserInfo(comp.getComplainantUserId()));
		comp.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(comp.getSupportTypeId()));
		comp.setLocationInfo(locationDAO.findLocationInfo(comp.getLocationId()));
		model.addAttribute("comp", comp);
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
		this.complaintDAO.endComplaint(complaintInfo.getId(),complaintInfo.getResponseText());

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
		return "transferComplaint";
	}
	
	@RequestMapping(value = "/assignComplaint", method = RequestMethod.GET)
	public String assignComplaint(Model model,Principal principal, @RequestParam("id") Integer id) {
		UserInfo user = userDAO.findLoginUserInfo(principal.getName());
		model.addAttribute("userInfo", user);
		ComplaintInfo comp = this.complaintDAO.findComplaintInfo(id);
		comp.setLocationInfo(locationDAO.findLocationInfo(comp.getLocationId()));
		comp.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(comp.getSupportTypeId()));
		model.addAttribute("comp", comp);
		List<UserInfo> list3 = userDAO.listUserInfosForAssignment(id);
		model.addAttribute("asgUser", list3);
		return "assignComplaint";
	}
	
	@RequestMapping(value="/getSupportComplaints",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getSupportComplaints(@RequestParam Integer id) {
		String res = "";
		List<ComplaintInfo> list4 = this.complaintDAO.listComplaintInfosForSupport(id);
		list4.addAll(complaintDAO.listComplaintInfosForSupportAck(id));
		for (ComplaintInfo tmp : list4) {
			tmp.setLocationInfo(locationDAO.findLocationInfo(tmp.getLocationId()));
			tmp.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(tmp.getSupportTypeId()));
			tmp.setComplainantUserInfo(userDAO.findUserInfo(tmp.getSupportUserId()));
		}
		for (ComplaintInfo tmp : list4) {
			if (tmp.getComplaintTime() == null ) {
				res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getLocationInfo().getDescription()+"</td><td>"+tmp.getSupportTypeInfo().getType()+"</td><td>"+tmp.getComplainantUserInfo().getUsername()+"</td><td>"+"Atama yapılmamış."+"</td><td><a href=\"listCompProcess?id="+tmp.getId()+"\" class=\"btn btn-success btn-xs\"><span class=\"glyphicon glyphicon-plus\"></span> Şikayet geçmişi</a></td></tr>");
			}
			else {
				res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getLocationInfo().getDescription()+"</td><td>"+tmp.getSupportTypeInfo().getType()+"</td><td>"+tmp.getComplainantUserInfo().getUsername()+"</td><td>"+tmp.getComplaintTime()+"</td><td><a href=\"listCompProcess?id="+tmp.getId()+"\" class=\"btn btn-success btn-xs\"><span class=\"glyphicon glyphicon-plus\"></span> Şikayet geçmişi</a></td></tr>");
			}
		}
		return res;
	}
	
	@RequestMapping(value="/getSupportAllComplaints",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getSupportAllComplaints(@RequestParam Integer id) {
		String res = "";
		List<ComplaintInfo> list4 = this.complaintDAO.listComplaintInfosByUserId(id);
		for (ComplaintInfo tmp : list4) {
			tmp.setLocationInfo(locationDAO.findLocationInfo(tmp.getLocationId()));
			tmp.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(tmp.getSupportTypeId()));
			tmp.setComplainantUserInfo(userDAO.findUserInfo(tmp.getSupportUserId()));
		}
		for (ComplaintInfo tmp : list4) {
			if (tmp.getComplaintTime() == null ) {
				res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getLocationInfo().getDescription()+"</td><td>"+tmp.getSupportTypeInfo().getType()+"</td><td>"+tmp.getComplainantUserInfo().getUsername()+"</td><td>"+"Atama yapılmamış."+"</td><td><a href=\"listCompProcess?id="+tmp.getId()+"\" class=\"btn btn-success btn-xs\"><span class=\"glyphicon glyphicon-plus\"></span> Şikayet geçmişi</a></td></tr>");
			}
			else {
				res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getLocationInfo().getDescription()+"</td><td>"+tmp.getSupportTypeInfo().getType()+"</td><td>"+tmp.getComplainantUserInfo().getUsername()+"</td><td>"+tmp.getComplaintTime()+"</td><td><a href=\"listCompProcess?id="+tmp.getId()+"\" class=\"btn btn-success btn-xs\"><span class=\"glyphicon glyphicon-plus\"></span> Şikayet geçmişi</a></td></tr>");
			}
		}
		return res;
	}
	
	@RequestMapping(value="/getComplaintList",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getComplaintList(@RequestParam(required = false) Integer id, @RequestParam(required = false) Integer id2 ) {
		String res = "";
		List<ComplaintInfo> list4 = null;
		if (id == 0 && id2 != 0) {
			list4 = this.complaintDAO.listComplaintInfos(null, id2);
		}
		else if (id != 0 && id2 == 0){
			list4 = this.complaintDAO.listComplaintInfos(id, null);
		}
		else if ((id == 0 && id2 == 0)){
			list4 = this.complaintDAO.listComplaintInfos(null, null);
		}
		else {
			list4 = this.complaintDAO.listComplaintInfos(id, id2);
		}
		if (list4.isEmpty() || list4 == null ) {
			System.out.println("Liste boş döndü");
			return res;
		}
		for (ComplaintInfo tmp : list4) {
			tmp.setLocationInfo(locationDAO.findLocationInfo(tmp.getLocationId()));
			tmp.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(tmp.getSupportTypeId()));
			tmp.setComplainantUserInfo(userDAO.findUserInfo(tmp.getSupportUserId()));
		}
		System.out.println("size:"+list4.size());
		for (ComplaintInfo tmp : list4) {
			if (tmp.getComplaintTime() == null && tmp.getSupportUserId() == null) {
				res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getLocationInfo().getDescription()+"</td><td>"+tmp.getSupportTypeInfo().getType()+"</td><td>"+"Atama yapılmamış."+"</td><td>"+"Atama yapılmamış."+"</td><td><a href=\"listCompProcess?id="+tmp.getId()+"\" class=\"btn btn-danger btn-xs\"><span class=\"glyphicon glyphicon-plus\"></span> Şikayet geçmişi</a></td></tr>");
			}
			else {
				res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getLocationInfo().getDescription()+"</td><td>"+tmp.getSupportTypeInfo().getType()+"</td><td>"+tmp.getComplainantUserInfo().getUsername()+"</td><td>"+tmp.getComplaintTime()+"</td><td><a href=\"listCompProcess?id="+tmp.getId()+"\" class=\"btn btn-success btn-xs\"><span class=\"glyphicon glyphicon-plus\"></span> Şikayet geçmişi</a></td></tr>");
			}
		}
		return res;
	}
	
	@RequestMapping(value = "/listCompProcess", method = RequestMethod.GET)
	public String listCompProcess(Model model,Principal principal, @RequestParam("id") Integer id) {
		ComplaintInfo main = this.complaintDAO.findComplaintInfo(id);
		List<ComplaintInfo> tree = this.complaintDAO.listComplaintProcess(id);
		for (ComplaintInfo t : tree) {
			t.setLocationInfo(locationDAO.findLocationInfo(t.getLocationId()));
			t.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(t.getSupportTypeId()));
			t.setComplainantUserInfo(userDAO.findUserInfo(t.getComplainantUserId()));
		}
		model.addAttribute("tree", tree);
		model.addAttribute("main", main);
		return "listCompProcess";
	}
	
	@RequestMapping(value = "/assignedComplaint", method = RequestMethod.POST)
	public String assignedComplaint(Model model, //
			@ModelAttribute("complaintForm") @Validated ComplaintInfo complaintInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			model.addAttribute("compMsg", "Hatalı giriş.");
			System.out.println("Hata!");
		}
		
		this.complaintDAO.assingComplaint(complaintInfo.getId(), complaintInfo.getSupportUserId());

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("compMsg", "Şikayet atandı.");

//		return "redirect:/deptList";
		return "redirect:/assignComplaints";
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
		this.complaintDAO.transferComplaint(complaintInfo.getId(),complaintInfo.getResponseText(),complaintInfo.getLocationId(),complaintInfo.getSupportTypeId(),complaintInfo.getComplaintText(),complaintInfo.isEnded());

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("compMsg", "Şikayet yönlendirildi.");

//		return "redirect:/deptList";
		return "redirect:/supporter";
	}
	
	
}
