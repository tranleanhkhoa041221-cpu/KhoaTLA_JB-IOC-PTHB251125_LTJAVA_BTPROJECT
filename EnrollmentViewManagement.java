package BaiTapProject.Presentation;

import BaiTapProject.Business.IEnrollmentService;
import BaiTapProject.Business.Impl.EnrollmentServiceImpl;
import BaiTapProject.Model.Course;
import BaiTapProject.Model.Enrollment;
import BaiTapProject.Model.EnrollmentView;

import java.util.List;
import java.util.Scanner;

public class EnrollmentViewManagement {

    private static final Scanner sc = new Scanner(System.in);
    private static final IEnrollmentService enrollmentService = new EnrollmentServiceImpl();

    public static void showMenu() {
        while (true) {
            System.out.println("===== QUẢN LÝ ĐĂNG KÝ KHÓA HỌC =====");
            System.out.println("1. Hiển thị học viên theo khóa học");
            System.out.println("2. Thêm học viên vào khóa học");
            System.out.println("3. Xóa học viên khỏi khóa học");
            System.out.println("4. Duyệt đăng ký");
            System.out.println("5. Từ chối đăng ký");
            System.out.println("6. Xóa đăng ký");
            System.out.println("7. Phân trang");
            System.out.println("8. Quay về menu chính");

            int choice;
            while (true) {
                System.out.print("Chọn: ");
                try {
                    choice = Integer.parseInt(sc.nextLine().trim());
                    break;
                } catch (Exception e) {
                    System.out.println("Nhập số hợp lệ");
                }
            }

            switch (choice) {
                case 1 -> viewByCourse();
                case 2 -> addStudentToCourse();
                case 3 -> removeStudentFromCourse();
                case 4 -> approveEnrollment();
                case 5 -> denyEnrollment();
                case 6 -> deleteEnrollment();
                case 7 -> paginate();
                case 8 -> { return; }
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chỉ chọn 1–8");
            }
        }
    }

    private static void viewByCourse() {
        int courseId;

        while (true) {
            System.out.print("Nhập ID khóa học: ");
            try {
                courseId = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        List<EnrollmentView> list = enrollmentService.getEnrollmentsByCourse(courseId);

        if (list.isEmpty()) {
            System.out.println("Không có học viên nào đăng ký khóa học này!");
            return;
        }

        printEnrollmentTable(list);
    }

    private static void addStudentToCourse() {
        int studentId, courseId;

        while (true) {
            System.out.print("Nhập ID học viên: ");
            try {
                studentId = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        while (true) {
            System.out.print("Nhập ID khóa học: ");
            try {
                courseId = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        enrollmentService.addStudentToCourse(studentId, courseId);
    }

    private static void removeStudentFromCourse() {
        int studentId, courseId;

        while (true) {
            System.out.print("Nhập ID học viên muốn xóa: ");
            try {
                studentId = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        while (true) {
            System.out.print("Nhập ID khóa học: ");
            try {
                courseId = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        EnrollmentView info = enrollmentService.findEnrollmentView(studentId, courseId);

        if (info == null) {
            System.out.println("Học viên không đăng ký khóa học này");
            return;
        }

        System.out.println("===== THÔNG TIN ĐĂNG KÝ =====");
        System.out.printf("ID đăng ký   : %d\n", info.getEnrollmentId());
        System.out.printf("Học viên     : %s\n", info.getStudentName());
        System.out.printf("Email        : %s\n", info.getStudentEmail());
        System.out.printf("Khóa học     : %s\n", info.getCourseName());
        System.out.printf("Ngày đăng ký : %s\n", info.getRegisteredAt());
        System.out.printf("Trạng thái   : %s\n", info.getStatus());
        System.out.println("==============================");

        System.out.print("Xác nhận xóa (y/n): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println("Hủy thao tác");
            return;
        }

        enrollmentService.removeStudentFromCourse(studentId, courseId);
    }

    private static void approveEnrollment() {
        int id;

        while (true) {
            System.out.print("Nhập ID muốn phê duyệt: ");
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        enrollmentService.approveEnrollment(id);
    }
    private static void denyEnrollment() {
        int id;

        while (true) {
            System.out.print("Nhập ID muốn từ chối: ");
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        enrollmentService.denyEnrollment(id);
    }

    private static void deleteEnrollment() {
        int id;

        while (true) {
            System.out.print("Nhập ID muốn xóa: ");
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        Enrollment e = enrollmentService.findById(id);
        if (e == null) {
            System.out.println("Không tìm thấy đăng ký");
            return;
        }

        System.out.println("===== THÔNG TIN ĐĂNG KÝ =====");
        System.out.printf("ID đăng ký   : %d\n", e.getId());
        System.out.printf("Học viên ID  : %d\n", e.getStudentId());
        System.out.printf("Khóa học ID  : %d\n", e.getCourseId());
        System.out.printf("Trạng thái   : %s\n", e.getStatus());
        System.out.println("==============================");

        System.out.print("Xác nhận xóa (y/n): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println("Hủy thao tác.");
            return;
        }

        enrollmentService.deleteEnrollment(id);
    }

    private static void paginate() {
        int page, size;

        while (true) {
            System.out.print("Nhập số trang: ");
            try {
                page = Integer.parseInt(sc.nextLine().trim());
                if (page <= 0) {
                    System.out.println("Số trang phải > 0!");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        while (true) {
            System.out.print("Nhập số lượng mỗi trang: ");
            try {
                size = Integer.parseInt(sc.nextLine().trim());
                if (size <= 0) {
                    System.out.println("Số lượng phải > 0!");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        int total = enrollmentService.countAll();
        if (total == 0) {
            System.out.println("Không có đăng ký");
            return;
        }

        int totalPage = (int) Math.ceil((double) total / size);

        if (page < 1 || page > totalPage) {
            System.out.println("Trang chỉ từ 1 -> " + totalPage);
            return;
        }

        List<EnrollmentView> list = enrollmentService.paginate(page, size);

        System.out.println("===== TRANG " + page + "/" + totalPage + " =====");

        if (list.isEmpty()) {
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
