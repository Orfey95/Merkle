package MerkleTree;
import Config.*;
public class SignatureVerification {

    WOTS_CR.SignatureVerification sv = new WOTS_CR.SignatureVerification();

    public boolean allSignatureVerify(Integer countLayer, String authPath, Integer s, Integer w, String realRoot, String SIGNATURE, String Message, String Y){
        boolean verify = false;
        if(oneTimeSignatureVerify(SIGNATURE, Message, s, w, Y) == false) {
            System.out.println("OneTimeSignature is not valid");
            return verify;
        }
        verify = merkleKeyVerify(Y, countLayer, authPath, s, realRoot);
        return  verify;
    }

    public boolean oneTimeSignatureVerify(String SIGNATURE, String Message, Integer s, Integer w, String Y){
        boolean equalSignature;
        equalSignature = sv.verifySignature(SIGNATURE, Message, s, w, Y);
        return  equalSignature;
    }

    public boolean merkleKeyVerify(String key, Integer countLayer, String SIGNATURE, Integer s, String realRoot){
        String authPath = getAuthPath(SIGNATURE, s, countLayer);
        String authPathBit = getAuthPathBit(SIGNATURE, s, countLayer);
        boolean verify = false;
        String root = key;
        for(int i = 0; i < countLayer - 1; i++) {
            String temp = authPath;
            temp = temp.substring(i * s / 4, i * s / 4 + s / 4); // нахождение подстроки с длиной в s символ
            if(temp.compareTo(root) != 0) {
                if(authPathBit.substring(i, i + 1).compareTo("0") == 0) {
                    root += temp;
                }
                else{
                    temp += root;
                    root = temp;
                }
                root = MD5HEX.md5Custom(root);
            }
        }
        if(root.compareTo(realRoot) == 0)
            verify = true;
        System.out.println("root: " + realRoot);
        System.out.println("r: " + root);
        return  verify;
    }

    public String getAuthPath(String SIGNATURE, Integer s, Integer countLayer){
        String authPath;
        authPath = SIGNATURE.substring(s/4 + s * WOTS_CR.KeyPairGeneration.t, (s/4 + s * WOTS_CR.KeyPairGeneration.t) + (s/4 * (countLayer - 1)));
        System.out.println("autpath: " + authPath);
        return authPath;
    }

    public  String getAuthPathBit(String SIGNATURE, Integer s, Integer countLayer){
        String authPathBit;
        authPathBit = SIGNATURE.substring(s/4 + s * WOTS_CR.KeyPairGeneration.t + (s/4 * (countLayer - 1)), (s/4 + s * WOTS_CR.KeyPairGeneration.t) + (s/4 * (countLayer - 1)) + (countLayer - 1));
        System.out.println("authPathBit: " + authPathBit);
        return authPathBit;
    }
}
