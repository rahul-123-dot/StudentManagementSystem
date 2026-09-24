import java.sql.*;
import java.util.Scanner;
public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/my_db";
    private static final String userName = "root";
    private static final String password =  "??";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\t======= Student Management System =======\n\n");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, userName, password);
            while (true) {
                System.out.println("1. Add Student");
                System.out.println("2. View All Student");
                System.out.println("3. Search Student");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Exit");

                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                if (choice <= 0 || choice > 6) {
                    System.out.println("error! Invalid Choice");
                    continue;
                }
                if (choice == 1) {
                    System.out.println("Enter Student name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Student Course: ");
                    String course = sc.nextLine();
                    System.out.println("Enter Student Marks: ");
                    double marks = sc.nextDouble();
                    String query = "INSERT INTO students(name, course, marks) VALUES (?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, course);
                    preparedStatement.setDouble(3, marks);
                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0)
                        System.out.println("\nStudent added successfully\n");
                    else System.out.println("\nStudent not added\n");
                }
                else if (choice == 2) {
                    String query = "SELECT * FROM students";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        System.out.println("Name: " + resultSet.getString("name"));
                        System.out.println("Course: " + resultSet.getString("course"));
                        System.out.println("Marks: " + resultSet.getDouble("marks"));
                    }
                }
                else if (choice == 3) {
                    System.out.print("Enter id: ");
                    int id = sc.nextInt();
                    String query = "SELECT * FROM students WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, id);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.next()) {
                        System.out.println("Id: " + rs.getInt("id"));
                        System.out.println("Name: " + rs.getString("name"));
                        System.out.println("Course: " + rs.getString("course"));
                        System.out.println("Marks: " + rs.getDouble("marks"));
                    }
                    else System.out.println("Student Not Found!");
                }
            }

        } catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
