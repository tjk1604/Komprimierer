package Komprimierer.main;

import java.util.Optional;

public class CompressedText{

    private Code code;

    private String cypher;

    public CompressedText(final String cypher, final Code code){
        this.code=Optional.of(code.createCopy());
        this.cypher=cypher.substring(1);
    }

    public static CompressedText fromString(String compressedTextAsString){
        return null;
    }

    public static CompressedText fromCodeBuilder(HuffmanCodeBuilder codeBuilder){
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

    public Optional<Code> getCode() {
        return code;
    }

    public String getCypher() {
        return "1"+cypher;
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
