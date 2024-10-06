package dev.buddly.flow_sync.repository;

import dev.buddly.flow_sync.model.UserDb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDb,Integer> {

    Optional<UserDb> findByUsername(String username);
}
