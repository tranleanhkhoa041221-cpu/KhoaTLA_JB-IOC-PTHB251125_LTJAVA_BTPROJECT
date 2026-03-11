package BaiTapProject.Dao.Impl;

import BaiTapProject.Dao.ICourseDao;
import BaiTapProject.Model.Course;
import BaiTapProject.Utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements ICourseDao {

        @Override
        public List<Course> findAll() {
            List<Course> list = new ArrayList<>();
            String sql = "SELECT * FROM course ORDER BY id";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("duration"),
                            rs.getString("instructor"),
                            rs.getDate("create_at").toLocalDate()
                    ));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        public Course findById(int id) {
            String sql = "SELECT * FROM course WHERE id = ?";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("duration"),
                            rs.getString("instructor"),
                            rs.getDate("create_at").toLocalDate()
                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    public Course findByName(String name) {
        String sql = "SELECT * FROM course WHERE name = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("duration"),
                        rs.getString("instructor"),
                        rs.getDate("create_at").toLocalDate()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
        public void insert(Course course) {
            String sql = "INSERT INTO course(name, duration, instructor) VALUES (?, ?, ?)";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, course.getName());
                ps.setInt(2, course.getDuration());
                ps.setString(3, course.getInstructor());
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void update(Course course) {
            String sql = "UPDATE course SET name = ?, duration = ?, instructor = ? WHERE id = ?";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, course.getName());
                ps.setInt(2, course.getDuration());
                ps.setString(3, course.getInstructor());
                ps.setInt(4, course.getId());
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM course WHERE id = ?";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, id);
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public List<Course> searchByName(String name) {
            List<Course> list = new ArrayList<>();
            String sql = " SELECT * FROM course WHERE name ILIKE ? ORDER BY id ";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, "%" + name + "%");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    list.add(new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("duration"),
                            rs.getString("instructor"),
                            rs.getDate("create_at").toLocalDate()
                    ));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        public List<Course> sort(String field, boolean asc) {
            List<Course> list = new ArrayList<>();

            List<String> valid = List.of("id", "name");
            if (!valid.contains(field)) field = "id";

            String order = asc ? "ASC" : "DESC";

            String sql = " SELECT * FROM course ORDER BY " + field + " " + order;

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("duration"),
                            rs.getString("instructor"),
                            rs.getDate("create_at").toLocalDate()
                    ));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        public List<Course> paginate(int page, int size) {
            List<Course> list = new ArrayList<>();
            String sql = " SELECT * FROM course ORDER BY id LIMIT ? OFFSET ? ";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, size);
                ps.setInt(2, (page - 1) * size);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    list.add(new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("duration"),
                            rs.getString("instructor"),
                            rs.getDate("create_at").toLocalDate()
                    ));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        public List<Course> recommendCourses(int studentId) {
            List<Course> list = new ArrayList<>();

            String sql = """
                   SELECT c.*, COUNT(e.student_id) AS total
                     FROM course c
                       JOIN enrollment e ON c.id = e.course_id
                          WHERE e.student_id IN (
                             SELECT student_id FROM enrollment
                                   WHERE course_id IN (
                                     SELECT course_id FROM enrollment
                                       WHERE student_id = ?
                                          )
                                    )
                         AND c.id NOT IN (
                          SELECT course_id FROM enrollment
                                   WHERE student_id = ?
                                              )
                             GROUP BY c.id
                         ORDER BY total DESC
                     LIMIT 3;
                    """;

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, studentId);
                ps.setInt(2, studentId);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    list.add(new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("duration"),
                            rs.getString("instructor"),
                            rs.getDate("create_at").toLocalDate()
                    ));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        public List<Course> findCoursesNotRegistered(int studentId) {
            List<Course> list = new ArrayList<>();

            String sql = " SELECT * FROM course WHERE id NOT IN ( SELECT course_id FROM enrollment WHERE student_id = ?) ORDER BY id ";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, studentId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    list.add(new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("duration"),
                            rs.getString("instructor"),
                            rs.getDate("create_at").toLocalDate()
                    ));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        public int countTotalCourses() {
            String sql = "SELECT COUNT(*) FROM course";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) return rs.getInt(1);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }
    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM course WHERE name = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean hasEnrollment(int courseId) {
        String sql = "SELECT 1 FROM enrollment WHERE course_id = ? LIMIT 1";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, courseId);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    }




