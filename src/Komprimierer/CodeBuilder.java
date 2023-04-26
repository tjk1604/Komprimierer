package Komprimierer;

public abstract class CodeBuilder {
    private  String text;
    private String cypher;

    public CodeBuilder(String text){

    }

    public String getText(){
        return this.text;
    }

    public String getCypher(){
        return this.cypher;
    }
}
