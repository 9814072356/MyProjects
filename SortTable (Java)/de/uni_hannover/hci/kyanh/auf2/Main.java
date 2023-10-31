package de.uni_hannover.hci.kyanh.auf2;
public class Main{
    public static void main(String[] args){
        BluRay br1 = new BluRay("90s Hits", 145, "The Beatles", "B00DR855EG4");
        Book book1 = new Book("To kill a mockingbird", 10, "J.B.Lippincott & Co.", "Harper Lee", "0-8752-5043-2");
        CD cd1 = new CD("Music for the soul", 25, "Wild Horse Multimedia", "Vary", "B00DR8955EG4");
        BluRay br2 = new BluRay("popular classical pieces", 30, "Unknown", "CD53126KI7E8");
        Book book2 = new Book("Murder on the orient express", 10, "Collins Crime Club", "Argatha Christie", "0-9069-3894-5");
        CD cd2 = new CD("Just Jazz", 25, "Sierosong Music PublishingOM Records", "Vary", "OP4653A313464AS3");
        BluRay br3 = new BluRay("Lord of the rings", 56, "Peter Jackson", "DDF54321SA6S873FSAF8");
        Book book3 = new Book("Kafka on the shore", 19, "Shinchosha", "Haruki Murakami", "1-84343-110-6");
        CD cd3 = new CD("Contemporary blues", 40, "Composer's Publishing Company", "B.B.King", "EA654321DAS65R44A3");
        BluRay br4 = new BluRay("The Girl with the Dragon Tattoo", 30, "David Fincher", "FA654D3VS4A68DF7G8SDA534GAS");
        Book book4 = new Book("Algorithms", 50, "California Book Club", "Robert Sedgwig & Kevin Wayne", "978-5-6119-9395-8");
        CD cd4 = new CD("Divide Album", 80, "Famous Music", "Ed Sheeran", "AS65D13S21A5DS4A35SD1A2SC3");
        ISortTable[] collection = {book1,book2,book3,book4,cd1,cd2,cd3,cd4,br1,br2,br3,br4};
        SortMode mode = SortMode.ID;
        System.out.println("-----------------------------------UNSORTED LIST-----------------------------------\n");
        for(int i = 0; i < collection.length; i++){
            collection[i].print(i+1);
        }
        System.out.println("\n-----------------------------------SORTED AFTER " + mode + "-----------------------------------\n");
        sort.Sort(mode,collection,0,collection.length - 1);
        for(int i = 0; i < collection.length; i++){
            collection[i].print(i+1);
        }
    }
}