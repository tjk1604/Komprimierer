package Komprimierer;

import java.util.Optional;

public class CompressedText{

    private Optional<Code> code;

    private String cypher;

    public CompressedText(final String cypher, final Code code){
        this.code=Optional.of(code.createCopy());
        this.cypher="1"+cypher;
    }

    public static CompressedText fromString(String compressedTextAsString){
        return null;
    }

    public static CompressedText fromCodeBuilder(CodeBuilder codeBuilder){
        return null;
    }

    @Override
    public String toString(){
        return null;
    }

    public String deCompress(){
        return null;
    }

    public String deCompress(final int layers){
        return null;
    }

    public static String convertSystem(final int source, final int target, String text){
        int validtest=2;
        int sourcepower=1;
        while(validtest!=source&&validtest<=32768){
            validtest=validtest*2;
            sourcepower++;
        }
        if(validtest!=source){
            throw new IllegalArgumentException();
        }
        validtest=2;
        int targetpower=1;
        while(validtest!=target&&validtest<=32768){
            validtest=validtest*2;
            targetpower++;
        }
        if(validtest!=target){
            throw new IllegalArgumentException();
        }

        StringBuilder converted=new StringBuilder();
        if(targetpower<sourcepower){
            int finalTargetpower = targetpower;
            int finalSourcepower = sourcepower;
            text.chars().forEach(cha->converted.append(convertChar(finalSourcepower, finalTargetpower,(char)cha)));
            return converted.toString();
        }else{
            return text;
        }

    }

    private static String convertChar(int source, int target, char ch){
        StringBuilder storage=new StringBuilder(ch);
        StringBuilder result=new StringBuilder();
        while(source<target){
            int finalSource = source;
            storage.chars().forEach(cha->{
                result.append((char)cha/(finalSource -1));
                result.append((char)cha-((cha/ finalSource -1)*(finalSource -1)));
            });
            source=source+1;
            storage.setLength(0);
            storage.append(result);
            result.setLength(0);
        }
        return result.toString();
    }
    /*
    größter Character des           Nullen bis zum  10 Klartext   für LZ Menge an Zeichen
    verwendeten Zahlensystems       Beginn des eig  110 LZ        Präfix menge an           Menge an
    (Zb 1 für binär; 9 für dec)     Codes                         bits der eig Zahl         Zeichen-1
    (eigene zahlensysteme bis 64,                   1110HM        0->  <=16 (4bits)         als x-Bitzahl
    danach basierend auf unicode)                                 10-> 17-32(5bits)
                                                                  110->33-64(6bits)

    Lempel-ziv: alle zeichen in unicode aufzählen, dann Beginn Cypher

    Huffmancode:Codewort-String-Paare nach String- dann Codewortlänge sortieren

    Länge größter String: 0-> ||<5
                          10->||5-8 es folgt 3bitzahl
                          110->||9-16 es folgt 4 bitzahl etc

    0 -> keine Strings ||=1
    00 ->keine Strings ||<=2
    00001 -> keine String ||<=4
    Es gibt String mit ||=5; es folgt:



    0 ->keine Codewörter || =1
    00 -> keine Codewörter ||<=2
    etc.
    10 -> ||Codewörter dieser Länge <9 zb 10111 ->8; 10010->3
    110-> || Coderwörter dieser Länge 9-16 zb 1101111-> 16: 1101011->12


    Bsp Länge Anzahl      -> 10100    0    0    0    0    1            0 0 10001 10010 111010010 11111101000000 0 11111101101100
            1       0        ^längster    ^keine        ^Es gibt       ^Tabelle
            2       0       String || 5   Strings ||<5  Strings ||5
            3       2       Hier gibt es nur Strings der Länge 5
            4       5
            5       19
            6       65
            7       0
            8       109
    Dann Codewörter-Stringpaare aufzählen; dann Cypher
    */

}
