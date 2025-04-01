// Custom interface that extends the JpaRepository interface. JpaRepository has built-in methods for CRUD operations
// on model entities and we extend that functionality by adding the findByShortCode method

package com.url_shortener.url_shortener.repository;

import com.url_shortener.url_shortener.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    UrlMapping findByShortCode(String shortCode);
}
