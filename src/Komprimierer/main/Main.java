package Komprimierer.main;

public class Main {
    public static void main(String[] args) {
        HuffmanCode test=new BijectiveHuffmanCode("teststringtiesngngstir");
        System.out.println(test.toString());
        System.out.println(test.enCypher("teststringtiesngngstir"));
        System.out.println(test.enCypher("teststringtiesngngstir",16));
        System.out.println(test.deCypher(test.enCypher("teststringtiesngngstir")));

    }
}