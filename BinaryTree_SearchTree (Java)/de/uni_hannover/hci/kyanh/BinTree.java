package de.uni_hannover.hci.kyanh;
import java.util.*;
public class BinTree {
	int val;
    BinTree left;
    BinTree right;
    String result = "";

    public BinTree(int val){
        this.val = val;
        this.left = null;
        this.right = null;
    }
    public BinTree getLeft(){
        return this.left;
    }

    public BinTree getRight(){
        return this.right;
    }

    public void setLeft(int i){
        this.left = new BinTree(i);
    }

    public void setRight(int i){
        this.right = new BinTree(i);
    }

    public int getValue(){
        return this.val;
    }
    /**
     * resturn a string of vaules from the tree, in order left->root->right
     * @param tree
     * @return
     */
    public String inOrder(){
        result += left != null ? left.inOrder() : "";
        result += getValue() + " ";
        result += right != null ? right.inOrder() : "";
        return result;
    }

    /**
     * search the tree in order left->root->right
     * @param tree
     * @param i
     * @return
     */
    public boolean search(int i){
        if(getLeft() != null && left.search(i))return true;
        if(i == getValue())return true;
        if(getRight() != null && right.search(i))return true;
        return false;
    }
    /**
     * ceate a binary tree by inserting values randomly to the left or right nodes should these nodes are empty
     * @param bTree
     * @param i
     * @return
     */
    public void insert(int i){
        Random rand = new Random();
        int randPos = rand.nextInt(2);
        if(randPos == 1){
            if(getRight() == null)this.setRight(i);
            else right.insert(i);
        }else{
            if(getLeft() == null)this.setLeft(i);
            else left.insert(i);
        }
    }
    
    /**
     * return a string which represents a binary tree
     */
    @Override
    public String toString() {
        return (getLeft() == null && getRight() == null ? "" : "(") 
             + (getLeft() == null && getRight() == null ? "" : (getLeft() == null && getRight() != null ? "_" : getLeft())) 
             + (getLeft() == null && getRight() == null ? getValue() : ", " + getValue() + ", ") 
             + (getLeft() == null && getRight() == null ? "" : (getLeft() != null && getRight() == null ? "_" : getRight())) 
             + (getLeft() == null && getRight() == null ? "" : ")");
    }
}
