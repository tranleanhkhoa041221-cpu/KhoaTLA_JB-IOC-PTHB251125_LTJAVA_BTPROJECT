package BaiTapProject.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Enrollment {
    private int id;
    private int studentId;
    private int courseId;
    private LocalDateTime registeredAt;
    private EnrollmentStatus status;

    public Enrollment() {
    }

    public Enrollment(int id, int studentId, int courseId,
                      LocalDateTime registeredAt, EnrollmentStatus status) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.registeredAt = registeredAt;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public void displayData() {
        System.out.printf("| %-6d | %-10d | %-10d | %-20s | %-10s |%n",
                id,
                studentId,
                courseId,
                registeredAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                status.name()
        );
    }
}


