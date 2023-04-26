package Komprimierer.main;

import Komprimierer.utils.datenstrukturen.StringList;

import java.util.HashMap;

public class HuffmanCode extends HashMap<String, String> implements Code{
    protected int biggestValueLength;
    protected int biggestCodeLength;
    public HuffmanCode(final String text){
        new HuffmanCodeBuilder(text).getCode();
        findLongestEntries();
    }

    public HuffmanCode(StringList tree){
        createCode(tree, "");
        findLongestEntries();
    }

    public HuffmanCode(HuffmanCode huffmanCode){
        for(String entry:huffmanCode.keySet()){
            put(entry, huffmanCode.get(entry));
        }
        findLongestEntries();
    }

    private void createCode(StringList list, String praefix){
        if(list.size()>1){
            for(int i=list.size()-1; i>=0; i--){
                createCode(list.get(i), praefix+i);
            }
        }else{
            put(praefix, list.toString());
        }
    }

    private void findLongestEntries(){
        this.biggestCodeLength=0;
        this.biggestValueLength=0;
        keySet().stream().forEach(str->{
            if(str.length()>this.biggestCodeLength){
                this.biggestCodeLength=str.length();
            }
            if(get(str).length()>this.biggestValueLength){
                this.biggestValueLength=get(str).length();
        }});
    }


    @Override
    public Code createCopy() {
        return new HuffmanCode(this);
    }
}
