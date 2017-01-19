package com.pporzuczek.web.rooms.app.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pporzuczek.web.rooms.app.model.Room;
import com.pporzuczek.web.rooms.app.model.Unit;

@Repository
public interface RoomRepository extends JpaRepository<Room, Serializable> {
	
	Room findById(Long id);
	Room findByName(String name);
	List<Room> findByUnit(Unit unit);
	
	@Query("select ro from Room ro " +
	         "where ro.name = :name and ro.unit = :unit")
	Room findByNameAndUnit(
			@Param("name") String name,  
			@Param("unit") Unit unit);
    
    @Modifying
    @Transactional
    @Query("update Room r set r.name = :name, r.positions = :positions, "
    		+ "r.computers = :computers, r.network = :network, r.projector = :projector, r.speakers = :speakers, "
    		+ "r.conditioning = :conditioning, r.board = :board, r.unit = :unit "
            + "where r.id = :id")
    int updateRoom(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("positions") int positions,
            @Param("computers") int computers,
            @Param("network") String network,
            @Param("projector") String projector,
            @Param("speakers") String speakers,
            @Param("conditioning") String conditioning,
            @Param("board") String board,
            @Param("unit") Unit unit);
}
