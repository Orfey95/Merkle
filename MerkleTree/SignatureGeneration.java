package MerkleTree;

public class SignatureGeneration {
    WOTS_CR.SignatureGeneration sg = new WOTS_CR.SignatureGeneration();
    public static String OneTimeSignature = "";
    public String authPathBit = "";

    public String authPathCalculate(String key, String [][] tree, Integer N, Integer countLayer){
        int keyIndex = 0;
        authPathBit = "";
        String authPath = "";
        for(int i = 0; i < N; i++) {
            if(tree[0][i].compareTo(key) == 0)
                keyIndex = i;
        }
        for(int i = 0; i < countLayer - 1; i++) {
            if(keyIndex % 2 == 0){
                keyIndex ++;
                authPathBit += 0;
            }
            else {
                keyIndex --;
                authPathBit += 1;
            }
            if(tree[i][keyIndex] != null)
                authPath += tree[i][keyIndex];
            else
                authPath += tree[i][keyIndex-1];
            keyIndex /= 2;
        }
        return authPath;
    }

    public String oneTimeSignatureGeneration(String Message, Integer s, Integer w, String X) {
        sg.generateSignature(Message, s, w, X);
        OneTimeSignature = sg.SIGNATURE;
        return sg.SIGNATURE;
    }

    public String SignatureGeneration(String key, String Message, Integer s, Integer w, String [][] tree, Integer N, Integer countLayer, String root, String X){
        String SIGNATURE = key + oneTimeSignatureGeneration(Message, s, w, X) + authPathCalculate(key, tree, N, countLayer) + authPathBit + root;
        return SIGNATURE;// oneTimeY + oneTimeSig + authPath + root
    }
}
