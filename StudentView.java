package BaiTapProject.Presentation;

import BaiTapProject.Business.ICourseService;
import BaiTapProject.Business.IEnrollmentService;
import BaiTapProject.Business.IStudentService;
import BaiTapProject.Business.Impl.CourseServiceImpl;
import BaiTapProject.Business.Impl.EnrollmentServiceImpl;
import BaiTapProject.Business.Impl.StudentServiceImpl;
import BaiTapProject.Model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Scanner;

public class StudentView {
        private static final Scanner sc = new Scanner(System.in);
        private static final IStudentService studentService = new StudentServiceImpl();
        private static final IEnrollmentService enrollmentService = new EnrollmentServiceImpl();
        private static final ICourseService courseService = new CourseServiceImpl();

        public static void show(Student student) {

            while (true) {
                System.out.println("========= MENU HỌC VIÊN =========");
                System.out.println("1. Xem danh sách khóa học");
                System.out.println("2. Đăng ký khóa học");
                System.out.println("3. Xem khóa học đã đăng ký");
                System.out.println("4. Hủy đăng ký");
                System.out.println("5. Đổi mật khẩu");
                System.out.println("6. Đề xuất khóa học");
                System.out.println("7. Xem khóa học chưa đăng ký");
                System.out.println("8. Sắp xếp khóa học");
                System.out.println("9. Đăng xuất");
                System.out.println("=================================");


                int choice;
                try {
                    System.out.print("Chọn: ");
                    choice = Integer.parseInt(sc.nextLine().trim());
                } catch (Exception e) {
                    System.out.println("Vui lòng nhập số!");
                    continue;
                }

                switch (choice) {
                    case 1 -> showAllCourses();
                    case 2 -> registerCourse(student.getId());
                    case 3 -> viewRegisteredCourses(student.getId());
                    case 4 -> cancelRegistration(student.getId());
                    case 5 -> changePassword(student);
                    case 6 -> recommendCourses(student.getId());
                    case 7 -> viewUnregisteredCourses(student.getId());
                    case 8 -> sortCourses(student.getId());
                    case 9 -> {
                        System.out.println("Đăng xuất thành công");
                        return;
                    }
                    default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chỉ chọn 1-9");
                }
            }
        }

        private static void showAllCourses() {
            List<Course> list = courseService.getAllCourses();
            if (list.isEmpty()) {
                System.out.println("Không có khóa học nào");
                return;
            }
            courseService.printCourseTable(list);
        }

        private static void registerCourse(int studentId) {
            int courseId;
            try {
                System.out.print("Nhập ID khóa học muốn đăng ký: ");
                courseId = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số");
                return;
            }

            Course c = courseService.findById(courseId);
            if (c == null) {
                System.out.println("Khóa học không tồn tại");
                return;
            }

            enrollmentService.registerCourse(studentId, courseId);
        }


        private static void viewRegisteredCourses(int studentId) {
            List<EnrollmentView> list = enrollmentService.getEnrollmentsByStudent(studentId);

            if (list.isEmpty()) {
                System.out.println("Bạn chưa đăng ký khóa học nào");
                return;
            }

            printEnrollmentTable(list);
        }

    private static void cancelRegistration(int studentId) {

        int courseId;

        while (true) {
            System.out.print("Nhập ID khóa học muốn hủy: ");
            try {
                courseId = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        EnrollmentView info = enrollmentService.findEnrollmentView(studentId, courseId);

        if (info == null) {
            System.out.println("Bạn chưa đăng ký khóa học này");
            return;
        }

        System.out.print("Xác nhận hủy đăng ký (y/n): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println("Hủy thao tác");
            return;
        }
        enrollmentService.cancelEnrollment(info.getEnrollmentId());
    }

    private static void changePassword(Student student) {
            System.out.println("===== ĐỔI MẬT KHẨU =====");

            String oldPass;
            while (true) {
                System.out.print("Nhập mật khẩu cũ: ");
                oldPass = sc.nextLine().trim();

                if (oldPass.isEmpty()) {
                    System.out.println("Mật khẩu cũ không được để trống");
                    continue;
                }

                if (!BCrypt.checkpw(oldPass, student.getPassword())) {
                    System.out.println("Mật khẩu cũ không đúng");
                    continue;
                }

                break;
            }

            String newPass;
            while (true) {
                System.out.print("Nhập mật khẩu mới: ");
                newPass = sc.nextLine().trim();

                if (newPass.isEmpty()) {
                    System.out.println("Mật khẩu mới không được để trống");
                    continue;
                }

                if (newPass.length() < 6) {
                    System.out.println("Mật khẩu mới phải >= 6 ký tự");
                    continue;
                }

                if (BCrypt.checkpw(newPass, student.getPassword())) {
                    System.out.println("Mật khẩu mới không được trùng mật khẩu cũ");
                    continue;
                }
                break;
            }
            boolean ok = studentService.changePassword(student, oldPass, newPass);

            if (ok) {
                System.out.println("Đổi mật khẩu thành công");


            } else {
                System.out.println("Đổi mật khẩu thất bại");
            }
        }


        private static void recommendCourses(int studentId) {
            List<Course> list = courseService.recommendCourses(studentId);

            if (list.isEmpty()) {
                System.out.println("Không có khóa học đề xuất");
                return;
            }

            courseService.printCourseTable(list);
        }
        private static void viewUnregisteredCourses(int studentId) {
            List<Course> list = courseService.findCoursesNotRegistered(studentId);

            if (list.isEmpty()) {
                System.out.println("Bạn đã đăng ký tất cả khóa học");
                return;
            }

            courseService.printCourseTable(list);
        }

        private static void sortCourses(int studentId) {
            System.out.println("===== SẮP XẾP KHÓA HỌC =====");
            System.out.println("1. Tên A-Z");
            System.out.println("2. Tên Z-A");
            System.out.println("3. Ngày đăng ký tăng dần");
            System.out.println("4. Ngày đăng ký giảm dần");


            int choice;
            try {
                System.out.print("Chọn: ");
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số!");
                return;
            }

            String field;
            boolean asc;

            switch (choice) {
                case 1 -> { field = "c.name"; asc = true; }
                case 2 -> { field = "c.name"; asc = false; }
                case 3 -> { field = "e.registered_at"; asc = true; }
                case 4 -> { field = "e.registered_at"; asc = false; }
                default -> {
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chỉ chọn 1-4");
                    return;
                }
            }

            List<EnrollmentView> list = enrollmentService.sortRegisteredCourses(studentId, field, asc);

            if (list == null || list.isEmpty()) {
                System.out.println("Không có dữ liệu");
                return;
            }

            printEnrollmentTable(list);
        }

        private static void printEnrollmentTable(List<EnrollmentView> list) {

            System.out.printf("+%s+%s+%s+%s+%s+%s+\n",
                    "-".repeat(8), "-".repeat(20), "-".repeat(25),
                    "-".repeat(20), "-".repeat(20), "-".repeat(12));

            System.out.printf("| %-6s | %-18s | %-23s | %-18s | %-18s | %-10s |\n",
                    "ID", "Student", "Email", "Course", "Registered At", "Status");

            System.out.printf("+%s+%s+%s+%s+%s+%s+\n",
                    "-".repeat(8), "-".repeat(20), "-".repeat(25),
                    "-".repeat(20), "-".repeat(20), "-".repeat(12));

            for (EnrollmentView e : list) {
                e.displayData();
            }

            System.out.printf("+%s+%s+%s+%s+%s+%s+\n",
                    "-".repeat(8), "-".repeat(20), "-".repeat(25),
                    "-".repeat(20), "-".repeat(20), "-".repeat(12));
        }
}
