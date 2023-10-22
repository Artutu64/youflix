package fr.cytech.pau.youflix.Utils;

import java.util.Random;

public class RandomUtil {

    public static Long getRandomLong(int n){
        Random random = new Random();
        return random.nextLong(n);
    }

    public static Long getRandomId(){
        return getRandomLong(Integer.MAX_VALUE);
    }
    
}
