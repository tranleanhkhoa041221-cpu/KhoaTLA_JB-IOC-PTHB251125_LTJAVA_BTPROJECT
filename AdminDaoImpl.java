package BaiTapProject.Dao.Impl;

import BaiTapProject.Dao.IAdminDao;
import BaiTapProject.Model.Admin;
import BaiTapProject.Utils.ConnectionDB;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoImpl implements IAdminDao {
    @Override
    public List<Admin> findAll() {
        List<Admin> list = new ArrayList<>();
        String sql = "SELECT * FROM admin ORDER BY id ASC";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Admin(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Admin findById(int id) {
        String sql = "SELECT * FROM admin WHERE id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Admin findByUsername(String username) {
        String sql = "SELECT * FROM admin WHERE username = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Admin admin) {
        String sql = "INSERT INTO admin(username, password) VALUES (?, ?)";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Admin admin) {
        String sql = "UPDATE admin SET username = ?, password = ? WHERE id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());
            ps.setInt(3, admin.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM admin WHERE id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Admin> paginate(int page, int size) {
        List<Admin> list = new ArrayList<>();
        String sql = "SELECT * FROM admin ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, size);
            ps.setInt(2, (page - 1) * size);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Admin(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean checkOldPassword(int id, String oldPassword) {
        String sql = "SELECT password FROM admin WHERE id = ?";

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
    public void updatePassword(int id, String newPassword) {
        String sql = "UPDATE admin SET password = ? WHERE id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
