package BaiTapProject.Dao.Impl;

import BaiTapProject.Dao.IEnrollmentDao;
import BaiTapProject.Model.*;
import BaiTapProject.Utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDaoImpl implements IEnrollmentDao {
    @Override
    public boolean existsEnrollment(int studentId, int courseId) {
        String sql = "SELECT 1 FROM enrollment WHERE student_id = ? AND course_id = ? AND status IN ('WAITING','CONFIRM')";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void insert(Enrollment enrollment) {
        String sql = """
                INSERT INTO enrollment(student_id, course_id, status)
                VALUES (?, ?, ?::enrollment_status)
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, enrollment.getStudentId());
            ps.setInt(2, enrollment.getCourseId());
            ps.setString(3, enrollment.getStatus().name());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Enrollment> findAll() {
        List<Enrollment> list = new ArrayList<>();
        String sql = " SELECT * FROM enrollment ORDER BY id ";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Enrollment(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getTimestamp("registered_at").toLocalDateTime(),
                        EnrollmentStatus.valueOf(rs.getString("status"))
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Enrollment findById(int id) {
        String sql = "SELECT * FROM enrollment WHERE id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Enrollment(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getTimestamp("registered_at").toLocalDateTime(),
                        EnrollmentStatus.valueOf(rs.getString("status"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Enrollment findByStudentAndCourse(int studentId, int courseId) {
        String sql = "SELECT * FROM enrollment WHERE student_id = ? AND course_id = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Enrollment(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getTimestamp("registered_at").toLocalDateTime(),
                        EnrollmentStatus.valueOf(rs.getString("status"))
                );
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public void updateStatus(int id, EnrollmentStatus status) {
        String sql = "UPDATE enrollment SET status=? WHERE id=?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM enrollment WHERE id=? AND status='WAITING'";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void removeStudentFromCourse(int studentId, int courseId) {

        String sql = "DELETE FROM enrollment WHERE student_id=? AND course_id=? AND status='CONFIRM' ";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cancelEnrollment(int id) {
        String sql = "UPDATE enrollment SET status = 'CANCEL' WHERE id=? AND status = 'WAITING'";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public int countStudentsByCourse(int courseId) {
        String sql = "SELECT COUNT(*) FROM enrollment WHERE course_id = ? AND status='CONFIRM'";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    @Override
    public List<EnrollmentView> findByCourseId(int courseId) {
        List<EnrollmentView> list = new ArrayList<>();

        String sql = """
                SELECT e.id AS enrollment_id, s.id AS student_id, s.name AS student_name,
                       s.email AS student_email, c.id AS course_id, c.name AS course_name,
                       e.registered_at, e.status
                FROM enrollment e
                JOIN student s ON e.student_id = s.id
                JOIN course c ON e.course_id = c.id
                WHERE c.id = ?
                ORDER BY e.id
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EnrollmentView v = new EnrollmentView(
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("student_email"),
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getTimestamp("registered_at").toLocalDateTime(),
                        EnrollmentStatus.valueOf(rs.getString("status"))
                );
                list.add(v);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public List<EnrollmentView> findByStudentId(int studentId) {
        List<EnrollmentView> list = new ArrayList<>();

        String sql = """
                SELECT e.id AS enrollment_id, s.id AS student_id, s.name AS student_name,
                       s.email AS student_email, c.id AS course_id, c.name AS course_name,
                       e.registered_at, e.status
                FROM enrollment e
                JOIN student s ON e.student_id = s.id
                JOIN course c ON e.course_id = c.id
                WHERE s.id = ?
                ORDER BY e.id
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EnrollmentView v = new EnrollmentView(
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("student_email"),
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getTimestamp("registered_at").toLocalDateTime(),
                        EnrollmentStatus.valueOf(rs.getString("status"))
                );
                list.add(v);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public List<EnrollmentView> sortRegisteredCourses(int studentId, String field, boolean asc) {

        List<String> validFields = List.of("course_name", "registered_at");

        if (!validFields.contains(field)) {
            field = "course_name";
        }

        String orderBy = field.equals("course_name") ? "c.name" : "e.registered_at";

        String sql = """
                SELECT e.id AS enrollment_id, s.id AS student_id, s.name AS student_name,
                       s.email AS student_email, c.id AS course_id, c.name AS course_name,
                       e.registered_at, e.status
                FROM enrollment e
                JOIN student s ON e.student_id = s.id
                JOIN course c ON e.course_id = c.id
                WHERE s.id = ?
               """ +
                "ORDER BY " + orderBy + (asc ? " ASC" : " DESC");

        List<EnrollmentView> list = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EnrollmentView v = new EnrollmentView(
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("student_email"),
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getTimestamp("registered_at").toLocalDateTime(),
                        EnrollmentStatus.valueOf(rs.getString("status"))
                );
                list.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    @Override
    public int countAll() {

        String sql = "SELECT COUNT(*) FROM enrollment";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
    @Override
    public List<EnrollmentView> paginate(int page, int size) {
        List<EnrollmentView> list = new ArrayList<>();

        String sql = """
          SELECT e.id AS enrollment_id,
                    e.student_id,
                     s.name AS student_name,
                  s.email AS student_email,
                  e.course_id,
                     c.name AS course_name,
               e.registered_at,
                e.status
           FROM enrollment e
           JOIN student s ON e.student_id = s.id
        JOIN course c ON e.course_id = c.id
        ORDER BY e.id
                LIMIT ? OFFSET ?
    """;

        try (Connection c = ConnectionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, size);
            ps.setInt(2, (page - 1) * size);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new EnrollmentView(
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("student_email"),
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getTimestamp("registered_at").toLocalDateTime(),
                        EnrollmentStatus.valueOf(rs.getString("status"))
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    @Override
    public EnrollmentView findEnrollmentView(int studentId, int courseId) {
        String sql = """
                      SELECT
                                 e.id AS enrollment_id,
                                 s.id AS student_id,
                                 s.name AS student_name,
                                 s.email AS student_email,
                                 c.id AS course_id,
                                 c.name AS course_name,
                                 e.registered_at,
                                 e.status
                             FROM enrollment e
                             JOIN student s ON e.student_id = s.id
                             JOIN course c ON e.course_id = c.id
                             WHERE e.student_id=? AND e.course_id=?
                """;

        try (Connection c = ConnectionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new EnrollmentView(
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("student_email"),
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getTimestamp("registered_at").toLocalDateTime(),
                        EnrollmentStatus.valueOf(rs.getString("status"))
                );
            }
        }  catch (SQLException e) {
            e.printStackTrace();
         }
        return null;
    }
}