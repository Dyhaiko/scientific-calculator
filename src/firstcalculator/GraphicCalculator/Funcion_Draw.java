
package firstcalculator.GraphicCalculator;

import firstcalculator.FunctionCalculator.UncertaintyCalculator;
import firstcalculator.ScientificCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Funcion_Draw {
    //窗口尺寸
    public static final int MYWIDTH = 1000;
    public static final int MYHEIGHT = 800;

    //缩放需求
    public static final int MAXSIZE = 5000;

    public String[] function = new String[3];
    {
        Arrays.fill(function, "");
    }
    Choice functionChooser = new Choice();

    public boolean[] canDraw = new boolean[3];
    {
        Arrays.fill(canDraw,false);
    }

    public JFrame jf = new JFrame("绘图计算器");
    TextField tips = new TextField("请输入函数表达式，格式为y=... 回车保存");
    TextField functionField = new TextField();
    TextField scaleField = new TextField();

    JButton actionButton = new JButton("Draw");
    JButton enlargerButton = new JButton("Enlarger");
    JButton reduceButton = new JButton("Reduce");
    JButton clearButton = new JButton("Clear");

    MyCanvas mc = new MyCanvas();

    //函数解决方案的实例化对象
    Function_Solution fs = new Function_Solution();

    public void init(){

        JMenuBar menuBar = new JMenuBar();
        jf.setJMenuBar(menuBar);
        JMenu menu = new JMenu("切换");
        menuBar.add(menu);
        JMenuItem item1 = new JMenuItem("科学计算器");
        JMenuItem item2 = new JMenuItem("绘图计算器");
        JMenuItem item3 = new JMenuItem("不确定度计算器");
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScientificCalculator scientificCalculator = new ScientificCalculator();
                scientificCalculator.setVisible(true);
                jf.setVisible(false);
            }
        });
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UncertaintyCalculator uncertaintyCalculator = new UncertaintyCalculator();
                uncertaintyCalculator.init();
                jf.setVisible(false);
            }
        });


        functionField.setColumns(20);
        functionField.setText(function[0]);
        scaleField.setColumns(15);
        //将三个函数表达式添加到下拉框中
        functionChooser.add("function1");
        functionChooser.add("function2");
        functionChooser.add("function3");

        tips.setColumns(20);
        tips.setEditable(false);
        tips.setForeground(Color.RED);

        JPanel jPanel1 = new JPanel();
        jPanel1.add(tips);
        jPanel1.add(functionChooser);
        jPanel1.add(functionField);
        actionButton.setBackground(Color.GREEN);
        functionField.setForeground(Color.RED);
        jPanel1.add(actionButton);
        jf.setLayout(new BorderLayout());
        jf.add(jPanel1, BorderLayout.SOUTH);

        //添加画布
        JPanel jPanel2 = new JPanel();
        jPanel2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(mc, BorderLayout.CENTER);
        jf.add(jPanel2, BorderLayout.CENTER);

        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout(new FlowLayout());
        //设置布局管理器：顺序水平放置

        enlargerButton.setBackground(Color.LIGHT_GRAY);
        reduceButton.setBackground(Color.LIGHT_GRAY);
        clearButton.setBackground(Color.LIGHT_GRAY);

        enlargerButton.setActionCommand("Enlarger");
        reduceButton.setActionCommand("Reduce");
        clearButton.setActionCommand("Clear");

        addButtonListener(enlargerButton);
        addButtonListener(reduceButton);
        addButtonListener(clearButton);

        jPanel3.add(enlargerButton);
        jPanel3.add(reduceButton);
        jPanel3.add(clearButton);

        JLabel label = new JLabel(" scale:");
        jPanel3.add(label);
        jPanel3.add(scaleField);
        jPanel1.add(jPanel3);



        //设置主窗口的大小，可见，默认关闭方式，位于windows窗口中间
        jf.setSize(MYWIDTH,MYHEIGHT);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);



    }


    //内部类，画布
    class MyCanvas extends Canvas{

    }

    public void addButtonListener(JButton b){

    }


    public static void main(String[] args){
        new Funcion_Draw().init();
    }



}
