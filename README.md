# Software Studio Assignment 6


## Notes
+ You will have to import the libraries on your own. All the libraries are provided on iLMS.
+ Even you followed the design TA shown in the video, you still have to explain the control of the program in the next section.

## Explanation of the Design
Explain your design in this section.  
我們的程式分為三個部分實作：Main、Character與MainApplet，其中Main是TA已經寫好的，故講解另外兩份code
Character：為星際大戰裡面的角色，各項資料(name、color...)由MainApplet讀入，並轉換成rgb的色碼。
		  而要link的target因為包含character與link的value兩項資訊，所以使用Hashmap做存取。
		  我們也根據角色點在不同狀況的形態設了4個state，分別為：在左側陣列時、滑鼠拖曳時、在圓圈上和add all/clear的動畫形態
		  滑鼠移動到角色點的坐標時，設定顯示名字與角色點放大的變數
		  也因為這些不同的state，有了三種角色的x,y坐標，現在的坐標、陣列的坐標與在圓上的坐標
MainApplet：在此class中，一開始先預設ep(版本) = 1，之後隨著使用者按數字鍵，會重新更新ep，並load不同的json檔做setup
           右上有目前版本的文字顯示與兩個button，使用ControlP5來控制
           class內也會針對現在在哪一個state，判斷name和link需不需要顯示，並且在每次加入新角色到圓裡面時，透過reset將角色點重新排列，並增加link
           而在state4中，我們使用Ani來讓角色點做快速add/remove的動畫效果，讓整體程式更加精緻
            
Example:
### Operation
+ 1.點擊ADD ALL的按鈕：可以將所有的角色全部拉入network中，美中不足的是!!! 無法像助教實際demo的一樣做analyzed(角色跟角色之間不會拉線)
+ 2.點擊CLEAR的按鈕：可以將所有的角色拉回左方排列，線也會全部清空!!!
+ 3.滑鼠移到角色上的時候：可以顯示該角色的名稱
+ 4.可以皆由單一拖拉的方式將角色個別代表的圓圈放到network上，神奇的是：借由拖拉到network上的角色，居然可以analyzed了!!!
+ 5.可以借由鍵盤上的1~7數字鍵，選擇您要analyzed哪一集的星際大戰的角色關係，非常人性，客製化



### Visualization

+ 1.精美的畫面，讓使用者心情愉悅
+ 2.角色之間的線的粗細，是根據link的valuen所設計的，似乎是代表星際大戰角色之間關係的深淺!?
+ 3.簡潔有力的直線條分析，不拖泥帶水，不歪七扭八，讓您輕鬆上手!!!
