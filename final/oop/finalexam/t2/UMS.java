package oop.finalexam.t2;


import java.util.ArrayList;
import java.util.List;

public class UMS {
    private List<Student> students;

    public UMS() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void printStudentData(Student student) {
        System.out.println("Student Name: " + student.getName());
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Enrolled Courses:");
        for (Course course : student.getCourses()) {
            System.out.println("--------------------------------");
            System.out.println(course.toString());
        }
    }

    public static void main(String[] args) {
        UMS ums = new UMS();

        Student me = new Student("lizi", "0245484588");

        me.addCourse(new Course("Calculus 2", "Calculus 1", "Integration techniques, Antiderivatives, Improper integrals and series"));
        me.addCourse(new Course("Mathematical Foundation of Computing", "Calculus 1, CS50 Introduction to Programming", "Logic, sets, functions, proofs, Elements of Combinatorics"));
        me.addCourse(new Course("Object Oriented Programming", "CS50 Introduction to Programming", "Java, Classes, Inheritance, Polymorphism"));
        me.addCourse(new Course("English C1-2", "English C1-1", "Academic writing, formal communication, speaking"));
        me.addCourse(new Course("Computer Organization", "CS50 Introduction to Programming", "Java syntax and data structures, Procedural programming, Encapsulation, polymorphism, inheritance, Packages"));

        ums.addStudent(me);
        ums.printStudentData(me);
    }
}
