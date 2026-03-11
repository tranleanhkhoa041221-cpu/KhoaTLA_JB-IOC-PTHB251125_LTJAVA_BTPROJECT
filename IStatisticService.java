package BaiTapProject.Business;

import BaiTapProject.Model.CourseStatistic;

import java.util.List;

public interface IStatisticService {
    int countTotalCourses();

    int countTotalStudents();

    List<CourseStatistic> getStudentsByCourse();

    List<CourseStatistic> top5MostStudentsCourses();

    List<CourseStatistic> coursesMoreThan10Students();
}


