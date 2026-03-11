package BaiTapProject.Business.Impl;

import BaiTapProject.Business.IStatisticService;
import BaiTapProject.Dao.ICourseDao;
import BaiTapProject.Dao.IStatisticDao;
import BaiTapProject.Dao.IStudentDao;
import BaiTapProject.Dao.Impl.CourseDaoImpl;
import BaiTapProject.Dao.Impl.StatisticDaoImpl;
import BaiTapProject.Dao.Impl.StudentDaoImpl;
import BaiTapProject.Model.CourseStatistic;

import java.util.List;

public class StatisticServiceImpl implements IStatisticService {

    private final ICourseDao courseDao = new CourseDaoImpl();
    private final IStudentDao studentDao = new StudentDaoImpl();
    private final IStatisticDao statisticDao = new StatisticDaoImpl();

    @Override
    public int countTotalCourses() {
        return courseDao.countTotalCourses();
    }

    @Override
    public int countTotalStudents() {
        return studentDao.countTotalStudents();
    }

    @Override
    public List<CourseStatistic> getStudentsByCourse() {
        return statisticDao.getStudentsByCourse();
    }

    @Override
    public List<CourseStatistic> top5MostStudentsCourses() {
        return statisticDao.top5MostStudentsCourses();
    }

    @Override
    public List<CourseStatistic> coursesMoreThan10Students() {
        return statisticDao.coursesMoreThan10Students();
    }
}


