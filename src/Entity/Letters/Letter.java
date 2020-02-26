package Entity.Letters;


import Entity.Users.User;

import javax.persistence.*;
import java.time.Instant;


@Entity
public class Letter {

    @Id
    @Column(name = "letter_id",nullable = false)
    private Integer letterID;

    @Column(name = "letter_text",nullable = false)
    private String letterText;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver")
    private User receiver;

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @Column(name = "sent_time")
    private int sentTime;

    public Letter(){}

    public Letter(
            String letterText,
            User author,
            User receiver
    )
    {
        this.letterText = letterText;
        this.author = author;
        this.sentTime = Math.toIntExact(Instant.now().getEpochSecond());
        this.receiver = receiver;
    }

    public Integer getLetterID() {
        return letterID;
    }

    public void setLetterID(Integer letterID) {
        this.letterID = letterID;
    }

    public String getLetterText() {
        return letterText;
    }

    public void setLetterText(String letterText) {
        this.letterText = letterText;
    }


    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getSentTime() {
        return sentTime;
    }

    public void setSentTime(int sentTime) {
        this.sentTime = sentTime;
    }
}
