package com.user_management_app.repositories;

import com.user_management_app.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<CityEntity,Integer> {

    public List<CityEntity> findByStateId(Integer stateId);
}
