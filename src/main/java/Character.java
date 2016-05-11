package main.java;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	//每個character有四個state,1:在左側的陣列 2:用滑鼠拖移時 3:在圓圈內 4:add/clear時
	public int state;
	private MainApplet parent;
	private String name;
	//private int value;
	private int r, g, b; //色碼轉換
	//因為有物件&&value，所以要宣告成hashmap
	private HashMap<Character,Integer> targets;
	
	//position
	public float x, y; //現在的x,y
	public float circle_x, circle_y; //在圓上的x,y
	public float arr_x, arr_y; //在左側陣列的x,y
	
	//是否顯示
	public boolean namevisible;
	public boolean linevisible;
	
	public Character(MainApplet parent, String name, float x, float y, String colour){
		this.parent = parent;
		this.name = name;
		this.x = x;
		this.y = y;
		//利用substring && Integer.parseInt來做字串轉十六進位的解碼
		this.r = Integer.parseInt(colour.substring(3,5), 16);
		this.g = Integer.parseInt(colour.substring(5,7), 16);
		this.b = Integer.parseInt(colour.substring(7,9), 16);
		this.targets =new HashMap<Character,Integer>();
	}

	public void display(){
		parent.stroke(255);
		parent.strokeWeight(1);
		
		if(this.state == 1){ //initial
			this.x = this.arr_x;
			this.y = this.arr_y;
		}
		else if(this.state == 2){ //mouse
			this.x = parent.mouseX;
			this.y = parent.mouseY;
		}
		else if(this.state == 3){ //circle
			this.x = this.circle_x;
			this.y = this.circle_y;
		}
		else{ //move
			
		}
		
		//滑鼠移到點上時
		if(this.namevisible == true){
			//滑鼠移到點上時，點會變大
			parent.fill(r, g, b, 100);
			parent.ellipse(x, y, 55, 55);
			
			//name的框框
			float text_width = 110;
			parent.rect(parent.mouseX, parent.mouseY - 15, text_width, 35, 30); //x,y,width,height,圓角
			//word
			parent.fill(255);
			parent.textSize(14);
			parent.text(name, parent.mouseX +10, parent.mouseY +5);
		}
		
		//滑鼠沒點到時，顯示一般大小
		parent.fill(r,g,b,100);
		parent.ellipse(x, y, 40, 40);
		parent.fill(255);
		
	}
	
	public void addTarget(Character c, Integer i){
		this.targets.put(c, i);
	}
	public HashMap<Character,Integer> getTarget(){
		return this.targets;
	}
}
