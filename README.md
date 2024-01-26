# Assignment And Exam System

## An internal system that helps teachers to create assignments and exams, and students to work on them

**What will the application do?**

This personal project will let user sign up an account as teacher or student, and then they can log in with the created 
account. As a student, student can view the list of assignments, their types, details and deadlines. They will be able 
to work on them, and depend on the assignments, they can do it in a focused period of time, or a just a normal quiz. 
The students can also view their grade for each assignment, the overall grade for the course and other statistics about 
the course like class size, grade distribution,...

As a teacher, user can add/kick a student out of their class from the student list. Teacher can create an assignment,
edit the assignment to make it a normal quiz or an exam (within a limited period of time and number of attempt). Teacher
can edit student's assignment grade, view the overall class grade, and export the report class data.

**Who will use it**

Anyone who wants to run a class effectively with the help of a system that can track the students' examination, 
assignments and grades. It is recommended for teachers needing post assignments to students and automatically grade the
students' performance, and for students who want to work on computer and a computer-based assignment system.

**Why is this project of interest to you?**

As a student, the idea of working with a computer based assignment system like Canvas has always made me feel curious.
I question myself how such Canvas is constructed, designed and pushed into production, which has changed the world's
teaching method in a positive way. Therefore, I believe with this project, teachers everywhere can apply it to make
their class management more effective, and make students feel easier tracking the assignments' deadline and working on
exams.

A *bulleted* list:
- As a user, I want to be able to log in/ sign up an account as a *teacher or student*
- As a user, I want to be able to view list of assignments, their types and deadlines in **Course**
- As a user, I want to be able to work on the assignments in the **Course List**
- As a user, I want to be able to add  **Grade** to my **Grade List** after finishing an assignment
- As a user, I want to be able to view my grade for the assignments
- As a user, I want to be able to view my overall grades for my courses
- As a user, I want to be able to view the courses that I am taking
- As a user, I want to be able to create a course and add to **Course List** as a teacher
- As a user, I want to be able to add a **list of assignment** to **Course**
- As a user, I want to be able to add/ kick students out of my course as a teacher
- As a user, I want to be able to create new assignment for **Course** as a teacher
- As a user, I want to be able to remove an assignment from **Course** as a teacher
- As a user, I want to be able to view my students' grade from student list in **Course**
- As a user, I want to be able to change a student score from student list in **Course**
- As a user, I want to be able to save my registered account to file
- As a user, I want to be able to save the state of the Course and Teacher to file
- As a user, I want to be able to sign up/log in with the saved account in file
- As a user, I want to be able to load the state of the Course and Teacher from file

User Stories

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by logging into
Teacher account (username: jack , password: 123) and create a new course by the given action button OR go to a course
and click **Add new student** button. You can also view the list of Courses and Students in each course
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by logging into
Teacher account, add new Students to the Course List. The second action can be done by logging into Student account and
work on an Assignment in a Course, which later generates a grade for that student.
- You can locate my visual component by logging into Teacher account, choose a Course with students' grade and click
**Show grade distribution** Button, which displays a pie graph
- You can save the state of my application by signing up a new user, submit an assignment as a student, create new
course or add new student to Course as a Teacher. This requires authentication, therefore every actions will result in
saving state
- You can reload the state of my application by running the application, the state will be automatically loaded to
ensure authentication works (as this is allowed by Professor).

# Phase 4: Task 2

- When a Course is added via a Teacher account (username: jack password: 123), the following will be printed at exit:
  
Date: Thu Nov 30 19:47:54 PST 2023
Description: Course ENG 110(id : 1000 ) added to teacher mike course list.

- When a Teacher added a Student to a specific Course, the following will be printed at exit: 

Date: Thu Nov 30 19:48:01 PST 2023
Description: Student Mike Tyson(id: 1002) added to Course ENG 110

- When a Student submitted an Assignment to a specific Course, the following will be printed at exit:

Date: Thu Nov 30 19:51:36 PST 2023
Description: Student Trung Nguyen (id: 1001) submitted answers for assignment Midterm 1 (id: 1000)

# Phase 4: Task 3

- The desktop app has 2 main classes: Teacher and Student ( both extend abstract class Person).
- The Student class has a list of Grade and Assignment ( the list of Course in Student is a list of Course id)
- The Assignment class has a list of Interface Question which is implemented by LongAnswer and MultipleChoice classes
- The Teacher class has a List of Course, and all the related courses are stored inside a specific teacher
- The Course class has a list of Assignment and a list of Student (They must be unique, not duplicating)
- The Grade class associates with Assignment class
- The Answers class associates with Student and Assignment classes
- The EventLogPrinter does not have any fields, it is used to store a static function to loop through EventLog and
print out all event information, and it depends on EventLog and Event
- The EventLog class has a list of Event, and the EventLog associates with itself
- The JsonWriter depends on Student and Teacher as it will run Student.toJson() and Teacher.toJson() and require those
classes
- The JsonReader depends on the whole Model Package ( depends on every single classes), as it will produce all Course,
Grade, Student, Teacher, Assignment, ... when create objects from Json file.


- If I had more time, I want to refactor the Student class. At first, I put a list of Course as a field and use method
Course.toJson() to convert it into Json, however, Course class also has a list of Student, so it might result in an 
infinite long Json file. As a result, I changed that field of Student into a list of Integer which is Course id. As a 
result, I cannot access the Course's information via a student object. I hope that this will be fixed in the near
future as it will improve the code efficiency.