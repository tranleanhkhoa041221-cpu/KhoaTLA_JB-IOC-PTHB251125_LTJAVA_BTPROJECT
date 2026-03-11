package BaiTapProject.Business.Impl;

import BaiTapProject.Business.IEnrollmentService;
import BaiTapProject.Dao.ICourseDao;
import BaiTapProject.Dao.IEnrollmentDao;
import BaiTapProject.Dao.IStudentDao;
import BaiTapProject.Dao.Impl.CourseDaoImpl;
import BaiTapProject.Dao.Impl.EnrollmentDaoImpl;
import BaiTapProject.Dao.Impl.StudentDaoImpl;
import BaiTapProject.Model.*;


import java.util.List;

public class EnrollmentServiceImpl implements IEnrollmentService {

    private final IEnrollmentDao enrollmentDao = new EnrollmentDaoImpl();
    private final IStudentDao studentDao = new StudentDaoImpl();
    private final ICourseDao courseDao = new CourseDaoImpl();

    @Override
    public void registerCourse(int studentId, int courseId) {

        Student student = studentDao.findById(studentId);
        if (student == null) {
            System.out.println("Học viên không tồn tại");
            return;
        }

        Course course = courseDao.findById(courseId);
        if (course == null) {
            System.out.println("Khóa học không tồn tại");
            return;
        }

        if (enrollmentDao.existsEnrollment(studentId, courseId)) {
            System.out.println("Học viên đã đăng ký khóa học này rồi");
            return;
        }

        Enrollment e = new Enrollment();
        e.setStudentId(studentId);
        e.setCourseId(courseId);
        e.setStatus(EnrollmentStatus.WAITING);

        enrollmentDao.insert(e);
        System.out.println("Đăng ký khóa học thành công. (Chờ duyệt)");
    }


    @Override
    public void approveEnrollment(int enrollmentId) {

        Enrollment e = enrollmentDao.findById(enrollmentId);

        if (e == null) {
            System.out.println("Không tìm thấy đăng ký");
            return;
        }

        if (e.getStatus() != EnrollmentStatus.WAITING) {
            System.out.println("Chỉ có thể duyệt đăng ký ở trạng thái WAITING");
            return;
        }

        enrollmentDao.updateStatus(enrollmentId, EnrollmentStatus.CONFIRM);
        System.out.println("Đã duyệt đăng ký");
    }

    @Override
    public void denyEnrollment(int enrollmentId) {

        Enrollment e = enrollmentDao.findById(enrollmentId);

        if (e == null) {
            System.out.println("Không tìm thấy đăng ký");
            return;
        }

        if (e.getStatus() != EnrollmentStatus.WAITING) {
            System.out.println("Chỉ có thể từ chối đăng ký ở trạng thái WAITING");
            return;
        }

        enrollmentDao.updateStatus(enrollmentId, EnrollmentStatus.DENIED);
        System.out.println("Đã từ chối đăng ký");
    }

    @Override
    public void cancelEnrollment(int enrollmentId) {

        Enrollment e = enrollmentDao.findById(enrollmentId);

        if (e == null) {
            System.out.println("Không tìm thấy đăng ký");
            return;
        }

        if (e.getStatus() != EnrollmentStatus.WAITING) {
            System.out.println("Chỉ có thể hủy đăng ký ở trạng thái WAITING");
            return;
        }

        boolean ok = enrollmentDao.cancelEnrollment(enrollmentId);

        if (ok) {
            System.out.println("Hủy đăng ký thành công");
        } else {
            System.out.println("Không thể hủy đăng ký");
        }
    }

    @Override
    public void deleteEnrollment(int enrollmentId) {

        Enrollment e = enrollmentDao.findById(enrollmentId);

        if (e == null) {
            System.out.println("Không tìm thấy đăng ký");
            return;
        }

        if (e.getStatus() != EnrollmentStatus.WAITING) {
            System.out.println("Chỉ có thể xóa khi WAITING");
            return;
        }

        enrollmentDao.delete(enrollmentId);
        System.out.println("Đã xóa đăng ký");
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {

        Student student = studentDao.findById(studentId);
        if (student == null) {
            System.out.println("Học viên không tồn tại");
            return;
        }

        Course course = courseDao.findById(courseId);
        if (course == null) {
            System.out.println("Khóa học không tồn tại");
            return;
        }

        if (enrollmentDao.existsEnrollment(studentId, courseId)) {
            System.out.println("Học viên đã có trong khóa học này");
            return;
        }

        Enrollment e = new Enrollment();
        e.setStudentId(studentId);
        e.setCourseId(courseId);
        e.setStatus(EnrollmentStatus.CONFIRM);

        enrollmentDao.insert(e);
        System.out.println("Đã thêm học viên vào khóa học");
    }

    @Override
    public void removeStudentFromCourse(int studentId, int courseId) {

        Enrollment e = enrollmentDao.findByStudentAndCourse(studentId, courseId);

        if (e == null) {
            System.out.println("Học viên không nằm trong khóa học");
            return;
        }
        if (e.getStatus() != EnrollmentStatus.CONFIRM) {
            System.out.println("Học viên chưa được duyệt trong khóa học");
            return;
        }

        enrollmentDao.removeStudentFromCourse(studentId, courseId);

        System.out.println("Đã xóa học viên khỏi khóa học");
    }

    @Override
    public Enrollment findById(int id) {
        return enrollmentDao.findById(id);
    }

    @Override
    public List<EnrollmentView> getEnrollmentsByCourse(int courseId) {
        return enrollmentDao.findByCourseId(courseId);
    }

    @Override
    public List<EnrollmentView> getEnrollmentsByStudent(int studentId) {
        return enrollmentDao.findByStudentId(studentId);
    }

    @Override
    public List<EnrollmentView> sortRegisteredCourses(int studentId, String field, boolean asc) {
        return enrollmentDao.sortRegisteredCourses(studentId, field, asc);
    }

    @Override
    public List<Enrollment> findAll() {
        return enrollmentDao.findAll();
    }

    @Override
    public EnrollmentView findEnrollmentView(int studentId, int courseId) {
        return enrollmentDao.findEnrollmentView(studentId, courseId);
    }
    @Override
    public int countAll() {
        return enrollmentDao.countAll();
    }

    @Override
    public List<EnrollmentView> paginate(int page, int size) {
        return enrollmentDao.paginate(page, size);
    }
}
