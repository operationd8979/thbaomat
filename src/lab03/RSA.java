/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab03;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author ESC
 */
public class RSA {
    int primeSize;
    BigInteger p,q,N,r,D,E;
    //Random  rnd = new Random();
    
    public RSA(){
        
    }
    
    public RSA(int primeSize){
        this.primeSize = primeSize;
        generatePrimeNumbers();
        generatePublicPrivateKeys();
    }
    
    public void generatePrimeNumbers(){
        p = BigInteger.probablePrime(primeSize/2, new Random());
        do{
            q = BigInteger.probablePrime(primeSize/2, new Random());
        }while(q.compareTo(p)==0);
    }
    
    public void generatePublicPrivateKeys(){
        N = p.multiply(q);
        r = p.subtract(BigInteger.valueOf(1));
        r = r.multiply(q.subtract(BigInteger.valueOf(1)));
        do{
            E = new BigInteger(2*primeSize,new Random());
        }while((E.compareTo(r)!=-1)||(E.gcd(r).compareTo(BigInteger.valueOf(1))!=0));
        D = E.modInverse(r);
    }
    
    public BigInteger[] encrypt(String message){
        int i;
        byte[] temp = new byte[1];
        byte[] digits = message.getBytes();
        BigInteger[] bigdigits = new BigInteger[digits.length];
        for(i = 0; i< bigdigits.length;i++){
            temp[0] = digits[i];
            bigdigits[i] = new BigInteger(temp);
        }
        BigInteger[] encrypted = new BigInteger[bigdigits.length];
        for(i = 0;i< bigdigits.length;i++)
            encrypted[i] = bigdigits[i].modPow(E, N);
        return(encrypted);
    }
    
    public BigInteger[] encrypt(String message,BigInteger userD,BigInteger userN){
        int i;
        byte[] temp = new byte[1];
        byte[] digits = message.getBytes();
        BigInteger[] bigdigits = new BigInteger[digits.length];
        for(i=0;i<bigdigits.length;i++){
            temp[0] = digits[i];
            bigdigits[i] = new BigInteger(temp);
        }
        
        BigInteger[] encrypted = new BigInteger[bigdigits.length];
        for(i=0;i<bigdigits.length;i++)
            encrypted[i] = bigdigits[i].modPow(userD, userN);
        
        return encrypted;
    }
    
    
    public String decrypt(BigInteger[] encrypted,BigInteger D,BigInteger N){
        int i;
        BigInteger[] decrypted = new BigInteger[encrypted.length];
        for(i=0;i<decrypted.length;i++)
            decrypted[i] = encrypted[i].modPow(D, N);
        
        char[] charArray = new char[decrypted.length];
        for(i=0;i<charArray.length;i++)
            charArray[i] = (char)(decrypted[i].intValue());
        return (new String(charArray));
    }
    
    public BigInteger getp(){
        return p;
    }
    public BigInteger getq(){
        return q;
    }
    public BigInteger getr(){
        return r;
    }
    public BigInteger getN(){
        return N;
    }
    public BigInteger getD(){
        return D;
    }
    public BigInteger getE(){
        return E;
    }
}
