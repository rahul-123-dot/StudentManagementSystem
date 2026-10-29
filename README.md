# Student Management System

A console-based Student Management System built using Java, JDBC, and MySQL. This project helps manage student records through a menu-driven interface.

## Features

- Add one or multiple students
- View all student records
- Search students by ID, name, course, and gender
- Update student details
- Delete student records
- Sort students by name, age, or marks
- Display student statistics:
    - Total number of students
    - Average marks
    - Highest marks
    - Lowest marks
- Input validation
- Batch insertion using JDBC

## Technologies Used

- Java
- JDBC (Java Database Connectivity)
- MySQL
- MySQL Connector/J
- IntelliJ IDEA

## Database Setup

Create the database in MySQL:

```sql
CREATE DATABASE student_management;
USE student_management;
```

Create the students table:

```sql
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    course VARCHAR(100) NOT NULL,
    marks DOUBLE NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(20) NOT NULL
);
```

## Configuration

Configure your MySQL connection in `Main.java`:

```java
String url = "jdbc:mysql://localhost:3306/student_management";
String username = "YOUR_MYSQL_USERNAME";
String password = "YOUR_MYSQL_PASSWORD";
```

Replace the example credentials with your own MySQL credentials.

**Important:** Never upload your actual database password to GitHub.

## How to Run

1. Clone or download this repository.
2. Open the project in IntelliJ IDEA.
3. Start your MySQL server.
4. Create the database and students table.
5. Configure your MySQL username and password.
6. Add MySQL Connector/J to the project.
7. Run `Main.java`.
8. Select an option from the menu.

## Concepts Practiced

- Java fundamentals and OOP
- Exception handling
- JDBC connectivity
- SQL CRUD operations
- PreparedStatement and ResultSet
- Batch processing
- SQL sorting and aggregate functions
- User input validation

## Future Improvements

- Add a graphical user interface
- Improve project structure using separate classes
- Add automated testing
- Improve database credential management

## Author

Rahul

---

This project was developed to practice Java, JDBC, SQL, and database operations.