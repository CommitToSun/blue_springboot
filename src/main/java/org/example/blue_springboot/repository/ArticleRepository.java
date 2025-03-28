package org.example.blue_springboot.repository;

import org.example.blue_springboot.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
