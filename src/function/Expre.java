package function;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;
public class Expre {
    public static Function logAB=new Function("LogAB",2) {
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
    static public String translate (String input)throws ArrayIndexOutOfBoundsException{//对字符串进行处理，使其能够被正确解析
        boolean absState=true;//是否还有绝对值函数的标记
        while(absState) {
            int point1=-1;
            for(int i=0;i<input.length();i++) {
                if(input.charAt(i)=='|'){
                    point1=i;
                    break;
                }
            }
            if(point1==-1) {
                absState=false;
            }
            else {
                boolean stateBegin=false;//是否开始的标记
                int num=0;
                int point2=-1;
                for(int i=point1;i<input.length();i++) {
                    if(input.charAt(i)=='|') {
                        if(stateBegin&&num==0) {
                            point2=i;
                            break;
                        }
                    }
                    else if(input.charAt(i)=='(') {
                        num++;
                        stateBegin=true;
                    }
                    else if(input.charAt(i)==')') {
                        num--;
                    }
                }
                if(point2==-1) {
                    return "";
                }
                else {
                    input=input.substring(0,point1)+"abs"+input.substring(point1+1,point2)+input.substring(point2+1);
                }

            }
        }
        boolean gaussState=true;//是否还有高斯函数的标记
        while(gaussState) {
            int point1=-1;
            int point2=-1;
            for(int i=0;i<input.length();i++) {
                if(input.charAt(i)=='['){
                    point1=i;
                    break;
                }
            }
            if(point1==-1) {
                gaussState=false;
            }
            else {
                int num=1;
                for(int i=point1+1;i<input.length();i++){
                    if(input.charAt(i)=='[') {
                        num++;
                    }
                    else if(input.charAt(i)==']') {
                        num--;
                        if(num==0) {
                            point2=i;
                            break;
                        }
                    }
                }
                input=input.substring(0,point1)+"floor("+input.substring(point1+1,point2)+")"+input.substring(point2+1);
            }
        }
        boolean piState=true;
        while(piState){
            int point=-1;
            for(int i=0;i<input.length();i++){
                if(input.charAt(i)=='P')
                {
                    point=i;
                    break;
                }
            }
            if(point==-1) {
                piState=false;
            }
            else {
                input=input.substring(0,point)+"pi"+input.substring(point+2);
            }
        }
        while(input.contains("log")) {
            int point = input.indexOf("log");
            input=input.substring(0,point)+"LogAB"+input.substring(point+3);
        }
        return input;
    }
    static public double count(String input){
        Expression e = new ExpressionBuilder(input).functions(logAB,ln,lg).build();
        return e.evaluate();
    }
    static public int getPosition(String input,int position) {
        if((input.charAt(position-1)<='9'&&input.charAt(position-1)>='0')){
            position=position-1;
        }
        else if(input.charAt(position-1)=='('||input.charAt(position-1)==')'||input.charAt(position-1)=='^'||input.charAt(position-1)=='√'||input.charAt(position-1)=='+'||input.charAt(position-1)=='-'||input.charAt(position-1)=='*'||input.charAt(position-1)=='/'||input.charAt(position-1)=='.'||input.charAt(position-1)=='|'||input.charAt(position-1)==','||input.charAt(position-1)=='e') {
            position=position-1;
        }
        else if(input.charAt(position-1)=='c'||input.charAt(position-1)=='o'||input.charAt(position-1)=='s'||input.charAt(position-1)=='i'||input.charAt(position-1)=='n'||input.charAt(position-1)=='l'||input.charAt(position-1)=='g'||input.charAt(position-1)=='t') {
            for(int i=position-1;i>=0;i--) {
                if(input.charAt(i)==' ') {
                    position=i;
                    break;
                }
            }
        }
        else if(input.charAt(position-1)=='[') {
            position=position-1;
        }
        else if(input.charAt(position-1)==']') {
            position=position-2;
        }
        else if(input.charAt(position-1)=='I'){
            position=position-2;
        }
        else if(input.charAt(position-1)=='P'){
            position=position-1;
        }
        return position;
    }
    static public String doBack(String input,int position){
        if(input.charAt(position-1)<='9'&&input.charAt(position-1)>='0') {
            input=input.substring(0,position-1)+input.substring(position);
        }
        else if(input.charAt(position-1)=='('||input.charAt(position-1)==')'||input.charAt(position-1)=='^'||input.charAt(position-1)=='√'||input.charAt(position-1)=='+'||input.charAt(position-1)=='-'||input.charAt(position-1)=='*'||input.charAt(position-1)=='/'||input.charAt(position-1)=='.'||input.charAt(position-1)=='|'||input.charAt(position-1)==','||input.charAt(position-1)=='e'||input.charAt(position-1)=='!'){
            input=input.substring(0,position-1)+input.substring(position);
        }
        else if(input.charAt(position-1)=='c'||input.charAt(position-1)=='o'||input.charAt(position-1)=='s'||input.charAt(position-1)=='i'||input.charAt(position-1)=='n'||input.charAt(position-1)=='l'||input.charAt(position-1)=='g'||input.charAt(position-1)=='t') {
            int point1=-1;
            int point2=-1;
            for(int i=position-1;i>=0;i--) {
                if(input.charAt(i)==' ')
                {
                    point1=i;
                    break;
                }
            }
            for(int i=position-1;i<input.length();i++) {
                if (input.charAt(i) == ' ') {
                    point2 = i;
                    break;
                }
            }
            input=input.substring(0,point1)+input.substring(point2+1);
        }
        else if(input.charAt(position-1)=='[') {
            int point=-1;
            int num=1;
            for(int i=position;i<input.length();i++) {
                if(input.charAt(i)=='[') {
                    num++;
                }
                else if(input.charAt(i)==']'){
                    num--;
                    if(num==0)
                    {
                        point=i;
                        break;
                    }
                }
            }
            input=input.substring(0,position-1)+input.substring(position,point)+input.substring(point+1);
        }
        else if(input.charAt(position-1)==']') {
            int point=-1;
            int num=1;
            for(int i=position-2;i>=0;i--) {
                if(input.charAt(i)==']') {
                    num++;
                }
                else if(input.charAt(i)=='['){
                    num--;
                    if(num==0)
                    {
                        point=i;
                        break;
                    }
                }
            }
            input=input.substring(0,point)+input.substring(point+1,position-1)+input.substring(position);
        }
        else if(input.charAt(position-1)=='I'){
            input=input.substring(0,position-2)+input.substring(position);
        }
        else if(input.charAt(position-1)=='P'){
            input=input.substring(0,position-1)+input.substring(position+1);
        }
        return input;
    }
    static public int updateCursorPosition(String input,int position){
        if(input.charAt(position-1)=='c'||input.charAt(position-1)=='o'||input.charAt(position-1)=='s'||input.charAt(position-1)=='i'||input.charAt(position-1)=='n'){
            for(int i=position-1;i<input.length();i++){
                if(input.charAt(i)==' '){
                    position=i+2;
                    break;
                }
            }
        }
        return position;
    }
}
