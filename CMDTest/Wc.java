import java.util.Scanner;

public class Wc {
	static boolean TRUE_OR_NOT = true;
	
	public static void main(String[] args){
		System.out.println("*-------------------WC统计-------------------*");
		System.out.println("基础功能说明：");
		System.out.println("  统计字符数:-c [您要查找的文件路径]");
		System.out.println("  统计词数:  -w [您要查找的文件路径]");
		System.out.println("  统计行数:  -l [您要查找的文件路径]");
		System.out.println("");
		System.out.println("拓展功能说明：");
		System.out.println("  统计空行、代码行、注释行:   	-a [您要查找的文件路径]");
		System.out.println("  递归处理:			-s [您要查找的文件路径]");
		System.out.println("");
		System.out.println("高级功能说明：");
		System.out.println("  图形化界面:-x");
		System.out.println("");
		System.out.println("若想退出程序请输入：退出");
		System.out.println("*--------------------------------------------*");
		System.out.println("请输入指令：");	
		while(TRUE_OR_NOT){
			Scanner command = new Scanner(System.in);
			String[] arr = command.nextLine().split("\\s");
			int len = arr.length;
			Function_Directory FD = new Function_Directory();
			FD.commandProcessor(arr,len,0,arr[arr.length-1]);	//把数据都交给FD对象里面
		}
	}
}
