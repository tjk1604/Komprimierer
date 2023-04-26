package Komprimierer;

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
        throw new IllegalArgumentException("char not in Komprimierer.CustomSystem");
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
}
