package com.pporzuczek.web.rooms.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

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
public class Account {
    @GeneratedValue
    @Id
    private Long id;
    
    @NotNull
    @Size(min = 3, max = 100, message = "Username must have at least 3 characters.")
    private String userName;
    
    @NotNull
    @Size(min = 3, max = 100, message = "Password must have at least 3 characters.")
    private String password;
    
    @Transient
    private String confirmPassword;
    
    @Email(message = "Email address is not valid.")
    @NotNull
    private String email;
    
    private String token;
    
    private String role = "ROLE_USER";
    
    private String firstName;
    
    private String lastName;
    
    private String address;
    
    private String companyName;
    
    private String lastLogin;
    
    @NotNull
    @ManyToOne
    private Organization organization;
    
    public Boolean isAdmin() {
        return this.role.equals("ROLE_ADMIN");
    }
    
    public Boolean isMatchingPasswords() {
        return this.password.equals(this.confirmPassword);
    }
}
