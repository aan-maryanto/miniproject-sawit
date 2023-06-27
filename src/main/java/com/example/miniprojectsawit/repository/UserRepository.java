package com.example.miniprojectsawit.repository;

import com.example.miniprojectsawit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByByPhoneAndPassword(String phone, String password);

    @Modifying
    @Query("update User u set u.name = :name where u.phone = :phone ")
    void updateByName(@Param("name") String name, @Param("phone") String phone);

}
