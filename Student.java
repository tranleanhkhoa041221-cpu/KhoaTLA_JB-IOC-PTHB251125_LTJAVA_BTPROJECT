package BaiTapProject.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Student {
    private int id;
    private String name;
    private LocalDate dob;
    private String email;
    private boolean sex;
    private String phone;
    private String password;
    private LocalDate createdAt;

    public Student() {
    }

    public Student(int id, String name, LocalDate dob, String email, boolean sex,
                   String phone, String password, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.password = password;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void displayData() {
        System.out.printf("| %-6d | %-20s | %-15s | %-20s | %-5s | %-12s | %-15s |%n",
                id,
                name,
                dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                email,
                sex ? "Nam" : "Nữ",
                phone,
                createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
    }
}
