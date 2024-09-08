package com.user_management_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "user_master")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String uname;
    private String email;

    private String pwd;
    private String pwdUpdated;

    private Long phno;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private CountryEntity country;
    @ManyToOne
    @JoinColumn(name = "stateId")
    private StateEntity state;
    @ManyToOne
    @JoinColumn(name = "cityId")
    private CityEntity city;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdDate;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDate updatedDate;
}
