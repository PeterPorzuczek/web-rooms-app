package com.pporzuczek.web.rooms.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pporzuczek.web.rooms.app.model.Room;
import com.pporzuczek.web.rooms.app.model.Unit;
import com.pporzuczek.web.rooms.app.repository.RoomRepository;

@Service
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private EventService eventService;

	public Room findById(Long id) {
		Room room = roomRepository.findById(id);
		return room;
	}
	
	public Room findByName(String name) {
		Room room = roomRepository.findByName(name);
		return room;
	}
	
	public List<Room> findByUnit(Unit unit) {
		List<Room> rooms = roomRepository.findByUnit(unit);
		return rooms;
	}
	
	public List<Room> list() {
		return roomRepository.findAll();
	}
	
    public Boolean delete(Long id) {
        this.roomRepository.delete(id);
        return true;
    }
    
    public Room add(Room room) {
    	if (this.roomRepository.findByNameAndUnit(room.getName(), room.getUnit()) == null) {
    		this.roomRepository.save(room);
    		return room;
    	} else {
    		return null;
    	}
    }
    
    public Room update(Room room) {
        return update(room.getId(), room);
    }
    
    public Room update(Long id, Room newData) {
    	
    	String checkName = newData.getName();
    	Long checkId = id;
    	
    	Room check = this.roomRepository.findByNameAndUnit(newData.getName(), newData.getUnit());
    	if (check != null) {
    		checkName = check.getName();
    		checkId = check.getId();
    	}

    	if ( checkName.equals(newData.getName()) || checkId.equals(id)) {
            this.roomRepository.updateRoom(
                    id, 
                    newData.getName(),
                    newData.getPositions(),
                    newData.getComputers(),
                    newData.getNetwork(),
                    newData.getProjector(),
                    newData.getSpeakers(),
                    newData.getConditioning(),
                    newData.getBoard(),
                    newData.getUnit());
            return newData;
    	} else {
    		return null;
    	}
    }
    
	public List<List<String>> listTable() {
		List<Room> rooms = roomRepository.findAll();
	    List<List<String>> roomsList = new LinkedList<List<String>>();
	    
	    Comparator<Room> comparator = new Comparator<Room>() {
		    @Override
		    public int compare(Room left, Room right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(rooms, comparator);
	    
	    for (Room room:rooms) {	    	
	    	List<String> roomData = new ArrayList<String>();
	    	
	    	roomData.add("<a style=\"color: #f9b012\" href=\"/room/edit/" + room.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25z\"></path><path d=\"M20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z\"></path></svg></a>");
	    	roomData.add(room.getName());
	    	roomData.add(room.getUnit().getName());
	    	roomData.add(String.valueOf(room.getPositions()));
	    	roomData.add(String.valueOf(room.getComputers()));
	    	
	    	if (room.getNetwork().equals("YES")) {
	    		roomData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
	    	}else{
	    		roomData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
	    	}
	    	
	    	if (room.getProjector().equals("YES")) {
	    		roomData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
	    	}else{
	    		roomData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
	    	}
	    	
	    	if (room.getSpeakers().equals("YES")) {
	    		roomData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
	    	}else{
	    		roomData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
	    	}
	    	
	    	if (room.getConditioning().equals("YES")) {
	    		roomData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
	    	}else{
	    		roomData.add("<span style=\"font-size:1px; color: #fffff\">NO</span><svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
	    	}
	    	
	    	if (room.getBoard().equals("YES")) {
	    		roomData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
	    	}else{
	    		roomData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
	    	}
	    	
	    	int count = eventService.countInRoom(room);
	    	roomData.add(String.valueOf(count));
	    	
	    	if (count != 0) {
	    		roomData.add("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\" fill=\"#E4E4E4\" ></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\" fill=\"#E4E4E4\"></path></svg>");
	    	}else{
	    		roomData.add("<a style=\"color: #f9b012\" href=\"/room/delete?id=" + room.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\"></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\"></path></svg></a>");
	    	}
	    	
	    	roomsList.add(roomData);
	    }
		return roomsList;
	}
	
	public List<List<String>> listTable(Long id) {
		List<Room> rooms = roomRepository.findAll();
	    List<List<String>> roomsList = new LinkedList<List<String>>();
	    
	    Comparator<Room> comparator = new Comparator<Room>() {
		    @Override
		    public int compare(Room left, Room right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(rooms, comparator);
	    
	    for (Room room:rooms) {	    	
	    	List<String> roomData = new ArrayList<String>();
	    	
	    	if (room.getUnit().getId().equals(id)) {
	    	
		    	roomData.add("<a style=\"color: #f9b012\" href=\"/book/room/" + room.getId() + "\"><svg fill=\"#000000\" height=\"32\" viewBox=\"0 0 32 32\" width=\"32\" xmlns=\"http://www.w3.org/2000/svg\"><path d=\"M8.59 16.34l4.58-4.59-4.58-4.59L10 5.75l6 6-6 6z\"/><path d=\"M0-.25h24v24H0z\" fill=\"none\"/></svg></a>");
		    	roomData.add(room.getName());
		    	roomData.add(String.valueOf(room.getPositions()));
		    	roomData.add(String.valueOf(room.getComputers()));
		    	
		    	if (room.getNetwork().equals("YES")) {
		    		roomData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
		    	}else{
		    		roomData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
		    	}
		    	
		    	if (room.getProjector().equals("YES")) {
		    		roomData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
		    	}else{
		    		roomData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
		    	}
		    	
		    	if (room.getSpeakers().equals("YES")) {
		    		roomData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
		    	}else{
		    		roomData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
		    	}
		    	
		    	if (room.getConditioning().equals("YES")) {
		    		roomData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
		    	}else{
		    		roomData.add("<span style=\"font-size:1px; color: #fffff\">NO</span><svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
		    	}
		    	
		    	if (room.getBoard().equals("YES")) {
		    		roomData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
		    	}else{
		    		roomData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
		    	}
		    	
		    	//int count = eventService.countInRoom(room);
		    	roomData.add("");
		    	
		    	
		    	roomsList.add(roomData);
	    	}
	    }
		return roomsList;
	}
	
	public List<List<String>> listTablePublic(String name) {
		List<Room> rooms = roomRepository.findAll();
	    List<List<String>> roomsList = new LinkedList<List<String>>();
	    
	    Comparator<Room> comparator = new Comparator<Room>() {
		    @Override
		    public int compare(Room left, Room right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(rooms, comparator);
	    
	    for (Room room:rooms) {	    	
	    	List<String> roomData = new ArrayList<String>();
	    	if (room.getUnit().getOrganization().getName().startsWith(name)) {
	    	roomData.add("<a style=\"color: #f9b012\" href=\"/calendar/room/" + room.getId() + "\"><svg fill=\"#000000\" height=\"32\" viewBox=\"0 0 32 32\" width=\"32\" xmlns=\"http://www.w3.org/2000/svg\"><path d=\"M8.59 16.34l4.58-4.59-4.58-4.59L10 5.75l6 6-6 6z\"/><path d=\"M0-.25h24v24H0z\" fill=\"none\"/></svg></a>");
	    	roomData.add(room.getName());
	    	roomData.add(String.valueOf(room.getUnit().getName()));
	    	//int count = eventService.countInRoom(room);
	    	roomData.add("");
	    	
	    	roomsList.add(roomData);
	    	}
	    }
		return roomsList;
	}
	
	public List<Room> listExport() {
		List<Room> rooms = roomRepository.findAll();
		
	    Comparator<Room> comparator = new Comparator<Room>() {
		    @Override
		    public int compare(Room left, Room right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(rooms, comparator);
		
		return rooms;
	}
	
	public int countInUnit(Unit unit) {
		return this.roomRepository.findByUnit(unit).size();
	}
	
	public List<Room> roomUnitList(Unit unit) {
		return this.roomRepository.findByUnit(unit);
	}
}
