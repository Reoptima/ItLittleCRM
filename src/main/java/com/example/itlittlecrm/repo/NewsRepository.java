package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
