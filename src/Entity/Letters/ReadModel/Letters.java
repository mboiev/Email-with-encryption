package Entity.Letters.ReadModel;

public class Letters {
    private int id;
    private String author;
    private int date;
    private String letter;

    public Letters(
        int id,
        String author,
        int date,
        String letter
    ){
        this.id = id;
        this.author = author;
        this.date = date;
        this.letter = letter;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public int getDate() {
        return date;
    }

    public String getLetter() {
        return letter;
    }
}
