package application;


public class lastMove {
	int lastIndex;
	int curIndex;
	int size;
	int section;
	String face;
	double lastX,lastY;
	 public lastMove(int last, int cur, int size, int section, String face, double lastX, double lastY) {
		 this.lastIndex = last;
		 this.curIndex = cur;
		 this.size = size;
		 this.section = section;
		 this.face = face;
		 this.lastX = lastX;
		 this.lastY = lastY;
	 }
	 
	 public int getLastIndex() {
		 return lastIndex;
	 }
	 
	 public int getCurIndex() {
		 return curIndex;
	 }
	 
	 public int getSize() {
		 return size;
	 }
	 
	 public int getSection() {
		 return section;
	 }
	 
	 public String getFace() {
		 return face;
	 }
	 
	 public double getLastX() {
		 return lastX;
	 }
	 
	 public double getLastY() {
		 return lastY;
	 }
	 
	 public void setLastIndex(int newLast) {
		 lastIndex = newLast;
	 }
	 
	 public void setCurIndex(int newCur) {
		 curIndex = newCur;
	 }
	 
	 public void setSize(int newSize) {
		 size = newSize;
	 }
	 
	 public void setSection(int newSection) {
		 section = newSection;
	 }
	 
	 public void setFace(String newFace) {
		 face = newFace;
	 }
	 
	 public void setLastX(double newX) {
		 lastX = newX;
	 }
	 
	 public void setLastY(double newY) {
		 lastY = newY;
	 }
}
