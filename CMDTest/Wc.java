import java.util.Scanner;

public class Wc {
	static boolean TRUE_OR_NOT = true;
	
	public static void main(String[] args){
		System.out.println("*-------------------WCͳ��-------------------*");
		System.out.println("��������˵����");
		System.out.println("  ͳ���ַ���:-c [��Ҫ���ҵ��ļ�·��]");
		System.out.println("  ͳ�ƴ���:  -w [��Ҫ���ҵ��ļ�·��]");
		System.out.println("  ͳ������:  -l [��Ҫ���ҵ��ļ�·��]");
		System.out.println("");
		System.out.println("��չ����˵����");
		System.out.println("  ͳ�ƿ��С������С�ע����:   	-a [��Ҫ���ҵ��ļ�·��]");
		System.out.println("  �ݹ鴦��:			-s [��Ҫ���ҵ��ļ�·��]");
		System.out.println("");
		System.out.println("�߼�����˵����");
		System.out.println("  ͼ�λ�����:-x");
		System.out.println("");
		System.out.println("�����˳����������룺�˳�");
		System.out.println("*--------------------------------------------*");
		System.out.println("������ָ�");	
		while(TRUE_OR_NOT){
			Scanner command = new Scanner(System.in);
			String[] arr = command.nextLine().split("\\s");
			int len = arr.length;
			Function_Directory FD = new Function_Directory();
			FD.commandProcessor(arr,len,0,arr[arr.length-1]);	//�����ݶ�����FD��������
		}
	}
}
