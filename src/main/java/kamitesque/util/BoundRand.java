package kamitesque.util;

import java.util.Random;

public class BoundRand {

    public static int nextInt(Random rand, int a, int b) {
        return Math.max(rand.nextInt(b), a);
    }
}
