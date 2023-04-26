package Komprimierer.utils.zahlensystem;

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
        throw new IllegalArgumentException("char not in Komprimierer.utils.zahlensystem.CustomSystem");
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

    public static String convertSystem(final int source, final int target, String text){
        int validtest=2;
        int sourcepower=1;
        while(validtest!=source&&validtest<=32768){
            validtest=validtest*2;
            sourcepower++;
        }
        if(validtest!=source){
            throw new IllegalArgumentException();
        }
        validtest=2;
        int targetpower=1;
        while(validtest!=target&&validtest<=32768){
            validtest=validtest*2;
            targetpower++;
        }
        if(validtest!=target){
            throw new IllegalArgumentException();
        }

        StringBuilder converted=new StringBuilder();
        if(targetpower<sourcepower){
            int finalTargetpower = targetpower;
            int finalSourcepower = sourcepower;
            text.chars().forEach(cha->converted.append(convertChar(finalSourcepower, finalTargetpower,(char)cha)));
            return converted.toString();
        }else{
            return text;
        }

    }

    private static String convertChar(int source, int target, char ch){
        StringBuilder storage=new StringBuilder(ch);
        StringBuilder result=new StringBuilder();
        while(source<target){
            int finalSource = source;
            storage.chars().forEach(cha->{
                result.append((char)cha/(finalSource -1));
                result.append((char)cha-((cha/ finalSource -1)*(finalSource -1)));
            });
            source=source+1;
            storage.setLength(0);
            storage.append(result);
            result.setLength(0);
        }
        return result.toString();
    }
}
