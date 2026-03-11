package BaiTapProject.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EnrollmentView {
        private int enrollmentId;
        private int studentId;
        private String studentName;
        private String studentEmail;
        private int courseId;
        private String courseName;
        private LocalDateTime registeredAt;
        private EnrollmentStatus status;

        public EnrollmentView() {
        }

        public EnrollmentView(int enrollmentId, int studentId, String studentName, String studentEmail,
                              int courseId, String courseName, LocalDateTime registeredAt, EnrollmentStatus status) {
            this.enrollmentId = enrollmentId;
            this.studentId = studentId;
            this.studentName = studentName;
            this.studentEmail = studentEmail;
            this.courseId = courseId;
            this.courseName = courseName;
            this.registeredAt = registeredAt;
            this.status = status;
        }

        public int getEnrollmentId() {
            return enrollmentId;
        }

        public void setEnrollmentId(int enrollmentId) {
            this.enrollmentId = enrollmentId;
        }

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getStudentEmail() {
            return studentEmail;
        }

        public void setStudentEmail(String studentEmail) {
            this.studentEmail = studentEmail;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
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
            System.out.printf("| %-6d | %-18s | %-23s | %-18s | %-18s | %-10s |\n",
                    enrollmentId,
                    studentName,
                    studentEmail,
                    courseName,
                    registeredAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    status.name());
        }
    }

