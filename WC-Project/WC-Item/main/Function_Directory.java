package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Function_Directory {
	public void commandProcessor(String[] arr,int len,int start,String fileName){
		if(arr[0].equals("-x")){						//由于只输入一个元素会报错，“-x”和“退出”先提前判断
			GUI_Frame GF = new GUI_Frame();				//一个图形化界面
			GF.setVisible(true);
		} else if(arr[0].equals("退出"))	{	Wc.TRUE_OR_NOT = false;
		} else{
			try{
				for(int i = start;i < len-1||i == 0;i++){
					String fileUrl = arr[arr.length-1].substring(arr[arr.length-1].lastIndexOf("\\")+1,arr[arr.length-1].lastIndexOf("."));
					System.out.println(fileUrl);
					if(fileUrl.equals("*"))	recursiveProcessor(arr);	//判断通用符号“*”
					else{
						String encoding = "GBK";
						File file = new File(fileName);
						InputStreamReader readFile = new InputStreamReader(new FileInputStream(file),encoding);
						BufferedReader fileContent = new BufferedReader(readFile);			
					switch(arr[i]){
						case "-c":
							charsCounter(fileContent);
							break;
						case "-w":
							wordsCounter(fileContent);
							break;
						case "-l":
							linesCounter(fileContent);
							break;
						case "-a":
							Expand_Count(fileContent);
							break;
						case "-s":
							recursiveProcessor(arr);
							break;
						default:break;
					}
				}}
			}catch(Exception e){
				System.out.println(arr[0]+"不是功能指令喔~,看看是不是哪里输入错误了。");
				e.printStackTrace();
			}
		}
	}
	
	static int charsCounter(BufferedReader fileContent) throws IOException{
		String 	lineCount 	= null;
		int 	charNum 	= 0;
		while((lineCount 	= fileContent.readLine()) != null){
			lineCount 		= lineCount.trim();
			for(int i = 0;i < lineCount.length();i++){
				char ch 	= lineCount.charAt(i);
				if(ch != '\n' && ch != '\t' && ch != ' ')
					charNum++;
			}
		}
		System.out.println("字符数：" + charNum);
		return charNum;
	}
	
	static int wordsCounter(BufferedReader fileContent) throws IOException{
		String 	REGEX 		= "[a-zA-Z]+\\b";		//判断词的正则表达式
		String 	lineCount 	= null;
		int		wordNum 	= 0;
		Pattern pattern 	= Pattern.compile(REGEX);
		while((lineCount = fileContent.readLine()) != null){
				lineCount 	= lineCount.trim();
			Matcher matcher = pattern.matcher(lineCount);
			while(matcher.find()){
				wordNum++;
			}
		}
		System.out.println("词数：" + wordNum);
		return wordNum;
	}
	
	static int linesCounter(BufferedReader fileContent) throws IOException{
		int 	lineNum 	= 0;
		String 	lineCount 	= null;
		while((lineCount = fileContent.readLine()) != null){	lineNum++;	}
		System.out.println("行数：" + lineNum);
		return 	lineNum;
	}
	
	static ArrayList<Integer> Expand_Count(BufferedReader fileContent)throws IOException{
		String 	lineCount 	= null;
		ArrayList<Integer> 	resultList 		= new ArrayList<Integer>();
		boolean 			isComment 		= false;
		int 	codeLineNum 		= 0;
		int 	blankLineNum 		= 0;
		int 	annotationLineNum 	= 0;
		while((lineCount = fileContent.readLine()) != null){			//对注释行的判断
			if(lineCount.contains("/*")){
				annotationLineNum++;
				isComment = true;
			} else if(isComment){
				annotationLineNum++;
				if(lineCount.contains("*/")){	isComment = false;	}
			} else if(lineCount.contains("//")){
				annotationLineNum++;
			} else if(lineCount.trim().length() > 1){
				codeLineNum++;
			} else{
				blankLineNum++;
			}
		}	
		System.out.println("空行数：" + blankLineNum);
		System.out.println("代码行数：" + codeLineNum);
		System.out.println("注释行数：" + annotationLineNum);
		resultList.add(blankLineNum);
		resultList.add(codeLineNum);
		resultList.add(annotationLineNum);
		return resultList;
	}
	
	public void recursiveProcessor(String[] arr) throws IOException{
		int			LA				= arr.length;
		int 		n				= 0;
		String 		fileUrl 		= arr[LA-1].substring(0,arr[LA-1].lastIndexOf("\\"));	//找到文件路径最先出现“\”的位置
		String 		fileEnd 		= arr[LA-1].substring(arr[LA-1].lastIndexOf("."));		//找到文件路径最先出现“.”的位置
        List<File> 	fileList 		= new ArrayList<File>();
        File 		file 			= new File(fileUrl);
        File[] 		files 			= file.listFiles();
        String[]    names			= file.list();
        String[]	CompletNames 	= null;
        for (File f : files) {
        	if (f.isDirectory()){	n++;   	}
        }
        CompletNames				= new String[n];
        n							= 0;
        if (files == null) {}
        for (File f : files) {
            if (f.isFile()&&f.getName().endsWith(fileEnd)) {		fileList.add(f);	} 
            if (f.isDirectory()){
            	CompletNames[n] = arr[LA-1].substring(0,arr[LA-1].lastIndexOf("\\"))+"\\"+f.getName();
            	System.out.println(CompletNames[n]);
            	n++;
           	}
        }
        for (File f1 : fileList) {
        	System.out.println(fileUrl+"\\"+f1.getName());
			String encoding = "GBK";
			InputStreamReader rC = new InputStreamReader(new FileInputStream(f1),encoding);
			InputStreamReader rW = new InputStreamReader(new FileInputStream(f1),encoding);
			InputStreamReader rL = new InputStreamReader(new FileInputStream(f1),encoding);
			InputStreamReader rA = new InputStreamReader(new FileInputStream(f1),encoding);
				BufferedReader C = new BufferedReader(rC);
				BufferedReader W = new BufferedReader(rW);
				BufferedReader L = new BufferedReader(rL);
				BufferedReader A = new BufferedReader(rA);
					charsCounter(C);
					wordsCounter(W);
					linesCounter(L);
					Expand_Count(A);
			C.close();								//我试过只写一个文本输入流，统计结果会累积，数据错误
			W.close();								//由于写入太多输入流，希望想找到更好的办法
			L.close();
			A.close();							
        }
		System.out.println("*-----------------------------*");
		if (CompletNames!=null)	sonProcessor(CompletNames,fileEnd);	
	}

	private void sonProcessor(String[] Names,String End) throws IOException {	
		String[]   		N 				= Names;				//寻找子文件，并把子文件里面符合要求的文件统计出来
		int				LengthA			= N.length;				//这里我运用了递归，但是发现是先递归到最里层才开始统计
		for(int i=0;i<Names.length;i++){
			int 		n				= 0;
			String 		fileUrl 		= N[i];
			String 		fileEnd 		= End;
			List<File> 	fileList 		= new ArrayList<File>();
	        File 		file 			= new File(fileUrl);
	        File[] 		files 			= file.listFiles();
	        String[]    names			= file.list();
	        String[]	CompletNames 	= null;
	        for (File f : files) {
	        	if (f.isDirectory()){	n++;	}
	        }
	        CompletNames				= new String[n];
	        n							=0;  
	        if (files == null) {}
	        for (File f : files) {
	            if (f.isFile()&&f.getName().endsWith(fileEnd)) {		fileList.add(f);	} 
	            if (f.isDirectory()){
	            	CompletNames[n] 	= N[i]+"\\"+f.getName();
	            	System.out.println(CompletNames[n]);
	            	n++;
	           	} 
	        }
	        if (CompletNames!=null)  sonProcessor(CompletNames,fileEnd);
	        for (File f1 : fileList) {
				System.out.println(N[i]+"\\"+f1.getName());
				String encoding = "GBK";
				InputStreamReader rC = new InputStreamReader(new FileInputStream(f1),encoding);
				InputStreamReader rW = new InputStreamReader(new FileInputStream(f1),encoding);
				InputStreamReader rL = new InputStreamReader(new FileInputStream(f1),encoding);
				InputStreamReader rA = new InputStreamReader(new FileInputStream(f1),encoding);
					BufferedReader C = new BufferedReader(rC);
					BufferedReader W = new BufferedReader(rW);
					BufferedReader L = new BufferedReader(rL);
					BufferedReader A = new BufferedReader(rA);
						charsCounter(C);
						wordsCounter(W);
						linesCounter(L);
						Expand_Count(A);
				C.close();
				W.close();
				L.close();
				A.close();
				System.out.println("*-----------------------------*");
	        }
		}
	}
}