package cn.edu.cqu.dbms;

import java.sql.*;

public class RemoveDept{
    static Connection conn = null;

    public static void connect(Connection con){
        conn = con;
    }

    public static void operate() throws SQLException{
        if(conn == null)
            ErrorPage.showErrorPage("错误：当前未连接数据库！");
        else{
            Application.clearScreen();
            String slsql = "select * from dept";
            Statement stmt = conn.createStatement();
            ResultSet rstSet = stmt.executeQuery(slsql);
            System.out.println("当前部门列表如下：");
            while(rstSet.next()){
                String id = rstSet.getString("deptid");
                String name = rstSet.getString("deptname");

                System.out.println("------------------------------------------------------------------");
                System.out.println("ID：" + id);
                System.out.println("部门名称：" + name);
            }
            System.out.println("------------------------------------------------------------------");
            System.out.println("请输入您要删除的部门ID：");
            int delID = Application.scanner.nextInt();
            Application.scanner.nextLine();
            Application.clearScreen();
            String sql = "delete from dept where deptid = " + String.valueOf(delID);
            stmt.executeUpdate(sql);
            System.out.println("已删除部门：ID" + String.valueOf(delID));
            System.out.println("请按回车键返回……");
            Application.scanner.nextLine();
        }
    }
}
