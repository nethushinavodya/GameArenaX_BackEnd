package org.example.gamearenax_backend.repository;

import org.example.gamearenax_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    User findByEmail(String email);


    @Modifying
    @Query(value = "UPDATE User u SET  email = ?1, role = ?2, country = ?3 WHERE u.username = ?4", nativeQuery = true)
    void updateUser(String email, String role, String country, String username);

    @Modifying
    @Query(value = "UPDATE User u SET u.role = 'Streamer' WHERE u.email = ?1", nativeQuery = true)
    void updateRole(String email, String streamer);

    @Modifying
    @Query(value = "UPDATE User u SET u.status = 'Deactivated' WHERE u.email = ?1", nativeQuery = true)
    void updateIsActive(String email);

    @Modifying
    @Query(value = "UPDATE User u SET u.role = 'Player' WHERE u.email = ?1", nativeQuery = true)
    void updatePlayerRole(String email, String player);

    @Modifying
    @Query(value = "UPDATE User u SET u.password = ?1 WHERE u.email = ?2", nativeQuery = true)
    void updatePassword(String encode, String email);

    boolean existsByUsername(String username);

    @Query(value = "SELECT * FROM User u WHERE u.role = 'Admin' OR u.role = 'User'", nativeQuery = true)
    List<User> getAllAdminsAndUsers();
}
