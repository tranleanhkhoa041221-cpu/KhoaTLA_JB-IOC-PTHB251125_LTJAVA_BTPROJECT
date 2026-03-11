package BaiTapProject.Business;

import BaiTapProject.Model.Admin;

public interface IAdminService {
    Admin findByUsername(String username);

    Admin login(String username, String password);

    boolean changePassword(Admin admin, String oldPass, String newPass);
}




