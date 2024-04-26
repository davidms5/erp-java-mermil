package com.mermil.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mermil.erp.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

}
