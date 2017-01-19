package com.pporzuczek.web.rooms.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pporzuczek.web.rooms.app.model.Organization;
import com.pporzuczek.web.rooms.app.repository.OrganizationRepository;

@Service
public class OrganizationService {
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private AccountService  accountService;
	
	@Autowired
	private UnitService  unitService;
	
	public Organization findById(Long id) {
		Organization organization = organizationRepository.findById(id);
		return organization;
	}
	
	public List<Organization> list() {
		return organizationRepository.findAll();
	}
	
    public Boolean delete(Long id) {
        this.organizationRepository.delete(id);
        return true;
    }
    
    public Organization add(Organization organization) {
    	if (this.organizationRepository.findByName(organization.getName()) == null) {
    		this.organizationRepository.save(organization);
    		return organization;
    	} else {
    		return null;
    	}
    }
    
    public Organization update(Organization organization) {
        return update(organization.getId(), organization);
    }
    
    public Organization update(Long id, Organization newData) {
    	if (this.organizationRepository.findByName(newData.getName()) == null) {
            this.organizationRepository.updateOrganization(
                    id, 
                    newData.getName());
            return newData;
    	} else {
    		return null;
    	}
    }
	
	public List<List<String>> listTable() {
		List<Organization> organizations = organizationRepository.findAll();
	    List<List<String>> organizationsList = new LinkedList<List<String>>();
	    
	    Comparator<Organization> comparator = new Comparator<Organization>() {
		    @Override
		    public int compare(Organization left, Organization right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(organizations, comparator);
	    
	    for (Organization organization:organizations) {	    	
	    	List<String> organizationData = new ArrayList<String>();
	    	
	    	organizationData.add("<a style=\"color: #f9b012\" href=\"/organization/edit/" + organization.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25z\"></path><path d=\"M20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z\"></path></svg></a>");
	    	organizationData.add(organization.getName());
	    	
	    	int countMembers = accountService.countInOrganization(organization);
	    	organizationData.add(String.valueOf(countMembers));
	    	
	    	int countUnits = unitService.countInOrganization(organization);
	    	organizationData.add(String.valueOf(countUnits));
	    	
	    	if (countMembers != 0 || countUnits != 0) {
	    		organizationData.add("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\" fill=\"#E4E4E4\" ></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\" fill=\"#E4E4E4\"></path></svg>");
	    	}else{
	    		organizationData.add("<a style=\"color: #f9b012\" href=\"/organization/delete?id=" + organization.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\"></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\"></path></svg></a>");
	    	}
	    	
	    	organizationsList.add(organizationData);
	    }
		return organizationsList;
	}
    
	public Map<String, Object> listExport() {
		List<Organization> organizations = organizationRepository.findAll();
	    Comparator<Organization> comparator = new Comparator<Organization>() {
		    @Override
		    public int compare(Organization left, Organization right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(organizations, comparator);
		
		Map<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
		for (Organization organization : organizations) {	
			Map<String, Object> linkedHashMapMap = new LinkedHashMap<String, Object>();
			linkedHashMapMap.put("id", organization.getId());
			linkedHashMapMap.put("name", organization.getName());
			linkedHashMapMap.put("accountCount", accountService.countInOrganization(organization));
			linkedHashMap.put("organization " + String.valueOf(organization.getId()), linkedHashMapMap);
		}
		return linkedHashMap;
	}
    
}
