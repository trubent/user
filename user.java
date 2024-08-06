import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class User {
    private String firstName;
    private String lastName;
    private String middleName;

    public User(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getMiddleName() { return middleName; }
}

class UserComparator<T extends User> implements Comparator<T> {
    @Override
    public int compare(T u1, T u2) {
        return u1.getLastName().compareTo(u2.getLastName());
    }
}

interface UserView<T extends User> {
    void sendOnConsole(List<T> users);
}

class StudentView implements UserView<Student> {
    @Override
    public void sendOnConsole(List<Student> students) {
        for (Student student : students) {
            System.out.println(student.getFirstName() + " " + student.getLastName());
        }
    }
}

class Student extends User {
    public Student(String firstName, String lastName, String middleName) {
        super(firstName, lastName, middleName);
    }
}

interface UserController<T extends User> {
    void create(T user);
}

class StudentController implements UserController<Student> {
    private List<Student> students = new ArrayList<>();
    private StudentView studentView = new StudentView();

    @Override
    public void create(Student student) {
        students.add(student);
        studentView.sendOnConsole(students);
    }
}

class Teacher extends User {
    public Teacher(String firstName, String lastName, String middleName) {
        super(firstName, lastName, middleName);
    }
}

class TeacherService {
    private List<Teacher> teachers = new ArrayList<>();

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void editTeacher(int index, Teacher teacher) {
        if (index >= 0 && index < teachers.size()) {
            teachers.set(index, teacher);
        }
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }
}

class TeacherView implements UserView<Teacher> {
    @Override
    public void sendOnConsole(List<Teacher> teachers) {
        for (Teacher teacher : teachers) {
            System.out.println(teacher.getFirstName() + " " + teacher.getLastName());
        }
    }
}

class TeacherController implements UserController<Teacher> {
    private TeacherService teacherService = new TeacherService();
    private TeacherView teacherView = new TeacherView();

    @Override
    public void create(Teacher teacher) {
        teacherService.addTeacher(teacher);
        teacherView.sendOnConsole(teacherService.getTeachers());
    }

    public void editTeacher(int index, Teacher teacher) {
        teacherService.editTeacher(index, teacher);
        teacherView.sendOnConsole(teacherService.getTeachers());
    }

    public void displayTeachers() {
        teacherView.sendOnConsole(teacherService.getTeachers());
    }
}

public class Main {
    public static void main(String[] args) {
        StudentController studentController = new StudentController();
        studentController.create(new Student("John", "Doe", "Middle"));

        TeacherController teacherController = new TeacherController();
        teacherController.create(new Teacher("Jane", "Smith", "Middle"));
        teacherController.editTeacher(0, new Teacher("Jane", "Doe", "Middle"));
        teacherController.displayTeachers();
    }
}
