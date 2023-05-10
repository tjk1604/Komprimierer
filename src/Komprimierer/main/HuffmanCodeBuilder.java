package Komprimierer.main;


import Komprimierer.utils.datenstrukturen.IntList;
import Komprimierer.utils.datenstrukturen.Pair;
import Komprimierer.utils.datenstrukturen.StringList;

import java.util.*;

public class HuffmanCodeBuilder {
    private HashMap<String, Integer> alphabet;
    private StringList tree;
    private final String text;
    private final int base;
    private final HuffmanCode huffmancode;
    //private final LinkedList<Integer> cypherAsList;


    public HuffmanCodeBuilder(final String text){
        this(text, createAlphabet(text), 2);
    }

    public HuffmanCodeBuilder(final String text, final int base){
        this(text, createAlphabet(text), base);
    }

    /*public HuffmanCodeBuilder(String text, int weightedAlphabet){
        //TODO
    }*/

    public HuffmanCodeBuilder(final String text, final HashMap<String, Integer> alphabet){
        this(text, alphabet, 2);

    }

    public HuffmanCodeBuilder(final String text, final HashMap<String, Integer> alphabet, final int base){
        assert(1<base&&base<65);//TODO, aktivieren, untescheidung bijek u wheighted
        this.alphabet=new HashMap<>(alphabet);
        this.base=base;
        this.tree=createTree();
        while(this.tree.size()==1){
            this.tree=this.tree.pollFirst();
        }
        this.huffmancode=new BijectiveHuffmanCode(this.tree);
        this.text=text;
        printCode(this.tree, "");
    }

    private void printCode(StringList list, String praefix){
        if(list.size()>1){
            for(int i=list.size()-1; i>=0; i--){
                printCode(list.get(i), praefix+ "|"+i);
            }
        }else{
            System.out.println(list.toString()+":"+this.alphabet.get(list.toString())+":"+praefix);
        }
    }

    private static HashMap<String, Integer> createAlphabet(String text){
        HashMap<String, Integer> alphabet=new HashMap<>();
        text.chars().forEach(ch->{
            String str=Character.toString(ch);
            if(alphabet.containsKey(str)){
                alphabet.put(str, alphabet.get(str)+1);
            }else{
                alphabet.put(str, 1);
            }
        });
        return alphabet;
    }

    private StringList createTree(){
        //TODO what if base>2
        TreeSet<Pair<StringList, IntList>> treeBuilder=new TreeSet<>((pair1, pair2)->{
            if(pair1.getValue().getSum()>pair2.getValue().getSum()){
                return 1;
            }else{
                return -1;
            }});
        alphabet.keySet().stream().forEach(str->treeBuilder.add(new Pair(new StringList(str), new IntList(alphabet.get(str)))));
        while(treeBuilder.size()>base-1){
            Pair<StringList, IntList> newKnoten=new Pair<>(new StringList(), new IntList());
            for(int i=0; i<base; i++){
                newKnoten.getKey().add(treeBuilder.first().getKey());
                newKnoten.getValue().add(treeBuilder.first().getValue());
                treeBuilder.pollFirst();
            }
            treeBuilder.add(newKnoten);
            System.out.println(treeBuilder);
            System.out.println(newKnoten.getValue().toString());
        }
        if(base>2){
            Pair<StringList, IntList> newKnoten=new Pair<>(new StringList(), new IntList());
            for(Pair<StringList, IntList> knoten: treeBuilder){
                newKnoten.getKey().add(knoten.getKey());
                newKnoten.getValue().add(knoten.getValue());
            }
            System.out.println(newKnoten);
            treeBuilder.clear();
            optimizeKnoten(newKnoten);
            treeBuilder.add(newKnoten);
        }
        System.out.println(treeBuilder);
        return treeBuilder.first().getKey();
    }

    private void optimizeKnoten(Pair<StringList, IntList> knoten){
        int subKnotenCount=knoten.getKey().size();
        if(1<subKnotenCount&&subKnotenCount<this.base) {
            int index = subKnotenCount - 1;
            StringList newKnotenStrings = new StringList();
            IntList newKnotenInts = new IntList();
            LinkedList<Integer> newKnotenOriginIndex = new LinkedList<>();
            for (int i = this.base - subKnotenCount; i > 0; i--) {
                while (index >= 0&&!(knoten.getKey().get(index).size() > 0 )) {
                    index--;
                }
                if (index < 0) {
                    subKnotenCount = 0;
                } else {
                    newKnotenStrings.addFirst(knoten.getKey().get(index).pollLast());
                    newKnotenInts.addFirst(knoten.getValue().get(index).pollLast());
                    newKnotenOriginIndex.addFirst(index);
                }
            }
            if (subKnotenCount != 0) {
                for (int i = index-1; i >= 0; i--) {
                    for (int j = 0; j < this.base; j++) {
                        if (knoten.getValue().get(i).size() > 0 && knoten.getValue().get(i).getLast().getSum() < newKnotenInts.get(j).getSum()) {
                            if (j > 0) {
                                newKnotenStrings.add(j, knoten.getKey().get(i).pollLast());
                                newKnotenInts.add(j, knoten.getValue().get(i).pollLast());
                                newKnotenOriginIndex.add(j, i);
                                knoten.getKey().get(newKnotenOriginIndex.peekFirst()).add(newKnotenStrings.pollFirst());
                                knoten.getValue().get(newKnotenOriginIndex.pollFirst()).add(newKnotenInts.pollFirst());
                            }
                            j = this.base;
                        }
                    }
                }
                for (int i = this.base - subKnotenCount; i > 0; i--) {
                    knoten.getKey().addFirst(newKnotenStrings.pollFirst());
                    knoten.getValue().addFirst(newKnotenInts.pollFirst());
                }
                for (int i = 0; i < this.base; i++) {
                    optimizeKnoten(new Pair<>(knoten.getKey().get(i), knoten.getValue().get(i)));
                }
            }
        }
    }

    public HashMap<String, Integer> getAlphabet() {
        return new HashMap<>(alphabet);
    }

    public String getText() {
        return text;
    }

    public int getBase() {
        return base;
    }

    public HuffmanCode getCode(){
        return this.huffmancode;
    }

}
