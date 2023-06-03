package cn.edu.cqu.dbms;

import java.sql.*;

public class GaussDBConnector{
    static final String JDBC_DRIVER = "com.huawei.opengauss.jdbc.Driver";
    static final String DB_URL = "jdbc:opengauss://110.41.126.115:8000/CQU_DB2021";

    public Connection connectToDb(String user, String pass){
        Connection conn = null;
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("连接数据库……");
            conn = DriverManager.getConnection(DB_URL, user, pass);
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    public Connection disconnect(){
        Connection conn = null;
        return conn;
    }
}