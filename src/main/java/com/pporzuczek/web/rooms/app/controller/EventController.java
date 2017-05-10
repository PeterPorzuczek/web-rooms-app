package com.pporzuczek.web.rooms.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pporzuczek.web.rooms.app.model.Account;
import com.pporzuczek.web.rooms.app.model.Event;
import com.pporzuczek.web.rooms.app.model.Room;
import com.pporzuczek.web.rooms.app.model.Unit;
import com.pporzuczek.web.rooms.app.service.AccountService;
import com.pporzuczek.web.rooms.app.service.EventService;
import com.pporzuczek.web.rooms.app.service.RoomService;
import com.pporzuczek.web.rooms.app.service.UnitService;

@Controller
public class EventController {
	
	@Autowired
	EventService eventService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private UnitService unitService;
	
	@RequestMapping(value="/calendar/addevent", method=RequestMethod.POST)
	public @ResponseBody Event addEvent(@RequestBody Event event) {
		Account a = accountService.getLoggedInAccount();
		if (a.getId().equals(event.getAccount().getId()) || a.isAdmin()) {
			return eventService.add(event);
		} else {
			return null;
		}
	}
	
	@RequestMapping(value="/calendar/updateevent", method=RequestMethod.PATCH)
	public @ResponseBody Event updateEvent(@RequestBody Event event) {
		Account a = accountService.getLoggedInAccount();
		if (a.getId().equals(event.getAccount().getId())){
			return eventService.update(event);
		} else {
			return null;
		}
	}
	
	@RequestMapping(value="/calendar/removeevent", method=RequestMethod.DELETE)
	public @ResponseBody void removeEvent(@RequestBody Event event) {
		Account a = accountService.getLoggedInAccount();
		if (a.getId().equals(event.getAccount().getId()) || a.isAdmin()){
			eventService.delete(event.getId());
		}
	}
	
	@RequestMapping(value="/calendar/events", method=RequestMethod.GET)
	public @ResponseBody List<Event> getEventsInRange(	@RequestParam(value = "start", required = true) String start, 
														@RequestParam(value = "end", required = true) String end,
														@RequestParam(value = "room", required = true) String room) {
		List<Event> events = eventService.findByDatesBetween(start, end, room);
		
		return clearAccount(events); 
	}	
	
	private List<Event> clearAccount(List<Event> e) {
		Account a = accountService.getLoggedInAccount();
		List<Event> events = e;
		for (int i = 0; i < events.size(); i++) {
			if (a != null) {
			Account eventAccount = events.get(i).getAccount();
			if (!a.getId().equals(eventAccount.getId())){
				eventAccount.setPassword("   ");
				eventAccount.setConfirmPassword("   ");
				eventAccount.setEmail("   ");
				eventAccount.setRole("   ");
				eventAccount.setToken("   ");
				eventAccount.setFirstName("   ");
				eventAccount.setLastName("   ");
				eventAccount.setAddress("   ");
				eventAccount.setCompanyName("   ");
				eventAccount.setLastLogin("   ");
				if (!a.isAdmin()) {
					eventAccount.setId(null);
					eventAccount.setUserName("   ");
				}
				events.get(i).setAccount(eventAccount);
			}
			}else{
				
				events.get(i).setAccount(null);
			}
		}
		return events;
	}
	
	@RequestMapping("/event/list/delete")
	public String delete(Long id) {
		Account a = accountService.getLoggedInAccount();
		if (a.getId().equals(eventService.findById(id).getAccount().getId()) || a.isAdmin()) { 
			eventService.delete(id);
		}
		return "redirect:/event/list/";
	}
	
	@RequestMapping("/event/list/delete/all")
	public String deleteAll(Long id) {
		Account a = accountService.getLoggedInAccount();
			List<Event> events = eventService.findByAccount(a);
			for (Event e : events) {
				eventService.delete(e.getId());
			}
		return "redirect:/event/list/";
	}
		
	@RequestMapping("/event/list/all/delete")
	public String deleteInAll(Long id) {
		Account a = accountService.getLoggedInAccount();
		if (a.isAdmin()) {
			eventService.delete(id);
		}
		return "redirect:/event/list/all";
	}
	
	@RequestMapping("/event/list/")
	public String list(Model model) {
		Account a = accountService.getLoggedInAccount();
        model.addAttribute("events", eventService.listTable(a));
		return "event/list";
	}
	
	@RequestMapping("/event/list/all")
	public String listall(Model model) {
		Account a = accountService.getLoggedInAccount();
        if (a.isAdmin()) {
        	 model.addAttribute("events", eventService.listTable());
        	 return "event/listall";
		}else{
			return "redirect:/";
		}
	}
	
	@RequestMapping(value="/event/edit/{id}", method=RequestMethod.GET)
	public String room(@PathVariable("id") Long id, Model model) {
		Account a = accountService.getLoggedInAccount();
		Event e = this.eventService.findById(id);
		if (a.getId().equals(e.getAccount().getId()) || a.isAdmin()) {
			model.addAttribute("room", e.getRoom());
			model.addAttribute("evente", this.eventService.findById(id));
			return "/event/calendar";
		} else{
			return "redirect:/";
		}
	}
	
	@ResponseBody
	@RequestMapping(value ="/event/list/all/export")
	public String listAllDownload(HttpServletResponse response) {
		
		String fileName = "eventList.json";
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		try {
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String content = gson.toJson(eventService.listExport(null));
		return content;
	}
	
	@ResponseBody
	@RequestMapping(value ="/event/list/export")
	public String listDownload(HttpServletResponse response) {
		Account a = accountService.getLoggedInAccount();
		
		String fileName = "eventList.json";
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		try {
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String content = gson.toJson(eventService.listExport(a));
		return content;
	}
	
	@RequestMapping("/book/unit")
	public String bookOrganization(Model model) {
		Account a = accountService.getLoggedInAccount();
        model.addAttribute("units", unitService.listTable(a.getOrganization()));
		return "book/unit";
	}
	
	@RequestMapping("/book/unit/{id}")
	public String bookUnit(@PathVariable("id") Long id, Model model) {
		Account a = accountService.getLoggedInAccount();
		List<Unit> availableUnits = unitService.organizationUnitList(a.getOrganization());
		for (Unit u : availableUnits){
			if(u.getId().equals(id)){
				model.addAttribute("unitId", id);
				model.addAttribute("rooms", roomService.listTable(u.getId()));
			}
		}
	   return "/book/room";
	}
	
	
	@RequestMapping(value="/book/room/{id}", method=RequestMethod.GET)
	public String bookRoom(@PathVariable("id") Long id, Model model) {
		Account a = accountService.getLoggedInAccount();
		List<Unit> availableUnits = unitService.organizationUnitList(a.getOrganization());
		for (Unit u : availableUnits) {
			List<Room> availableRooms = roomService.roomUnitList(u);
			for (Room r : availableRooms) {
				if (r.getId().equals(id)) {
					model.addAttribute("room", roomService.findById(id));
				}
			}
		}
		return "/book/calendar";
	}
	
	
	@RequestMapping("/calendar/room")
	public String checkOrganization(Model model) {
        model.addAttribute("rooms", roomService.listTablePublic("Uniwersystet Gdański - Wydział Zarządzania"));
		return "calendar/room";
	}
	
	@RequestMapping(value="/calendar/room/{id}", method=RequestMethod.GET)
	public String checkRoom(@PathVariable("id") Long id, Model model) {
		Room r = roomService.findById(id);
		if(r.getUnit().getOrganization().getName().startsWith("Uniwersystet Gdański - Wydział Zarządzania")) {
					model.addAttribute("room", r);
		}
		return "/calendar/calendar";
	}
}