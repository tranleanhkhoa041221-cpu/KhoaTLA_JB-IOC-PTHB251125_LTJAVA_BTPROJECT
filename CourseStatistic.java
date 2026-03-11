package BaiTapProject.Model;

public class CourseStatistic {
        private int courseId;
        private String courseName;
        private int totalStudents;

        public CourseStatistic() {
        }

        public CourseStatistic(int courseId, String courseName, int totalStudents) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.totalStudents = totalStudents;
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

        public int getTotalStudents() {
            return totalStudents;
        }

        public void setTotalStudents(int totalStudents) {
            this.totalStudents = totalStudents;
        }

        public void displayData() {
            System.out.printf("| %-6d | %-25s | %-10d |\n",
                    courseId,
                    courseName,
                    totalStudents);
        }
    }

