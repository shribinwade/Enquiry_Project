package in.shrihari.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.shrihari.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer>{

}
