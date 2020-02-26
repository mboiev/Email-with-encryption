package Entity.Users;


public interface Users {
    public void addUser(User user);
    public User findOneByLogin(String login);
    public User findOneByEmail(String email);
}
