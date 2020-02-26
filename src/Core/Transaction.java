package Core;

public interface Transaction {

    public void begin();
    public void rollback();
    public void commit();
}
