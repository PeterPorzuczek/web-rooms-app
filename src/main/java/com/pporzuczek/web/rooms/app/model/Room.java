package com.pporzuczek.web.rooms.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Room {
    @GeneratedValue
    @Id
    private Long id;
    
    @NotNull
    @Size(min = 3, max = 100, message = "Name must have at least 3 characters.")
    private String name;
    
    private int positions = 0;
    
    private int computers = 0;
    
    private String network = "NO";
    
    private String projector = "NO";
    
    private String speakers = "NO";
    
    private String conditioning = "NO";
    
    private String board = "NO";
       
    @ManyToOne
    @NotNull
    private Unit unit;
    
}