package com.techcareer.challenge.repository;

import com.techcareer.challenge.data.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleModel , Integer> {
}
