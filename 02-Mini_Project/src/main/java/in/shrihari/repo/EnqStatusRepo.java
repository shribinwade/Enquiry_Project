package in.shrihari.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.shrihari.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
