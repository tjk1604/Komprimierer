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
        result.insert(0, "1");
        return result.toString();
    }

    public String enCypher(String text, int power){
        assert(power>0);
        assert(CustomSystem.testPowerInBounds(this.base, power));
        boolean cypherPossible=true;
        boolean useCustomSystem=CustomSystem.useCustomSystem(this.base, power);
        StringBuilder result=new StringBuilder();
        StringBuilder tempresult=new StringBuilder();
        int textlength=text.length();
        int tempresultlength=0;
        while((textlength!=0||tempresultlength!=0)&&cypherPossible) {
            while(tempresultlength<power&&textlength!=0&&cypherPossible){
                cypherPossible=false;
                for (int i = 1; i <= biggestValueLength; i++) {
                    if(this.codesMappedToStrings.containsKey(text.substring(textlength-i))){
                        tempresult.insert(0, this.codesMappedToStrings.get(text.substring(textlength-i)));
                        tempresultlength=tempresult.length();
                        text=text.substring(0, textlength-i);
                        textlength=textlength-i;
                        i=biggestValueLength+1;
                        cypherPossible=true;
                    }
                }
                if(textlength==0){
                    tempresult.insert(0,"1");
                    tempresultlength++;
                    while(tempresult.length()%power!=0){
                        tempresult.insert(0,"0");
                        tempresultlength++;
                    }
                }
            }
            int nextChar=CustomSystem.cast(tempresult.charAt(tempresultlength-1));
            for(int i=1; i<power; i++){
                int nextDigit=CustomSystem.cast(tempresult.charAt(tempresultlength-i-1));
                for(int j=0; j<i; j++){
                    nextDigit=nextDigit*base;
                }
                nextChar=nextChar+nextDigit;
            }
            if(useCustomSystem){
                result.insert(0, CustomSystem.cast(nextChar));
            }else{
                result.insert(0, (char)nextChar);
            }
            tempresult.delete(tempresultlength-power, tempresultlength);
            tempresultlength=tempresultlength-power;
        }
        return result.toString();
    }

    public String deCypher(String cypher){
        boolean deCypherPossible=true;
        StringBuilder result=new StringBuilder();
        cypher=cypher.substring(1);
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

    public String deCypher(String cypher, int power) {
        StringBuilder result=new StringBuilder();
        StringBuilder cache=new StringBuilder();
        boolean useCustomSystem=CustomSystem.useCustomSystem(this.base, power);
        int nextChar;
        if(useCustomSystem){
            nextChar=CustomSystem.cast(cypher.charAt(0));
        }else{
            nextChar=(int)cypher.charAt(0);
        }
        int maxStelle=1;
        for(int i=1; i<power; i++){
            maxStelle=maxStelle*this.base;
        }
        int kawieichdasnennensollteiltisthaltderStellenwert=maxStelle;
        for(int i=0; i<power; i++){
            if(nextChar>=kawieichdasnennensollteiltisthaltderStellenwert){
                cache.append("1");
                nextChar=nextChar-kawieichdasnennensollteiltisthaltderStellenwert;
            }else{
                cache.append("0");
            }
            kawieichdasnennensollteiltisthaltderStellenwert=kawieichdasnennensollteiltisthaltderStellenwert/this.base;
        }
        cache.delete(0, cache.indexOf("1")+1);
        int cypherlength=cypher.length();
        for(int j=1; j<cypherlength;j++){
            if(useCustomSystem){
                nextChar=CustomSystem.cast(cypher.charAt(j));
            }else{
                nextChar=(int)cypher.charAt(j);
            }
            kawieichdasnennensollteiltisthaltderStellenwert=maxStelle;
            for(int i=0; i<power; i++){
                if(nextChar>=kawieichdasnennensollteiltisthaltderStellenwert){
                    cache.append("1");
                    nextChar=nextChar-kawieichdasnennensollteiltisthaltderStellenwert;
                }else{
                    cache.append("0");
                }
                kawieichdasnennensollteiltisthaltderStellenwert=kawieichdasnennensollteiltisthaltderStellenwert/this.base;
            }
            while(cache.length()>=biggestCodeLength){
                boolean deCypherPossible=false;
                for (int i = 1; i <= biggestCodeLength; i++) {
                    if(this.containsKey(cache.substring(0,i))){
                        result.append(this.get(cache.substring(0,i)));
                        cache=cache.delete(0, i);
                        i=biggestCodeLength+1;
                        deCypherPossible=true;
                    }
                }
                if(!deCypherPossible){
                    throw new IllegalArgumentException("DecypherNotPossible");
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
