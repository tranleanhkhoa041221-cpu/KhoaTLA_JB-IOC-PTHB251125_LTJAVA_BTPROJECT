package BaiTapProject.Presentation;

import BaiTapProject.Business.IStatisticService;
import BaiTapProject.Business.Impl.StatisticServiceImpl;
import BaiTapProject.Model.CourseStatistic;

import java.util.List;
import java.util.Scanner;

public class StatisticsViewManagement {

    private static final Scanner sc = new Scanner(System.in);
    private static final IStatisticService statisticService = new StatisticServiceImpl();

    public static void showMenu() {
        while (true) {
            System.out.println("===== THỐNG KÊ =====");
            System.out.println("1. Thống kê tổng số khóa học & tổng số học viên");
            System.out.println("2. Thống kê số học viên theo từng khóa học");
            System.out.println("3. Top 5 khóa học đông học viên nhất");
            System.out.println("4. Liệt kê khóa học có trên 10 học viên");
            System.out.println("5. Quay về menu chính");


            int choice;
            while (true) {
                System.out.print("Chọn: ");
                try {
                    choice = Integer.parseInt(sc.nextLine().trim());
                    break;
                } catch (Exception e) {
                    System.out.println("Vui lòng nhập số");
                }
            }

            switch (choice) {
                case 1 -> totalCounts();
                case 2 -> studentsByCourse();
                case 3 -> top5Courses();
                case 4 -> coursesMoreThan10();
                case 5 -> { return; }
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chỉ chọn 1-5");
            }
        }
    }

    private static void totalCounts() {
        int totalCourses = statisticService.countTotalCourses();
        int totalStudents = statisticService.countTotalStudents();

        System.out.println("===== THỐNG KÊ =====");
        System.out.printf("Tổng số khóa học : %d\n", totalCourses);
        System.out.printf("Tổng số học viên : %d\n", totalStudents);
        System.out.println("=====================");
    }

    private static void studentsByCourse() {
        List<CourseStatistic> list = statisticService.getStudentsByCourse();

        if (list.isEmpty()) {
            System.out.println("Không có dữ liệu");
            return;
        }

        printStatisticTable(list);
    }


    private static void top5Courses() {
        List<CourseStatistic> list = statisticService.top5MostStudentsCourses();

        if (list.isEmpty()) {
            System.out.println("Không có dữ liệu");
            return;
        }

        printStatisticTable(list);
    }

    private static void coursesMoreThan10() {
        List<CourseStatistic> list = statisticService.coursesMoreThan10Students();

        if (list.isEmpty()) {
            System.out.println("Không có khóa học nào trên 10 học viên");
            return;
        }

        printStatisticTable(list);
    }

    private static void printStatisticTable(List<CourseStatistic> list) {

        System.out.printf("+%s+%s+%s+\n",
                "-".repeat(8), "-".repeat(27), "-".repeat(16));

        System.out.printf("| %-6s | %-25s | %-14s |\n",
                "ID", "Tên khóa học", "Số học viên");

        System.out.printf("+%s+%s+%s+\n",
                "-".repeat(8), "-".repeat(27), "-".repeat(16));

        for (CourseStatistic cs : list) {
            cs.displayData();
        }

        System.out.printf("+%s+%s+%s+\n",
                "-".repeat(8), "-".repeat(27), "-".repeat(16));
    }
}
