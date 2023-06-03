package cn.edu.cqu.dbms;

import java.sql.*;

public class AddNewDept{
    static Connection conn = null;

    public static void connect(Connection con){
        conn = con;
    }

    static String idGet() throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery("select count(*) from dept");
        rst.next();
        int id = rst.getInt("count") + 1;
        return String.valueOf(id);
    }

    static String nameGet(){
        Application.clearScreen();
        System.out.println("请输入部门名：");
        String name = Application.scanner.nextLine();
        return name;
    }

    public static void operate() throws SQLException{
        if(conn == null)
            ErrorPage.showErrorPage("错误：当前未连接数据库！");
        else{
            String id = idGet();
            String name = nameGet();
            Application.clearScreen();
            String sql = "insert into dept values(" + id + ", '" + name + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("已增加部门：" + name);
            System.out.println("请按回车键返回……");
            Application.scanner.nextLine();
        }
    }

}
