import java.sql.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/student_management";
        String username = "root";
        String password = "??";
        Scanner sc = new Scanner(System.in);
        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Create connection
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("\n=================================");
            System.out.println("   STUDENT MANAGEMENT SYSTEM");
            System.out.println("=================================");
            while (true) {

                System.out.println("\n1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Search Student By Id");
                System.out.println("4. Search Student By Name");
                System.out.println("5. Search Student By Course");
                System.out.println("6. Search Students By Gender");
                System.out.println("7. Update Student");
                System.out.println("8. Delete Student");
                System.out.println("9. Exit");
                System.out.println("10. Sort Student");
                System.out.println("11. Student Statistics");

                System.out.print("\nEnter your choice: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.nextLine();
                    continue;
                }
                int choice = sc.nextInt();
                sc.nextLine();

                // =========================
                // 1. ADD STUDENT
                // =========================
                if (choice == 1) {
                    System.out.print("How Many Student You Want To Enter: ");
                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid! Please enter a number.");
                        sc.nextLine();
                        System.out.print("How Many Student You Want To Enter: ");
                    }
                    int n = sc.nextInt();
                    sc.nextLine();
                    while (n < 0) {
                        System.out.println("Invalid! Please enter a positive number.");
                        System.out.print("How Many Student You Want To Enter: ");
                        n = sc.nextInt();
                        sc.nextLine();
                    }
                    String query = "INSERT INTO students(name, course, marks, age, gender) VALUES (?, ?, ?, ?, ?)";
                    // Using try-with-resources ensures the PreparedStatement is automatically closed
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        for (int i = 1; i <= n; i++) {
                            System.out.println("\n--- Entering Details for Student " + i + " ---");
                            System.out.print("Enter Student Name: ");
                            String name = sc.nextLine();
                            while (name.trim().isEmpty()) {
                                System.out.println("Name cannot be empty!");
                                System.out.print("Enter Student Name: ");
                                name = sc.nextLine();
                            }
                            System.out.print("Enter Student Course: ");
                            String course = sc.nextLine();
                            while (course.trim().isEmpty()) {
                                System.out.println("Course cannot be empty!");
                                System.out.print("Enter Student Course: ");
                                course = sc.nextLine();
                            }
                            System.out.print("Enter Student Marks: ");
                            while (!sc.hasNextDouble()) {
                                System.out.println("Invalid marks! Please enter a number.");
                                sc.nextLine();
                                System.out.print("Enter Student Marks: ");
                            }
                            double marks = sc.nextDouble();
                            sc.nextLine();
                            while (marks < 0 || marks > 100) {
                                System.out.println("Invalid marks! Marks must be between 0 and 100.");
                                System.out.print("Enter Student Marks: ");
                                marks = sc.nextDouble();
                                sc.nextLine();
                            }
                            System.out.print("Enter Student Age: ");
                            while (!sc.hasNextInt()) {
                                System.out.println("Invalid age! Please enter a number.");
                                sc.nextLine();
                                System.out.print("Enter Student Age: ");
                            }
                            int age = sc.nextInt();
                            sc.nextLine();// consume leftover newline
                            while (age <= 0 || age > 100) {
                                System.out.println("Invalid age! Age must be between 1 and 100.");
                                System.out.print("Enter Student Age: ");
                                age = sc.nextInt();
                                sc.nextLine();
                            }
                            System.out.print("Enter Student Gender (Male, Female, Other): ");
                            String gender = sc.nextLine();
                            while (!gender.equalsIgnoreCase("Male")
                                    && !gender.equalsIgnoreCase("Female")
                                    && !gender.equalsIgnoreCase("Other")) {
                                System.out.println("Invalid gender!");
                                System.out.print("Enter Student Gender (Male, Female, Other): ");
                                gender = sc.nextLine();
                            }
                            // Bind values to placeholders
                            preparedStatement.setString(1, name);
                            preparedStatement.setString(2, course);
                            preparedStatement.setDouble(3, marks);
                            preparedStatement.setInt(4, age); // Fixed: setInt instead of setDouble
                            preparedStatement.setString(5, gender);
                            // Add the parameters to the batch execution
                            preparedStatement.addBatch(); // Fixed: No argument inside addBatch()
                        }
                        // Execute all batched inserts at once
                        int[] arr = preparedStatement.executeBatch();
                        System.out.println();
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i] > 0 || arr[i] == PreparedStatement.SUCCESS_NO_INFO) {
                                System.out.println("Student " + (i + 1) + " inserted successfully!");
                            }
                        }
                    }
                }
                // =========================
                // 2. VIEW ALL STUDENTS
                // =========================
                else if (choice == 2) {
                    String query = "SELECT * FROM students";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    System.out.println("--------------------------------------------------------------------------------");
                    System.out.printf(
                            "%-5s %-20s %-20s %-10s %-5s %-20s%n",
                            "ID", "NAME", "COURSE", "MARKS", "AGE", "Gender"
                    );
                    System.out.println("--------------------------------------------------------------------------------");
                    boolean found = false;
                    while (resultSet.next()) {
                        found = true;
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String course = resultSet.getString("course");
                        double marks = resultSet.getDouble("marks");
                        int age = resultSet.getInt("age");
                        String gender = resultSet.getString("gender");
                        System.out.printf("%-5d %-20s %-20s %-10.2f %-5d %-20s%n",
                                id, name, course, marks, age, gender
                        );
                    }
                    if (!found) System.out.println("No students found.");
                    resultSet.close();
                    statement.close();
                }
                // =========================
                // 3. SEARCH STUDENT BY ID
                // =========================
                else if (choice == 3) {
                    System.out.print("Enter Student ID: ");
                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid id! Please enter a number.");
                        sc.nextLine();
                        System.out.print("Enter Student ID: ");
                    }
                    int id = sc.nextInt();
                    sc.nextLine();
                    while (id <= 0) {
                        System.out.println("Invalid ! please enter a valid id number");
                        System.out.print("Enter Student Id: ");
                        id = sc.nextInt();
                        sc.nextLine();
                    }
                    String query = "SELECT * FROM students WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        System.out.println("\nStudent Found!");
                        System.out.println("-------------------------");
                        System.out.println("ID     : " + resultSet.getInt("id"));
                        System.out.println("Name   : " + resultSet.getString("name"));
                        System.out.println("Course : " + resultSet.getString("course"));
                        System.out.println("Marks  : " + resultSet.getDouble("marks"));
                        System.out.println("Age    : " + resultSet.getInt("age"));
                        System.out.println("Gender : " + resultSet.getString("gender"));
                    } else System.out.println("\nStudent Not Found!");
                    resultSet.close();
                    preparedStatement.close();
                }
                // =========================
                // 4. SEARCH STUDENT BY NAME
                // =========================
                else if (choice == 4) {
                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();
                    String query = "SELECT * FROM students WHERE name lIKE ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, "%" + name + "%");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    boolean flag = false;
                    while (resultSet.next()) {
                        flag = true;
                        System.out.println("\nStudent Found!");
                        System.out.println("-------------------------");
                        System.out.println("ID     : " + resultSet.getInt("id"));
                        System.out.println("Name   : " + resultSet.getString("name"));
                        System.out.println("Course : " + resultSet.getString("course"));
                        System.out.println("Marks  : " + resultSet.getDouble("marks"));
                        System.out.println("Age    : " + resultSet.getInt("age"));
                        System.out.println("Gender : " + resultSet.getString("gender"));
                    }
                    if (!flag) System.out.println("\nStudent Not Found!");
                    resultSet.close();
                    preparedStatement.close();
                }
                // =========================
                // 5. SEARCH STUDENT BY Course
                // =========================
                else if (choice == 5) {
                    System.out.print("Enter Student Course: ");
                    String course = sc.nextLine();
                    String query = "SELECT * FROM student WHERE course = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, "%" + course + "%");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    boolean flag = false;
                    while (resultSet.next()) {
                        flag = true;
                        System.out.println("\nStudent Found!");
                        System.out.println("-------------------------");
                        System.out.println("ID     : " + resultSet.getInt("id"));
                        System.out.println("Name   : " + resultSet.getString("name"));
                        System.out.println("Course : " + resultSet.getString("course"));
                        System.out.println("Marks  : " + resultSet.getDouble("marks"));
                        System.out.println("Age    : " + resultSet.getInt("age"));
                        System.out.println("Gender : " + resultSet.getString("gender"));
                    }
                    if (!flag) System.out.println("\nStudent Not Found!");
                    resultSet.close();
                    preparedStatement.close();
                }
                // =========================
                // 6. SEARCH STUDENT BY Gender
                // =========================
                else if (choice == 6) {
                    System.out.print("Enter Student Gender (Male, Female, Other): ");
                    String gender = sc.nextLine();
                    String query = "SELECT * FROM students WHERE gender = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, gender);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    boolean flag = false;
                    while (resultSet.next()) {
                        flag = true;
                        System.out.println("\nStudent Found!");
                        System.out.println("-------------------------");
                        System.out.println("ID     : " + resultSet.getInt("id"));
                        System.out.println("Name   : " + resultSet.getString("name"));
                        System.out.println("Course : " + resultSet.getString("course"));
                        System.out.println("Marks  : " + resultSet.getDouble("marks"));
                        System.out.println("Age    : " + resultSet.getInt("age"));
                        System.out.println("Gender  : " + resultSet.getString("gender"));
                    }
                    if (!flag) System.out.println("\nStudent Not Found!");
                    resultSet.close();
                    preparedStatement.close();
                }
                // =========================
                // 5. UPDATE STUDENT
                // =========================
                else if (choice == 7) {
                    System.out.print("Enter Student ID: ");
                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid id! Please enter a number.");
                        sc.nextLine();
                        System.out.print("Enter Student ID: ");
                    }
                    int id = sc.nextInt();
                    sc.nextLine();
                    while (id <= 0) {
                        System.out.println("Invalid ! please enter a valid id number");
                        System.out.print("Enter Student Id: ");
                        id = sc.nextInt();
                        sc.nextLine();
                    }
                    System.out.print("Enter New Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter New Course: ");
                    String course = sc.nextLine();
                    System.out.print("Enter New Marks: ");
                    while (!sc.hasNextDouble()) {
                        System.out.println("Invalid marks! Please enter a number.");
                        sc.nextLine();
                        System.out.print("Enter Student Marks: ");
                    }
                    double marks = sc.nextDouble();
                    sc.nextLine();
                    while (marks < 0 || marks > 100) {
                        System.out.println("Invalid marks! Marks must be between 0 and 100.");
                        System.out.print("Enter Student Marks: ");
                        marks = sc.nextDouble();
                        sc.nextLine();
                    }
                    System.out.print("Enter New Age: ");
                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid age! Please enter a number.");
                        sc.nextLine();
                        System.out.print("Enter Student Age: ");
                    }
                    int age = sc.nextInt();
                    sc.nextLine();
                    while (age <= 0 || age > 100) {
                        System.out.println("Invalid age! Age must be between 1 and 100.");
                        System.out.print("Enter Student Age: ");
                        age = sc.nextInt();
                        sc.nextLine();
                    }
                    System.out.print("Enter Student Gender (Male, Female, Other): ");
                    String gender = sc.nextLine();
                    while (!gender.equalsIgnoreCase("Male")
                            && !gender.equalsIgnoreCase("Female")
                            && !gender.equalsIgnoreCase("Other")) {
                        System.out.println("Invalid gender!");
                        System.out.print("Enter Student Gender (Male, Female, Other): ");
                        gender = sc.nextLine();
                    }
                    String query = "UPDATE students SET name = ?, course = ?, marks = ?, age = ?, gender = ? WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, course);
                    preparedStatement.setDouble(3, marks);
                    preparedStatement.setInt(4, age);
                    preparedStatement.setString(5, gender);
                    preparedStatement.setInt(6, id);
                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0)
                        System.out.println("\nStudent Updated Successfully!");
                    else System.out.println("\nStudent Not Found!");
                    preparedStatement.close();
                }
                // =========================
                // 6. DELETE STUDENT
                // =========================
                else if (choice == 8) {
                    System.out.print("Enter Student ID: ");
                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid id! Please enter a number.");
                        sc.nextLine();
                        System.out.print("Enter Student ID: ");
                    }
                    int id = sc.nextInt();
                    sc.nextLine();
                    while (id <= 0) {
                        System.out.println("Invalid ! please enter a valid id number");
                        System.out.print("Enter Student Id: ");
                        id = sc.nextInt();
                        sc.nextLine();
                    }
                    System.out.print("Are you sure you want to delete this student? (yes/no): ");
                    String confirmation = sc.nextLine();
                    if (confirmation.equalsIgnoreCase("yes")) {
                        String query = "DELETE FROM students WHERE id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, id);
                        int rowAffected = preparedStatement.executeUpdate();
                        if (rowAffected > 0)
                            System.out.println("\nStudent Deleted Successfully!");
                        else System.out.println("\nStudent Not Found!");
                        preparedStatement.close();
                    }
                    else System.out.println("Delete cancelled.");
                }
                // =========================
                // 7. EXIT
                // =========================
                else if (choice == 9) {
                    System.out.println("\n=================================");
                    System.out.println("       Thank You!");
                    System.out.println("       Goodbye!");
                    System.out.println("=================================");
                    break;
                }
                // =========================
                // 7. SORT
                // =========================
                else if (choice == 10) {
                    System.out.println("1. Sort By Name");
                    System.out.println("2. Sort By Age");
                    System.out.println("3. Sort By Marks");
                    System.out.print("Enter Your Choice: ");
                    int choicee = sc.nextInt();
                    sc.nextLine();
                    if (choicee == 1) {
                        String query = "SELECT * FROM students  ORDER BY name ASC";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            System.out.println("-------------------------");
                            System.out.println("ID     : " + resultSet.getInt("id"));
                            System.out.println("Name   : " + resultSet.getString("name"));
                            System.out.println("Course : " + resultSet.getString("course"));
                            System.out.println("Marks  : " + resultSet.getDouble("marks"));
                            System.out.println("Age    : " + resultSet.getInt("age"));
                            System.out.println("Gender : " + resultSet.getString("gender"));
                        }
                        resultSet.close();
                        preparedStatement.close();
                    }
                    else if (choicee == 2) {
                        String query = "SELECT * FROM students ORDER BY age ASC";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            System.out.println("-------------------------");
                            System.out.println("ID     : " + resultSet.getInt("id"));
                            System.out.println("Name   : " + resultSet.getString("name"));
                            System.out.println("Course : " + resultSet.getString("course"));
                            System.out.println("Marks  : " + resultSet.getDouble("marks"));
                            System.out.println("Age    : " + resultSet.getInt("age"));
                            System.out.println("Gender : " + resultSet.getString("gender"));
                        }
                        resultSet.close();
                        preparedStatement.close();
                    }
                    else if (choicee == 3) {
                        String query = "SELECT * FROM students ORDER BY marks ASC";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            System.out.println("-------------------------");
                            System.out.println("ID     : " + resultSet.getInt("id"));
                            System.out.println("Name   : " + resultSet.getString("name"));
                            System.out.println("Course : " + resultSet.getString("course"));
                            System.out.println("Marks  : " + resultSet.getDouble("marks"));
                            System.out.println("Age    : " + resultSet.getInt("age"));
                            System.out.println("Gender : " + resultSet.getString("gender"));
                        }
                        resultSet.close();
                        preparedStatement.close();
                    }
                    else {
                        System.out.println("Invalid Choice!!");
                    }
                }
                // =========================
                // 11. Student Statistics
                // =========================
                else if (choice == 11) {
                    String query = "SELECT COUNT(*) as total, AVG(marks) AS average, MAX(marks) AS highest, MIN(marks) AS lowest FROM students";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.next()) {
                        System.out.println("-------------- Student Statistics -----\n");
                        System.out.println("Total Student: " + rs.getInt("total"));
                        System.out.println("Average Marks: " + rs.getDouble("average"));
                        System.out.println("Highest Marks: " + rs.getDouble("highest"));
                        System.out.println("Lowest Marks: " + rs.getDouble("lowest"));

                    }
                    else System.out.println("Data Not Found!");
                    rs.close();
                    preparedStatement.close();
                }
                // =========================
                // INVALID CHOICE
                // =========================
                else System.out.println("\nInvalid choice! Please enter 1-6.");
            }
            connection.close();
            sc.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error occurred!");
            e.printStackTrace();
        }
    }
}
