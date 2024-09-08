package com.user_management_app.repositories;

import com.user_management_app.entities.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<StateEntity,Integer> {

    public List<StateEntity> findByCountryId(Integer countryId);
}
