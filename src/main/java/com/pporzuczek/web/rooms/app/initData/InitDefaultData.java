package com.pporzuczek.web.rooms.app.initData;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pporzuczek.web.rooms.app.model.Account;
import com.pporzuczek.web.rooms.app.model.Organization;
import com.pporzuczek.web.rooms.app.service.AccountService;
import com.pporzuczek.web.rooms.app.service.OrganizationService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitDefaultData {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@PostConstruct
	private void initDefaultData() {
		log.debug("Init default start");
		
		/*
		List<Organization> organizations = new ArrayList<Organization>();
		Organization organization;
		
		organization = new Organization();
		organization.setName("Rooms Company");
		organizations.add(organization);
		
		for(Organization o : organizations) {        
			organizationService.add(o);
		}
		
		List<Account> accounts = new ArrayList<Account>();
		Account account;
		
		account = new Account();
		account.setUserName("piopor");
		account.setPassword("piopor4321");
		account.setEmail("piotrporzuczek@outlook.com");
		account.setFirstName("Piotr");
		account.setLastName("Porzuczek");
		account.setRole("ROLE_ADMIN");
		account.setAddress("");
		account.setCompanyName("Rooms");
		account.setOrganization(organizations.get(0));
		accounts.add(account);
		
		account = new Account();
		account.setUserName("agndzi");
		account.setPassword("agndzi4321");
		account.setEmail("agnieszka.dorota.dzieciol@gmail.com");
		account.setFirstName("Agnieszka");
		account.setLastName("Dzięcioł");
		account.setRole("ROLE_ADMIN");
		account.setAddress("");
		account.setCompanyName("Rooms");
		account.setOrganization(organizations.get(0));
		accounts.add(account);
		
		account = new Account();
		account.setUserName("aneewe");
		account.setPassword("aneewe4321");
		account.setEmail("aneta.ewertowska@gmail.com");
		account.setFirstName("Aneta");
		account.setLastName("Ewertowska");
		account.setRole("ROLE_ADMIN");
		account.setAddress("");
		account.setCompanyName("Rooms");
		account.setOrganization(organizations.get(0));
		accounts.add(account);
		
		account = new Account();
		account.setUserName("matkwi");
		account.setPassword("matkwi4321");
		account.setEmail("songoas@gmail.com");
		account.setFirstName("Mateusz");
		account.setLastName("Kwiatkowski");
		account.setRole("ROLE_ADMIN");
		account.setAddress("");
		account.setCompanyName("Rooms");
		account.setOrganization(organizations.get(0));
		accounts.add(account);
		
		for(Account a : accounts) {        
			accountService.register(a);
		}
		
		
//		List<Organization> org = organizationService.list();
//		for (Organization o : org) {
//			System.out.println(String.valueOf(o.getAccount().size()));
//		}
		*/
		
		log.debug("Init default done");
	}
}