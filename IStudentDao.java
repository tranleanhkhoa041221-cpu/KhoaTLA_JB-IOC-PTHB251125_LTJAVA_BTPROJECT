package BaiTapProject.Dao;

import BaiTapProject.Model.Student;

import java.util.List;

public interface IStudentDao {
        List<Student> findAll();
        Student findById(int id);
        Student findByEmail(String email);
        Student findByPhone(String phone);

        void insert(Student student);
        void update(Student student);
        void delete(int id);

        List<Student> search(String keyword);


        List<Student> sort(String field, boolean asc);


        List<Student> paginate(int page, int size);

        int countTotalStudents();


        boolean existsByEmail(String email);

        boolean existsByPhone(String phone);

        boolean checkOldPassword(int id, String oldPassword);
        void updatePassword(int id, String newPassword);

        boolean hasEnrollment(int studentId);

        Student findByEmailOrPhone(String input);


}
