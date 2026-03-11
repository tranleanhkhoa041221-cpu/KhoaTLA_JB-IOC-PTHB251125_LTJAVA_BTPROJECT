package BaiTapProject;

import BaiTapProject.Business.IAdminService;
import BaiTapProject.Business.IStudentService;
import BaiTapProject.Business.Impl.AdminServiceImpl;
import BaiTapProject.Business.Impl.StudentServiceImpl;
import BaiTapProject.Model.Admin;
import BaiTapProject.Model.Student;
import BaiTapProject.Presentation.AdminView;
import BaiTapProject.Presentation.StudentView;


import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        menuLogin();
    }

    private static final Scanner sc = new Scanner(System.in);
    private static final IStudentService studentService = new StudentServiceImpl();
    private static final IAdminService adminService = new AdminServiceImpl();

    public static void menuLogin() {
        while (true) {
            System.out.println("======== HỆ THỐNG QUẢN LÝ ĐÀO TẠO ========");
            System.out.println("1. Đăng nhập với tư cách Admin");
            System.out.println("2. Đăng nhập với tư cách Học viên");
            System.out.println("3. Đăng ký tài khoản học viên");
            System.out.println("4. Thoát");
            System.out.println("==========================================");
            System.out.print("Chọn: ");

            int choice;
            while (true) {
                try {
                    choice = Integer.parseInt(sc.nextLine().trim());
                    break;
                } catch (Exception e) {
                    System.out.println("Vui lòng nhập số");
                    System.out.print("Chọn: ");
                }
            }

            switch (choice) {
                case 1 -> loginAdmin();
                case 2 -> loginStudent();
                case 3 -> registerStudent();
                case 4 -> {
                    System.out.println("Thoát chương trình");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chỉ chọn 1–4");
            }
        }
    }

    private static void registerStudent() {

        System.out.println("===== ĐĂNG KÝ TÀI KHOẢN HỌC VIÊN =====");

        Student st = new Student();

        while (true) {
            System.out.print("Tên: ");
            st.setName(sc.nextLine().trim());
            if (st.getName().isEmpty()) {
                System.out.println("Tên không được để trống");
                continue;
            }
            if (st.getName().length() > 100) {
                System.out.println("Tên không được vượt quá 100 ký tự");
                continue;
            }
            break;
        }

        while (true) {
            try {
                System.out.print("Ngày sinh (yyyy-MM-dd): ");
                LocalDate dob = LocalDate.parse(sc.nextLine().trim());

                if (dob.isAfter(LocalDate.now())) {
                    System.out.println("Ngày sinh không được ở tương lai");
                    continue;
                }

                st.setDob(dob);
                break;
            } catch (Exception e) {
                System.out.println("Ngày sinh không hợp lệ");
            }
        }

        while (true) {
            System.out.print("Email: ");
            st.setEmail(sc.nextLine().trim());

            if (st.getEmail().isEmpty()) {
                System.out.println("Email không được để trống");
                continue;
            }
            if (!st.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                System.out.println("Email không hợp lệ");
                continue;
            }
            if (studentService.findByEmail(st.getEmail()) != null) {
                System.out.println("Email đã tồn tại");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Giới tính (1 = Nam, 2 = Nữ): ");
            String input = sc.nextLine().trim();

            if (input.equals("1")) {
                st.setSex(true);
                break;
            }
            if (input.equals("2")) {
                st.setSex(false);
                break;
            }

            System.out.println("Chỉ được nhập 1 hoặc 2");
        }

        while (true) {
            System.out.print("Số điện thoại: ");
            st.setPhone(sc.nextLine().trim());

            if (st.getPhone().isEmpty()) {
                st.setPhone(null);
                break;
            }

            if (!st.getPhone().matches("^0\\d{9}$")) {
                System.out.println("Số điện thoại phải gồm 10 số và bắt đầu bằng 0");
                continue;
            }

            if (studentService.findByPhone(st.getPhone()) != null) {
                System.out.println("Số điện thoại đã tồn tại");
                continue;
            }

            break;
        }

        while (true) {
            System.out.print("Mật khẩu (>= 6 ký tự): ");
            st.setPassword(sc.nextLine().trim());

            if (st.getPassword().isEmpty()) {
                System.out.println("Mật khẩu không được để trống");
                continue;
            }
            if (st.getPassword().length() < 6) {
                System.out.println("Mật khẩu phải có ít nhất 6 ký tự");
                continue;
            }
            break;
        }

        boolean ok = studentService.register(st);

        if (ok) {
            System.out.println("Đăng ký thành công. Mời bạn đăng nhập.");
            loginStudent();
        } else {
            System.out.println("Đăng ký thất bại");
        }
    }

    private static void loginStudent() {

        String email;
        String password;

        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine().trim();

            if (email.isEmpty()) {
                System.out.println("Email không được để trống");
                continue;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                System.out.println("Email không hợp lệ!");
                continue;
            }

            if (studentService.findByEmail(email) == null) {
                System.out.println("Email không tồn tại");
                continue;
            }

            break;
        }
        while (true) {
            System.out.print("Password: ");
            password = sc.nextLine().trim();

            if (password.isEmpty()) {
                System.out.println("Password không được để trống");
                continue;
            }

            Student st = studentService.login(email, password);
            if (st == null) {
                continue;
            }

            System.out.println("Đăng nhập thành công");
            StudentView.show(st);
            return;
        }
    }

    private static void loginAdmin() {

        String username;
        String password;

        while (true) {
            System.out.print("Username: ");
            username = sc.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("Username không được để trống");
                continue;
            }

            if (adminService.findByUsername(username) == null) {
                System.out.println("Username không tồn tại");
                continue;
            }

            break;
        }

        while (true) {
            System.out.print("Password: ");
            password = sc.nextLine().trim();

            if (password.isEmpty()) {
                System.out.println("Password không được để trống");
                continue;
            }

            Admin ad = adminService.login(username, password);
            if (ad == null) {
                continue;
            }

            System.out.println("Đăng nhập thành công");
            AdminView.show(ad);
            return;
        }
    }
}
