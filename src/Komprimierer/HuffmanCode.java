package Komprimierer;

import java.util.HashMap;

public abstract class HuffmanCode extends HashMap<String, String> implements Code{
    protected final int biggestValueLength;
    protected final int biggestCodeLength;
    public HuffmanCode(final String text){
        this.put("00","a");
        this.put("01","b");
        this.put("10","c");
        this.put("11","ac");
        this.biggestValueLength=this.entrySet().stream().mapToInt(entry->entry.getValue().length()).max().getAsInt();
        this.biggestCodeLength=this.entrySet().stream().mapToInt(entry->entry.getKey().length()).max().getAsInt();
    }
}
