package BaiTapProject.Dao;

import BaiTapProject.Model.CourseStatistic;

import java.util.List;

public interface IStatisticDao {
    List<CourseStatistic> getStudentsByCourse();
    List<CourseStatistic> top5MostStudentsCourses();
    List<CourseStatistic> coursesMoreThan10Students();
}
