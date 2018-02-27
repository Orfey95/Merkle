package WOTS_CR;
import Config.*;
public class SignatureGeneration {
    
    MD5Binary md5B = new MD5Binary();
    public String CBinary = ""; // CheckSum binary

    public String messageAddZeros(String Message, Integer s, Integer w){
        int temp = s % w; // Проверка на кратность s к w
        String tempS = ""; //Строка для добавочных нулей
        if(temp > 0){
            for(int i = 0; i < w - temp; i++){
                tempS += "0";
            }
            Message = tempS + Message; // Подстановка нулей в начало сообщения
        }
        return Message;
    }

    public Integer [] messageSeparate(String Message, Integer s, Integer w){
        int CDecimal = 0;

        Message = messageAddZeros(Message, s, w);
        Integer [] blocksOfMessage = new Integer[(int)Math.ceil(Message.length() / w)];
        int k = 0;  //счетчик для индексов массива
        for(int i = 0; i < Message.length(); i = i + w) { // проход по массиву через каждые w символа для нахождения новой подстроки
            String S1 = Message.substring(i, i + w); // нахождение подстроки с длиной в w символа
            blocksOfMessage[k++] = Integer.parseInt(S1, 2); // присваивание подстроки к элементу массива
            CDecimal += ((int) Math.pow(2, w)) - Integer.parseInt(S1, 2); // Подсчет CheckSum
        }
        CBinary = Integer.toBinaryString(CDecimal); //Перевод CheckSum в 2-ю сс
        //System.out.println("CheckSum = " + CDecimal + "(10)");
        //System.out.println("CheckSum = " + CBinary + "(2)");
        return blocksOfMessage;
    }

    public String checkSumAddZeros(String CBinary, Integer s, Integer w){
        int tCheckSum = KeyPairGeneration.t2;
        int temp = (tCheckSum * w) - CBinary.length(); // Проверка на кратность s к w
        String tempS = ""; //Строка для добавочных нулей
        if(temp > 0){
            for(int i = 0; i < temp; i++){
                tempS += "0";
            }
            CBinary = tempS + CBinary; // Подстановка нулей в начало CheckSum
        }
        return CBinary;
    }

    public Integer [] checkSumSeparate(Integer s, Integer w){
        CBinary = checkSumAddZeros(CBinary, s, w);
        int tCheckSum = KeyPairGeneration.t2;
        Integer [] blocksOfCheckSum = new Integer[tCheckSum];
        int k = 0;  //счетчик для индексов массива
        for(int i = 0; i < CBinary.length(); i = i + w) { // проход по массиву через каждые w символа для нахождения новой подстроки
            String S1 = CBinary.substring(i, i + w); // нахождение подстроки с длиной в w символа
            blocksOfCheckSum[k++] = Integer.parseInt(S1, 2); // присваивание подстроки к элементу массива
        }
        return blocksOfCheckSum;
    }

    public Integer [] messagePlusCheckSum(String Message, Integer s, Integer w){ // Соединение blocksOfMessage и blocksOfCheckSum
        Integer [] messageArray = messageSeparate(Message, s, w);
        Integer [] checkSumArray = checkSumSeparate(s, w);
        Integer [] fullArray = new Integer[KeyPairGeneration.t];
        int temp = (int)Math.ceil(messageAddZeros(Message, s, w).length() / w);
        for(int i = 0; i < temp; i++){
            fullArray[i] = messageArray[i];
        }
        int k = 0;  //счетчик для индексов массива
        for (int j = temp; j < KeyPairGeneration.t; j++) {
            fullArray[j] = checkSumArray[k];
            k++;
        }
        return fullArray;
    }

    public String SIGNATURE = "";

    public void generateSignature(String Message, Integer s, Integer w, String X) {
        Integer[]b = messagePlusCheckSum(Message, s, w);
        //System.out.println("Full Array = " + Arrays.toString(messagePlusCheckSum(Message, s, w)));
        String Xi = "";
        //System.out.println("blen = " + b.length);
        for (int i = 0; i < KeyPairGeneration.t; i++) {
            Xi = X.substring(i * s, i * s + s); // нахождение подстроки с длиной в s символ
            SIGNATURE += calculateSignatureI(Xi, b[i]);
        }
    }

    private String calculateSignatureI(String Xi, Integer bi) {
        String sigi = Xi;
        // System.out.println("bi" + bi);
        for (int i = 1; i <= bi; i++) {
            sigi = md5B.md5Custom(sigi);
        }
        return sigi;
    }
}

