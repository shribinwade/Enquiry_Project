package in.shrihari.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.shrihari.entity.CoursesEntity;

public interface CourseRepo extends JpaRepository<CoursesEntity, Integer> {

}
