package BaiTapProject.Business;


import BaiTapProject.Model.Course;

import java.util.List;

public interface ICourseService {

    void addCourse(Course course);

    void updateCourse(Course course);

    void deleteCourse(int id);

    Course findById(int id);

    Course findByName(String name);

    List<Course> getAllCourses();

    int countTotalCourses();

    List<Course> searchByName(String name);

    List<Course> sortCourses(String field, boolean asc);

    List<Course> paginateCourses(int page, int size);

    List<Course> findCoursesNotRegistered(int studentId);

    List<Course> recommendCourses(int studentId);

    void updateCourseName(int id, String newName);

    void updateCourseDuration(int id, int newDuration);

    void updateCourseInstructor(int id, String newInstructor);

    void printCourseTable(List<Course> list);
}