import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class testt {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hospital";
        String username = "root";
        String password = "Jothi@2003";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients");

            while (rs.next()) {
                System.out.println("Patient ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

