    package com.example.com.domain.entity;
    import com.example.com.enums.Gender;
    import com.example.com.enums.Role;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import lombok.*;
    import java.sql.Timestamp;
    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.UUID;

    @Entity
    @Table(name = "users", schema = "gdc-db")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id", updatable = false, nullable = false)
        private UUID id;

//        @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//        @JsonManagedReference("user-rides")
//        private List<Ride> rides;

        @Column(name = "name", nullable = false)
        private String name;

        private String deviceToken;

        @Column(name = "age", nullable = false)
        private Integer age;

        @Enumerated(EnumType.STRING)
        @Column(name = "gender", nullable = false)
        private Gender gender;

        @Column(name = "phone", unique = true, nullable = false)
        private String phone;

        @Column(name = "email", unique = true, nullable = false)
        private String email;

        @Column(name = "password", nullable = false)
        private String password;

        @Column(name = "is_verified", nullable = false)
        private Boolean isVerified;

        @Column(name = "role", nullable = false)
        @Enumerated(EnumType.STRING)
        private Role role;

        @Column(name = "street")
        private String street;

        @Column(name = "city")
        private String city;

        @Column(name = "state")
        private String state;

        @Column(name = "zip_code")
        private String zipCode;

        @Column(name = "country")
        private String country;

        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;
        @PrePersist
        protected void create(){
            createdAt=LocalDateTime.now();
        }
    }
