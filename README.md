Student Management System
A console-based Student Management System built with Java, JDBC, and MySQL. The application allows users to manage student records through a menu-driven interface.
Features
- Add students — insert one or multiple student records.
- View all students — display the stored student records.
- Search students by ID, name, course, or gender.
- Update student details — edit a student's name, course, marks, age, and gender.
- Delete students — remove a record after confirmation.
- Sort students by name, age, or marks.
- View student statistics — calculate total students, average marks, highest marks, and lowest marks.
- Input validation for fields such as marks, age, gender, and menu choices.
- PreparedStatement for parameterized SQL queries and batch insertion when adding multiple students.
  Technologies Used
- Java
- JDBC (Java Database Connectivity)
- MySQL
- MySQL Connector/J
- IntelliJ IDEA (development environment)
  Database Setup
1. Make sure MySQL Server is installed and running.
2. Create the database:
   CREATE DATABASE student_management;
   USE student_management;
3. Create the students table:
   CREATE TABLE students (
   id INT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(100) NOT NULL,
   course VARCHAR(100) NOT NULL,
   marks DOUBLE NOT NULL,
   age INT NOT NULL,
   gender VARCHAR(20) NOT NULL
   );
   Configuration
   In Main.java, configure the JDBC URL and your local MySQL username. Keep your database password private.
   String url = "jdbc:mysql://localhost:3306/student_management";
   String username = "YOUR_MYSQL_USERNAME";
   String password = "YOUR_MYSQL_PASSWORD";
   Replace the example username and password with your local MySQL credentials. Do not commit real passwords or other secrets to GitHub.

Make sure MySQL Connector/J is added to the project's dependencies/classpath. If you use Maven, you can add the MySQL Connector/J dependency to pom.xml; otherwise, add the driver JAR to your project libraries.
How to Run
1. Clone or download this repository.
2. Open the project in your Java IDE.
3. Ensure the MySQL database and students table have been created.
4. Configure the JDBC URL, username, and password in Main.java.
5. Add MySQL Connector/J to the project's classpath.
6. Run Main.java.
7. Choose an option from the menu and follow the prompts.
   Concepts Practiced
- Java classes and control flow
- Loops and conditional statements
- User input with Scanner
- Exception handling
- JDBC database connectivity
- SQL CRUD operations (INSERT, SELECT, UPDATE, and DELETE)
- PreparedStatement, ResultSet, and batch processing
- Aggregate functions (COUNT, AVG, MAX, and MIN)
- Sorting records with SQL ORDER BY
  Future Improvements
- Separate the application into model, DAO, service, and UI layers.
- Use try-with-resources consistently for JDBC resources.
- Add stronger validation and clearer error messages.
- Move database credentials to environment variables or a configuration file excluded from version control.
- Add automated tests and a graphical or web interface.
  Author
  Rahul
  This project was created for learning and practicing Java, JDBC, and MySQL database operations.