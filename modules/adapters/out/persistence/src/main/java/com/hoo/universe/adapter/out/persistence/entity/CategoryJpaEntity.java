package com.hoo.universe.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "CATEGORY")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)", nullable = false, unique = true)
    private UUID uuid;

    @Column(nullable = false, length = 100)
    private String titleEng;

    @Column(nullable = false, length = 100)
    private String titleKor;

    public static CategoryJpaEntity create(UUID uuid, String eng, String kor) {
        return new CategoryJpaEntity(null, uuid, eng, kor);
    }

    public void update(String eng, String kor) {
        this.titleEng = eng != null? eng : this.titleEng;
        this.titleKor = kor != null? kor : this.titleKor;
    }
}
