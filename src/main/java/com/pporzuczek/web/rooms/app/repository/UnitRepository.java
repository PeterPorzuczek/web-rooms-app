package com.pporzuczek.web.rooms.app.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pporzuczek.web.rooms.app.model.Organization;
import com.pporzuczek.web.rooms.app.model.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Serializable> {
	
	Unit findById(Long id);
	Unit findByName(String name);
	List<Unit> findByOrganization(Organization organization);
	
	@Query("select un from Unit un " +
	         "where un.name = :name and un.organization = :organization")
	Unit findByNameAndOrganization(
			@Param("name") String name,  
			@Param("organization") Organization organization);
    
    @Modifying
    @Transactional
    @Query("update Unit u set u.name = :name, u.address = :address, u.organization = :organization  "
            + "where u.id = :id")
    int updateUnit(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("address") String address,
            @Param("organization") Organization organization);
}