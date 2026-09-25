import java.sql.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/student_management";
        String username = "root";
        String password = "#deadman!!";
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
                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Student Course: ");
                    String course = sc.nextLine();
                    System.out.print("Enter Student Marks: ");
                    double marks = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter Student Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Student Gender (Male, Female, Other): ");
                    String gender = sc.nextLine();
                    String query = "INSERT INTO students(name, course, marks, age, gender) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, course);
                    preparedStatement.setDouble(3, marks);
                    preparedStatement.setDouble(4, age);
                    preparedStatement.setString(5, gender);
                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0)
                        System.out.println("\nStudent added successfully!");
                    else System.out.println("\nStudent not added.");
                    preparedStatement.close();
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
                    int id = sc.nextInt();
                    sc.nextLine();
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
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter New Course: ");
                    String course = sc.nextLine();
                    System.out.print("Enter New Marks: ");
                    double marks = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter New Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Student Gender (Male, Female, Other): ");
                    String gender = sc.nextLine();
                    String query = "UPDATE students SET name = ?, course = ?, marks = ?, age = ?, gender = ? WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, course);
                    preparedStatement.setDouble(3, marks);
                    preparedStatement.setInt(4, age);
                    preparedStatement.setString(5, course);
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
                    int id = sc.nextInt();
                    sc.nextLine();
                    String query = "DELETE FROM students WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, id);
                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0)
                        System.out.println("\nStudent Deleted Successfully!");
                    else System.out.println("\nStudent Not Found!");
                    preparedStatement.close();
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
