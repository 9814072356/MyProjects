package de.uni_hannover.hci.kyanh.auf1;
import java.util.*;
public abstract class BinTree {
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

    public abstract void setLeft(int i);

    public abstract void setRight(int i);

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
    public abstract boolean search(int i);
    /**
     * ceate a binary tree by inserting values randomly to the left or right nodes should these nodes are empty
     * @param bTree
     * @param i
     * @return
     */
    public abstract void insert(int i);
    
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
