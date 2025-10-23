package com.hms2i.repository;

import com.hms2i.entity.Islanduser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IslanduserRepository extends JpaRepository<Islanduser, Long> {

    Optional<Islanduser> findByUsername(String username);
    Optional<Islanduser> findByEmail(String email);

}