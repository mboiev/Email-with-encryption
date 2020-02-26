package Core.Utils;

import java.math.BigInteger;
import java.util.HashMap;
public class RSAUtils {

    private PrimeNumberUtil primeGenerator;

    public RSAUtils(PrimeNumberUtil primeGenerator){
        this.primeGenerator = primeGenerator;
    }

    public HashMap<String,BigInteger> generateKeys(){
        BigInteger p = primeGenerator.generatePrime();
        BigInteger q = primeGenerator.generatePrime();
        BigInteger modulo = p.multiply(q);
        BigInteger euler = p.subtract(new BigInteger("1")).multiply(q.subtract(new BigInteger("1")));
        BigInteger openExp = primeGenerator.generateCoPrime(euler);
        BigInteger secretExp = openExp.modInverse(euler);
        HashMap<String,BigInteger> keys = new HashMap<String, BigInteger>();
        keys.put("OpenExponent", openExp);
        keys.put("Modulo", modulo);
        keys.put("SecretExponent", secretExp);
        return keys;
    }

    public  BigInteger rsaEncode(BigInteger message, BigInteger openExponent, BigInteger modulo){
        return message.modPow(openExponent, modulo);
    }

    public  BigInteger rsaDecode(BigInteger cypherMessage, BigInteger secretExponent, BigInteger modulo){
        return cypherMessage.modPow(secretExponent,modulo);
    }

}
