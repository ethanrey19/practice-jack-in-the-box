public class Main {
    public static void main(String[] args) {
        JackInTheBox jackInTheBox = new JackInTheBox();

        while(!jackInTheBox.getOpen()){
            jackInTheBox.crank();
        }

        jackInTheBox.close();

    }
}
