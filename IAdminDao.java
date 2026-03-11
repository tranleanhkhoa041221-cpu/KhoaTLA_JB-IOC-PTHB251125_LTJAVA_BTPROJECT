package BaiTapProject.Dao;

import BaiTapProject.Model.Admin;

import java.util.List;

public interface IAdminDao {
    List<Admin> findAll();

    Admin findById(int id);

    Admin findByUsername(String username);

    void insert(Admin admin);

    void update(Admin admin);

    void delete(int id);

    List<Admin> paginate(int page, int size);

    boolean checkOldPassword(int id, String oldPassword);


    void updatePassword(int id, String newPassword);

}


