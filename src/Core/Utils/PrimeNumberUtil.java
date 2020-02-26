package Core.Utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PrimeNumberUtil {

    public BigInteger generatePrime(){
        SecureRandom rnd = new SecureRandom();
        return BigInteger.probablePrime(1024, rnd);
    }

    public BigInteger generateCoPrime(BigInteger number){
        BigInteger coPrime = generateRandomLessThan(number);
        if(checkCoPrime(number, coPrime).equals(new BigInteger("1")))
            return coPrime;
        return generateCoPrime(number);
    }

    private BigInteger checkCoPrime(BigInteger number1, BigInteger number2){
        BigInteger temp;
        while (!number2.equals(new BigInteger("0"))){
            temp = number1;
            number1 = number2;
            number2 = temp.mod(number2);
        }
        return number1;
    }

    private BigInteger generateRandomLessThan(BigInteger maxLimit){
        BigInteger minLimit = new BigInteger("1");
        BigInteger space = maxLimit.subtract(minLimit);
        SecureRandom rnd = new SecureRandom();
        BigInteger result = new BigInteger(1024,rnd);
        if(result.compareTo(minLimit)<0)
            result = result.add(minLimit);
        if(result.compareTo(space)>= 0)
            result = result.mod(space).add(minLimit);
        return  result;
    }
}
