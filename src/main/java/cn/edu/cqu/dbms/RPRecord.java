package cn.edu.cqu.dbms;

import java.sql.*;
import java.time.LocalDate;

public class RPRecord{
    static Connection conn = null;

    public static void connect(Connection con){
        conn = con;
    }

    public static void operate() throws SQLException{
        if(conn == null)
            ErrorPage.showErrorPage("错误：当前未连接数据库！");
        else{
            Application.clearScreen();
            String slsql = "select * from staff";
            Statement stmt = conn.createStatement();
            ResultSet rstSet = stmt.executeQuery(slsql);
            System.out.println("当前员工列表如下：");
            while(rstSet.next()){
                String id = rstSet.getString("staffid");
                String name = rstSet.getString("staffname");
                String gender = rstSet.getString("staffgender");

                // 获取部门
                Statement dpt = conn.createStatement();
                String qry1 = "select deptname from dept where deptid = " + rstSet.getString("deptid");
                ResultSet dptset = dpt.executeQuery(qry1);
                dptset.next();
                String deptname = dptset.getString("deptname");

                // 获取职位
                Statement job = conn.createStatement();
                String qry2 = "select jobname from job where jobid = " + rstSet.getString("jobid");
                ResultSet jobset = job.executeQuery(qry2);
                jobset.next();
                String jobname = jobset.getString("jobname");

                String phone = rstSet.getString("staffphone");
                String email = rstSet.getString("staffemail");

                Date birthdate = rstSet.getDate("staffbirthdate");
                Date entrydate = rstSet.getDate("staffentrydate");

                int salary = rstSet.getInt("staffsalary");

                String graduate = rstSet.getString("staffgraduate");
                String major = rstSet.getString("staffmajor");
                String acadequal = rstSet.getString("staffacadequal");

                System.out.println("------------------------------------------------------------------");
                System.out.println("ID：" + id);
                System.out.println("姓名：" + name);
                System.out.println("性别：" + gender);
                System.out.println("部门：" + deptname);
                System.out.println("职位：" + jobname);
                System.out.println("电话：" + phone);
                System.out.println("邮箱：" + email);
                System.out.println("出生日期：" + birthdate);
                System.out.println("入职日期：" + entrydate);
                System.out.println("工资：" + salary);
                System.out.println("毕业院校：" + graduate);
                System.out.println("主修专业：" + major);
                System.out.println("学历：" + acadequal);
            }
            System.out.println("------------------------------------------------------------------");
            System.out.println("请输入您要授予奖惩的员工ID：");
            int editID = Application.scanner.nextInt();
            Application.scanner.nextLine();
            String staffID = String.valueOf(editID);

            giveRP(staffID);
            showResult(staffID);

            System.out.println("请按回车键返回……");
            Application.scanner.nextLine();
        }
    }

    static void giveRP(String staffID) throws SQLException{
        String roRP = "";
        while(true){
            Application.clearScreen();
            System.out.println("请选择奖惩类型：");
            System.out.print("1. 奖励\n2. 惩罚\n");
            int choice = Application.scanner.nextInt();
            Application.scanner.nextLine();
            if(choice == 1){
                roRP = "奖励";
                break;
            }
            else if(choice == 2){
                roRP = "惩罚";
                break;
            }
            else
                ErrorPage.showErrorPage("错误：输入无效，请重新输入！");
        }
        Application.clearScreen();
        System.out.println("请输入奖惩原因：");
        String rpReason = Application.scanner.nextLine();
        LocalDate date = LocalDate.now();
        String currentDate = date.toString();
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery("select count(*) from rp");
        rst.next();
        String rpID = String.valueOf(rst.getInt("count") + 1);
        String sql = "insert into rp values(" + staffID + ", " + rpID + ", '" + roRP + "', '" + currentDate + "', '"
                + rpReason + "')";
        stmt.executeUpdate(sql);
    }

    static void showResult(String staffID) throws SQLException{
        Application.clearScreen();
        System.out.println("操作完成，现有奖惩记录如下：");
        Statement stmt = conn.createStatement();
        String sql = "select * from rp where staffid = " + staffID;
        ResultSet rst = stmt.executeQuery(sql);
        while(rst.next()){
            String rpID = rst.getString("rpid");
            String roRP = rst.getString("rorp");
            String rpDate = rst.getString("rpdate");
            String rpReason = rst.getString("rpreason");

            System.out.println("------------------------------------------------------------------");
            System.out.println("奖惩ID：" + rpID);
            System.out.println("奖惩类型：" + roRP);
            System.out.println("奖惩日期：" + rpDate);
            System.out.println("奖惩原因：" + rpReason);
        }
        System.out.println("------------------------------------------------------------------");
    }
}
