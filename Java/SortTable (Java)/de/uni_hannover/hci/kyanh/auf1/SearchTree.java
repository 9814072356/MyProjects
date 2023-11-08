package de.uni_hannover.hci.kyanh.auf1;
/**
 * SearchTree inherits all methods of BinTree
 */
public class SearchTree extends BinTree{
    public SearchTree(int val){
        super(val);
    }
    public void setLeft(int i){
        this.left = new SearchTree(i);
    }
    public void setRight(int i){
        this.right = new SearchTree(i);
    }
    /**
     * create a search tree, in which all of the smaller values than that of the root stay on left nodes and the larger stay on the right
     * @param sTree
     * @param i
     * @return
     */
    public void insert(int i){
        if(i < this.getValue()){
            if(this.getLeft()== null)this.setLeft(i);
            else left.insert(i);
        }else if(i > this.getValue()){
            if(this.getRight() == null)this.setRight(i);
            else right.insert(i);
        }
        return;
    }
    /**
     * search the stree in the same way of creating it
     * @param tree
     * @param i
     * @return
     */
    public boolean search(int i){
        if(i == getValue())return true;
        if(i <= getValue() && this.getLeft() != null)return this.getLeft().search(i);
        if(i > getValue() && this.getRight() != null)return this.getRight().search(i);
        return false;
    }
}
