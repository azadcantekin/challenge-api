package com.techcareer.challenge.repository;

import com.techcareer.challenge.data.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface UserRepository extends JpaRepository<UserModel,Integer> {
    @Query(value = "SELECT CASE " +
            "WHEN COUNT(s) > 0 THEN " +
            "TRUE ELSE FALSE END FROM User u WHERE u.email =?1" , nativeQuery = true)
    boolean selectExistsEmail(String email);

    UserModel findByEmail(String email);
}
