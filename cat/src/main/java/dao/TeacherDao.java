package dao;


import domain.Degree;
import domain.Department;
import domain.ProfTitle;
import domain.Teacher;
import service.DepartmentService;

import java.sql.SQLException;
import java.util.Collection;
import java.util.TreeSet;

public final class TeacherDao {
    private static TeacherDao teacherDao = new TeacherDao();
    private static Collection<Teacher> teachers;

    static {

        try {
            ProfTitle assProf = ProfTitleDao.getInstance().find(2);
            ProfTitle lecture = ProfTitleDao.getInstance().find(3);

            Degree phd = DegreeDao.getInstance().find(1);
            Degree master = DegreeDao.getInstance().find(2);


            Department misDept = DepartmentService.getInstance().find(2);

            teachers = new TreeSet<Teacher>();
            Teacher teacher = new Teacher(1, "02001","苏同", assProf, phd, misDept);
            teachers.add(teacher);
            teachers.add(new Teacher(2, "02002","刘霞", lecture, phd, misDept));
            teachers.add(new Teacher(3, "02004","王方", assProf, phd, misDept));
            teachers.add(new Teacher(4, "02005","刘峰", assProf, master, misDept));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private TeacherDao() {
    }

    public static TeacherDao getInstance() {
        return teacherDao;
    }

    public Collection<Teacher> findAll() throws SQLException{
        return TeacherDao.teachers;
    }

    public Teacher find(Integer id) throws SQLException {
        Teacher desiredTeacher = null;
        for (Teacher teacher : teachers) {
            if (id.equals(teacher.getId())) {
                desiredTeacher = teacher;
                break;
            }
        }
        return desiredTeacher;
    }

    public boolean update(Teacher teacher) throws SQLException{
        teachers.remove(teacher);
        return teachers.add(teacher);
    }

    public boolean add(Teacher teacher) throws SQLException{
        return teachers.add(teacher);
    }

    public boolean delete(Integer id) throws SQLException{
        Teacher teacher = this.find(id);
        return this.delete(teacher);
    }

    public boolean delete(Teacher teacher) throws SQLException{
        return teachers.remove(teacher);
    }


}
