package com.quest.etna.repositories;

import com.quest.etna.model.User;

import java.util.Optional;

// import org.springframework.data.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    void deleteById(Long id);

    Optional<String> findRoleByUsername(String username);
    //fidn Role
}