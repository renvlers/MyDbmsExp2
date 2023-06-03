package cn.edu.cqu.dbms;

import java.sql.*;

public class DeptAdjust{
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
            System.out.println("请输入您要调岗的员工ID：");
            int editID = Application.scanner.nextInt();
            Application.scanner.nextLine();
            ResultSet rstSet2 = stmt
                    .executeQuery("select deptid, jobid from staff where staffid = " + String.valueOf(editID));
            rstSet2.next();
            String oldDeptID = rstSet2.getString("deptid");
            String newDeptID = adjustDept(editID);
            createRecord(editID, oldDeptID, newDeptID);
            showResult(editID);
            System.out.println("请按回车键返回……");
            Application.scanner.nextLine();
        }
    }

    static String adjustDept(int editID) throws SQLException{
        Application.clearScreen();
        System.out.println("请依次输入新部门与新职位名，以空格分割，以回车结束：");
        String ipt = Application.scanner.nextLine();
        String[] deptAndJob = ipt.split((" "));
        String newDept = deptAndJob[0];
        String newJob = deptAndJob[1];
        String newDeptID = getDeptID(newDept);
        String newJobID = getJobID(newJob);
        String sql = "update staff set deptid = " + newDeptID + ", jobid = " + newJobID + " where staffid = "
                + String.valueOf(editID);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        return newDeptID;
    }

    static void createRecord(int editID, String oldDeptID, String newDeptID) throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery("select count(*) from dept_adjustment");
        rst.next();
        String daID = String.valueOf(rst.getInt("count") + 1);
        String staffID = String.valueOf(editID);
        String sql = "insert into dept_adjustment values(" + staffID + ", " + daID + ", " + oldDeptID + ", " + newDeptID
                + ")";
        stmt.executeUpdate(sql);
    }

    static void showResult(int editID) throws SQLException{
        Application.clearScreen();
        System.out.println("调岗完成！以下是修改后的结果：");
        String slsql = "select * from staff where staffid = " + String.valueOf(editID);
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

    static String getDeptID(String deptName) throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery("select deptid from dept where deptname = '" + deptName + "'");
        rst.next();
        String deptid = rst.getString("deptid");
        return deptid;
    }

    static String getJobID(String jobName) throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery("select jobid from job where jobname = '" + jobName + "'");
        rst.next();
        String jobid = rst.getString("jobid");
        return jobid;
    }
}
