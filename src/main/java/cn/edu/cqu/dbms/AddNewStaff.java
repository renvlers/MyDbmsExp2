package cn.edu.cqu.dbms;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddNewStaff{
    static Connection conn = null;

    public static void connect(Connection con){
        conn = con;
    }

    static boolean isValidDate(String inputDate, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try{
            dateFormat.parse(inputDate);
            return true;
        }
        catch(ParseException e){
            return false;
        }
    }

    static boolean isNumeric(String str){
        for(char c: str.toCharArray()){
            if(!Character.isDigit(c)){
                return false; // 如果字符不是数字，则返回 false
            }
        }
        return true; // 所有字符都是数字，返回 true
    }

    static String idGet() throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery("select count(*) from staff");
        rst.next();
        int id = rst.getInt("count") + 1;
        return String.valueOf(id);
    }

    static String nameGet(){
        Application.clearScreen();
        System.out.println("请输入姓名：");
        String name = Application.scanner.nextLine();
        return name;
    }

    static String genderGet(){
        while(true){
            Application.clearScreen();
            System.out.println("请选择性别：");
            System.out.print("1. 男\n2. 女\n");
            int choice = Application.scanner.nextInt();
            Application.scanner.nextLine();
            if(choice == 1)
                return "男";
            else if(choice == 2)
                return "女";
            else
                ErrorPage.showErrorPage("错误：输入无效，请重新输入！");
        }
    }

    static String deptGet() throws SQLException{
        Application.clearScreen();
        System.out.println("请输入部门：");
        String deptname = Application.scanner.nextLine();
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery("select deptid from dept where deptname = '" + deptname + "'");
        rst.next();
        String deptid = rst.getString("deptid");
        return deptid;
    }

    static String jobGet() throws SQLException{
        Application.clearScreen();
        System.out.println("请输入职位：");
        String jobname = Application.scanner.nextLine();
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery("select jobid from job where jobname = '" + jobname + "'");
        rst.next();
        String jobid = rst.getString("jobid");
        return jobid;
    }

    static String phoneGet(){
        while(true){
            Application.clearScreen();
            System.out.println("请输入电话号码：");
            String phone = Application.scanner.nextLine();
            if(phone.length() != 11 || !isNumeric(phone))
                ErrorPage.showErrorPage("错误：输入无效，请重新输入！");
            else
                return phone;
        }
    }

    static String emailGet(){
        while(true){
            Application.clearScreen();
            System.out.println("请输入电子邮箱：");
            String email = Application.scanner.nextLine();
            if(!email.contains(Character.toString('@')))
                ErrorPage.showErrorPage("错误：输入无效，请重新输入！");
            else
                return email;
        }
    }

    static String birthdateGet(){
        while(true){
            Application.clearScreen();
            System.out.println("请输入出生日期，使用 YYYY-MM-DD 格式：");
            String birthdate = Application.scanner.nextLine();
            String format = "yyyy-MM-dd";
            if(!isValidDate(birthdate, format))
                ErrorPage.showErrorPage("错误：输入无效，请重新输入！");
            else
                return birthdate;
        }
    }

    static String entrydateGet(){
        while(true){
            Application.clearScreen();
            System.out.println("请输入入职日期，使用 YYYY-MM-DD 格式：");
            String entrydate = Application.scanner.nextLine();
            String format = "yyyy-MM-dd";
            if(!isValidDate(entrydate, format))
                ErrorPage.showErrorPage("错误：输入无效，请重新输入！");
            else
                return entrydate;
        }
    }

    static String salaryGet(){
        Application.clearScreen();
        System.out.println("请输入工资：");
        int salary = Application.scanner.nextInt();
        Application.scanner.nextLine();
        return String.valueOf(salary);
    }

    static String graduateGet(){
        Application.clearScreen();
        System.out.println("请输入毕业院校：");
        String graduate = Application.scanner.nextLine();
        return graduate;
    }

    static String majorGet(){
        Application.clearScreen();
        System.out.println("请输入主修专业：");
        String major = Application.scanner.nextLine();
        return major;
    }

    static String acadequalGet(){
        while(true){
            Application.clearScreen();
            System.out.println("请选择学历：");
            System.out.print("1. 本科\n2. 硕士研究生\n3. 博士研究生\n");
            int choice = Application.scanner.nextInt();
            Application.scanner.nextLine();
            if(choice == 1)
                return "本科";
            else if(choice == 2)
                return "硕士研究生";
            else if(choice == 3)
                return "博士研究生";
            else
                ErrorPage.showErrorPage("错误：输入无效，请重新输入！");
        }
    }

    public static void operate() throws SQLException{
        if(conn == null)
            ErrorPage.showErrorPage("错误：当前未连接数据库！");
        else{
            String id = idGet();
            String name = nameGet();
            String gender = genderGet();
            String deptid = deptGet();
            String jobid = jobGet();
            String phone = phoneGet();
            String email = emailGet();
            String birthdate = birthdateGet();
            String entrydate = entrydateGet();
            String salary = salaryGet();
            String graduate = graduateGet();
            String major = majorGet();
            String acadequal = acadequalGet();
            Application.clearScreen();
            String sql = "insert into staff values(" + id + ", " + deptid + ", " + jobid + ", " + "'" + name + "'"
                    + ", " + "'" + gender + "'" + ", " + phone + ", " + "'" + email + "'" + ", " + "'" + birthdate + "'"
                    + ", " + "'" + entrydate + "'" + ", " + salary + ", " + "'" + graduate + "'" + ", " + "'" + major
                    + "'" + ", " + "'" + acadequal + "'" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("已增加员工：" + name);
            System.out.println("请按回车键返回……");
            Application.scanner.nextLine();
        }
    }
}
