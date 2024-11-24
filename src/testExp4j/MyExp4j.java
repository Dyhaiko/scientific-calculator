package testExp4j;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
public class MyExp4j {
    public static void main(String[] args)
    {
        Expression e=new ExpressionBuilder("3*sin(cos(x))").variable("x").build();
        e.setVariable("x",Math.PI);
        double result=e.evaluate();
        System.out.println(result);
    }
}
