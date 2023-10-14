import java.sql.*;

public class main {
    public static void main(String[] args) throws Exception{
        String url = "jdbc:postgresql://localhost:5432/dvdrental";
        String username = "postgres";
        String password = "9814072356";
        String sql = "select * from actor where first_name='Nick'";
        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();
        //st.executeQuery(sql);
        ResultSet rs = st.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
    }

}
