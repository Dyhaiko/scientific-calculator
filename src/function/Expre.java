package function;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;

public class Expre {
    public static Function logAB=new Function("logab",2) {
        public double apply(double... var1) {
            return Math.log(var1[1])/Math.log(var1[0]);
        }
    };
    public static Function lg=new Function("lg",1) {
        public double apply(double... doubles) {
            return Math.log(doubles[0])/Math.log(10);
        }
    };
    public static Function ln=new Function("ln") {
        public double apply(double... doubles) {
            return Math.log(doubles[0]);
        }
    };
    static public double count(String input){
        Expression e = new ExpressionBuilder(input).functions(logAB,ln,lg).build();
        return e.evaluate();
    }
    static public String transition(String input,int prePosition){
        String temp=input;
        temp=temp.substring(0,prePosition)+"\uFFFD"+temp.substring(prePosition);
        for(int i=0;i<temp.length();i++){
            if(temp.charAt(i)=='C'){
                temp=temp.substring(0,i)+"cos("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='S'){
                temp=temp.substring(0,i)+"sin("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='T'){
                temp=temp.substring(0,i)+"tan("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='p'){
                temp=temp.substring(0,i)+"PI"+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='L'){
                temp=temp.substring(0,i)+"lg("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='N'){
                temp=temp.substring(0,i)+"ln("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='Q'){
                temp=temp.substring(0,i)+"√("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='G'){
                temp=temp.substring(0,i)+"log("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='A'){
                temp=temp.substring(0,i)+"abs("+temp.substring(i+1);
            }
        }
        return temp;
    }
    static public String transitionWithOutCursor(String input,int prePosition){
        String temp=input;
        for(int i=0;i<temp.length();i++){
            if(temp.charAt(i)=='C'){
                temp=temp.substring(0,i)+"cos("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='S'){
                temp=temp.substring(0,i)+"sin("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='T'){
                temp=temp.substring(0,i)+"tan("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='p'){
                temp=temp.substring(0,i)+"PI"+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='L'){
                temp=temp.substring(0,i)+"lg("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='N'){
                temp=temp.substring(0,i)+"ln("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='Q'){
                temp=temp.substring(0,i)+"√("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='G'){
                temp=temp.substring(0,i)+"log("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='A'){
                temp=temp.substring(0,i)+"abs("+temp.substring(i+1);
            }
        }
        return temp;
    }
    static public int getBackPosition(String input,int prePosition){
        if(input.charAt(prePosition-1)==','||input.charAt(prePosition-1)==']'){
            return getBackPosition(input,prePosition-1);
        }
        return prePosition-1;
    }
    static public String goBack(String input,int prePosition){
        if(input.charAt(prePosition-1)==','||input.charAt(prePosition-1)==']'){
            return goBack(input,prePosition-1);
        }
        else{
            if(input.charAt(prePosition-1)=='G'){
                int count=0;
                int position = 0;
                for(int i=prePosition-1;i<input.length();i++){
                    if(input.charAt(i)=='G'){
                        count++;
                    }
                    else if(input.charAt(i)==','){
                        count--;
                        if(count==0){
                            position=i;
                            break;
                        }
                    }
                }
                return input.substring(0,prePosition-1)+input.substring(position+1);
            }
            else if(input.charAt(prePosition-1)=='['){
                int count =0;
                int position = 0;
                for(int i=prePosition-1;i<input.length();i++){
                    if(input.charAt(i)=='['){
                        count++;
                    }
                    else if(input.charAt(i)==']'){
                        count--;
                        if(count==0){
                            position=i;
                            break;
                        }
                    }
                }
                return input.substring(0,prePosition-1)+input.substring(prePosition,position)+input.substring(position+1);
            }
            else {
                return input.substring(0,prePosition-1)+input.substring(prePosition);
            }
        }
    }
    static public String turnIntoExpression(String input){
        String temp=input;
        for(int i=0;i<temp.length();i++){
            if(temp.charAt(i)=='C'){
                temp=temp.substring(0,i)+"cos("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='S'){
                temp=temp.substring(0,i)+"sin("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='T'){
                temp=temp.substring(0,i)+"tan("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='p'){
                temp=temp.substring(0,i)+"pi"+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='L'){
                temp=temp.substring(0,i)+"lg("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='N'){
                temp=temp.substring(0,i)+"ln("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='Q'){
                temp=temp.substring(0,i)+"sqrt("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='G'){
                temp=temp.substring(0,i)+"logab("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='A'){
                temp=temp.substring(0,i)+"abs("+temp.substring(i+1);
            }
            else if(temp.charAt(i)=='['){
                int count=0;
                int position=0;
                for(int j=i;j<temp.length();j++){
                    if(temp.charAt(j)=='['){
                        count++;
                    }
                    else if(temp.charAt(j)==']'){
                        count--;
                        if(count==0){
                            position=j;
                            break;
                        }
                    }
                }
                temp=temp.substring(0,i)+"floor("+temp.substring(i+1,position)+")"+temp.substring(position+1);
            }
        }
        return temp;
    }
    //这里的合法性只是说式子可以被正确解析，不代表可以一定能够求出值
    static public boolean isLegal(String input){
        if(input.isEmpty()){
            return false;
        }
        for(int i=0;i<input.length();i++){
            if(input.charAt(i)<='Z'&&input.charAt(i)>='A'){
                input=input.substring(0,i+1)+"("+input.substring(i+1);
            }
        }
        //判断是否合法先判断括号是否都有对应
        int numOfSquareBrackets=0;
        int numOfParentheses=0;
        for(int i=0;i<input.length();i++){
            if(input.charAt(i)=='['){
                for(int j=i;j<input.length();j++){
                    if(input.charAt(j)=='['){
                        numOfSquareBrackets++;
                    }
                    else if(input.charAt(j)==']'){
                        numOfSquareBrackets--;
                        if(numOfSquareBrackets==0){
                            break;
                        }
                    }
                    else if(input.charAt(j)=='('){
                        numOfParentheses++;
                    }
                    else if(input.charAt(j)==')'){
                        numOfParentheses--;
                    }
                }
                if(numOfParentheses!=0){
                    return false;
                }
            }
        }
        for(int i=0;i<input.length();i++){
            if(input.charAt(i)=='('){
                numOfParentheses++;
            }
            else if(input.charAt(i)==')'){
                numOfParentheses--;
            }
        }
        if(numOfParentheses!=0){
            return false;
        }
        else{
            //接下来判断括号内是否都是非空的；
            int frontParentheses=0;
            for(int i=0;i<input.length();i++){
                if(input.charAt(i)=='('||input.charAt(i)=='['){
                    frontParentheses=i;
                }
                else if(input.charAt(i)==')'||input.charAt(i)==']'){
                    if(i==frontParentheses+1){
                        return false;
                    }
                }
            }
            return true;
        }
    }
    static public double count(String input,double x){
        Expression e = new ExpressionBuilder(input).functions(logAB,ln,lg).variables("x").build().setVariable("x",x);
        return e.evaluate();
    }
    static public double countDefiniteIntegral(String input,double a,double b){

        double value=0;
        try{
            count(transitionWithOutCursor(input,0),a);
            count(transitionWithOutCursor(input,0),b);
        }
        catch (RuntimeException o){
            throw o;
        }
        for(int i=0;i<100000;i++){
            double temp;
            try{
                temp=count(transitionWithOutCursor(input,0),a+ (b-a)* (i+0.5) /100000)/100000;
                value+=temp;
            }
            catch(RuntimeException o){
                throw o;
            }
        }
        return value*(b-a);
    }
}


