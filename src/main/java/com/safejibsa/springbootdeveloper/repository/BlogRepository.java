package com.safejibsa.springbootdeveloper.repository;

import com.safejibsa.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {

}
