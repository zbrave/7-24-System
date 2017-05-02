package tr.edu.yildiz.ce.controller;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.model.ComplaintInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class ComplaintController {

	@Autowired
	private ComplaintDAO complaintDAO;
	
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
}
