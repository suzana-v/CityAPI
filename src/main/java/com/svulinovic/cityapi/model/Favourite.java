package com.svulinovic.cityapi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Favourite", uniqueConstraints = @UniqueConstraint(columnNames={"userId", "cityId"}))
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityId", nullable = false)
    private City city;

}
