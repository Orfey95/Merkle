package Config;
import java.security.SecureRandom;

public class PRG {

    private int ShiftRegister = 0x5F2348EC;

    private  void GenerateStartValue(){
        SecureRandom rand = new SecureRandom();
        ShiftRegister = rand.nextInt(2147483647);
    }

    private int LFSR() {
        ///(31,3,0)
        ShiftRegister = ((((ShiftRegister >> 3)
                ^ (ShiftRegister)) /* это нулевая степень */
                & 0x00000001)
                << 30) /* а это, "выбивающаяся из ряда" 31-я степень */
                | (ShiftRegister >> 1);
        return ShiftRegister & 0x00000001;
    }

    public String Random128(){
        String str = "";
        GenerateStartValue();
        for (int i=0; i<128; i++){
            str += LFSR();
        }
        return  str;
    }
}