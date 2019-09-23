import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class GUI_Frame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	Toolkit 					kit 				= Toolkit.getDefaultToolkit();
	Dimension 					screenSize 			= kit.getScreenSize();
	static  JTextArea 			textArea 			= new JTextArea();
	private JPanel    			chooseBar			= new JPanel();
	private JButton				count_c		 		= new JButton("�ַ���");
	private JButton				count_w 			= new JButton("����");
	private JButton				count_l	 			= new JButton("����");
	private JButton				count_a				= new JButton("��չ����");
	private JButton				count_all			= new JButton("�ܽ�");
	private JButton				count_close			= new JButton("�ر�");
	private Font 				buttonFont 			= new Font("����",Font.BOLD,25);
	int 						charNum 			= 0;
	int 						wordNum 			= 0;
	int 						lineNum 			= 0;
	int 						blankLineNum 		= 0;
	int 						codeLineNum 		= 0;
	int 						annotationLineNum 	= 0;
	int 						Type				= 0;
	ArrayList<Integer> 			resultList 			= null;
	
	
	GUI_Frame(){
		setTitle("WCͳ��");
		setSize(2*screenSize.width/4,3*screenSize.height/4);			
		setLocation(2*screenSize.width/4,screenSize.height/8);
		initEventListeners();   		
		add(chooseBar,BorderLayout.NORTH);
		initChooseBar();
    	initTextArea();
	}
	
	private void initChooseBar(){												//����ѡ��������
		chooseBar.add(count_c);
		chooseBar.add(count_w);
		chooseBar.add(count_l);
		chooseBar.add(count_a);
		chooseBar.add(count_all);
		chooseBar.add(count_close);
        count_c.setFont(buttonFont);
        count_w.setFont(buttonFont);
        count_l.setFont(buttonFont);
        count_a.setFont(buttonFont);
        count_all.setFont(buttonFont);
        count_close.setFont(buttonFont);
    }
	
	private void initTextArea(){
        textArea.setFont(new Font("����", Font.PLAIN, 30));
        textArea.setMargin(new Insets(3,10,3,10));
        textArea.setLineWrap(true);
        textArea.setDragEnabled(true);
        JScrollPane panel = new JScrollPane(textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(panel, BorderLayout.CENTER);
    }
	
	private void initEventListeners(){
		count_c.addActionListener(		this::countC	);
		count_w.addActionListener(		this::countW	);
		count_l.addActionListener(		this::countL	);
		count_a.addActionListener(		this::countA	);
		count_all.addActionListener(	this::countAll	);
		count_close.addActionListener(	this::countClose);
	}
	
	private void countC(ActionEvent event){
		Type = 1;
		CountStart();
	}
	private void countW(ActionEvent event){
		Type = 2;
		CountStart();
	}
	private void countL(ActionEvent event){
		Type = 3;
		CountStart();
	}
	private void countA(ActionEvent event){
		Type = 4;
		CountStart();
	}
	private void countAll(ActionEvent event){
		Type = 5;
		CountStart();
	}
	private void countClose(ActionEvent event){
		Type = 0;
		System.exit(0);
	}
	
	public void CountStart(){
		JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.showDialog(new JLabel(), "ѡ��Ҫͳ�Ƶ��ļ�");
        File file = chooser.getSelectedFile();
        try{
			String encoding = "GBK";
			InputStreamReader readFile = new InputStreamReader(new FileInputStream(file),encoding);
			BufferedReader fileContent = new BufferedReader(readFile);
			switch(Type){
			case 1:
				charNum = Function_Directory.charsCounter(fileContent);
				textArea.append(file+":\n"+"�ַ�����" + charNum + "\n\n");
				break;
			case 2:
				wordNum = Function_Directory.wordsCounter(fileContent);
				textArea.append(file+":\n������" + wordNum+"\n\n");
				break;
			case 3:
				lineNum = Function_Directory.linesCounter(fileContent);
				textArea.append(file+":\n������" + lineNum+"\n\n");
				break;
			case 4:
				resultList = Function_Directory.Expand_Count(fileContent);
				blankLineNum = resultList.get(0);
				codeLineNum = resultList.get(1);
				annotationLineNum = resultList.get(2);
				textArea.append(file+":\n��������" + blankLineNum + "\n����������" 
						+ codeLineNum + "\nע������" + annotationLineNum+"\n\n");
				break;
			case 5:
				charNum = Function_Directory.charsCounter(fileContent);
				wordNum = Function_Directory.wordsCounter(fileContent);
				lineNum = Function_Directory.linesCounter(fileContent);
				resultList = Function_Directory.Expand_Count(fileContent);
				blankLineNum = resultList.get(0);
				codeLineNum = resultList.get(1);
				annotationLineNum = resultList.get(2);
				textArea.append(file+":\n"+"�ַ�����" + charNum + "\n������" 
						+ wordNum + "\n������" + lineNum + "\n��������" + blankLineNum + "\n����������" 
						+ codeLineNum + "\nע������" + annotationLineNum+"\n");	
			}			
		}catch(IOException e){
				System.out.println("�ļ�·����������ļ���֧���~");
				e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		GUI_Frame GF = new GUI_Frame();
		GF.setVisible(true);
	}

}
