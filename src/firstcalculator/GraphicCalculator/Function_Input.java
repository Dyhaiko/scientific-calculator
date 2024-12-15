package firstcalculator.GraphicCalculator;

import firstcalculator.FunctionCalculator.Definite_Integral_Calculator;
import firstcalculator.FunctionCalculator.UncertaintyCalculator;
import firstcalculator.ScientificCalculator;
import function.Expre;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Function_Input extends JFrame {
    private JTextField jTextField;
    private JPanel jPanel = new JPanel();
    private JButton[] jButtons;
    private int cursorPosition;//用于存放鼠标光标的位置
//    private CardLayout cardLayout = new CardLayout();
//    private JPanel cardPanel = new JPanel();
    public String function_expression;
    private String preExpression;
    private int prePosition;

    public Function_Input() {
        //切换界面
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("切换");
        menuBar.add(menu);
        JMenuItem item1 = new JMenuItem("科学计算器");
        JMenuItem item2 = new JMenuItem("绘图计算器");
        JMenuItem item3 = new JMenuItem("不确定度计算器");
        JMenuItem item4 = new JMenuItem("函数图像");
        JMenuItem item5 = new JMenuItem("定积分");
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu.add(item4);
        menu.add(item5);
        cursorPosition = 0;
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建绘图界面
                ScientificCalculator scientificCalculator = new ScientificCalculator();
                // 隐藏当前界面
                setVisible(false);
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Funcion_Draw graphicCalculator = new Funcion_Draw();
                graphicCalculator.init();
                // 隐藏当前界面
                setVisible(false);
            }
        });
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建绘图界面
                UncertaintyCalculator uncertaintyCalculator = new UncertaintyCalculator();
                uncertaintyCalculator.init();
                // 隐藏当前界面
                setVisible(false);
            }
        });
        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建绘图界面
                Funcion_Draw graphic_Drawer = new Funcion_Draw();
                graphic_Drawer.init();
                // 隐藏当前界面
                setVisible(false);
            }
        });
        item5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建绘图界面
                Definite_Integral_Calculator definite_Integral_Calculator = new Definite_Integral_Calculator();
                definite_Integral_Calculator.init();
                // 隐藏当前界面
                setVisible(false);
            }
        });

        this.setTitle("输入函数表达式");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        Border margain = new EmptyBorder(new Insets(100, 0, 100, 0));
        jTextField = new JTextField(30);
        jTextField.setText("\uFFFD");
        jTextField.setEditable(false);
        jTextField.setBorder(margain);
        jTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(jTextField, "North");
        preExpression="";
        prePosition=0;

        jPanel.setLayout(new GridLayout(7, 6, 3, 3));
        String name[] = {
                "x^2", "x^3", "1/x", "[x]", "C","Back",
                "√x", "sin", "cos", "tan", "<-","->",
                "x^y", "e^x", "(", ")", "/","*",
                "10^x", "7", "8", "9", "-","+",
                "log", "4", "5", "6", "x","y",
                "lg", "1", "2", "3", "PI","Draw",
                "ln", "abs", "0", ".", "e","Save"
        };
        jButtons = new JButton[name.length];
        MyActionListener actionListener = new MyActionListener();
        for (int i = 0; i < name.length; i++) {

            jButtons[i] = new JButton(name[i]);
            jButtons[i].addActionListener(actionListener);//为按钮添加到监视器
            jButtons[i].setBackground(Color.lightGray);
            if (name[i].equals("="))
                jButtons[i].setBackground(Color.RED);
            else if ((int) name[i].charAt(0) >= 48 && (int) name[i].charAt(0) <= 57
                    && name[i].length() == 1)
                jButtons[i].setBackground(Color.WHITE);
            else if (name[i].equals("Back"))
                jButtons[i].setBackground(Color.GRAY);
            jButtons[i].setForeground(Color.black);
            jButtons[i].setFont(new Font("Arial", Font.PLAIN, 15));
//            jButtons[i].addActionListener(new MyActionListener());
            jPanel.add(jButtons[i], "Center");
        }

        this.add(jPanel);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    //事件监视器
    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String input=e.getActionCommand();
            if(input.equals("C")){
                preExpression="";
                prePosition=0;
            }
            else if(input.equals("sin")){
                preExpression=preExpression.substring(0,prePosition)+"S"+preExpression.substring(prePosition);
                prePosition+=1;
            }
            else if(input.equals("cos")){
                preExpression=preExpression.substring(0,prePosition)+"C"+preExpression.substring(prePosition);
                prePosition+=1;
            }
            else if(input.equals("tan")){
                preExpression=preExpression.substring(0,prePosition)+"T"+preExpression.substring(prePosition);
                prePosition+=1;
            }
            else if(input.equals("lg")){
                preExpression=preExpression.substring(0,prePosition)+"L"+preExpression.substring(prePosition);
                prePosition+=1;
            }
            else if(input.equals("ln")){
                preExpression=preExpression.substring(0,prePosition)+"N"+preExpression.substring(prePosition);
                prePosition+=1;
            }
            else if(input.equals("abs")){
                preExpression=preExpression.substring(0,prePosition)+"A"+preExpression.substring(prePosition);
                prePosition+=1;
            }
            else if(input.equals("->")){
                if(prePosition<preExpression.length()){
                    prePosition=prePosition+1;
                }
            }
            else if(input.equals("<-")){
                if(prePosition>0){
                    prePosition=prePosition-1;
                }
            }
            else if(input.equals("x^y")){
                preExpression=preExpression.substring(0,prePosition)+"()^()"+preExpression.substring(prePosition);
                prePosition++;
            }
            else if(input.equals("10^x")){
                preExpression=preExpression.substring(0,prePosition)+"10^()"+preExpression.substring(prePosition);
                prePosition+=4;
            }
            else if(input.equals("PI")){
                preExpression=preExpression.substring(0,prePosition)+"p"+preExpression.substring(prePosition);
                prePosition+=1;
            }
            else if(input.equals("x^2")){
                preExpression=preExpression.substring(0,prePosition)+"()^2"+preExpression.substring(prePosition);
                prePosition+=1;
            }
            else if(input.equals("x^3")){
                preExpression=preExpression.substring(0,prePosition)+"()^3"+preExpression.substring(prePosition);
                prePosition+=1;
            }
            else if(input.equals("1/x")){
                preExpression=preExpression.substring(0,prePosition)+"1/()"+preExpression.substring(prePosition);
                prePosition+=3;
            }
            else if(input.equals("√x")){
                preExpression=preExpression.substring(0,prePosition)+"Q)"+preExpression.substring(prePosition);
                prePosition+=1;
            }
            else if(input.equals("Back")){
                if(prePosition>0){
                    int temp=Expre.getBackPosition(preExpression,prePosition);
                    preExpression=Expre.goBack(preExpression,prePosition);
                    prePosition=temp;
                }
            }
            else if(input.equals("log")){
                preExpression=preExpression.substring(0,prePosition)+"G,)"+preExpression.substring(prePosition);
                prePosition+=1;
            }
            else if(input.equals("e^x")){
                preExpression=preExpression.substring(0,prePosition)+"e^()"+preExpression.substring(prePosition);
                prePosition+=3;
            }
            else if(input.equals("[x]")){
                preExpression=preExpression.substring(0,prePosition)+"[]"+preExpression.substring(prePosition);
                prePosition+=1;
            }
            else if(input.equals("Draw")){

            }
            else if(input.equals("Save")){
                if(Expre.isLegal(preExpression)){
                    preExpression="isLegal";
                }
                else{
                    preExpression="grammar wrong";
                }
            }
            else{
                preExpression=preExpression.substring(0,prePosition)+input+preExpression.substring(prePosition);
                prePosition++;
            }
            jTextField.setText(Expre.transition(preExpression,prePosition));
        }
    }
    public static void main(String[] args){
        Function_Input fi = new Function_Input();
    }
}
