package com.example.vinyl.vibes.entity;

import com.example.vinyl.vibes.dto.AlbumDTO;
import com.example.vinyl.vibes.enums.Genre;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "albums")
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "description", length = 3000)
    private String description;

    @Column(name = "artist")
    private String artist;

    @Column(name = "year")
    private Integer year;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "dislikes")
    private Integer dislikes;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;



    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public AlbumDTO toDTO() {
        return AlbumDTO.builder()
                .id(this.id)
                .artist(this.artist)
                .name(this.name)
                .image(this.image)
                .year(this.year)
                .description(this.description)
                .genre(this.genre)
                .likes(this.likes)
                .dislikes(this.dislikes)
                .build();
    }

    @PrePersist
    private void prePersist() {
        LocalDateTime date = LocalDateTime.now();
        this.createdAt = date;
        this.updatedAt = date;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album album)) return false;
        return Objects.equals(getId(), album.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
