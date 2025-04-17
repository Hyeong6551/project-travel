package edu.du.detailpage.repository;


import edu.du.detailpage.entity.DetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<DetailEntity,Long> {
}
