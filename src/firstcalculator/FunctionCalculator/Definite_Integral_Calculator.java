package firstcalculator.FunctionCalculator;

import firstcalculator.GraphicCalculator.Function_Input;
import firstcalculator.ScientificCalculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Definite_Integral_Calculator {
    private JFrame jf = new JFrame("不确定度计算器");
    private static final int MYWIDTH = 1000;
    private static final int MYHEIGHT = 800;

    public void init(){
        //设置菜单栏
        JMenuBar menuBar = new JMenuBar();
        jf.setJMenuBar(menuBar);
        JMenu menu = new JMenu("切换");
        menuBar.add(menu);
        JMenuItem item1 = new JMenuItem("科学计算器");
        JMenuItem item2 = new JMenuItem("绘图计算器");
        JMenuItem item3 = new JMenuItem("不确定度计算器");
        JMenuItem item5 = new JMenuItem("定积分");
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu.add(item5);

        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScientificCalculator scientificCalculator = new ScientificCalculator();
                scientificCalculator.setVisible(true);
                jf.setVisible(false);
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Function_Input fi = new Function_Input();
                // 隐藏当前界面
                jf.setVisible(false);
            }
        });
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建绘图界面
                UncertaintyCalculator uncertaintyCalculator = new UncertaintyCalculator();
                uncertaintyCalculator.init();
                // 隐藏当前界面
                jf.setVisible(false);
            }
        });

        //设置主窗口的大小，可见，默认关闭方式，位于windows窗口中间
        jf.setSize(MYWIDTH,MYHEIGHT);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

    }


    public static void main(String[] args){
        new UncertaintyCalculator().init();
    }
}
