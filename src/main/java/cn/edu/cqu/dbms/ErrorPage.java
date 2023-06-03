package cn.edu.cqu.dbms;

public class ErrorPage{

    public static void showErrorPage(String info){
        Application.clearScreen();
        System.out.println(info);
        System.out.println("请按回车键返回……");
        Application.scanner.nextLine();
    }
}
