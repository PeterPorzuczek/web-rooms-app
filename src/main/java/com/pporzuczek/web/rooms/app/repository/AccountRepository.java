package com.pporzuczek.web.rooms.app.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pporzuczek.web.rooms.app.model.Account;
import com.pporzuczek.web.rooms.app.model.Organization;

@Repository
public interface AccountRepository extends JpaRepository<Account, Serializable> {
    Account findOneByUserName(String name);
    Account findOneByEmail(String email);
    Account findOneByUserNameOrEmail(String username, String email);
    Account findOneByToken(String token);
    Account findOneByOrganization(Organization organization);
    List<Account> findByOrganization(Organization organization);
    
    @Modifying
    @Transactional
    @Query("update Account a set a.email = :email, a.firstName = :firstName, "
            + "a.lastName = :lastName, a.address = :address, a.companyName = :companyName, "
    		+ "a.role = :role, a.organization = :organization "
            + "where a.userName = :userName")
    int updateUser(
            @Param("userName") String userName, 
            @Param("email") String email,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("address") String address,
            @Param("companyName") String companyName,
    		@Param("role") String role,
    		@Param("organization") Organization organization);
    @Modifying
    @Transactional
    @Query("update Account a set a.email = :email, a.firstName = :firstName, "
            + "a.lastName = :lastName, a.address = :address, a.companyName = :companyName, "
    		+ "a.password = :password, a.role = :role, a.organization = :organization "
            + "where a.userName = :userName")
    int updateUserWithPassword(
            @Param("userName") String userName, 
            @Param("email") String email,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("address") String address,
            @Param("companyName") String companyName,
    		@Param("password") String password,
    		@Param("role") String role,
    		@Param("organization") Organization organization);
    
    @Modifying
    @Transactional
    @Query("update Account a set a.lastLogin = CURRENT_TIMESTAMP where a.userName = ?1")
    int updateLastLogin(String userName);
   
}