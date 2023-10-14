var cols, rows;
var w = 30;
var grid = [];
var stack = [];
var current;
solver = {
  x :null,
  y :null,
  Width :w-w/10,
  Height :w-w/10,
  update:function(){
    if(mouseIsPressed){
      this.x = mouseX-w/2;
      this.y = mouseY-w/2;
    this.y = Math.max(Math.min(this.y, rows*w - this.Height), 0);
    this.x = Math.max(Math.min(this.x, cols*w - this.Width), 0);
    }
    function keyReleased(){
      this.x = this.x;
      this.y = this.y;
    }
  },
  draw:function(){
    fill(255,0,0);
    rect(this.x,this.y,this.Width,this.Height);
  }
}
function setup() {
  	createCanvas(floor(random(700,innerWidth)), floor(random(700,innerHeight)));
  	cols = floor(width/w);
  	rows = floor(height/w);

  	for (var   j = 0; j < rows; j++) {
    	for (var i = 0; i < cols; i++) {
      		var cell = new Cell(i, j);
      		grid.push(cell);
          
    	}
  	}
  current = grid[floor(random(0,grid.length))];
  frameRate(100);
}

function Cell(i, j) {
    this.i = i;
    this.j = j;
    this.walls = [true, true, true, true];
    this.visited = false;

    this.checkNeighbors = function() {
      var neighbors = [];

      var top    = grid[index(i, j -1)];
      var right  = grid[index(i+1, j)];
      var bottom = grid[index(i, j+1)];
      var left   = grid[index(i-1, j)];

      if (top && !top.visited) {
          neighbors.push(top);
      }
      if (right && !right.visited) {
          neighbors.push(right);
      }
      if (bottom && !bottom.visited) {
          neighbors.push(bottom);
      }
      if (left && !left.visited) {
          neighbors.push(left);
      }

      if (neighbors.length > 0) {
          var r = floor(random(0, neighbors.length));
          return neighbors[r];
      }
      else {
          return undefined;
      }
    }
    this.highlight = function(){
      var x = this.i*w;
      var y = this.j*w;
        noStroke();
        if(x == i && y == j){
          fill("white");
          rect(x+3, y+3, w/2, w/2);         
        }else{       
          fill(0, 0, 255);
          rect(x, y, w, w);
        }
        
        fill(0,255,0);
        rect((cols - 1)*w+2, (rows - 1)*w+2, w-2, w-2);
        solver.update();
        solver.draw();
    }
    this.show = function() {
      var wallsX = this.i*w;
      var wallsY = this.j*w;
      stroke("black");
      if (this.walls[0]) {
          line(wallsX, wallsY, wallsX+w, wallsY);
      }
      if (this.walls[1]) {
          line(wallsX+w, wallsY, wallsX + w, wallsY+w);
      }
      if (this.walls[2]) {
          line(wallsX+w, wallsY+w, wallsX, wallsY+w);
      }
      if (this.walls[3]) {
          line(wallsX, wallsY+w, wallsX, wallsY);
      }

      if (this.visited) {
          noStroke();
      }
    }
}

function draw(){
  	background("greenyellow");
  	for(var i = 0; i < grid.length; i++){
  		grid[i].show();
  	}
  	current.visited = true;
  	current.highlight();
  	var next = current.checkNeighbors();
  	if(next){
  		next.visited = true;
  		stack.push(current);
  		removeWalls(current, next);
  		current = next;
  	}
  	else if(stack.length > 0){
  		current = stack.pop();
  	}
}

function index(i, j) {
  	if (i < 0 || j < 0 || i > cols-1 || j > rows-1) {
    	return -1;
  	}
  	return i + j * cols;
}

function removeWalls(a, b) {
  	var x = a.i - b.i;
  	if (x === 1) {
    	a.walls[3] = false;
    	b.walls[1] = false;
  	}
  	else if (x === -1) {
    	a.walls[1] = false;
    	b.walls[3] = false;
  	}
  	var y = a.j - b.j;
  	if (y === 1) {
    	a.walls[0] = false;
    	b.walls[2] = false;
  	}
  	else if (y === -1) {
    	a.walls[2] = false;
    	b.walls[0] = false;
  	}
}