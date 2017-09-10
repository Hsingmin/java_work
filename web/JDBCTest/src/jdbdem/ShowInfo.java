package jdbdem;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  

public class ShowInfo {  
    private static final String SQLEXCEPTION =   
        "jdbc:mysql://127.0.0.1:3306/mydb?mytable=root&password=";  
      
    private static final String SQL = "jdbc:mysql://127.0.0.1/mydb?";  
    private static final String USER = "root";  
    private static final String PASSWORD = "";  
    public static void main(String[] args) throws Exception {  
        System.out.println("userId info:");  
        getInfoFromDatabase(SQL, USER, PASSWORD, 1);  
        System.out.println("==================================");  
        System.out.println("userName info:");  
        getInfoFromDatabase(SQL, USER, PASSWORD, 2);  
          
        // ���쳣��Ϣ��Access denied for user ''@'localhost' to database 'mydb'  
        getInfoFromDatabase(SQLEXCEPTION, 1);  
    }  
    /** 
     * ��ȡ���ݿ��б��������Ϣ 
     *  
     * @param sql 
     *            sql��� 
     * @param user 
     *            �û��� 
     * @param password 
     *            �û����� 
     * @param columnIndex 
     *            �С�������.1 ������userId��2 ������userName 
     */  
    public static void getInfoFromDatabase(String sql, String user, String password, int columnIndex) {  
        Connection conn = null;  
        Statement statement = null;  
        ResultSet result = null;  
        try {  
            //Class.forName("com.mysql.jdbc.Driver").newInstance();  
            Class.forName("com.mysql.jdbc.Driver");//������Driver  
            conn = DriverManager.getConnection(sql, user, password);  
            statement = conn.createStatement();  
            result = statement.executeQuery("select * from mytable");  
            while (result.next()) {  
                System.out.println("userName= " + result.getString(columnIndex));  
            }  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (result != null) {  
                    result.close();  
                    result = null;  
                }  
                if (statement != null) {  
                    statement.close();  
                    statement = null;  
                }  
                if (conn != null) {  
                    conn.close();  
                    conn = null;  
                }  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    /** 
     * ��ȡ���ݿ��б��������Ϣ,���ط��� 
     *  
     * @param sql 
     *            sql��� 
     * @param columnIndex 
     *            �С�������.1 ������userId��2 ������userName 
     */  
    public static void getInfoFromDatabase(String sql, int columnIndex) {  
        Connection conn = null;  
        Statement statement = null;  
        ResultSet result = null;  
        try {  
            //Class.forName("com.mysql.jdbc.Driver").newInstance();  
            Class.forName("com.mysql.jdbc.Driver");  
            conn = DriverManager.getConnection(sql);  
            statement = conn.createStatement();  
            result = statement.executeQuery("select * from mytable");  
            while (result.next()) {  
                System.out.println("userName= " + result.getString(columnIndex));  
            }  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (result != null) {  
                    result.close();  
                    result = null;  
                }  
                if (statement != null) {  
                    statement.close();  
                    statement = null;  
                }  
                if (conn != null) {  
                    conn.close();  
                    conn = null;  
                }  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}  