package com.example.TeamUp.Repositories;
import com.example.TeamUp.Entities.ReviewEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>  {
    
}
