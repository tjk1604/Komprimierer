package Komprimierer;

import java.util.LinkedList;

public class IntList extends LinkedList<IntList> {
    private int sum;

    public IntList(){
        super();
        this.sum=0;
    }

    public IntList(Integer x){
        super();
        this.sum=x;
    }

    public IntList(LinkedList<IntList> list){
        super(list);
        this.sum=0;
        for (IntList x:list) {
            this.sum=this.sum+x.getSum();
        }
    }

    public boolean add(IntList list){
        this.sum=this.sum+list.getSum();
        return super.add(list);
    }

    public int getSum(){
        return this.sum;
    }

    public String toString(){
        StringBuilder result=new StringBuilder();
        result.append("/");
        this.stream().forEach(intl->result.append("["+intl.toString()+"]"));
        result.append(this.sum+"|");
        return result.toString();
    }
}
