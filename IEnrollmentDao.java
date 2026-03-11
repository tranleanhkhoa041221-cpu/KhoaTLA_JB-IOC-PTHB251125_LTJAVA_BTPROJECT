package BaiTapProject.Dao;

import BaiTapProject.Model.*;

import java.util.List;

public interface IEnrollmentDao {

    List<Enrollment> findAll();

    Enrollment findById(int id);

    void insert(Enrollment enrollment);

    void updateStatus(int id, EnrollmentStatus status);

    void delete(int id);

    void removeStudentFromCourse(int studentId, int courseId);

    List<EnrollmentView> findByCourseId(int courseId);

    List<EnrollmentView> findByStudentId(int studentId);

    EnrollmentView findEnrollmentView(int studentId, int courseId);

    boolean existsEnrollment(int studentId, int courseId);

    int countStudentsByCourse(int courseId);

    Enrollment findByStudentAndCourse(int studentId, int courseId);

    List<EnrollmentView> sortRegisteredCourses(int studentId, String field, boolean asc);

    boolean cancelEnrollment(int id);

    int countAll();

    List<EnrollmentView> paginate(int page, int size);

}
