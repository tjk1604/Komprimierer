package Komprimierer;

import java.util.LinkedList;

public class StringList extends LinkedList<StringList>{
    private final String string;

    public StringList(){
        super();
        this.string="";
    }

    public StringList(String string){
        super();
        this.string=string;
    }

    public StringList(LinkedList<StringList> list){
        super(list);
        this.string="";
    }

    public String getString(){
        return this.string;
    }

    public boolean isFinal(){
        return this.string.equals("");
    }

    public String toString(){
        StringBuilder result=new StringBuilder();
        if(this.size()>0) {
            this.stream().forEach(strl -> result.append("[" + strl.toString() + "]"));
        }
        result.append(this.string);
        return result.toString();
    }

}
