# University Management System (UMS)

## Program Description

This program implements a simple University Management System (UMS) designed to manage students and their enrolled learning courses.

### Classes Overview

- **UMS**  
  The main management system class that holds a list of `Student` objects. It provides functionality to add students and print detailed student data.

- **Student**  
  Represents a university student with two key properties: `name` and `studentId`. Each student maintains a list of `Course` objects representing their enrolled learning courses.

- **Course**  
  Represents a learning course, with the following string fields:
  - `title`
  - `acceptancePrerequisites`
  - `majorTopics`  
  Each field has associated getter and setter methods for data encapsulation and manipulation.

### Functional Requirements

- The UMS class manages multiple students.
- Each student can enroll in multiple courses.
- The `printStudentData(Student student)` method prints the student’s basic information followed by detailed information on each enrolled course.
- If the student matches the specified identity ("lizi", student ID "0245484588"), the exact learning courses (from Argus) are returned to meet the requirement.

## Package and Folder Structure

All classes belong to the package:

src/oop/finalexam/t2/


## UML Diagram

Below is a simple textual UML diagram representation for clarity:

+-------------------+
|       UMS         |  <<public class>>
+-------------------+
| - students: List<Student> |
+-------------------+
| + addStudent(student: Student): void  |
| + printStudentData(student: Student): void |
+-------------------+

          1
          |
          *
+-------------------+
|     Student       |  <<class>>
+-------------------+
| - name: String    |
| - studentId: String|
| - courses: List<Course> |
+-------------------+
| + addCourse(course: Course): void |
| + getName(): String       |
| + getStudentId(): String  |
| + getCourses(): List<Course> |
+-------------------+

          1
          |
          *
+-----------------------------+
|           Course            |  <<class>>
+-----------------------------+
| - title: String             |
| - acceptancePrerequisites: String |
| - majorTopics: String       |
+-----------------------------+
| + getTitle(): String        |
| + setTitle(title: String): void |
| + getAcceptancePrerequisites(): String |
| + setAcceptancePrerequisites(prereq: String): void |
| + getMajorTopics(): String  |
| + setMajorTopics(majorTopics: String): void |
+-----------------------------+



