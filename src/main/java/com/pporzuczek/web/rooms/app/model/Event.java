package com.pporzuczek.web.rooms.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Event {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String title;
	private String description; 
	private String speaker;
	
	@Column(name="start")
	private Date start;
	
	@Column(name="stop")
	private Date end;
	
    @NotNull
    @ManyToOne
    private Room room;
   
    @NotNull
    @ManyToOne
    private Account account;
	
	public Event(String title, String description, String speaker, Date start, Date end, Room room, Account account) {
		super();
		this.title = title;
		this.description = description;
		this.speaker = speaker;
		this.start = start;
		this.end = end;
		this.room = room;
		this.account = account;
	}
	
	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + ", description="
				+ description + ", speaker=" + speaker + ", start=" + start
				+ ", stop=" + end + ", room=" + room + ", account=" + account + "]";
	}
}