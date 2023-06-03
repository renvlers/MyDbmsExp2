package cn.edu.cqu.dbms;

import java.sql.*;
import java.util.Scanner;

public class Application{
    public static Scanner scanner = new Scanner(System.in, "GBK");

    public static void clearScreen(){
        try{
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static boolean checkIfConnected(Connection conn){
        if(conn == null)
            return false;
        else
            return true;
    }

    public static void main(String[] args) throws SQLException{
        GaussDBConnector connector = new GaussDBConnector();
        String username = "db_user2021_231";
        String password = "db_user@123";
        Connection conn = connector.connectToDb(username, password);

        while(true){
            clearScreen();

            System.out.println("---------------------欢迎使用企业员工管理系统---------------------");
            if(conn == null)
                System.out.println("当前未连接数据库");
            else
                System.out.println("当前已连接数据库，用户名为：" + username);
            System.out.println("------------------------------------------------------------------");
            System.out.println("请输入您要进行的操作：");
            System.out.println("1. 查找现有员工");
            System.out.println("2. 增加一位新员工");
            System.out.println("3. 删除一位员工");
            System.out.println("4. 修改员工基本信息");
            System.out.println("5. 调整员工的岗位");
            System.out.println("6. 调整员工的薪资");
            System.out.println("7. 发放工资（模拟）");
            System.out.println("8. 添加奖惩记录");
            System.out.println("9. 添加请假记录");
            System.out.println("10. 增加一个部门");
            System.out.println("11. 删除一个部门");
            System.out.println("12. 增加一个职位");
            System.out.println("13. 删除一个职位");
            System.out.println("14. 退出程序");

            int number = scanner.nextInt();

            if(number == 14){
                clearScreen();
                scanner.close();
                System.exit(0);
            }
            else if(number >= 1 && number <= 13){
                if(!checkIfConnected(conn))
                    ErrorPage.showErrorPage("错误：当前未连接数据库！");
                else
                    switch(number){
                    case 1:
                        SelectFrom.connect(conn);
                        SelectFrom.operate();
                        break;
                    case 2:
                        AddNewStaff.connect(conn);
                        AddNewStaff.operate();
                        break;
                    case 3:
                        RemoveStaff.connect(conn);
                        RemoveStaff.operate();
                        break;
                    case 4:
                        EditInfo.connect(conn);
                        EditInfo.operate();
                        break;
                    case 5:
                        DeptAdjust.connect(conn);
                        DeptAdjust.operate();
                        break;
                    case 6:
                        SryAdjust.connect(conn);
                        SryAdjust.operate();
                        break;
                    case 7:
                        SryRecord.connect(conn);
                        SryRecord.operate();
                        break;
                    case 8:
                        RPRecord.connect(conn);
                        RPRecord.operate();
                        break;
                    case 9:
                        LeaveRecord.connect(conn);
                        LeaveRecord.operate();
                        break;
                    case 10:
                        AddNewDept.connect(conn);
                        AddNewDept.operate();
                        break;
                    case 11:
                        RemoveDept.connect(conn);
                        RemoveDept.operate();
                        break;
                    case 12:
                        AddNewJob.connect(conn);
                        AddNewJob.operate();
                        break;
                    case 13:
                        RemoveJob.connect(conn);
                        RemoveJob.operate();
                        break;
                    }

            }
            else
                ErrorPage.showErrorPage("错误：输入无效，请重新输入！");
        }
    }
}