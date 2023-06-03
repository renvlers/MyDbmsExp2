package cn.edu.cqu.dbms;

import java.sql.*;

public class SelectFrom{
    static Connection conn = null;

    public static void connect(Connection con){
        conn = con;
    }

    static void showResult(String sql) throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rstSet = stmt.executeQuery(sql);
        System.out.println("查询结果如下：");
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
    }

    static void selectByName() throws SQLException{
        Application.clearScreen();
        System.out.println("请输入您要查找的员工姓名，以空格分割，以回车结束：");
        String ipt = Application.scanner.nextLine();
        String[] names = ipt.split((" "));
        String sql = "select * from staff where ";
        for(int i = 0; i < names.length; i++){
            sql += ("staffname = " + "'" + names[i] + "'");
            if(i != names.length - 1)
                sql += " or ";
        }
        showResult(sql);
        System.out.println("请按回车键返回……");
        Application.scanner.nextLine();
    }

    static void selectBySalary() throws SQLException{
        Application.clearScreen();
        System.out.println("请依次输入最低工资和最高工资，以空格分割，以回车结束：");
        String ipt = Application.scanner.nextLine();
        String[] salaries = ipt.split((" "));
        String sql = "select * from staff where ";
        sql += "staffsalary >= " + salaries[0] + " and staffsalary <= " + salaries[1];
        showResult(sql);
        System.out.println("请按回车键返回……");
        Application.scanner.nextLine();
    }

    static void selectTheFull() throws SQLException{
        Application.clearScreen();
        String sql = "select * from staff";
        showResult(sql);
        System.out.println("请按回车键返回……");
        Application.scanner.nextLine();
    }

    public static void operate() throws SQLException{
        if(conn == null)
            ErrorPage.showErrorPage("错误：当前未连接数据库！");
        else{
            while(true){
                Application.clearScreen();
                System.out.println("---------------------请选择查找方式---------------------");
                System.out.println("1. 按姓名查找");
                System.out.println("2. 按工资查找");
                System.out.println("3. 查询所有员工");

                int number = Application.scanner.nextInt();
                Application.scanner.nextLine();

                switch(number){
                case 1:
                    selectByName();
                    return;
                case 2:
                    selectBySalary();
                    return;
                case 3:
                    selectTheFull();
                    return;
                default:
                    ErrorPage.showErrorPage("错误：输入无效，请重新输入！");
                    break;
                }
            }
        }
    }
}
