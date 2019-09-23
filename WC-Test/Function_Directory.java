package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Function_Directory {
	public void commandProcessor(String[] arr,int len,int start,String fileName){
		if(arr[0].equals("-x")){
			WcGui WG = new WcGui();
			WG.go();
		} else {
			try{
				for(int i = start;i < len-1||i == 0;i++){
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
							recursiveProcessing(arr);
							break;
						default:break;
					}
				}
			}catch(Exception e){
				System.out.println(arr[0]+"不是功能指令喔~,看看是不是哪里输入错误了。");
				e.printStackTrace();
			}
		}
	}
	
	static int charsCounter(BufferedReader fileContent) throws IOException{
		String lineContent = null;
		int charNum = 0;
		while((lineContent = fileContent.readLine()) != null){
			lineContent = lineContent.trim();
			for(int i = 0;i < lineContent.length();i++){
				char ch = lineContent.charAt(i);
				if(ch != '\n' && ch != '\t' && ch != ' ')
					charNum++;
			}
		}
		System.out.println("字符数：" + charNum);
		return charNum;
	}
	
	static int wordsCounter(BufferedReader fileContent) throws IOException{
		String REGEX = "[a-zA-Z]+\\b";
		Pattern pattern = Pattern.compile(REGEX);
		String lineContent = null;
		int wordNum = 0;
		while((lineContent = fileContent.readLine()) != null){
			lineContent = lineContent.trim();
			Matcher matcher = pattern.matcher(lineContent);
			while(matcher.find()){
				wordNum++;
			}
		}
		System.out.println("词数：" + wordNum);
		return wordNum;
	}
	
	static int linesCounter(BufferedReader fileContent) throws IOException{
		String 	lineContent 	= null;
		int 	lineNum 		= 0;
		while((lineContent = fileContent.readLine()) != null){	lineNum++;	}
		System.out.println("行数：" + lineNum);
		return 	lineNum;
	}
	
	static ArrayList<Integer> Expand_Count(BufferedReader fileContent)throws IOException{
		ArrayList<Integer> 	resultList 		= new ArrayList<Integer>();
		String 				lineContent 	= null;
		boolean 			isComment 		= false;
		int 				codeLineNum 	= 0;
		int 				blankLineNum 	= 0;
		int 				annotationLineNum = 0;
		while((lineContent = fileContent.readLine()) != null){
			if(lineContent.contains("/*")){
				annotationLineNum++;
				isComment = true;
			} else if(isComment){
				annotationLineNum++;
				if(lineContent.contains("*/")){	isComment = false;	}
			} else if(lineContent.contains("//")){
				annotationLineNum++;
			} else if(lineContent.trim().length() > 1){
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
	
	public void recursiveProcessing(String[] arr) throws IOException{
		String 		fileDir 		= arr[arr.length-1].substring(0,arr[arr.length-1].lastIndexOf("\\"));
		String 		fileFilter 		= arr[arr.length-1].substring(arr[arr.length-1].lastIndexOf("."));
        List<File> 	fileList 		= new ArrayList<File>();
        File 		file 			= new File(fileDir);
        File[] 		files 			= file.listFiles();
        if (files == null) {	return;		}
        for (File f : files) {
            if (f.isFile()&&f.getName().endsWith(fileFilter)) {		fileList.add(f);	} 
        }
        for (File f1 : fileList) {
			System.out.println(f1.getName());
			for(int i = 0;i < 4;i++){
				String encoding = "GBK";
				InputStreamReader readFile = new InputStreamReader(new FileInputStream(f1),encoding);
				BufferedReader fileContent = new BufferedReader(readFile);
				switch(i){
					case 0:
						charsCounter(fileContent);
						break;
					case 1:
						wordsCounter(fileContent);
						break;
					case 2:
						linesCounter(fileContent);
						break;
					case 3:
						Expand_Count(fileContent);
						break;
				}
			}
			System.out.println("-----------------------------");
        }
	}
}