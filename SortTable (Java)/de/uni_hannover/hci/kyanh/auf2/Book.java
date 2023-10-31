package de.uni_hannover.hci.kyanh.auf2;
public class Book implements ISortTable{
    String title;
    String Author;
    String publisher;
    int price;
    String ISBN13;

    public Book(String title, int price, String publisher, String Author,String ISBN13){
        this.title = title;
        this.price = price;
        this.publisher = publisher;
        this.Author = Author;
        this.ISBN13 = ISBN13;
    }

    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.Author;
    }

    public String getPublisher(){
        return this.publisher;
    }

    public int getPrice(){
        return this.price;
    }

    public String getCode(){
        return this.ISBN13;
    }

    public String getSortString(SortMode mode){
        String s = "";
        if(mode == SortMode.INFO){
            s += "${" + this.getTitle() + "}" + "-${" + this.getAuthor() + "}" + "-${" + this.getPublisher() + "}";
        }else if(mode == SortMode.PRICE){
            s += "${";
            for(int i = 0; i < 6 - Integer.toString(this.getPrice()).length(); i++){
                s += "0";
            }
            s += this.getPrice() + "}";
        }else if(mode == SortMode.ID){
            s += "${" + this.getCode() + "}";
        }
        return s;
    }

    public void print(int index){
        System.out.printf("Book " + index + " => Info: "+this.getSortString(SortMode.INFO) + " - " + "Price: "+this.getSortString(SortMode.PRICE)+" - "+"ISBN13 Code: "+this.getSortString(SortMode.ID)+"\n");
    }
}