package firstcalculator.GraphicCalculator;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Function_Solution {

    ShowDialog sd = new ShowDialog();
    public String getFunction(String str){
        //去除空格 并去除y=
        str = str.replace(" ","");
        str = str.toLowerCase();
        if(str.length() >= 2 && str.charAt(0) == 'y' && str.charAt(1) == '='){
            return str.substring(2);
        }else{
            return str;
        }
    }

    //try and catch,
    public boolean checkFunction(String function){
        boolean check = true;
        try{
            //调用exp4j
            Expression e = new ExpressionBuilder(function)
                    .variables("x")
                    .build()
                    .setVariable("x", 1);  // 用一个随机值替换变量，看是否能够成功计算
            //设置变量，构建函数，选取变量值进行计算 通过计算来抛出异常
            double result = e.evaluate();
            System.out.println("函数表达式 y=" + function + "有效，函数已保存");


        }catch(Exception e){
            check = false;
            System.out.println("函数表达式 y=" + function + "无效："+e.getMessage());
        }

        return check;
    }

    //在确认函数表达式正确的前提下计算

    public double calculateFunction(String function, double x) {
        double result = 0;
        Expression e = new ExpressionBuilder(function)
                .variables("x")
                .build()
                .setVariable("x", x);
        result = e.evaluate();
        return result;
    }



}
