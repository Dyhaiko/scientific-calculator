package function;

import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;
public class Expre {
    static public double count(String input){
        Expression e = new ExpressionBuilder(input).build();
        return e.evaluate();
    }
}
