package function;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;
public class Expre {
    public static Function logAB=new Function("logAB",2) {
        public double apply(double... var1)
        {
            return Math.log(var1[0])/Math.log(var1[1]);
        }
    };
    static public String translate(String input){//对字符串进行处理，使其能够被正确解析
        boolean absState=false;//用于判断绝对值的位置
        for(int i=0;i<input.length();i++)
        {
            if(input.charAt(i)=='√')
            {
                input=input.substring(0,i)+"sqrt"+input.substring(i+1);
            }
            else if(input.charAt(i)=='|')
            {
                if(!absState)
                {
                    input=input.substring(0,i)+"abs("+input.substring(i+1);
                    absState=true;
                }
                else
                {
                    input=input.substring(0,i)+")"+input.substring(i+1);
                    absState=false;
                }
            }
        }
        return input;
    }
    static public double count(String input){
        Expression e = new ExpressionBuilder(input).function(logAB).build();
        return e.evaluate();
    }
}
