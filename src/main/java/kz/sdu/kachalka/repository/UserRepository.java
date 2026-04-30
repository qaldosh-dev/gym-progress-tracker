package kz.sdu.kachalka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import kz.sdu.kachalka.entity.User;
import kz.sdu.kachalka.entity.UserRole;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByRoleInOrderById(Iterable<UserRole> roles);
    Optional<User> findByEmailIgnoreCase(String email);

    @Modifying
    @Query("UPDATE User SET role = :role WHERE id = :id")
    void updateRole(@Param("id") int id, @Param("role") UserRole newRole);
}