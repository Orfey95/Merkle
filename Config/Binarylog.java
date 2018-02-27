package Config;
public class Binarylog {
    public static Double binlog(Double value){
        value = Math.log10(value) / Math.log10(2.);
        return value;
    }
}

