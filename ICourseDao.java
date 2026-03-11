package BaiTapProject.Dao;

import BaiTapProject.Model.Course;

import java.util.List;

public interface ICourseDao {
    List<Course> findAll();

    Course findById(int id);

    void insert(Course course);

    void update(Course course);

    void delete(int id);

    List<Course> searchByName(String name);

    List<Course> sort(String field, boolean asc);

    List<Course> paginate(int page, int size);

    int countTotalCourses();

    List<Course> recommendCourses(int studentId);

    List<Course> findCoursesNotRegistered(int studentId);

    boolean existsByName(String name);
    boolean hasEnrollment(int courseId);
    Course findByName(String name);
}


