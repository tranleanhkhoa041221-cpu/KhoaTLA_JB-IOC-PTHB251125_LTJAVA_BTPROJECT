package BaiTapProject.Presentation;

import BaiTapProject.Business.IStudentService;
import BaiTapProject.Business.Impl.StudentServiceImpl;
import BaiTapProject.Model.Student;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class StudentViewManagemet {
        private static final Scanner sc = new Scanner(System.in);
        private static final IStudentService studentService = new StudentServiceImpl();

        public static void showMenu() {
            while (true) {
                System.out.println("===== QUẢN LÝ HỌC VIÊN =====");
                System.out.println("1. Hiển thị danh sách học viên");
                System.out.println("2. Thêm học viên");
                System.out.println("3. Chỉnh sửa học viên");
                System.out.println("4. Xóa học viên");
                System.out.println("5. Tìm kiếm (ID / Tên / Email)");
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
                    case 2 -> addStudent();
                    case 3 -> updateMenu();
                    case 4 -> deleteStudent();
                    case 5 -> searchStudent();
                    case 6 -> sortStudent();
                    case 7 -> paginate();
                    case 8 -> { return; }
                    default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chỉ chọn 1-8");
                }
            }
        }

        private static void showAll() {
            List<Student> list = studentService.getAllStudents();
            if (list.isEmpty()) {
                System.out.println("Không có học viên nào");
                return;
            }
            studentService.printStudentTable(list);
        }

        private static void addStudent() {
            System.out.println("===== THÊM HỌC VIÊN =====");
            String name;
            while (true) {
                System.out.print("Tên: ");
                name = sc.nextLine().trim();
                if (name.isEmpty()) {
                    System.out.println("Không được để trống");
                    continue;
                }
                if (name.length() > 100) {
                    System.out.println("Không vượt quá 100 ký tự");
                    continue;
                }
                break;
            }


            LocalDate dob;
            while (true) {
                System.out.print("Ngày sinh (yyyy-MM-dd): ");
                try {
                    dob = LocalDate.parse(sc.nextLine().trim());
                    if (dob.isAfter(LocalDate.now())) {
                        System.out.println("Ngày sinh không được ở tương lai");
                        continue;
                    }
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Ngày sinh không hợp lệ");
                }
            }

            String email;
            while (true) {
                System.out.print("Email: ");
                email = sc.nextLine().trim();
                if (email.isEmpty()) {
                    System.out.println("Không được để trống");
                    continue;
                }
                if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    System.out.println("Email không hợp lệ");
                    continue;
                }
                if (studentService.findByEmail(email) != null) {
                    System.out.println("Email đã tồn tại");
                    continue;
                }
                break;
            }

            Boolean sex = null;
            while (true) {
                System.out.print("Giới tính (1 = Nam, 2 = Nữ): ");
                String input = sc.nextLine().trim();

                if (input.equals("1")) { sex = true; break; }
                if (input.equals("2")) { sex = false; break; }

                System.out.println("Chỉ được chọn 1 hoặc 2");
            }

            String phone;
            while (true) {
                System.out.print("Số điện thoại: ");
                phone = sc.nextLine().trim();

                if (phone.isEmpty()) {
                    phone = null;
                    break;
                }

                if (!phone.matches("^0\\d{9}$")) {
                    System.out.println("Số điện thoại phải gồm 10 số và bắt đầu bằng 0");
                    continue;
                }

                if (studentService.findByPhone(phone) != null) {
                    System.out.println("Số điện thoại đã tồn tại");
                    continue;
                }

                break;
            }

            String password;
            while (true) {
                System.out.print("Mật khẩu: ");
                password = sc.nextLine().trim();
                if (password.isEmpty()) {
                    System.out.println("Mật khẩu không được để trống");
                    continue;
                }
                if (password.length() < 6) {
                    System.out.println("Mật khẩu phải >= 6 ký tự");
                    continue;
                }
                break;
            }

            Student s = new Student();
            s.setName(name);
            s.setDob(dob);
            s.setEmail(email);
            s.setSex(sex);
            s.setPhone(phone);
            s.setPassword(password);

            studentService.addStudent(s);
        }


        private static void updateMenu() {
            int id;
            while (true) {
                System.out.print("Nhập ID học viên: ");
                try {
                    id = Integer.parseInt(sc.nextLine().trim());
                    break;
                } catch (Exception e) {
                    System.out.println("Nhập số hợp lệ");
                }
            }

            Student s = studentService.findById(id);
            if (s == null) {
                System.out.println("Không tìm thấy học viên");
                return;
            }
            System.out.println("===== THÔNG TIN HỌC VIÊN =====");
            System.out.printf("Tên        : %s\n", s.getName());
            System.out.printf("Ngày sinh  : %s\n", s.getDob());
            System.out.printf("Email      : %s\n", s.getEmail());
            System.out.printf("SĐT        : %s\n", s.getPhone());
            System.out.printf("Giới tính  : %s\n", s.isSex() ? "Nam" : "Nữ");
            System.out.printf("Ngày tạo   : %s\n", s.getCreatedAt());
            System.out.println("==============================");

            while (true) {
                System.out.println("===== SỬA HỌC VIÊN =====");
                System.out.println("1. Sửa tên");
                System.out.println("2. Sửa email");
                System.out.println("3. Sửa số điện thoại");
                System.out.println("4. Sửa ngày sinh");
                System.out.println("5. Sửa giới tính");
                System.out.println("6. Quay về menu chính");

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
                    case 2 -> updateEmail(id);
                    case 3 -> updatePhone(id);
                    case 4 -> updateDob(id);
                    case 5 -> updateSex(id);
                    case 6 -> { return; }
                    default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chỉ chọn 1-6");
                }
            }
        }


        private static void updateName(int id) {

            Student s = studentService.findById(id);

            System.out.println("Tên hiện tại: " + s.getName());

            while (true) {
                System.out.print("Tên mới: ");
                String name = sc.nextLine().trim();

                if (name.isEmpty()) {
                    System.out.println("Tên không được để trống");
                    continue;
                }
                if (name.length() > 100) {
                    System.out.println("Tên không được vượt quá 100 ký tự");
                    continue;
                }

                studentService.updateStudentName(id, name);
                break;
            }
        }

        private static void updateEmail(int id) {

            Student s = studentService.findById(id);

            System.out.println("Email hiện tại: " + s.getEmail());

            while (true) {
                System.out.print("Email mới: ");
                String email = sc.nextLine().trim();

                if (email.isEmpty()) {
                    System.out.println("Email không được để trống");
                    continue;
                }

                if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    System.out.println("Email không hợp lệ");
                    continue;
                }

                Student exist = studentService.findByEmail(email);
                if (exist != null && exist.getId() != id) {
                    System.out.println("Email đã tồn tại");
                    continue;
                }

                studentService.updateStudentEmail(id, email);
                break;
            }
        }

        private static void updatePhone(int id) {

            Student s = studentService.findById(id);

            System.out.println("SĐT hiện tại: " + s.getPhone());

            while (true) {
                System.out.print("SĐT mới: ");
                String phone = sc.nextLine().trim();

                if (phone.isEmpty()) {
                    studentService.updateStudentPhone(id, null);
                    break;
                }

                if (!phone.matches("^0\\d{9}$")) {
                    System.out.println("Số điện thoại không hợp lệ");
                    continue;
                }

                Student exist = studentService.findByPhone(phone);
                if (exist != null && exist.getId() != id) {
                    System.out.println("Số điện thoại đã tồn tại");
                    continue;
                }

                studentService.updateStudentPhone(id, phone);
                break;
            }
        }

        private static void updateDob(int id) {

            Student s = studentService.findById(id);

            System.out.println("Ngày sinh hiện tại: " + s.getDob());

            while (true) {
                System.out.print("Ngày sinh mới (yyyy-MM-dd): ");
                String input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Không được để trống");
                    continue;
                }

                try {
                    LocalDate dob = LocalDate.parse(input);
                    if (dob.isAfter(LocalDate.now())) {
                        System.out.println("Ngày sinh không được ở tương lai");
                        continue;
                    }
                    studentService.updateStudentDob(id, dob);
                    break;
                } catch (Exception e) {
                    System.out.println("Ngày sinh không hợp lệ");
                }
            }
        }

        private static void updateSex(int id) {

            Student s = studentService.findById(id);

            System.out.println("Giới tính hiện tại: " + (s.isSex() ? "Nam" : "Nữ"));

            while (true) {
                System.out.print("Giới tính mới (1 = Nam, 2 = Nữ): ");
                String input = sc.nextLine().trim();

                if (input.equals("1")) {
                    studentService.updateStudentSex(id, true);
                    break;
                }
                if (input.equals("2")) {
                    studentService.updateStudentSex(id, false);
                    break;
                }

                System.out.println("Chỉ đc chọn 1 hoặc 2");
            }
        }

        private static void deleteStudent() {
            int id;
            while (true) {
                System.out.print("Nhập ID học viên cần xóa: ");
                try {
                    id = Integer.parseInt(sc.nextLine().trim());
                    break;
                } catch (Exception e) {
                    System.out.println("Nhập số hợp lệ");

                }
            }


            Student s = studentService.findById(id);
            if (s == null) {
                System.out.println("Không tìm thấy học viên");
                return;
            }

            System.out.println("===== THÔNG TIN HỌC VIÊN =====");
            System.out.printf("Tên        : %s\n", s.getName());
            System.out.printf("Ngày sinh  : %s\n", s.getDob());
            System.out.printf("Email      : %s\n", s.getEmail());
            System.out.printf("SĐT        : %s\n", s.getPhone());
            System.out.printf("Giới tính  : %s\n", s.isSex() ? "Nam" : "Nữ");
            System.out.printf("Ngày tạo   : %s\n", s.getCreatedAt());
            System.out.println("==============================");

            System.out.print("Xác nhận xóa (y/n): ");
            if (!sc.nextLine().trim().equalsIgnoreCase("y")) {
                System.out.println("Hủy thao tác");
                return;
            }

            studentService.deleteStudent(id);
        }

        private static void searchStudent() {
            System.out.print("Nhập từ khóa (ID / Tên / Email): ");
            String keyword = sc.nextLine().trim();

            List<Student> list;

            if (keyword.matches("\\d+")) {
                Student s = studentService.findById(Integer.parseInt(keyword));
                if (s == null) {
                    System.out.println("Không tìm thấy");
                    return;
                }
                list = List.of(s);
            }
            else if (keyword.contains("@")) {
                Student s = studentService.findByEmail(keyword);
                if (s == null) {
                    System.out.println("Không tìm thấy");
                    return;
                }
                list = List.of(s);
            }
            else {
                list = studentService.searchStudents(keyword);
                if (list.isEmpty()) {
                    System.out.println("Không tìm thấy");
                    return;
                }
            }

            studentService.printStudentTable(list);
        }

        private static void sortStudent() {
            System.out.println("1. Tên A-Z");
            System.out.println("2. Tên Z-A");
            System.out.println("3. ID tăng");
            System.out.println("4. ID giảm");
            int choice;
            while (true) {
                System.out.print("Chọn: ");
                try {
                    choice = Integer.parseInt(sc.nextLine().trim());
                    break;
                } catch (Exception e) {
                    System.out.println("Nhập số hợp lệ!");
                }
            }

            List<Student> list;

            switch (choice) {
                case 1 -> list = studentService.sortStudents("name", true);
                case 2 -> list = studentService.sortStudents("name", false);
                case 3 -> list = studentService.sortStudents("id", true);
                case 4 -> list = studentService.sortStudents("id", false);
                default -> {
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chỉ chọn 1-4");
                    return;
                }
            }

            studentService.printStudentTable(list);
        }


        private static void paginate() {
            int page, size;

            while (true) {
                System.out.print("Nhập số trang: ");
                try {
                    page = Integer.parseInt(sc.nextLine().trim());
                    if (page <= 0) {
                        System.out.println("Số trang phải > 0");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Nhập số hợp lệ!");
                }
            }

            while (true) {
                System.out.print("Nhập số lượng mỗi trang: ");
                try {
                    size = Integer.parseInt(sc.nextLine().trim());
                    if (size <= 0) {
                        System.out.println("Số lượng mỗi trang phải > 0");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Nhập số hợp lệ");
                }
            }

            int total = studentService.countTotalStudents();

            if (total == 0) {
                System.out.println("Không có khóa học");
                return;
            }

            int totalPage = (int) Math.ceil((double) total / size);

            if (page < 1 || page > totalPage) {
                System.out.println("Trang chỉ từ 1 -> " + totalPage);
                return;
            }

            List<Student> list = studentService.paginateStudents(page, size);

            System.out.println("===== TRANG " + page + "/" + totalPage + " =====");

            if (list.isEmpty()) {
                System.out.println("Không có dữ liệu");
                return;
            }

            studentService.printStudentTable(list);

        }

}
