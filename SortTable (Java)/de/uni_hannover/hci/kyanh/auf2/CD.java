package de.uni_hannover.hci.kyanh.auf2;
public class CD implements ISortTable{
    String AlbumTitle;
    String artist;
    String publisher;
    int price;
    String ASINCode;

    public CD(String AlbumTitle, int price, String publisher, String artist, String ASINCode){
        this.AlbumTitle = AlbumTitle;
        this.price = price;
        this.publisher = publisher;
        this.artist = artist;
        this.ASINCode = ASINCode;
    }

    public String getAlbum(){
        return this.AlbumTitle;
    }

    public String getArtist(){
        return this.artist;
    }

    public String getPublisher(){
        return this.publisher;
    }

    public int getPrice(){
        return this.price;
    }

    public String getCode(){
        return this.ASINCode;
    }

    public String getSortString(SortMode mode){
        String s = "";
        if(mode == SortMode.INFO){
            s += "${" + this.getAlbum() + "}" + "-${" + this.getArtist() + "}" + "-${" + this.getPublisher() + "}";
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
        System.out.printf("Album " + index + " => Info: "+this.getSortString(SortMode.INFO) + " - " + "Price: "+this.getSortString(SortMode.PRICE)+" - "+"ASIN Code: "+this.getSortString(SortMode.ID)+"\n");
    }
}