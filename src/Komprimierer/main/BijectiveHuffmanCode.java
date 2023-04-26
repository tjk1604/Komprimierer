package Komprimierer.main;

import Komprimierer.utils.datenstrukturen.StringList;

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
}
