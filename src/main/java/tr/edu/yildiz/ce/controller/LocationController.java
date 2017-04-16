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

import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.model.LocationInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class LocationController {
	
	@Autowired
	private LocationDAO locationDAO;
	
	private String formLocation(Model model, LocationInfo locationInfo) {
		model.addAttribute("locationForm", locationInfo);

		if (locationInfo.getId() == null) {
			model.addAttribute("formTitle", "Create Dept");
		} else {
			model.addAttribute("formTitle", "Edit Dept");
		}

		return "formDept";
	}
	
	@RequestMapping(value = "/saveLocation", method = RequestMethod.POST)
	public String saveLocation(Model model, //
			@ModelAttribute("locationForm") @Validated LocationInfo locationInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors()) {
			return this.formLocation(model, locationInfo);
		}
		
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(locationInfo.getDescription().getBytes("ISO-8859-1"), "UTF-8");
			locationInfo.setDescription(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Dept name cannot converted.");
			e.printStackTrace();
		}

		this.locationDAO.saveLocation(locationInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("message", "Bölüm eklendi.");

//		return "redirect:/deptList";
		return "redirect:/addRules";
	}
}
