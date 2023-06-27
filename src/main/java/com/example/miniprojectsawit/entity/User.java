package com.example.miniprojectsawit.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", length = 60)
    private String name;

    @Column(name = "phone", length = 13)
    private String phone;

    @Column(name = "password")
    private String password;


}
