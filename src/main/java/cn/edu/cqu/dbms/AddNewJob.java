package cn.edu.cqu.dbms;

import java.sql.*;

public class AddNewJob{
    static Connection conn = null;

    public static void connect(Connection con){
        conn = con;
    }

    static String idGet() throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery("select count(*) from job");
        rst.next();
        int id = rst.getInt("count") + 1;
        return String.valueOf(id);
    }

    static String nameGet(){
        Application.clearScreen();
        System.out.println("请输入职位名：");
        String name = Application.scanner.nextLine();
        return name;
    }

    static String deptGet() throws SQLException{
        Application.clearScreen();
        System.out.println("请输入职位所属部门：");
        String deptname = Application.scanner.nextLine();
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery("select deptid from dept where deptname = '" + deptname + "'");
        rst.next();
        String deptid = rst.getString("deptid");
        return deptid;
    }

    public static void operate() throws SQLException{
        if(conn == null)
            ErrorPage.showErrorPage("错误：当前未连接数据库！");
        else{
            String id = idGet();
            String name = nameGet();
            String dept = deptGet();
            Application.clearScreen();
            String sql = "insert into job values(" + id + ", " + dept + ", '" + name + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("已增加职位：" + name);
            System.out.println("请按回车键返回……");
            Application.scanner.nextLine();
        }
    }

}
