package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.enums.BookConservation;
import com.projeto.apibiblioteca.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);
    Optional<User> findByName(String name);

    @Query("""
    SELECT u FROM User u
    WHERE (COALESCE(:name, '') = '' OR UPPER(u.name) LIKE UPPER(CONCAT('%', :name, '%')))
        AND (COALESCE(:email, '') = '' OR UPPER(u.email) LIKE UPPER(CONCAT('%', :email, '%')))
        AND (:role IS NULL OR u.role = :role)
""")
    List<User> findByFilters(
            @Param("name") String name,
            @Param("email") String email,
            @Param("role") UserRole role
    );
}
