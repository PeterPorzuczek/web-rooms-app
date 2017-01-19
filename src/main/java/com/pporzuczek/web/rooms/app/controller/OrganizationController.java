package com.pporzuczek.web.rooms.app.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pporzuczek.web.rooms.app.model.Organization;
import com.pporzuczek.web.rooms.app.service.OrganizationService;

@Controller
public class OrganizationController {

	private Logger log = LoggerFactory.getLogger(OrganizationController.class);
	
	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping("/organization/list")
	public String list(ModelMap map) {
	   map.addAttribute("organizations", organizationService.listTable());
	   return "organization/list";
	}

	@ResponseBody
	@RequestMapping(value ="/organization/list/export")
	public String listDownload(HttpServletResponse response) {
		String fileName = "organizationList.json";
		response.setContentType("application/force-download");
	  response.setHeader("Content-Disposition", "attachment; filename="+fileName);
	  try {
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  Gson gson = new GsonBuilder().setPrettyPrinting().create();
	  String content = gson.toJson(organizationService.listExport());
	  return content;
	}
	
	@RequestMapping(value = "/organization/add", method = RequestMethod.GET)
	public String add(Organization organization) {
	   return "/organization/add";
	}
	
	@RequestMapping(value = "/organization/add", method = RequestMethod.POST)
	public String addPost(@Valid Organization organization, BindingResult result) {
	   if (result.hasErrors()) {
	       return "organization/add";
	   }
	   
	   Organization registeredOrganization = organizationService.add(organization);
	   if (registeredOrganization != null) {
	      return "redirect:/organization/list";
	   } else {
	       log.error("Organization already exists: " + organization.getName());
	       //result.rejectValue("organization", "error.alreadyExists", "This organization already exists, please try another name.");
	       return "redirect:/organization/add" + "?error";
	   }
	}

	@RequestMapping("/organization/edit/{id}")
	public String edit(@PathVariable("id") Long id, Organization organization) {
		Organization o = organizationService.findById(id);
		organization.setId(o.getId());
		organization.setName(o.getName());
	   
	   return "/organization/edit";
	}

	@RequestMapping(value = "/organization/edit", method = RequestMethod.POST)
	public String editPost(@Valid Organization organization, BindingResult result) {
		 
	   if (result.hasFieldErrors("name")) {
	       return "/organization/edit";
	   }

		Organization org = organizationService.update(organization);
		
		if (org == null) {
			return "redirect:/organization/edit/" + organization.getId() + "?error";
		} else {
			return "redirect:/organization/edit/" + organization.getId() + "?updated";
		}
	}
	
	@RequestMapping("/organization/delete")
	public String delete(Long id) {
		organizationService.delete(id);
	   return "redirect:/organization/list";
	}

}
