import java.util.Random;

public class RandomWrapper {

    private Random random;

    public RandomWrapper (){
        random = new Random();
    }

    public int nextInt(int bound){
        return random.nextInt(bound);
    }

}
