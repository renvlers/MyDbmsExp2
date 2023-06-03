package cn.edu.cqu.dbms;

import java.sql.*;

public class SryAdjust{
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
            System.out.println("请输入您要调薪的员工ID：");
            int editID = Application.scanner.nextInt();
            Application.scanner.nextLine();
            String staffID = String.valueOf(editID);
            ResultSet rstSet2 = stmt.executeQuery("select staffsalary from staff where staffid = " + staffID);
            rstSet2.next();
            String oldSry = rstSet2.getString("staffsalary");
            String newSry = adjustSalary(staffID);
            createRecord(staffID, oldSry, newSry);
            showResult(staffID);
            System.out.println("请按回车键返回……");
            Application.scanner.nextLine();
        }
    }

    static String adjustSalary(String staffID) throws SQLException{
        Application.clearScreen();
        System.out.println("请输入调整后的工资：");
        int ipt = Application.scanner.nextInt();
        Application.scanner.nextLine();
        String newSalary = String.valueOf(ipt);
        String sql = "update staff set staffsalary = " + newSalary + "where staffid = " + staffID;
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        return newSalary;
    }

    static void createRecord(String staffID, String oldSalary, String newSalary) throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery("select count(*) from salary_adjustment");
        rst.next();
        String saID = String.valueOf(rst.getInt("count") + 1);
        String sql = "insert into salary_adjustment values(" + staffID + ", " + saID + ", " + oldSalary + ", "
                + newSalary + ")";
        stmt.executeUpdate(sql);
    }

    static void showResult(String staffID) throws SQLException{
        Application.clearScreen();
        System.out.println("调薪完成！以下是修改后的结果：");
        String slsql = "select * from staff where staffid = " + staffID;
        Statement stmt = conn.createStatement();
        ResultSet rstSet = stmt.executeQuery(slsql);
        rstSet.next();
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
        System.out.println("------------------------------------------------------------------");
    }
}
