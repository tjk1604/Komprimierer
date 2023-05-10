package Komprimierer.main;

import Komprimierer.utils.datenstrukturen.StringList;
import Komprimierer.utils.zahlensystem.CustomSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class HuffmanCode extends HashMap<String, String> implements Code{
    protected int biggestValueLength;
    protected int biggestCodeLength;

    final int base;
    public HuffmanCode(final String text){
        this.base=2;
        this.putAll(new HuffmanCodeBuilder(text,2).getCode());
        findLongestEntries();
    }

    public HuffmanCode(final String text, final int base){
        this.base=base;
        this.putAll(new HuffmanCodeBuilder(text, base).getCode());
        findLongestEntries();
    }

    public HuffmanCode(StringList tree){
        createCode(tree, new LinkedList<Integer>());
        findLongestEntries();
        this.base=tree.size();

    }

    public HuffmanCode(HuffmanCode huffmanCode){
        for(String entry:huffmanCode.keySet()){
            put(entry, huffmanCode.get(entry));
        }
        findLongestEntries();
        this.base= huffmanCode.getBase();
    }

    private void createCode(StringList list, LinkedList<Integer> praefix){
        if(list.size()>1){
            for(int i=list.size()-1; i>=0; i--){
                LinkedList<Integer> nextPraefix=new LinkedList<>(praefix);
                nextPraefix.add(0, i);
                createCode(list.get(i), nextPraefix);
            }
        }else{

            put(CustomSystem.intListToString(praefix, true), list.toString());
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


    private int getBase(){
        return this.base;
    }
    @Override
    public Code createCopy() {
        return new HuffmanCode(this);
    }

    public String toString(){
        StringBuilder str=new StringBuilder();
        str.append("[");
        for(String key:this.keySet()){
            str.append("(");
            str.append(this.get(key));
            str.append("|");
            str.append(key);
            str.append(")");
        }
        str.append("]");
        return str.toString();
    }

    public String enCypher(String text){
        return null;
    }

    public String enCypher(String text, int power){
        return null;
    }

    public String deCypher(String cypher){
        return null;
    }

    public String deCypher(String cypher, int power){
        return null;
    }

    private String deCode(String code){
        return null;
    }

    private String encode(String letter){
        return null;
    }
}
