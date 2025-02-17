package org.fastcampus.student_management;

import org.fastcampus.student_management.application.course.CourseService;
import org.fastcampus.student_management.application.course.dto.CourseInfoDto;
import org.fastcampus.student_management.application.student.StudentService;
import org.fastcampus.student_management.application.student.dto.StudentInfoDto;
import org.fastcampus.student_management.repo.CourseRepository;
import org.fastcampus.student_management.repo.StudentRepository;
import org.fastcampus.student_management.ui.course.CourseController;
import org.fastcampus.student_management.ui.course.CoursePresenter;
import org.fastcampus.student_management.ui.student.StudentController;
import org.fastcampus.student_management.ui.student.StudentPresenter;
import org.fastcampus.student_management.ui.UserInputType;

public class Main {

  public static void main(String[] args) {
    StudentRepository studentRepository = new StudentRepository();
    CourseRepository courseRepository = new CourseRepository();

    StudentService studentService = new StudentService(studentRepository);
    CourseService courseService = new CourseService(courseRepository, studentService);

    CoursePresenter coursePresenter = new CoursePresenter();
    StudentPresenter studentPresenter = new StudentPresenter();

    CourseController courseController = new CourseController(coursePresenter, courseService, studentPresenter);
    StudentController studentController = new StudentController(studentPresenter, studentService);

    // 기본 dummy값
    StudentInfoDto s1 = new StudentInfoDto("홍길동", 20, "서울시 강남구");
    StudentInfoDto s2 = new StudentInfoDto("김길동", 21, "서울시 노원구");
    StudentInfoDto s3 = new StudentInfoDto("강길동", 22, "서울시 용산구");
    studentService.saveStudent(s1); studentService.saveStudent(s2); studentService.saveStudent(s3);

    CourseInfoDto c1 = new CourseInfoDto("바이올린", 1500, "MONDAY", "홍길동", 1717299008L);
    CourseInfoDto c2 = new CourseInfoDto("첼로", 1200, "SUNDAY", "강길동", 1717299008L);
    CourseInfoDto c3 = new CourseInfoDto("피아노", 1100, "FRIDAY", "김길동", 1717299008L);
    courseService.registerCourse(c1); courseService.registerCourse(c2); courseService.registerCourse(c3);

    studentPresenter.showMenu();
    UserInputType userInputType = studentController.getUserInput();
    while (userInputType != UserInputType.EXIT) {
      switch (userInputType) {
        case NEW_STUDENT:
          studentController.registerStudent();
          break;
        case NEW_COURSE:
          courseController.registerCourse();
          break;
        case SHOW_COURSE_DAY_OF_WEEK:
          courseController.showCourseDayOfWeek();
          break;
        case ACTIVATE_STUDENT:
          studentController.activateStudent();
          break;
        case DEACTIVATE_STUDENT:
          studentController.deactivateStudent();
          break;
        case CHANGE_FEE:
          courseController.changeFee();
          break;
        default:
          studentPresenter.showErrorMessage();
          break;
      }
      studentPresenter.showMenu();
      userInputType = studentController.getUserInput();
    }
  }
}