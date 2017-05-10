package com.pporzuczek.web.rooms.app.initData;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pporzuczek.web.rooms.app.model.Account;
import com.pporzuczek.web.rooms.app.model.Organization;
import com.pporzuczek.web.rooms.app.model.Room;
import com.pporzuczek.web.rooms.app.model.Unit;
import com.pporzuczek.web.rooms.app.service.AccountService;
import com.pporzuczek.web.rooms.app.service.OrganizationService;
import com.pporzuczek.web.rooms.app.service.RoomService;
import com.pporzuczek.web.rooms.app.service.UnitService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitDefaultData {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private RoomService roomService;
	
	@PostConstruct
	private void initDefaultData() {
		log.debug("Init default start");
		///*
		List<Organization> organizations = new ArrayList<Organization>();
		Organization organization;
		
		organization = new Organization();
		organization.setName("Rooms");
		organizations.add(organization);
		
		organization = new Organization();
		organization.setName("Other Company");
		organizations.add(organization);
		
		for(Organization o : organizations) {        
			organizationService.add(o);
		}
		
		List<Account> accounts = new ArrayList<Account>();
		Account account;
		
		account = new Account();
		account.setUserName("admin");
		account.setPassword("admin");
		account.setEmail("admin@admin.com");
		account.setFirstName("Admin");
		account.setLastName("Admin");
		account.setRole("ROLE_ADMIN");
		account.setAddress("");
		account.setCompanyName("Rooms");
		account.setOrganization(organizations.get(0));
		accounts.add(account);
		
		account = new Account();
		account.setUserName("user");
		account.setPassword("user");
		account.setEmail("user@user.com");
		account.setFirstName("User");
		account.setLastName("User");
		account.setRole("ROLE_USER");
		account.setAddress("");
		account.setCompanyName("Rooms");
		account.setOrganization(organizations.get(0));
		accounts.add(account);
		
		for(Account a : accounts) {        
			accountService.register(a);
		}
		try {
			List<Unit> units = new ArrayList<Unit>();
			Unit unit;
			
			unit = new Unit();
			unit.setName("Building A");
			unit.setAddress("Armii Krajowej 101 81-824 Sopot");
			unit.setOrganization(organizations.get(0));
			units.add(unit);
			
			unit = new Unit();
			unit.setName("Building B");
			unit.setAddress("Armii Krajowej 101 81-824 Sopot");
			unit.setOrganization(organizations.get(0));
			units.add(unit);
			
			for(Unit u : units) {        
				unitService.add(u);
			}
			
			List<Room> rooms = new ArrayList<Room>();
			Room room;
			
			room = new Room();
			room.setName("Room 1");
			room.setPositions(10);
			room.setComputers(1);
			room.setBoard("NO");
			room.setConditioning("NO");
			room.setNetwork("YES");
			room.setProjector("NO");
			room.setSpeakers("NO");
			room.setUnit(unitService.findByName("Building A"));
			rooms.add(room);
			
			room = new Room();
			room.setName("Room 2");
			room.setPositions(30);
			room.setComputers(30);
			room.setBoard("YES");
			room.setConditioning("NO");
			room.setNetwork("YES");
			room.setProjector("NO");
			room.setSpeakers("NO");
			room.setUnit(unitService.findByName("Building A"));
			rooms.add(room);
	
			room = new Room();
			room.setName("Room 333");
			room.setPositions(50);
			room.setComputers(1);
			room.setBoard("YES");
			room.setConditioning("NO");
			room.setNetwork("YES");
			room.setProjector("NO");
			room.setSpeakers("NO");
			room.setUnit(unitService.findByName("Building B"));
			rooms.add(room);
			
			for(Room r : rooms) {        
				roomService.add(r);
			}
		}
		catch(Exception e){
			log.debug("Section works only on first run on db creation");
		}
		//*/
		log.debug("Init default done");
	}
}