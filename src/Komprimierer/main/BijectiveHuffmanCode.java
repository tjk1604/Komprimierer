package Komprimierer.main;

import Komprimierer.utils.datenstrukturen.StringList;
import Komprimierer.utils.zahlensystem.CustomSystem;

import java.util.ArrayList;
import java.util.HashMap;

public class BijectiveHuffmanCode extends HuffmanCode{

    private HashMap<String, String> codesMappedToStrings;
    public BijectiveHuffmanCode(final String text){
        super(text);
        createReverseCode();
    }

    public BijectiveHuffmanCode(StringList tree){
        super(tree);
        createReverseCode();
    }

    public BijectiveHuffmanCode(HuffmanCode huffmanCode){
        super(huffmanCode);
        createReverseCode();
    }

    private void createReverseCode(){
        this.codesMappedToStrings=new HashMap<>();
        for(String str: keySet()){
            this.codesMappedToStrings.put(get(str), str);
        }
    }

    public String enCypher(String text){
        boolean cypherPossible=true;
        StringBuilder result=new StringBuilder();
        while(text.length()!=0&&cypherPossible) {
            cypherPossible=false;
            for (int i = 1; i <= biggestValueLength; i++) {
                if(this.codesMappedToStrings.containsKey(text.substring(0,i))){
                    result.append(this.codesMappedToStrings.get(text.substring(0,i)));
                    text=text.substring(i);
                    i=biggestValueLength+1;
                    cypherPossible=true;
                }
            }
        }
        return result.toString();
    }

    public String enCypher(String text, int power){
        boolean cypherPossible=true;
        boolean useCustomSystem=CustomSystem.useCustomSystem(this.base, power);
        StringBuilder result=new StringBuilder();
        StringBuilder tempresult=new StringBuilder();
        int textlength=text.length();
        int tempresultlength;
        while(textlength!=0&&cypherPossible) {
            cypherPossible=false;
            for (int i = 1; i <= biggestValueLength; i++) {
                if(this.codesMappedToStrings.containsKey(text.substring(textlength-i))){
                    tempresult.insert(0, this.codesMappedToStrings.get(text.substring(textlength-i)));
                    text=text.substring(0,textlength-i);
                    i=biggestValueLength+1;
                    cypherPossible=true;
                }
            }
            tempresultlength=tempresult.length();
            if(tempresultlength>=power){
                int nextChar=CustomSystem.cast(tempresult.charAt(tempresultlength-1));
                for(int i=1; i<power;i++){
                    int nextDigit=CustomSystem.cast(tempresult.charAt(tempresultlength-i-1));
                    for(int j=0; j<i;j++){
                        nextDigit=nextDigit*this.base;
                    }
                }
                if(useCustomSystem){
                    result.insert(0, CustomSystem.cast(nextChar));
                }else{
                    result.insert(0, (char) nextChar);
                }
                tempresult.delete(tempresultlength-power+1, tempresultlength);
            }
        }
        return result.toString();
    }

    public String deCypher(String cypher){
        boolean deCypherPossible=true;
        StringBuilder result=new StringBuilder();
        while(cypher.length()!=0&&deCypherPossible) {
            deCypherPossible=false;
            for (int i = 1; i <= biggestCodeLength; i++) {
                if(this.containsKey(cypher.substring(0,i))){
                    result.append(this.get(cypher.substring(0,i)));
                    cypher=cypher.substring(i);
                    i=biggestCodeLength+1;
                    deCypherPossible=true;
                }
            }
        }
        return result.toString();
    }

    private String deCode(String code){
        return null;
    }

    private String encode(String letter){
        return null;
    }
}
