package cn.edu.cqu.dbms;

import java.sql.*;

public class EditInfo{
    static Connection conn = null;

    public static void connect(Connection con){
        conn = con;
    }

    static boolean isNumeric(String str){
        for(char c: str.toCharArray()){
            if(!Character.isDigit(c)){
                return false; // 如果字符不是数字，则返回 false
            }
        }
        return true; // 所有字符都是数字，返回 true
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
            System.out.println("请输入您要修改信息的员工ID：");
            int editID = Application.scanner.nextInt();
            Application.scanner.nextLine();
            editList(editID);
            Application.clearScreen();
            System.out.println("修改完成！");
            System.out.println("请按回车键返回……");
            Application.scanner.nextLine();
        }
    }

    static void editList(int editID) throws SQLException{
        while(true){
            Application.clearScreen();
            System.out.print("1. 修改姓名\n2. 修改电话号码\n3. 修改电子邮箱\n");
            int choice = Application.scanner.nextInt();
            Application.scanner.nextLine();
            if(choice == 1){
                editName(editID);
                return;
            }

            else if(choice == 2){
                editPhone(editID);
                return;
            }

            else if(choice == 3){
                editEmail(editID);
                return;
            }
            else
                ErrorPage.showErrorPage("错误：输入无效，请重新输入！");
        }
    }

    static void editName(int editID) throws SQLException{
        Application.clearScreen();
        System.out.println("请输入修改后的名字：");
        String newName = Application.scanner.nextLine();
        String sql = "update staff set staffname = '" + newName + "' where staffid = " + String.valueOf(editID);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }

    static void editPhone(int editID) throws SQLException{
        while(true){
            Application.clearScreen();
            System.out.println("请输入修改后的电话号码：");
            String newPhone = Application.scanner.nextLine();
            String sql = "update staff set staffphone = '" + newPhone + "' where staffid = " + String.valueOf(editID);
            if(newPhone.length() != 11 || !isNumeric(newPhone))
                ErrorPage.showErrorPage("错误：输入无效，请重新输入！");
            else{
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                return;
            }
        }
    }

    static void editEmail(int editID) throws SQLException{
        while(true){
            Application.clearScreen();
            System.out.println("请输入修改后的电子邮箱：");
            String newEmail = Application.scanner.nextLine();
            String sql = "update staff set staffemail = '" + newEmail + "' where staffid = " + String.valueOf(editID);
            if(!newEmail.contains(Character.toString('@')))
                ErrorPage.showErrorPage("错误：输入无效，请重新输入！");
            else{
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                return;
            }
        }
    }
}
