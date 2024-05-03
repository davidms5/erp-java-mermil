package com.mermil.erp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING) // This specifies that the values are stored as strings
    @Column(columnDefinition = "varchar(255) default 'EMPLEADO'") // Default value is "EMPLEADO"
    private UserRole rol;
    // private String rol;

    public enum UserRole {
        ADMIN, EMPLEADO
    }
}
