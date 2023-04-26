import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL {
    public static MySQL _instance = null;
    public Connection connection;

    public Connection getConnection(){
        return this.connection;
    }

    public MySQL(String user, String pass){
        String url = "jdbc:mysql://localhost:3306/prac3";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static MySQL instance(){
        if(_instance == null) _instance = new MySQL("root", "");
        return _instance;
    }
}
