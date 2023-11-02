package de.uni_hannover.hci.kyanh;
/**
 * SearchTree inherits all methods of BinTree
 */
public class SearchTree extends BinTree{
    public SearchTree(int val){
        super(val);
    }
    /**
     * create a search tree, in which all of the smaller values than that of the root stay on left nodes and the larger stay on the right
     * @param sTree
     * @param i
     * @return
     */
    @Override
    public void insert(int i){
        if(i < this.val){
            if(this.left == null){
                this.left = new SearchTree(i);
                return;
            }
            else left.insert(i);
        }else if(i > this.val){
            if(this.right == null){
                this.right = new SearchTree(i);
                return;
            }
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
    @Override
    public boolean search(int i){
        if(i == getValue())return true;
        if(i <= getValue() && this.getLeft() != null)return this.getLeft().search(i);
        if(i > getValue() && this.getRight() != null)return this.getRight().search(i);
        return false;
    }
}
