package Komprimierer;

import java.util.HashMap;

public abstract class BijectiveHuffmanCode extends HuffmanCode{
    private final HashMap<String, String> invertedCode=new HashMap<>();

    public BijectiveHuffmanCode(final String text){
        super(text);
        this.entrySet().stream().forEach(entry->this.invertedCode.put(entry.getValue(), entry.getKey()));
    }

    public String encode(String chars){
        return this.invertedCode.get(chars);
    }

    public String decode(String code){
        return this.get(code);
    }

    public String encipher(String text){
        StringBuilder cypher=new StringBuilder();
        int charsLength;
        while(text.length()>0){
            charsLength=this.biggestValueLength;
            while(charsLength>text.length()){
                charsLength--;
            }
            while(charsLength>0){
                String subString=text.substring(0, charsLength);
                if (this.containsValue(subString)){
                    cypher.append(this.encode(subString));
                    text=text.substring(charsLength);
                    charsLength=0;
                }
                charsLength--;
            }
            if(charsLength==0){
                throw new IllegalArgumentException();
            }
        }
        return cypher.toString();
    }

    public String decipher(String cypher){
        StringBuilder text=new StringBuilder();
        int codeLength;
        while(cypher.length()>0){
            codeLength=this.biggestCodeLength;
            while(codeLength>cypher.length()){
                codeLength--;
            }
            while(codeLength>0){
                String subString=cypher.substring(0, codeLength);
                if (this.containsKey(subString)){
                    text.append(this.decode(subString));
                    cypher=cypher.substring(codeLength);
                    codeLength=0;
                }
                codeLength--;
            }
            if(codeLength==0){
                throw new IllegalArgumentException();
            }
        }
        return text.toString();
    }
}
