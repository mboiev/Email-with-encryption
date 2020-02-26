package Entity.Users;

import Core.Utils.HashUtil;

import javax.persistence.*;


@Entity
public class User {


    @Id
    @Column(name = "user_id", nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userID;

    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    @Column(name = "email", length = 60, nullable = false)
    private String email;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "public_key", nullable = false)
    private String publicKey;


    public User() {
    }

    public User(
            String userName,
            String password,
            String publicKey
    )
    {
        this.userID = null;
        this.userName = userName;
        this.password = new HashUtil(password).messageToHash();
        this.email = generateEmail(userName);
        this.publicKey = publicKey;

    }


    private String hashPassword(String password){
        return password;
    }

    private String generateEmail(String userName){
        return userName+"@smail.com";
    }


    public Integer getUserID() {
        return userID;
    }


    public String getUserName() {
        return userName;
    }


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


    public String getPublicKey() {
        return publicKey;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
