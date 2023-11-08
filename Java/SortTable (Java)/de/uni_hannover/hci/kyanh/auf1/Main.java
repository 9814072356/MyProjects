package de.uni_hannover.hci.kyanh.auf1;
public class Main {
	public static void main(String[] args) {
		int[] nodes = {3, 6, 16, 32, 7, 2, 54 ,15};
        BinTree sTree = new SearchTree(10);
        BinTree rTree = new RandTree(10);

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

        System.out.println("rTree before insert: " + rTree);
        for(int i = 0; i < nodes.length; i++){
            rTree.insert(nodes[i]);
        }
        System.out.println("rTree after insert: " + rTree);
        String Bresult = "normal tree in order: " + rTree.inOrder();
        System.out.println(Bresult);
        System.out.print("search rTree: ");
        for(int i = 0; i <= 10; i++){
            System.out.printf("%d " + rTree.search(i) + "|",i);
        }
        
        System.out.println("");
	}

}
