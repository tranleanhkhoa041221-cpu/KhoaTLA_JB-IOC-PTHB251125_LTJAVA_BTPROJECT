package BaiTapProject.Business;


import BaiTapProject.Model.Enrollment;
import BaiTapProject.Model.EnrollmentView;

import java.util.List;

public interface IEnrollmentService {

    void registerCourse(int studentId, int courseId);

    void approveEnrollment(int enrollmentId);

    void denyEnrollment(int enrollmentId);

    void cancelEnrollment(int enrollmentId);

    void deleteEnrollment(int enrollmentId);

    void addStudentToCourse(int studentId, int courseId);

    void removeStudentFromCourse(int studentId, int courseId);

    int countAll();

    Enrollment findById(int id);

    List<EnrollmentView> getEnrollmentsByCourse(int courseId);

    List<EnrollmentView> getEnrollmentsByStudent(int studentId);

    List<EnrollmentView> sortRegisteredCourses(int studentId, String field, boolean asc);

    List<Enrollment> findAll();

    EnrollmentView findEnrollmentView(int studentId, int courseId);

    List<EnrollmentView> paginate(int page, int size);

}
