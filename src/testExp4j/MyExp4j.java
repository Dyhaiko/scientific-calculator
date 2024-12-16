package testExp4j;
import function.Expre;

public class MyExp4j {
    public static void main(String[] args)
    {
        String a="1/(x+1)";
        double result;
        try {
            result = Expre.countDefiniteIntegral(a, 0, 2);
        }catch (RuntimeException o){
            result=0;
        }
        System.out.println(result);
    }
}
