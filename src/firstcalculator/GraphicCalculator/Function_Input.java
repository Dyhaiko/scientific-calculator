package firstcalculator.GraphicCalculator;

import firstcalculator.FunctionCalculator.DefiniteIntegralCalculator;
import firstcalculator.FunctionCalculator.UncertaintyCalculator;
import firstcalculator.ScientificCalculator;
import function.Expre;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static function.Expre.*;

public class Function_Input extends JFrame {
    private JTextField jTextField;
    private JPanel jPanel = new JPanel();
    private JButton[] jButtons;
    //用于存储历史记录的列表组件
    private JList<String> historyList;
    private DefaultListModel<String> historyModel;
    private int historyNum;
    private String preExpression;
    private int prePosition;
    private ShowDialog sd = new ShowDialog();
    private JFrame jf;

    public String[] pre = new String[3];{
        for(int i = 0;i < 3;i++){
            pre[i] = "";
        }
    }
    private int prePointer;
    private int preNum;
    public void set_Function(String[] func,String[] pre){
        System.arraycopy(pre, 0, this.pre, 0, 3);
        for(int i=0;i<3;i++){
            if(!pre[i].isEmpty()){
                preNum++;
            }
        }
        for(int i=0;i<3;i++){
            if(i==prePointer){
                historyModel.setElementAt("->"+transitionWithOutCursor(pre[i],0),i);
            }
            else{
                historyModel.setElementAt(transitionWithOutCursor(pre[i],0),i);
            }
        }
    }
    public Function_Input() {
        //切换界面
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("切换");
        menuBar.add(menu);
        JMenuItem item1 = new JMenuItem("科学计算器");
        JMenuItem item2 = new JMenuItem("函数输入");
        JMenuItem item3 = new JMenuItem("不确定度计算器");
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
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
                Function_Input fi = new Function_Input();
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
        preNum=0;
        jPanel.setLayout(new GridLayout(7, 6, 3, 3));
        String name[] = {
                "x^2", "1/x", "[x]", "x", "C","Back",
                "√x", "sin", "cos", "tan", "<-","->",
                "x^y", "e", "(", ")", "/","*",
                "10^x", "7", "8", "9", "-","+",
                "log", "4", "5", "6", "Del","↑",
                "lg", "1", "2", "3", "PI","Draw",
                "ln", "abs", "0", ".", "DI","Save"
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
            jPanel.add(jButtons[i], "Center");
        }
        historyModel = new DefaultListModel<>();// 创建历史记录列表模型
        historyList = new JList<>(historyModel);// 创建历史记录列表组件
        historyModel.addElement("");
        historyModel.addElement("");
        historyModel.addElement("");
        prePointer=0;
        historyList.setVisibleRowCount(3);// 设置列表的可见行数
        Font listFont = new Font("Arial", Font.PLAIN, 20);// 设置列表的字体大小
        historyList.setFont(listFont);
        JScrollPane scrollPane = new JScrollPane(historyList);
        scrollPane.setPreferredSize(new Dimension(300, 500));// 设置滚动条的大小
        this.add(scrollPane, "East");
        this.add(jPanel);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        jf = this;

    }
    //事件监视器
    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String input=e.getActionCommand();
            if(input.equals("C")){
                preExpression="";
                prePosition=0;
            }
            else if(input.equals("↑")){
                prePointer=(prePointer+Math.min(3,preNum+1)-1)%Math.min(3,preNum+1);
            }
            else if(input.equals("Del")){
                if(preNum!=0&&!pre[prePointer].isEmpty()) {
                    for (int i = prePointer; i < 2; i++) {
                        pre[i] = pre[i + 1];
                    }
                    if(preNum==3){
                        pre[2]="";
                    }
                    preNum--;
                    prePointer=(prePointer+Math.min(3,preNum+1)-1)%Math.min(3,preNum+1);
                }
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
                String[] function = new String[3];{
                    for(int i = 0;i < 3;i++)
                        function[i] = "";
                }
                for(int i = 0;i < 3;i++){
                    if(historyModel.size()<i+1){
                        break;
                    }
                    function[i] = transitionWithOutCursor(pre[i],0);
                }
                new Function_Draw(function,pre);
                setVisible(false);
            }
            else if(input.equals("Save")){
                boolean isLegal = isLegal(preExpression);
                if(isLegal){
                    if(pre[prePointer].isEmpty()){
                        preNum++;
                    }
                    pre[prePointer] = preExpression;
                    prePointer=(prePointer+1)%3;
                    prePosition = 0;
                    preExpression = "";

                }else{
                    sd.showEnterWarningDialog(jf);
                }
            }else if(input.equals("DI")){
                DefiniteIntegralCalculator di = new DefiniteIntegralCalculator();
                di.DefiniteIntegralCalculatorStarter(pre);
                setVisible(false);
            }
            else{
                preExpression=preExpression.substring(0,prePosition)+input+preExpression.substring(prePosition);
                prePosition++;
            }
            jTextField.setText(Expre.transition(preExpression,prePosition));
            for(int i=0;i<3;i++){
                if(i==prePointer){
                    historyModel.setElementAt("->"+transitionWithOutCursor(pre[i],0),i);
                }
                else{
                    historyModel.setElementAt(transitionWithOutCursor(pre[i],0),i);
                }
            }
        }
    }
    public static void main(String[] args){
        Function_Input fi = new Function_Input();
    }
}