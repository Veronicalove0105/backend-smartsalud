import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDB {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?prepareThreshold=0";
        try (Connection conn = DriverManager.getConnection(url, "postgres.ccigzpqtanxtbrciqctl", "N8Zhcwq9iOpYimOa");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'")) {
            while (rs.next()) {
                System.out.println(rs.getString("table_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
