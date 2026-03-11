package BaiTapProject.Business.Impl;

import BaiTapProject.Business.ICourseService;
import BaiTapProject.Dao.ICourseDao;
import BaiTapProject.Dao.Impl.CourseDaoImpl;
import BaiTapProject.Model.Course;

import java.util.List;

public class CourseServiceImpl implements ICourseService {

    private final ICourseDao courseDao = new CourseDaoImpl();


    @Override
    public void addCourse(Course course) {
        if (courseDao.existsByName(course.getName())) {
            System.out.println("Tên khóa học đã tồn tại");
            return;
        }

        courseDao.insert(course);
        System.out.println("Thêm khóa học thành công");
    }

    @Override
    public void updateCourse(Course course) {

        Course current = courseDao.findById(course.getId());
        if (current == null) {
            System.out.println("Không tìm thấy khóa học");
            return;
        }
        Course existing = courseDao.findByName(course.getName());
        if (existing != null && existing.getId() != course.getId()) {
            System.out.println("Tên khóa học đã được sử dụng");
            return;
        }

        if (courseDao.hasEnrollment(course.getId())) {
            System.out.println("Không thể sửa khóa học vì đã có học viên đăng ký");
            return;
        }

        courseDao.update(course);
        System.out.println("Cập nhật khóa học thành công");
    }

    @Override
    public void updateCourseName(int id, String newName) {

        Course c = courseDao.findById(id);
        if (c == null) {
            System.out.println("Không tìm thấy khóa học");
            return;
        }

        Course existing = courseDao.findByName(newName);
        if (existing != null && existing.getId() != id) {
            System.out.println("Tên khóa học đã tồn tại");
            return;
        }

        if (courseDao.hasEnrollment(id)) {
            System.out.println("Không thể sửa vì khóa học đã có học viên đăng ký");
            return;
        }

        c.setName(newName);
        courseDao.update(c);
        System.out.println("Cập nhật tên khóa học thành công");
    }

    @Override
    public void updateCourseDuration(int id, int newDuration) {

        Course c = courseDao.findById(id);
        if (c == null) {
            System.out.println("Không tìm thấy khóa học");
            return;
        }

        if (courseDao.hasEnrollment(id)) {
            System.out.println("Không thể sửa vì khóa học đã có học viên đăng ký");
            return;
        }

        c.setDuration(newDuration);
        courseDao.update(c);
        System.out.println("Cập nhật thời lượng thành công");
    }

    @Override
    public void updateCourseInstructor(int id, String newInstructor) {

        Course c = courseDao.findById(id);
        if (c == null) {
            System.out.println("Không tìm thấy khóa học");
            return;
        }

        if (courseDao.hasEnrollment(id)) {
            System.out.println("Không thể sửa vì khóa học đã có học viên đăng ký");
            return;
        }

        c.setInstructor(newInstructor);
        courseDao.update(c);
        System.out.println("Cập nhật giảng viên thành công");
    }

    @Override
    public void deleteCourse(int id) {

        Course c = courseDao.findById(id);
        if (c == null) {
            System.out.println("Không tìm thấy khóa học");
            return;
        }

        if (courseDao.hasEnrollment(id)) {
            System.out.println("Không thể xóa khóa học vì đã có học viên đăng ký");
            return;
        }

        courseDao.delete(id);
        System.out.println("Xóa khóa học thành công");
    }

    @Override
    public Course findById(int id) {
        return courseDao.findById(id);
    }

    @Override
    public Course findByName(String name) {
        return courseDao.findByName(name);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseDao.findAll();
    }
    @Override
    public int countTotalCourses() {
        return courseDao.countTotalCourses();
    }

    @Override
    public List<Course> searchByName(String name) {
        return courseDao.searchByName(name);
    }

    @Override
    public List<Course> sortCourses(String field, boolean asc) {
        return courseDao.sort(field, asc);
    }

    @Override
    public List<Course> paginateCourses(int page, int size) {
        return courseDao.paginate(page, size);
    }

    @Override
    public List<Course> findCoursesNotRegistered(int studentId) {
        return courseDao.findCoursesNotRegistered(studentId);
    }

    @Override
    public List<Course> recommendCourses(int studentId) {
        return courseDao.recommendCourses(studentId);
    }

    @Override
    public void printCourseTable(List<Course> list) {

        System.out.printf("+%s+%s+%s+%s+%s+\n",
                "-".repeat(10), "-".repeat(29), "-".repeat(14),
                "-".repeat(24), "-".repeat(19));

        System.out.printf("| %-8s | %-27s | %-12s | %-22s | %-17s |\n",
                "ID", "Name", "Duration", "Instructor", "Created At");

        System.out.printf("+%s+%s+%s+%s+%s+\n",
                "-".repeat(10), "-".repeat(29), "-".repeat(14),
                "-".repeat(24), "-".repeat(19));

        for (Course c : list) {
            c.displayData();
        }

        System.out.printf("+%s+%s+%s+%s+%s+\n",
                "-".repeat(10), "-".repeat(29), "-".repeat(14),
                "-".repeat(24), "-".repeat(19));
    }
}
