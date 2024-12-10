package firstcalculator.GraphicCalculator;

import javax.swing.*;

public class ShowDialog {
    public void showEnterWarningDialog(JFrame jf){
        String wrongMessage = "错误的函数表达式！正确的函数表达式如下：\n" +
                "y = x, y = sinx,y = sin(x),y=x^2,y=2^x\n" +
                "......";
        JOptionPane.showMessageDialog(jf, wrongMessage, "WARNING", JOptionPane.WARNING_MESSAGE);
    }

    public void showEmptyWarningDialog(JFrame jf){
        String message = "输入的表达式为空！空表达式已保存";
        JOptionPane.showMessageDialog(jf,message,"WARNING",JOptionPane.WARNING_MESSAGE);

    }

    //处理放缩过度
    public void showScaleMessageDialog(JFrame jf){
        JOptionPane.showMessageDialog(jf, "尺寸过大/过小\n" +
                "scale>=0.25 && scale<=5", "WARNING", JOptionPane.WARNING_MESSAGE);
    }

    //处理三个表达式均为空的情况
    public  void showThreeFunctionsEmpty(JFrame jf){
        JOptionPane.showMessageDialog(jf, "你的三个函数表达式均为空！", "WARNING", JOptionPane.WARNING_MESSAGE);
    }

    //弹出保存信息
    public void showFunctionSavedDialog(JFrame jf,String function){
        JOptionPane.showMessageDialog(jf, "你的表达式:"+function+"有效，已保存", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
    }











}
