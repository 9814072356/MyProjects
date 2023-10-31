package de.uni_hannover.hci.kyanh.auf2;
public class BluRay implements ISortTable{
    String title;
    int price;
    String publisher;
    String ASINCODE;

    public BluRay(String title, int price, String publisher,String ASINCode){
        this.title = title;
        this.price = price;
        this.publisher = publisher;
        this.ASINCODE = ASINCode;
    }

    public String getTitle(){
        return this.title;
    }

    public String getPublisher(){
        return this.publisher;
    }

    public int getPrice(){
        return this.price;
    }

    public String getCode(){
        return this.ASINCODE;
    }

    public String getSortString(SortMode mode){
        String s = "";
        if(mode == SortMode.INFO){
            s += "${" + this.getTitle() + "}" + "-${" + this.getPublisher() + "}";
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
        System.out.printf("Disc " + index + " => Info: "+this.getSortString(SortMode.INFO) + " - " + "Price: "+this.getSortString(SortMode.PRICE)+" - "+"ASIN Code: "+this.getSortString(SortMode.ID)+"\n");
    }
}