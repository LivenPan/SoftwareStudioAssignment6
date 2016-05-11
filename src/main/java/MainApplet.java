package main.java;

import java.util.ArrayList;
import java.util.Random;

import controlP5.ControlP5;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PFont;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
 * This class is for sketching outcome using Processing You can do major UI
 * control and some visualization in this class.
 */

@SuppressWarnings("serial")
public class MainApplet extends PApplet {
	
	private String files = "main/resources/starwars-episode-1-interactions.json";
	private ArrayList<Character> characters;
	private ArrayList<Character> charactersInCircle;
	
	public ArrayList<Character> getCharacters() {
		return characters;
	}
	public void addCharacters(Character ch) {
		characters.add(ch);
	}
	private ArrayList<Character> getCharactersInCircle() {
		return charactersInCircle;
	}

	private int ep = 1;
	
	private ControlP5 cp5;
	
	private int circleDiameter;
	private int circleX;
	private int circleY;
	private boolean click = false;

	public void setCircleDiameter(int r) {
		circleDiameter = r;
	}

	public void setCircleX(int x) {
		circleX = x;
	}

	public void setCircleY(int y) {
		circleY = y;
	}

	public int getCircleDiameter() {
		return circleDiameter;
	}

	public int getCircleX() {
		return circleX;
	}

	public int getCircleY() {
		return circleY;
	}

	private final static int width = 1200, height = 650;

	public void setup() {

		size(width, height);
		this.circleDiameter = 530;
		this.circleX = 600;
		this.circleY = 300;
		characters = new ArrayList<Character>();
		this.charactersInCircle = new ArrayList<Character>();

		// 用ep來設定現在要讀哪個資料
		for (int i = ep; i <= ep; i++) {
			files = "main/resources/starwars-episode-" + i + "-interactions.json";
		}

		loadData();

		cp5 = new ControlP5(this);
		cp5.addButton("addall").setLabel("ADD ALL").setPosition(900, 100).setSize(200, 50);
		cp5.addButton("clear").setLabel("CLEAR").setPosition(900, 200).setSize(200, 50);

		Ani.init(this);
		smooth();

	}

	public void draw() {
		stroke(0);
		strokeWeight(5);
		background(255);
		fill(0xFFCCFF00);
		this.ellipse(circleX, circleY, circleDiameter, circleDiameter);

		// 設定character的state和狀態
		for (Character ch : characters) {
			if ((this.mouseX <= ch.x + 20 && this.mouseX >= ch.x - 20)
					&& (this.mouseY <= ch.y + 20 && this.mouseY >= ch.y - 20)) {
				ch.namevisible = true;

				// 滑鼠點
				if (this.mousePressed == true) {
					// 如果之前沒有被點
					if (!click) {
						click = true;
						ch.state = 2; // 第二個state是滑鼠拖曳
					}
				}
				// 滑鼠沒按
				else {
					if (ch.state == 2) {
						// 如果是被拉到圓圈裡面了 讓他固定在圓上
						if ((ch.x - getCircleX()) * (ch.x - getCircleX())
								+ (ch.y - this.circleY) * (ch.y - this.circleY)
								- getCircleDiameter() * getCircleDiameter() / 4 < 0.01) {
							ch.state = 3; // 第三個state
							ch.linevisible = true;// Show the link

							if (!getCharactersInCircle().contains(ch))
								addCharactersInCircle(ch);
						}
						// 如果在圓圈外 讓他回到陣列
						else {
							ch.state = 1;
							ch.linevisible = false;
						}
						reset();
					}

					// Reset
					click = false;
				}
			} else {
				// 如果完全沒動 就不需要顯示名字
				ch.namevisible = false;
			}

			ch.display();
		}
		// 連線
		drawLine();
		fill(255, 165, 0);
		text("Star Wars " + ep, 870, 70);
	}

	public void keyPressed() {
		if (key >= '1' && key <= '7') {
			// 用number來設定現在的版本 並洗掉畫面重新load資料
			ep = key - '0';
			this.clear();
			this.setup();
			this.start();
		}
	}
	
	
	public void addCharactersInCircle(Character ch) {
		charactersInCircle.add(ch);
		reset();
	}

	public void addall() {
		for (Character ch : characters)
			if (!this.charactersInCircle.contains(ch)) {
				ch.namevisible = true;
				this.addCharactersInCircle(ch);
			}

		for (Character ch : characters) {
			ch.state = 4; // 全部加入的動畫
			Ani ani = Ani.to(ch, 1.3f, "x", ch.circle_x);
			Ani.to(ch, 1.3f, "y", ch.circle_y);
			ani.setCallback("onEnd:addAniDone");
		}
		// drawLine();
	}

	public void clear() {
		for (Character ch : characters) {
			if (this.charactersInCircle.contains(ch)) {
				charactersInCircle.remove(ch);
				reset();
				ch.linevisible = false;

				ch.state = 4; // 全部消除的動畫
				Ani ani = Ani.to(ch, 1.3f, "x", ch.arr_x);
				Ani.to(ch, 1.3f, "y", ch.arr_y);

				ani.setCallback("onEnd:removeAniDone");
			}
		}
	}
	

	private void loadData() {
		JSONObject data = loadJSONObject(files);
		JSONArray nodes = data.getJSONArray("nodes");
		JSONArray links = data.getJSONArray("links");

		for (int j = 0; j < nodes.size(); j++) {
			JSONObject node = nodes.getJSONObject(j);
			String name = node.getString("name");
			String colour = node.getString("colour");

			Character ch = new Character(this, name, 0, 0, colour);

			this.addCharacters(ch);
		}

		this.setCharacter_arrY();

		for (int j = 0; j < links.size(); j++) {
			JSONObject link = links.getJSONObject(j);
			int source = link.getInt("source");
			int target = link.getInt("target");
			Integer value = link.getInt("value");

			Character src = this.getCharacters().get(source);
			src.addTarget(this.getCharacters().get(target), value);
		}

	}


	public void setCharacter_arrY() {
		int arrx = 30, arry = 50;
		for (Character ch : characters) {
			ch.arr_x = arrx;
			ch.arr_y = arry;
			ch.state = 1; // 在左側
			arrx += 80;
			if (arrx > 320) {
				arry += 50;
				arrx = 30;
			}
		}
	}



	// 重新排列
	public void reset() {
		double pos = 0;
		for (Character cha : getCharactersInCircle()) {
			cha.circle_x = ((float) (getCircleX() + getCircleDiameter() / 2 * Math.cos(Math.toRadians(pos))));
			cha.circle_y = ((float) (getCircleY() + getCircleDiameter() / 2 * Math.sin(Math.toRadians(pos))));
			pos += (double) 360 / (double) getCharactersInCircle().size();
		}
	}

	// 畫出關係線
	public void drawLine() {
		for (Character ch : charactersInCircle) {
			for (Character tar : ch.getTarget().keySet()) {
				if (tar.linevisible == true) {
					int intense = ch.getTarget().get(tar);// ch.getTarget().get(tar);
					this.strokeWeight(intense);
					this.stroke(0, 77, 173, 250);
					this.line(ch.x, ch.y, tar.x, tar.y);
					// this.curve(10, 10, ch.x, ch.y, tar.x, tar.y, 10, 10);
				}
			}
		}
	}
}