package BaiTapProject.Presentation;

import BaiTapProject.Business.ICourseService;
import BaiTapProject.Business.Impl.CourseServiceImpl;
import BaiTapProject.Model.Course;

import java.util.List;
import java.util.Scanner;

public class CourseViewManagement {

    private static final Scanner sc = new Scanner(System.in);
    private static final ICourseService courseService = new CourseServiceImpl();

    public static void showMenu() {
        while (true) {
            System.out.println("===== QUẢN LÝ KHÓA HỌC =====");
            System.out.println("1. Hiển thị danh sách khóa học");
            System.out.println("2. Thêm mới khóa học");
            System.out.println("3. Chỉnh sửa khóa học");
            System.out.println("4. Xóa khóa học");
            System.out.println("5. Tìm kiếm theo tên");
            System.out.println("6. Sắp xếp");
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
                case 1 -> showAll();
                case 2 -> addCourse();
                case 3 -> updateCourse();
                case 4 -> deleteCourse();
                case 5 -> searchCourse();
                case 6 -> sortCourse();
                case 7 -> paginate();
                case 8 -> {
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chỉ chọn 1–8");
            }
        }
    }

    private static void showAll() {
        System.out.println("==== Danh Sách Khóa Học ====");
        List<Course> list = courseService.getAllCourses();
        if (list.isEmpty()) {
            System.out.println("Không có khóa học nào");
            return;
        }
        courseService.printCourseTable(list);
    }

    private static void addCourse() {
        System.out.println("===== THÊM KHÓA HỌC =====");

        String name;
        while (true) {
            System.out.print("Tên khóa học: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Không được để trống");
                continue;
            }
            if (name.length() > 100) {
                System.out.println("Không vượt quá 100 ký tự");
                continue;
            }
            if (courseService.findByName(name) != null) {
                System.out.println("Tên khóa học đã tồn tại");
                continue;
            }
            break;
        }

        int duration;
        while (true) {
            System.out.print("Thời lượng (giờ): ");
            try {
                duration = Integer.parseInt(sc.nextLine().trim());
                if (duration <= 0) {
                    System.out.println("Phải > 0");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        String instructor;
        while (true) {
            System.out.print("Giảng viên: ");
            instructor = sc.nextLine().trim();
            if (instructor.isEmpty()) {
                System.out.println("Không được để trống");
                continue;
            }
            break;
        }

        Course c = new Course();
        c.setName(name);
        c.setDuration(duration);
        c.setInstructor(instructor);

        courseService.addCourse(c);
    }

    private static void updateCourse() {
        int id;
        while (true) {
            System.out.print("Nhập ID khóa học cần chỉnh sửa: ");
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        Course c = courseService.findById(id);
        if (c == null) {
            System.out.println("Không tìm thấy khóa học có id là: " + id);
            return;
        }

        System.out.println("===== THÔNG TIN KHÓA HỌC =====");
        System.out.printf("Tên        : %s\n", c.getName());
        System.out.printf("Thời lượng : %s\n", c.getDuration());
        System.out.printf("Giảng viên : %s\n", c.getInstructor());
        System.out.printf("Ngày tạo   : %s\n", c.getCreatedAt());
        System.out.println("==============================");

        while (true) {
            System.out.println("===== SỬA KHÓA HỌC =====");
            System.out.println("1. Sửa tên");
            System.out.println("2. Sửa thời lượng");
            System.out.println("3. Sửa giảng viên");
            System.out.println("4. Quay lại");
            System.out.print("Chọn: ");

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
                case 1 -> updateName(id);
                case 2 -> updateDuration(id);
                case 3 -> updateInstructor(id);
                case 4 -> {
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chỉ chọn 1–4");
            }
        }
    }

    private static void updateName(int id) {

        Course c = courseService.findById(id);

        System.out.println("Tên hiện tại: " + c.getName());

        String newName;

        while (true) {
            System.out.print("Tên mới: ");
            newName = sc.nextLine().trim();

            if (newName.isEmpty()) {
                System.out.println("Không được để trống");
                continue;
            }

            if (newName.length() > 100) {
                System.out.println("Không vượt quá 100 ký tự");
                continue;
            }

            Course exist = courseService.findByName(newName);
            if (exist != null && exist.getId() != id) {
                System.out.println("Tên khóa học đã tồn tại");
                continue;
            }

            break;
        }

        courseService.updateCourseName(id, newName);
    }

    private static void updateDuration(int id) {

        Course c = courseService.findById(id);

        System.out.println("Thời lượng hiện tại: " + c.getDuration());

        int duration;

        while (true) {
            System.out.print("Thời lượng mới: ");
            try {
                duration = Integer.parseInt(sc.nextLine().trim());
                if (duration <= 0) {
                    System.out.println("Phải > 0");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
            }
        }

        courseService.updateCourseDuration(id, duration);
    }

    private static void updateInstructor(int id) {

        Course c = courseService.findById(id);

        System.out.println("Giảng viên hiện tại: " + c.getInstructor());

        String instructor;

        while (true) {
            System.out.print("Giảng viên mới: ");
            instructor = sc.nextLine().trim();

            if (instructor.isEmpty()) {
                System.out.println("Không được để trống");
                continue;
            }

            break;
        }

        courseService.updateCourseInstructor(id, instructor);
    }

    private static void deleteCourse() {
        int id;
        while (true) {
            System.out.print("Nhập ID khóa học cần xóa: ");
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ!");
            }
        }

        Course c = courseService.findById(id);
        if (c == null) {
            System.out.println("Không tìm thấy khóa học có id là: " + id);
            return;
        }
        System.out.println("===== THÔNG TIN KHÓA HỌC =====");
        System.out.printf("Tên        : %s\n", c.getName());
        System.out.printf("Thời lượng : %s\n", c.getDuration());
        System.out.printf("Giảng viên : %s\n", c.getInstructor());
        System.out.printf("Ngày tạo   : %s\n", c.getCreatedAt());
        System.out.println("==============================");

        System.out.print("Xác nhận xóa (y/n): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println("Đã hủy thao tác xóa");
            return;
        }

        courseService.deleteCourse(id);
    }

    private static void searchCourse() {
        System.out.print("Nhập tên khóa học cần tìm: ");
        String keyword = sc.nextLine().trim();

        List<Course> list = courseService.searchByName(keyword);

        if (list.isEmpty()) {
            System.out.println("Không tìm thấy khóa học có tên là: " + keyword);
            return;
        }

        courseService.printCourseTable(list);
    }

    private static void sortCourse() {
        System.out.println("1. Sắp xếp theo Tên A-Z");
        System.out.println("2. Sắp xếp theo Tên Z-A");
        System.out.println("3. Sắp xếp theo ID tăng");
        System.out.println("4. Sắp xếp theo ID giảm");
        System.out.print("Chọn: ");

        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ");
                System.out.print("Chọn: ");
            }
        }

        List<Course> list;

        switch (choice) {
            case 1 -> list = courseService.sortCourses("name", true);
            case 2 -> list = courseService.sortCourses("name", false);
            case 3 -> list = courseService.sortCourses("id", true);
            case 4 -> list = courseService.sortCourses("id", false);
            default -> {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng chỉ chọn 1–4");
                return;
            }
        }

        courseService.printCourseTable(list);
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
                System.out.println("Nhập số hợp lệ!");
            }
        }

        while (true) {
            System.out.print("Nhập Số lượng mỗi trang: ");
            try {
                size = Integer.parseInt(sc.nextLine().trim());
                if (size <= 0) {
                    System.out.println("Số lượng mỗi trang Phải > 0!");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ!");
            }
        }

        int total = courseService.countTotalCourses();

        if (total == 0) {
            System.out.println("Không có khóa học");
            return;
        }

        int totalPage = (int) Math.ceil((double) total / size);

        if (page < 1 || page > totalPage) {
            System.out.println("Trang chỉ từ 1 -> " + totalPage);
            return;
        }

        List<Course> list = courseService.paginateCourses(page, size);

        System.out.println("===== TRANG " + page + "/" + totalPage + " =====");

        if (list.isEmpty()) {
            System.out.println("Không có dữ liệu");
            return;
        }

        courseService.printCourseTable(list);

    }
}
