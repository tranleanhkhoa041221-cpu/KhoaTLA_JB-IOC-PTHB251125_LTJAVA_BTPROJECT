package BaiTapProject.Business;

import BaiTapProject.Model.Student;

import java.time.LocalDate;
import java.util.List;

public interface IStudentService {
    void addStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(int id);

    Student findById(int id);

    List<Student> getAllStudents();

    int countTotalStudents();

    void updateStudentName(int id, String newName);

    void updateStudentEmail(int id, String newEmail);

    void updateStudentPhone(int id, String newPhone);

    void updateStudentDob(int id, LocalDate newDob);

    void updateStudentSex(int id, boolean newSex);

    List<Student> searchStudents(String keyword);

    List<Student> sortStudents(String field, boolean asc);

    List<Student> paginateStudents(int page, int size);

    boolean register(Student student);

    Student login(String email, String password);

    boolean changePassword(Student student, String oldPass, String newPass);

    Student findByEmail(String email);

    Student findByPhone(String phone);

    void printStudentTable(List<Student> list);
}
