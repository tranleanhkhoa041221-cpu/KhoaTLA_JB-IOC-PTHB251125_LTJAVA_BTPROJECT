package BaiTapProject.Business.Impl;

import BaiTapProject.Business.IStudentService;
import BaiTapProject.Dao.IStudentDao;
import BaiTapProject.Dao.Impl.StudentDaoImpl;
import BaiTapProject.Model.Student;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.List;

public class StudentServiceImpl implements IStudentService {

    private final IStudentDao studentDao = new StudentDaoImpl();

    @Override
    public boolean register(Student student) {

        if (studentDao.existsByEmail(student.getEmail())) {
            System.out.println("Email đã tồn tại");
            return false;
        }

        if (student.getPhone() != null && studentDao.existsByPhone(student.getPhone())) {
            System.out.println("Số điện thoại đã tồn tại");
            return false;
        }

        student.setPassword(BCrypt.hashpw(student.getPassword(), BCrypt.gensalt(12)));
        studentDao.insert(student);
        return true;
    }

    @Override
    public Student login(String email, String password) {
        Student student = studentDao.findByEmail(email);

        if (student == null) {
            System.out.println("Email không tồn tại");
            return null;
        }

        if (!BCrypt.checkpw(password, student.getPassword())) {
            System.out.println("Mật khẩu không đúng");
            return null;
        }

        return student;
    }

    @Override
    public void addStudent(Student student) {

        if (studentDao.existsByEmail(student.getEmail())) {
            System.out.println("Email đã tồn tại");
            return;
        }

        if (student.getPhone() != null && studentDao.existsByPhone(student.getPhone())) {
            System.out.println("Số điện thoại đã tồn tại");
            return;
        }

        student.setPassword(BCrypt.hashpw(student.getPassword(), BCrypt.gensalt(12)));
        studentDao.insert(student);
        System.out.println("Thêm học viên thành công");
    }

    @Override
    public void updateStudent(Student student) {

        Student current = studentDao.findById(student.getId());
        if (current == null) {
            System.out.println("Không tìm thấy học viên");
            return;
        }

        Student existingEmail = studentDao.findByEmail(student.getEmail());
        if (existingEmail != null && existingEmail.getId() != student.getId()) {
            System.out.println("Email đã được sử dụng");
            return;
        }

        Student existingPhone = studentDao.findByPhone(student.getPhone());
        if (existingPhone != null && existingPhone.getId() != student.getId()) {
            System.out.println("Số điện thoại đã được sử dụng");
            return;
        }

        studentDao.update(student);
        System.out.println("Cập nhật học viên thành công");
    }

    @Override
    public void updateStudentName(int id, String newName) {

        Student s = studentDao.findById(id);
        if (s == null) {
            System.out.println("Không tìm thấy học viên");
            return;
        }

        s.setName(newName);
        studentDao.update(s);
        System.out.println("Cập nhật tên thành công");
    }

    @Override
    public void updateStudentEmail(int id, String newEmail) {

        Student s = studentDao.findById(id);
        if (s == null) {
            System.out.println("Không tìm thấy học viên");
            return;
        }

        Student existing = studentDao.findByEmail(newEmail);
        if (existing != null && existing.getId() != id) {
            System.out.println("Email đã được sử dụng");
            return;
        }

        s.setEmail(newEmail);
        studentDao.update(s);
        System.out.println("Cập nhật email thành công");
    }

    @Override
    public void updateStudentPhone(int id, String newPhone) {

        Student s = studentDao.findById(id);
        if (s == null) {
            System.out.println("Không tìm thấy học viên");
            return;
        }

        Student existing = studentDao.findByPhone(newPhone);
        if (existing != null && existing.getId() != id) {
            System.out.println("Số điện thoại đã được sử dụng");
            return;
        }

        s.setPhone(newPhone);
        studentDao.update(s);
        System.out.println("Cập nhật số điện thoại thành công");
    }

    @Override
    public void updateStudentDob(int id, LocalDate newDob) {

        Student s = studentDao.findById(id);
        if (s == null) {
            System.out.println("Không tìm thấy học viên");
            return;
        }

        s.setDob(newDob);
        studentDao.update(s);
        System.out.println("Cập nhật ngày sinh thành công");
    }

    @Override
    public void updateStudentSex(int id, boolean newSex) {

        Student s = studentDao.findById(id);
        if (s == null) {
            System.out.println("Không tìm thấy học viên");
            return;
        }

        s.setSex(newSex);
        studentDao.update(s);
        System.out.println("Cập nhật giới tính thành công");
    }

    @Override
    public void deleteStudent(int id) {

        Student current = studentDao.findById(id);
        if (current == null) {
            System.out.println("Không tìm thấy học viên");
            return;
        }

        if (studentDao.hasEnrollment(id)) {
            System.out.println("Không thể xóa học viên vì đã đăng ký khóa học");
            return;
        }

        studentDao.delete(id);
        System.out.println("Xóa học viên thành công");
    }

    @Override
    public Student findById(int id) {
        return studentDao.findById(id);
    }

    @Override
    public Student findByEmail(String email) {
        return studentDao.findByEmail(email);
    }

    @Override
    public Student findByPhone(String phone) {
        return studentDao.findByPhone(phone);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.findAll();
    }
    @Override
    public int countTotalStudents() {
        return studentDao.countTotalStudents();
    }

    @Override
    public List<Student> searchStudents(String keyword) {
        return studentDao.search(keyword);
    }

    @Override
    public List<Student> sortStudents(String field, boolean asc) {
        return studentDao.sort(field, asc);
    }

    @Override
    public List<Student> paginateStudents(int page, int size) {
        return studentDao.paginate(page, size);
    }

    @Override
    public boolean changePassword(Student student, String oldPass, String newPass) {
        if (!studentDao.checkOldPassword(student.getId(), oldPass)) {
            System.out.println("Mật khẩu cũ không đúng");
            return false;
        }

        if (BCrypt.checkpw(newPass, student.getPassword())) {
            System.out.println("Mật khẩu mới không được trùng mật khẩu cũ");
            return false;
        }

        if (newPass.length() < 6) {
            System.out.println("Mật khẩu mới phải có ít nhất 6 ký tự");
            return false;
        }

        String hashed = BCrypt.hashpw(newPass, BCrypt.gensalt(12));
        studentDao.updatePassword(student.getId(), hashed);
        student.setPassword(hashed);
        return true;
    }

    @Override
    public void printStudentTable(List<Student> list) {

        System.out.printf("+%s+%s+%s+%s+%s+%s+%s+\n",
                "-".repeat(8), "-".repeat(22), "-".repeat(17),
                "-".repeat(22), "-".repeat(7), "-".repeat(14),
                "-".repeat(17));

        System.out.printf("| %-6s | %-20s | %-15s | %-20s | %-5s | %-12s | %-15s |\n",
                "ID", "Name", "DOB", "Email", "Sex", "Phone", "Created At");

        System.out.printf("+%s+%s+%s+%s+%s+%s+%s+\n",
                "-".repeat(8), "-".repeat(22), "-".repeat(17),
                "-".repeat(22), "-".repeat(7), "-".repeat(14),
                "-".repeat(17));

        for (Student s : list) {
            s.displayData();
        }

        System.out.printf("+%s+%s+%s+%s+%s+%s+%s+\n",
                "-".repeat(8), "-".repeat(22), "-".repeat(17),
                "-".repeat(22), "-".repeat(7), "-".repeat(14),
                "-".repeat(17));
    }
}
