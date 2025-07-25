package org.example.gamearenax_backend.repository;

import org.example.gamearenax_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, String> {
    boolean existsByEmail(String email);

    User findByEmail(String email);


    @Modifying
    @Query(value = "UPDATE User u SET  email = ?1, role = ?2, country = ?3 WHERE u.username = ?4", nativeQuery = true)
    void updateUser(String email, String role, String country, String username);

    @Modifying
    @Query(value = "UPDATE User u SET u.role = ?2 WHERE u.email = ?1", nativeQuery = true)
    void updateRole(String email, String streamer);
}
