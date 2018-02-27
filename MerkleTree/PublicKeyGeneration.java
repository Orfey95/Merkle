package MerkleTree;
import Config.*;
import WOTS_CR.*;

public class PublicKeyGeneration {
   // PRG prg = new PRG();
    KeyPairGeneration kpg = new KeyPairGeneration();

    public String privateKeyGeneration(){
        String hashPrivateKey = kpg.X;
        return hashPrivateKey;
    }

    public String publicKeyGeneration(){
        String hashPublicKey = MD5HEX.md5Custom(kpg.Y);
        return hashPublicKey;
    }

    public static String [][] keysArray;

    public String [][] creationKeysArray(Integer s, Integer w, Integer N){
        keysArray = new String[2][N];
        for(int i = 0; i < N; i++) {
            kpg.generatePairKey(s, w);
            keysArray[0][i] = privateKeyGeneration();
            keysArray[1][i] = publicKeyGeneration();
        }
        return keysArray;
    }

    public static String root;
    public static String[][] tree;
    public static int countLayer;

    public String treeBilding(String [][] keysArray, Integer N){ // Tree building with L-Tree
        countLayer = (int)Math.ceil(Binarylog.binlog((double) N)) + 1;
        if(keysArray[1].length % 2 == 0)
            tree = new String[countLayer][N];
        else
            tree = new String[countLayer][N+1];
        for(int i = 0; i < N; i++) {
            tree[0][i] = keysArray[1][i];
        }
        int k = 0;
        for (int i = 1; i < countLayer; i++) {
            for (int j = 0; j < N / (Math.pow(2, i - 1)); j += 2) {
                if(tree[i - 1][j + 1] != null)
                    tree[i][k] = MD5HEX.md5Custom(tree[i - 1][j] + tree[i - 1][j + 1]);
                else
                    tree[i][k] = tree[i - 1][j];
                k++;
            }
            k = 0;
        }

        for (int i = 0; i < countLayer; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(tree[i][j] + "\t");
            }
            System.out.println();
        }

        root = tree[countLayer-1][0];
        return root;
    }
}
