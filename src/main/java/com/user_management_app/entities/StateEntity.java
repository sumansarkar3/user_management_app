package com.user_management_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "state_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stateId;
    private String stateName;

    private Integer countryId;
}
