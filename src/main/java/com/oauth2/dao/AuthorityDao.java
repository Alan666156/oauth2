package com.oauth2.dao;


import com.oauth2.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityDao extends JpaRepository<Authority, String> {
}
