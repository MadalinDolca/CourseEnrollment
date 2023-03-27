# 📚 Course Enrollment

Spring Boot web app that allows students to enroll to courses and to be evaluated by the teachers assigned to the respective courses.

## 🌟 Features

- User authentication
- JWT authorization
- Role-based access (student, teacher, admin)
- Students can enroll or abandon courses
- Students can view the courses they are enrolled to and their grades
- Teachers can evaluate the students enrolled in each of the courses they teach or are assigned to
- Administrators can view a list of all users according to their roles and the courses they are active in
- Administrators can add or delete courses
- Administrators can manage the existing users' data, change their role, delete their accounts or to create a new user
- Administrators can assign teachers to each existing course and to enroll or remove students

## 🔮 Technologies

- Spring Boot
  - Spring Web
  - Spring Data JPA
  - Spring Security
- Kotlin
- MySQL
  - MySQL Connector
- Thymeleaf
- Lombok
- ModelMapper
- JWT
- jQuery
- Bootstrap

## 👀 Preview

| Login | Register |
| :-: | :-: |
| <img src="art/login.png" width="500px"/> | <img src="art/register.png" width="500px"/> |

### Student

| Profile | Available courses |
| :-: | :-: |
| <img src="art/profile.png" width="500px"/> | <img src="art/student_available_courses.png" width="500px"/> |
| Enrollments | Grades |
| <img src="art/student_enrollments.png" width="500px"/> | <img src="art/student_grades.png" width="500px"/> |

### Teacher

| Taught courses | Enrolled students to course |
| :-: | :-: |
| <img src="art/teacher_taught_courses.png" width="500px"/> | <img src="art/teacher_enrolled_students_to_course.png" width="500px"/> |
| Add/change grade |
| <img src="art/teacher_add_change_grade.png" width="500px"/> |

### Admin

| View students | View teachers |
| :-: | :-: |
| <img src="art/admin_view_students.png" width="500px"/> | <img src="art/admin_view_teachers.png" width="500px"/> |
| Add user | Edit user |
| <img src="art/admin_add_user.png" width="500px"/> | <img src="art/admin_edit_user.png" width="500px"/> |
| Manage student enrollments | Manage teacher courses |
| <img src="art/admin_manage_student_enrollments.png" width="500px"/> | <img src="art/admin_manage_teacher_courses.png" width="500px"/> |
| View courses | |
| <img src="art/admin_view_courses.png" width="500px"/> | |
| View enrolled students to course | View assigned teachers to course |
| <img src="art/admin_view_enrolled_students_to_course.png" width="500px"/> | <img src="art/admin_view_assigned_teachers_to_course.png" width="500px"/> |

