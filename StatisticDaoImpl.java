package BaiTapProject.Dao.Impl;

import BaiTapProject.Dao.IStatisticDao;
import BaiTapProject.Model.CourseStatistic;
import BaiTapProject.Utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticDaoImpl implements IStatisticDao {
    @Override
    public List<CourseStatistic> getStudentsByCourse() {
        List<CourseStatistic> list = new ArrayList<>();

        String sql = """
            SELECT c.id, c.name, COUNT(e.student_id) AS total
            FROM courses c
            LEFT JOIN enrollments e ON c.id = e.course_id
            GROUP BY c.id, c.name
        """;

        try (Connection c = ConnectionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new CourseStatistic(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("total")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<CourseStatistic> top5MostStudentsCourses() {
        List<CourseStatistic> list = new ArrayList<>();

        String sql = """
                SELECT c.id, c.name, COUNT(e.id) AS total
                FROM course c
                LEFT JOIN enrollment e ON c.id = e.course_id AND e.status='CONFIRM'
                GROUP BY c.id
                ORDER BY total DESC
                LIMIT 5
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CourseStatistic cs = new CourseStatistic(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("total")
                );
                list.add(cs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public List<CourseStatistic> coursesMoreThan10Students() {
        List<CourseStatistic> list = new ArrayList<>();

        String sql = """
                SELECT c.id, c.name, COUNT(e.id) AS total
                FROM course c
                LEFT JOIN enrollment e ON c.id = e.course_id AND e.status='CONFIRM'
                GROUP BY c.id
                HAVING COUNT(e.id) > 10
                ORDER BY total DESC
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CourseStatistic cs = new CourseStatistic(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("total")
                );
                list.add(cs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

}
