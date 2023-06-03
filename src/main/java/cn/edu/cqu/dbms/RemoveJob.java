package cn.edu.cqu.dbms;

import java.sql.*;

public class RemoveJob{
    static Connection conn = null;

    public static void connect(Connection con){
        conn = con;
    }

    public static void operate() throws SQLException{
        if(conn == null)
            ErrorPage.showErrorPage("错误：当前未连接数据库！");
        else{
            Application.clearScreen();
            String slsql = "select * from job";
            Statement stmt = conn.createStatement();
            ResultSet rstSet = stmt.executeQuery(slsql);
            System.out.println("当前职位列表如下：");
            while(rstSet.next()){
                String id = rstSet.getString("jobid");
                String name = rstSet.getString("jobname");

                // 获取部门
                Statement dpt = conn.createStatement();
                String qry1 = "select deptname from dept where deptid = " + rstSet.getString("deptid");
                ResultSet dptset = dpt.executeQuery(qry1);
                dptset.next();
                String deptname = dptset.getString("deptname");

                System.out.println("------------------------------------------------------------------");
                System.out.println("ID：" + id);
                System.out.println("职位名称：" + name);
                System.out.println("所属部门：" + deptname);
            }
            System.out.println("------------------------------------------------------------------");
            System.out.println("请输入您要删除的职位ID：");
            int delID = Application.scanner.nextInt();
            Application.scanner.nextLine();
            Application.clearScreen();
            String sql = "delete from job where jobid = " + String.valueOf(delID);
            stmt.executeUpdate(sql);
            System.out.println("已删除职位：ID" + String.valueOf(delID));
            System.out.println("请按回车键返回……");
            Application.scanner.nextLine();
        }
    }
}
