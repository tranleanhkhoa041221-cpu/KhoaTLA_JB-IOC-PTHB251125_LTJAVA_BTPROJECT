package BaiTapProject.Dao.Impl;

import BaiTapProject.Dao.IStudentDao;
import BaiTapProject.Model.Student;
import BaiTapProject.Utils.ConnectionDB;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements IStudentDao {

        @Override
        public List<Student> findAll() {
            List<Student> list = new ArrayList<>();
            String sql = " SELECT * FROM student ORDER BY id ";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("dob").toLocalDate(),
                            rs.getString("email"),
                            rs.getBoolean("sex"),
                            rs.getString("phone"),
                            rs.getString("password"),
                            rs.getDate("create_at").toLocalDate()
                    ));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        public Student findById(int id) {
            String sql = " SELECT * FROM student WHERE id = ? ";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("dob").toLocalDate(),
                            rs.getString("email"),
                            rs.getBoolean("sex"),
                            rs.getString("phone"),
                            rs.getString("password"),
                            rs.getDate("create_at").toLocalDate()
                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Student findByEmail(String email) {
            String sql = "SELECT * FROM student WHERE email = ?";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("dob").toLocalDate(),
                            rs.getString("email"),
                            rs.getBoolean("sex"),
                            rs.getString("phone"),
                            rs.getString("password"),
                            rs.getDate("create_at").toLocalDate()
                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Student findByPhone(String phone) {
            String sql = "SELECT * FROM student WHERE phone = ?";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, phone);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("dob").toLocalDate(),
                            rs.getString("email"),
                            rs.getBoolean("sex"),
                            rs.getString("phone"),
                            rs.getString("password"),
                            rs.getDate("create_at").toLocalDate()
                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void insert(Student student) {
            String sql = "INSERT INTO student(name, dob, email, sex, phone, password) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, student.getName());
                ps.setDate(2, Date.valueOf(student.getDob()));
                ps.setString(3, student.getEmail());
                ps.setBoolean(4, student.isSex());
                ps.setString(5, student.getPhone());
                ps.setString(6, student.getPassword());
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void update(Student student) {
            String sql = "UPDATE student SET name = ?, dob = ?, email = ?, sex = ?, phone = ?, password = ? WHERE id = ?";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, student.getName());
                ps.setDate(2, Date.valueOf(student.getDob()));
                ps.setString(3, student.getEmail());
                ps.setBoolean(4, student.isSex());
                ps.setString(5, student.getPhone());
                ps.setString(6, student.getPassword());
                ps.setInt(7, student.getId());
                ps.executeUpdate();

            } catch (SQLException e) {
               e.printStackTrace();
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM student WHERE id = ?";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, id);
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public List<Student> search(String keyword) {
            List<Student> list = new ArrayList<>();
            String sql = "SELECT * FROM student WHERE name ILIKE ? OR email ILIKE ? OR id::TEXT ILIKE ? ORDER BY id";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                String like = "%" + keyword + "%";
                ps.setString(1, like);
                ps.setString(2, like);
                ps.setString(3, like);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    list.add(new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("dob").toLocalDate(),
                            rs.getString("email"),
                            rs.getBoolean("sex"),
                            rs.getString("phone"),
                            rs.getString("password"),
                            rs.getDate("create_at").toLocalDate()
                    ));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        public List<Student> sort(String field, boolean asc) {
            List<Student> list = new ArrayList<>();
            List<String> valid = List.of("id", "name");

            if (!valid.contains(field)) field = "id";

            String order = asc ? "ASC" : "DESC";

            String sql = "SELECT * FROM student ORDER BY " + field + " " + order;

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("dob").toLocalDate(),
                            rs.getString("email"),
                            rs.getBoolean("sex"),
                            rs.getString("phone"),
                            rs.getString("password"),
                            rs.getDate("create_at").toLocalDate()
                    ));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        public List<Student> paginate(int page, int size) {
            List<Student> list = new ArrayList<>();
            String sql = " SELECT * FROM student ORDER BY id LIMIT ? OFFSET ?";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, size);
                ps.setInt(2, (page - 1) * size);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    list.add(new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("dob").toLocalDate(),
                            rs.getString("email"),
                            rs.getBoolean("sex"),
                            rs.getString("phone"),
                            rs.getString("password"),
                            rs.getDate("create_at").toLocalDate()
                    ));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        public void updatePassword(int id, String newPassword) {
            String sql = "UPDATE student SET password = ? WHERE id = ?";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, newPassword);
                ps.setInt(2, id);
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean existsByEmail(String email) {
            String sql = "SELECT 1 FROM student WHERE email = ?";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                return rs.next();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean existsByPhone(String phone) {
            String sql = "SELECT 1 FROM student WHERE phone = ?";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, phone);
                ResultSet rs = ps.executeQuery();
                return rs.next();

            } catch (SQLException e) {
               e.printStackTrace();
            }
            return false;
        }
    @Override
    public int countTotalStudents() {
        String sql = "SELECT COUNT(*) FROM student";

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
    public boolean checkOldPassword(int id, String oldPassword) {
        String sql = "SELECT password FROM student WHERE id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                return BCrypt.checkpw(oldPassword, hashedPassword);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean hasEnrollment(int studentId) {
        String sql = "SELECT 1 FROM enrollment WHERE student_id = ? LIMIT 1";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public Student findByEmailOrPhone(String input) {
        String sql = "SELECT * FROM student WHERE email = ? OR phone = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, input);
            ps.setString(2, input);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("dob").toLocalDate(),
                        rs.getString("email"),
                        rs.getBoolean("sex"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getDate("create_at").toLocalDate()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}


