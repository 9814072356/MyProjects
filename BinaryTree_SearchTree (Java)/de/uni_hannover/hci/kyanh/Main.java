package de.uni_hannover.hci.kyanh;
public class Main {
	public static void main(String[] args) {
		int[] nodes = {3, 6, 16, 32, 7, 2, 54 ,15};
        BinTree sTree = new SearchTree(10);
        BinTree bTree = new BinTree(10);

        System.out.println("");
        
        System.out.println("sTree before insert: " + sTree);
        for(int i = 0; i < nodes.length; i++){
            sTree.insert(nodes[i]);
        }
        System.out.println("sTree after insert: " + sTree);
        String Sresult = "search tree in order: " + sTree.inOrder();    
        System.out.println(Sresult);
        System.out.print("search sTree: ");
        for(int i = 0; i <= 10; i++){
            System.out.printf("%d " + sTree.search(i) + "|",i);
        }

        System.out.println("");
        System.out.println("--------------------------------------------------------------");

        System.out.println("bTree before insert: " + bTree);
        for(int i = 0; i < nodes.length; i++){
            bTree.insert(nodes[i]);
        }
        System.out.println("bTree after insert: " + bTree);
        String Bresult = "normal tree in order: " + bTree.inOrder();
        System.out.println(Bresult);
        System.out.print("search bTree: ");
        for(int i = 0; i <= 10; i++){
            System.out.printf("%d " + bTree.search(i) + "|",i);
        }
        
        System.out.println("");
	}

}
