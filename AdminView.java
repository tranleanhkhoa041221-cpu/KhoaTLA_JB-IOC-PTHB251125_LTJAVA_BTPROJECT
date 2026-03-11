package BaiTapProject.Presentation;

import BaiTapProject.Business.IAdminService;
import BaiTapProject.Business.Impl.AdminServiceImpl;
import BaiTapProject.Model.Admin;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;

public class AdminView {
    private static final Scanner sc = new Scanner(System.in);
    private static final IAdminService adminService = new AdminServiceImpl();

    public static void show(Admin admin) {

        while (true) {
            System.out.println("======== MENU ADMIN ========");
            System.out.println("1. Quản lý khóa học");
            System.out.println("2. Quản lý học viên");
            System.out.println("3. Quản lý đăng ký khóa học");
            System.out.println("4. Thống kê");
            System.out.println("5. Đổi mật khẩu");
            System.out.println("6. Đăng xuất");
            System.out.println("============================");

            int choice;
            while (true) {
                try {
                    System.out.print("Chọn: ");
                    choice = Integer.parseInt(sc.nextLine().trim());
                    break;
                } catch (Exception e) {
                    System.out.println("Vui lòng nhập số");
                }
            }

            switch (choice) {
                case 1 -> CourseViewManagement.showMenu();
                case 2 -> StudentViewManagemet.showMenu();
                case 3 -> EnrollmentViewManagement.showMenu();
                case 4 -> StatisticsViewManagement.showMenu();
                case 5 -> changePassword(admin);
                case 6 -> {
                    System.out.println("Đăng xuất thành công");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chỉ chọn 1–6");
            }
        }
    }

    private static void changePassword(Admin admin) {
        System.out.println("===== ĐỔI MẬT KHẨU =====");

        String oldPass;
        while (true) {
            System.out.print("Nhập mật khẩu cũ: ");
            oldPass = sc.nextLine().trim();

            if (oldPass.isEmpty()) {
                System.out.println("Mật khẩu cũ không được để trống");
                continue;
            }

            if (!BCrypt.checkpw(oldPass, admin.getPassword())) {
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

            if (BCrypt.checkpw(newPass, admin.getPassword())) {
                System.out.println("Mật khẩu mới không được trùng mật khẩu cũ");
                continue;
            }
            break;
        }
        boolean ok = adminService.changePassword(admin, oldPass, newPass);

        if (ok) {
            System.out.println("Đổi mật khẩu thành công");


        } else {
            System.out.println("Đổi mật khẩu thất bại");
        }
    }


}
