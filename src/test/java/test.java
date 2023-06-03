import cn.edu.cqu.dbms.*;
import java.sql.*;

public class test{

    public static void main(String[] args) throws SQLException{
        GaussDBConnector connector = new GaussDBConnector();
        String username = "db_user2021_231";
        String password = "db_user@123";
        Connection conn = connector.connectToDb(username, password);
        RemoveJob.connect(conn);
        RemoveJob.operate();
    }
}
