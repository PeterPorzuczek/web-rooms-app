package com.pporzuczek.web.rooms.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pporzuczek.web.rooms.app.model.Room;
import com.pporzuczek.web.rooms.app.model.Unit;
import com.pporzuczek.web.rooms.app.service.RoomService;
import com.pporzuczek.web.rooms.app.service.UnitService;

@Controller
public class RoomController {
	
	private Logger log = LoggerFactory.getLogger(RoomController.class);
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private UnitService unitService;
	
	@RequestMapping("/room/list")
	public String list(ModelMap map) {
	   map.addAttribute("rooms", roomService.listTable());
	   return "room/list";
	}
	
	@ResponseBody
	@RequestMapping(value ="/room/list/export")
	public String listDownload(HttpServletResponse response) {
		String fileName = "roomList.json";
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		try {
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String content = gson.toJson(roomService.listExport());

		return content;
	}
	
	@RequestMapping(value = "/room/add", method = RequestMethod.GET)
	public String add(Room room, Model model) {
		List<Unit> unitList = unitService.list();
		model.addAttribute("unitList", unitList);

	   return "/room/add";
	}
	
	@RequestMapping(value = "/room/add", method = RequestMethod.POST)
	public String addPost(@Valid Room room, BindingResult result, Model model) {
		List<Unit> unitList = unitService.list();
		model.addAttribute("unitList", unitList);

	   if (result.hasErrors()) {
	       return "room/add";
	   }
	   
	   Room registeredRoom = roomService.add(room);

	   if (registeredRoom != null) {
	      return "redirect:/room/list";
	   } else {
	       log.error("Room already exists: " + room.getName());
	       //result.rejectValue("organization", "error.alreadyExists", "This organization already exists, please try another name.");
	       return "redirect:/room/add" + "?error";
	   }
	}
	
	@RequestMapping("/room/edit/{id}")
	public String edit(@PathVariable("id") Long id, Room room, Model model) {
		Room r = roomService.findById(id);
		room.setId(r.getId());
		room.setName(r.getName());
		room.setUnit(r.getUnit());
		room.setPositions(r.getPositions());
		room.setComputers(r.getComputers());
		room.setNetwork(r.getNetwork());
		room.setProjector(r.getProjector());
		room.setSpeakers(r.getSpeakers());
		room.setConditioning(r.getConditioning());
		room.setBoard(r.getBoard());
		
		List<Unit> unitList = unitService.list();
		model.addAttribute("unitList", unitList);
	   
	   return "/room/edit";
	}

	@RequestMapping(value = "/room/edit", method = RequestMethod.POST)
	public String editPost(@Valid Room room, BindingResult result, Model model) {
		 
		List<Unit> unitList = unitService.list();
		model.addAttribute("unitList", unitList);
		
	   if (result.hasFieldErrors("name")) {
	       return "/room/edit";
	   }

		Room roo = roomService.update(room);
		
		if (roo == null) {
			return "redirect:/room/edit/" + room.getId() + "?error";
		} else {
			return "redirect:/room/edit/" + room.getId() + "?updated";
		}
	}
	
	@RequestMapping("/room/delete")
	public String delete(Long id) {
		roomService.delete(id);

	   return "redirect:/room/list";
	}
}
