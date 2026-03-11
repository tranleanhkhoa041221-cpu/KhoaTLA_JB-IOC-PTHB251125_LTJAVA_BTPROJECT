package BaiTapProject.Business.Impl;

import BaiTapProject.Business.IAdminService;
import BaiTapProject.Dao.IAdminDao;
import BaiTapProject.Dao.Impl.AdminDaoImpl;
import BaiTapProject.Model.Admin;
import org.mindrot.jbcrypt.BCrypt;

public class AdminServiceImpl implements IAdminService {

    private final IAdminDao adminDao = new AdminDaoImpl();

    @Override
    public Admin findByUsername(String username) {
        return adminDao.findByUsername(username);
    }

    @Override
    public Admin login(String username, String password) {

        Admin admin = adminDao.findByUsername(username);
        if (admin == null) {
            System.out.println("Username không tồn tại.");
            return null;
        }

        if (!BCrypt.checkpw(password, admin.getPassword())) {
            System.out.println("Mật khẩu không đúng.");
            return null;
        }

        return admin;
    }

    @Override
    public boolean changePassword(Admin admin, String oldPass, String newPass) {
        if (!adminDao.checkOldPassword(admin.getId(), oldPass)) {
            System.out.println("Mật khẩu cũ không đúng.");
            return false;
        }

        if (BCrypt.checkpw(newPass, admin.getPassword())) {
            System.out.println("Mật khẩu mới không được trùng mật khẩu cũ.");
            return false;
        }

        if (newPass.length() < 6) {
            System.out.println("Mật khẩu mới phải có ít nhất 6 ký tự.");
            return false;
        }

        String hashed = BCrypt.hashpw(newPass, BCrypt.gensalt(12));
        adminDao.updatePassword(admin.getId(), hashed);
        admin.setPassword(hashed);
        return true;
    }
}
