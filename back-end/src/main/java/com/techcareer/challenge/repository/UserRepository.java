package com.techcareer.challenge.repository;

import com.techcareer.challenge.data.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<UserModel,Integer> {
    @Query(value = "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM T_User u WHERE u.email = :email" , nativeQuery = true)
    boolean existsByEmail(@Param("email") String email);

    UserModel findByEmail(String email);
}
