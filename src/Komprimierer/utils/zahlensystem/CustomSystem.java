package Komprimierer.utils.zahlensystem;

import java.util.LinkedList;

public abstract class CustomSystem{
    public static char[] alphabet=new char[]{'0','1','2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'ö','ü'};

    public static boolean useCustomSystem(final int base, final int power){
        assert(base>1);
        assert(power>0);
        int base2=base;
        for(int i=1; i<power; i++){
            base2=base2*base;
        }
        return base2<65;
    }

    public static boolean useCustomSystem(int start, final int base, final int powerDifference){
        assert(base>1);
        assert(start>0);
        if(powerDifference>0){
            for(int i=0; i<powerDifference; i++){
                start=start*base;
            }
        }else{
            for(int i=0; i>powerDifference; i--){
                start=start/base;
            }
        }
        assert(start>=base);
        return start<65;
    }

    public static boolean useCustomSystem(final char ch){
        for(int i=0; i<64; i++){
            if(ch==alphabet[i]){
                return true;
            }
        }
        return false;
    }

    public static char cast(int x){
        return alphabet[x];
    }

    public static int cast(char x){
        for(int i=0; i<64; i++){
            if(x==alphabet[i]){
                return i;
            }
        }
        throw new IllegalArgumentException("char not in CustomSystem");
    }

    public static String toReverseBaseString(int number, int base, int power){
        assert(base<65);
        StringBuilder result=new StringBuilder();
        for(int i=0; i<power; i++){
            result.append(CustomSystem.cast(number%base));
            number=number/base;
        }
        return result.toString();
    }

    public static String convertSystem(int targetPower, String original){
        assert(targetPower>0);
        assert(testSystemValidity(original.charAt(1), original.charAt(0)));
        assert(original.length()>2);
        int base;
        int sourcePower;
        LinkedList<Integer> originalList=new LinkedList<>();
        CastHelper<Integer, Character> castHelper;
        if(CustomSystem.useCustomSystem(original.charAt(0))){
            castHelper= (list, ch)->list.add(0, CustomSystem.cast(ch));
            base=CustomSystem.cast(original.charAt(1))+1;
            sourcePower=determineSourcePower(CustomSystem.cast(original.charAt(1)), CustomSystem.cast(original.charAt(0)));
        }else{
            base=(int) original.charAt(1)+1;
            castHelper= (list, ch)->list.add(0, (int) ch.charValue());
            sourcePower=determineSourcePower((int) original.charAt(1), (int) original.charAt(0));
        }


        assert(base<65);
        if(sourcePower==targetPower){
            return original;
        }else {
            StringBuilder result=new StringBuilder();
            result.append(createPraefix(base, targetPower));
            original = original.substring(2);
            original.chars().forEach(ch -> castHelper.cast(originalList, Character.valueOf((char) ch)));
            System.out.println(originalList);

            LinkedList<Integer> targetList=convertSystemInList(originalList, base, sourcePower, targetPower);
            result.append(intListToString(targetList, CustomSystem.useCustomSystem(base, targetPower)));
            return result.toString();
        }
    }


    private static LinkedList<Integer> convertSystemInList(LinkedList<Integer> originalList, final int base, final int sourcePower, final int targetPower){
        LinkedList<Integer> targetList=new LinkedList<>();
        final int bufferSize=sourcePower*targetPower;
        StringBuilder buffer= new StringBuilder();
        while(originalList.size()>=targetPower) {
            for (int i = 0; i < bufferSize; i=i  + sourcePower) {
                buffer.append(CustomSystem.toReverseBaseString(originalList.get(0), base, sourcePower));
                originalList.remove(0);
            }
            for(int i=0; i<bufferSize; i=i+targetPower){
                int current=0;
                for(int j=0; j<targetPower; j++){
                    int sum=CustomSystem.cast(buffer.charAt(0));
                    for(int k=0; k<j; k++) {
                        sum =  base * sum;
                    }
                    current=current+sum;
                    buffer.deleteCharAt(0);
                }
                targetList.add(current);
            }
        }
        if(originalList.size()!=0){
            do{
                originalList.add(0);
            }while(originalList.size()<targetPower);
            targetList.addAll(convertSystemInList(originalList, base, sourcePower, targetPower));
        }

        while(targetList.getLast()==0){
            targetList.removeLast();
        }
        System.out.println(targetList);
        return targetList;

    }

    public static String intListToString(LinkedList<Integer> list, final boolean useCustomSystem){
        StringBuilder result=new StringBuilder();
        CastHelper<Integer, StringBuilder> casthelper;
        System.out.println(list);
        if(useCustomSystem){
            casthelper=(liste, stringBuilder)->liste.forEach(number->stringBuilder.insert(0, CustomSystem.cast(number)));
        }else{
            casthelper=(liste, stringBuilder)->liste.forEach(number->stringBuilder.insert(0, (char)(int)number));
        }
        casthelper.cast(list, result);
        System.out.println(list);
        return result.toString();
    }


    private static int determineSourcePower(int base, int max){
        base=base+1;
        max=max+1;
        int power=1;
        while(max>base){
            max=max/base;
            power=power+1;
        }
        if(max!=base){
            throw new IllegalArgumentException("IllegalSystem");
        }
        return power;
    }

    private static boolean testSystemValidity(final char base, final char max){
        int baseint;
        int maxint;
        if(CustomSystem.useCustomSystem(max)){
            baseint=CustomSystem.cast(base)+1;
            maxint=CustomSystem.cast(max)+1;
        }else{
            baseint=(int) base+1;
            maxint=(int) max+1;
        }
        while(maxint>baseint){
            if(maxint%baseint!=0){
                return false;
            }
            maxint=maxint/baseint;
        }
        return maxint==baseint;
    }

    private static boolean testPowerInBounds(final int base, final int power){
        int x=base;
        for(int i=1; i<power;i++){
            x=x*base;
            if(x>65536){
                return false;
            }
        }
        return true;
    }

    private static String createPraefix(final int base, final int power){
        StringBuilder praefix=new StringBuilder();
        int x=base;
        for(int i=1; i<power;i++){
            x=x*base;
            if(x>65536){
                throw new IllegalArgumentException("TargetPower too high");
            }
        }
        System.out.println((char)x-1);
        if(x>64){
            praefix.append((char) (x-1));
            praefix.append((char) (base-1));
        }else{
            praefix.append(CustomSystem.cast(x-1));
            praefix.append(CustomSystem.cast(base-1));
        }
        return praefix.toString();
    }
}
