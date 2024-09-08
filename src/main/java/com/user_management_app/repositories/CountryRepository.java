package com.user_management_app.repositories;

import com.user_management_app.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity,Integer> {
}
