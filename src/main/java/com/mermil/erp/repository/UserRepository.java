package com.mermil.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermil.erp.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {

}
