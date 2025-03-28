package org.example.blue_springboot.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private String imageUrl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
}
