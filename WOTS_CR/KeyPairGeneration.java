package WOTS_CR;
import Config.*;
public class KeyPairGeneration {
    Binarylog bl = new Binarylog();
    PRG prg = new PRG();
    MD5Binary md5B = new MD5Binary();
   // MD5HEX md5H = new MD5HEX();
    public static int t;
    public static int t1;
    public static int t2;
    public static String X = "";
    public static String Y = "";

    public void calculateLengths(Integer s, Integer w){
        t1 = (int)Math.ceil(new Double(s) / new Double(w));
        t2 = (int)Math.ceil((double)((int)Math.ceil(bl.binlog((double) t1)) + 1 + w)/(double)w);
        t = t1 + t2;
    }


    public void generatePairKey(Integer s, Integer w) {
        calculateLengths(s, w);
        String Xi = "";
        String Yi = "";
        X = "";
        Y = "";
        for(int i = 1; i <= t; i++){
            Xi = prg.Random128();
            X += Xi;
            Yi = calculateYi(Xi, Yi, w);
            Y += Yi;
        }
        //Y = md5H.md5Custom(Y);
    }

    private String calculateYi(String Xi, String Yi, Integer w){
        Yi = Xi;
        for(int i = 1; i <= Math.pow(2, w) - 1; i++){
            Yi = md5B.md5Custom(Yi);
        }
        return Yi;
    }
}
