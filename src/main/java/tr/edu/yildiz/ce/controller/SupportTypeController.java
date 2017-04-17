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

import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.model.SupportTypeInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class SupportTypeController {
	
	@Autowired
	private SupportTypeDAO supportTypeDAO;
	
	private String formSupportType(Model model, SupportTypeInfo supportTypeInfo) {
		model.addAttribute("supportTypeForm", supportTypeInfo);

		if (supportTypeInfo.getId() == null) {
			model.addAttribute("formTitle", "Create Dept");
		} else {
			model.addAttribute("formTitle", "Edit Dept");
		}

		return "formDept";
	}
	
	@RequestMapping(value = "/saveSupportType", method = RequestMethod.POST)
	public String saveSupportType(Model model, //
			@ModelAttribute("supportTypeForm") @Validated SupportTypeInfo supportTypeInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors()) {
			return this.formSupportType(model, supportTypeInfo);
		}
		
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(supportTypeInfo.getType().getBytes("ISO-8859-1"), "UTF-8");
			supportTypeInfo.setType(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Dept name cannot converted.");
			e.printStackTrace();
		}

		this.supportTypeDAO.saveSupportType(supportTypeInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("message", "Bölüm eklendi.");

//		return "redirect:/deptList";
		return "redirect:/admin";
	}
}
