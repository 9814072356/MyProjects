package de.uni_hannover.hci.kyanh.auf1;
import java.util.*;
public class RandTree extends BinTree{
    public RandTree(int val){
        super(val);
    }
    public void setLeft(int i){
        this.left = new RandTree(i);
    }
    public void setRight(int i){
        this.right = new RandTree(i);
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
            if(this.getRight() == null)this.setRight(i);
            else right.insert(i);
        }else{
            if(this.getLeft() == null)this.setLeft(i);
            else left.insert(i);
        }
    }
}
