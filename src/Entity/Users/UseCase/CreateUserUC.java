package Entity.Users.UseCase;

import Core.Transaction;
import Core.Utils.PrimeNumberUtil;
import Core.Utils.RSAUtils;
import Entity.Users.UseCase.CreateUser.Command;
import Entity.Users.User;
import Entity.Users.Users;
import Services.Services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.HashMap;

public class CreateUserUC {

    private Transaction transaction;
    private Users users;

    public CreateUserUC(
            Transaction transaction,
            Users users
    ) {
        this.transaction = transaction;
        this.users = users;
    }

    public void execute(Command command) {
        this.transaction.begin();

        if(users.findOneByLogin(command.getLogin()) != null) {
            command.getResponder().userExists(command.getLogin());
            this.transaction.rollback();
            return;
        }
        RSAUtils rsa = new RSAUtils(new PrimeNumberUtil());
        HashMap<String, BigInteger> keys  = rsa.generateKeys();
        User user = new User(
                command.getLogin(),
                command.getPassword(),
                keys.get("OpenExponent")+";"+keys.get("Modulo")
        );
        String privateKey = keys.get("SecretExponent")+";"+keys.get("Modulo");
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("key"+command.getLogin()));
            writer.write(privateKey);
            writer.close();
        }catch (Throwable e){
            this.transaction.rollback();
        }

        this.users.addUser(user);
        try{
            this.transaction.commit();
        }catch (Throwable e)
        {
            this.transaction.rollback();
            throw e;
        }

        command.getResponder().userCreated(user);

    }
}
