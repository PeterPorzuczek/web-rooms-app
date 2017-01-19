package com.pporzuczek.web.rooms.app.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pporzuczek.web.rooms.app.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Serializable> {
	
	Organization findById(Long id);
	Organization findByName(String name);
    
    @Modifying
    @Transactional
    @Query("update Organization o set o.name = :name "
            + "where o.id = :id")
    int updateOrganization(
            @Param("id") Long id,
            @Param("name") String name);
	
}
