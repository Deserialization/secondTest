package com.zs.code;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.w3c.dom.css.ElementCSSInlineStyle;

public class Bowling {					//输入字符串
  public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		System.out.print("输入打掉的瓶子：");
	    String str = br.readLine(); 
	    int score = calculateScore(str);
	    System.out.println(score);
}  
  public boolean checkInputValid(String input){//验证合法
	  return true;
  }  
  public static Integer calculateScore(String input){//计算总分
	  int score = 0;
	  int status = -1;
	  String[] inputSplited = input.split("\\|");
	  String ietmLast = "";				//第十局的
	  String ietmApdenx = "";			//附加的
	  for(int i =0; i < inputSplited.length -1; i ++){
		  if(i <= 9){			     
			  score += countScore(inputSplited[i],inputSplited[i+1],inputSplited[i+2]);
				}else{
						ietmLast = inputSplited[i - 1];
						ietmApdenx = inputSplited[inputSplited.length - 1];
							  int ietm1,ietm2 = 0;
							  if(ietmApdenx.length() > 1){
								 ietm1 = checkStrIsNumberOrX(ietmApdenx.substring(0));
								 ietm2 = checkStrIsNumberOrX(ietmApdenx.substring(0,1));
							  }else{
								  if(ietmApdenx.indexOf("X") > -1){
									  ietm1 = 10;
								  }else{
									  ietm1 = Integer.parseInt(ietmApdenx);
								  }
							  }						 
							score += countLastRound(ietmLast,ietm1,ietm2);
				}
		  }
	  return score;
}
  
  public static Integer countLastRound(String ietmLast,int ietm1,int ietm2){//上一投的结果
		int score = 0;
		int status = judgeItem(ietmLast);
		if(status == 0){
			score = 10 + ietm1 + ietm2;
		}else if(status == 1){
			score = Integer.parseInt(ietmLast.substring(0)) + ietm1;
		}
		return score;	  
  }
  public static int checkStrIsNumberOrX(String item){//判断是数字还是x
	 if(item.indexOf("X") > -1){
		 return 10;   
	 }else{
		 return Integer.parseInt(item);
	 }
  }

  public static Integer judgeItem(String item){//判断状态
	  int status = -1;
	  if (item.indexOf("X") > -1) {
		status = 0;
	}
	  if (item.indexOf("/") > -1) {
			status = 1;
		}
	  if (item.indexOf("-") > -1) {
			status = 2;
		}
	  return status;
}
  
  public static int generateNumber(String item){//截取两一个或者两个的
	  String splitOne = item.substring(0);//开始索引的地方
	  String splitTwo = item.substring(0,1);//开始索引的地方，其实长度
	  Boolean numberOne = splitOne.matches("[0-9]+");//判断字符串是不是数字
	  
	  Boolean numberTwo = splitTwo.matches("[0-9]+");
	  if(numberOne == true){
		  return Integer.parseInt(splitOne);
	  }
	  if(numberTwo == true){
		  return Integer.parseInt(splitTwo);
	  }
	  return -1;
  }
	  
 public static Integer calculateAfterOneScore(String item){//计算之后的一个数字
	 int score = 0;
	 int status = judgeItem(item);
	 if(status == 0){
		 score = 10;
	 }else if(status == 1){
		 score = Integer.parseInt(item.substring(0));
	 }else if(status == 2){
		 score = generateNumber(item);
	 }
	 return score;
 }
public static Integer calculateAfterTwoScore(String itemOne,String itemTwo){//两个数字
	int score = 0;
	int status1 = judgeItem(itemOne);
	if(status1 == 0){
		score = 10;
		int status2 = judgeItem(itemTwo);
		if(status2 == 0){
			score = 20;
		}
		if(status2 == 1){
			score += Integer.parseInt(itemOne.substring(0));
		}
		if(status2 == 2){
			score += generateNumber(itemOne);
		}		
	}
	if(status1 == 1){
		score = 10 + Integer.parseInt(itemOne.substring(0)) + 10;
	}
	if(status1 == 2){
		score = 10 + generateNumber(itemOne);
	}
	return score;	
}
public static Integer countScore(String item,String item1,String item2){
	int score = 0;
	int status = judgeItem(item);
	if(status == 0){
		score = 10 + calculateAfterTwoScore(item1,item2);
	}else if(status == 1){
		score = Integer.parseInt(item.substring(0)) + calculateAfterOneScore(item1);
	}else if (status == 2) {
		int number = generateNumber(item);
		if(number != -1){
			score = number;
		}
}
	return score;
}
}