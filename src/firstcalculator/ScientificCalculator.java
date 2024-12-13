package firstcalculator;

import firstcalculator.FunctionCalculator.Definite_Integral_Calculator;
import firstcalculator.FunctionCalculator.UncertaintyCalculator;
import firstcalculator.GraphicCalculator.Function_Draw;
import firstcalculator.GraphicCalculator.Function_Input;
import function.Expre;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class ScientificCalculator extends JFrame {
    private JTextField jTextField;
    private JPanel jPanel = new JPanel();
    private JButton[] jButtons;
    //用于存储历史记录的列表组件
    private JList<String> historyList;
    //存储历史记录的列表模型
    private DefaultListModel<String> historyModel;
    private int historyNum;
    private String preExpression;
    private int prePosition;
//    private CardLayout cardLayout = new CardLayout();
//    private JPanel cardPanel = new JPanel();

    public ScientificCalculator(){
        //切换界面
        historyNum=0;
        preExpression="";
        prePosition=0;
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
        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建绘图界面
                Function_Draw graphic_Drawer = new Function_Draw();
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

        this.setTitle("科学计算器");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);

        Border margain = new EmptyBorder(new Insets(100,0,100,0));
        jTextField = new JTextField(30);
        jTextField.setText("\uFFFD");
        jTextField.setEditable(false);
        jTextField.setBorder(margain);
        jTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(jTextField,"North");

        jPanel.setLayout(new GridLayout(7, 6, 3, 3));
        String name[] = {
                "x^2", "x^3", "1/x", "[x]", "C","Back",
                "√x", "sin", "cos", "tan", "<-","->",
                "x^y", "e^x", "(", ")", "/","*",
                "10^x", "7", "8", "9", "-","+",
                "log", "4", "5", "6", "e","PI",
                "lg", "1", "2", "3", "<<",">>",
                "ln", "abs", "0", ".", "=","t"
        };
        jButtons = new JButton[name.length];
        MyActionListener actionListener = new MyActionListener();
        for(int i = 0; i < name.length; i++){

            jButtons[i] = new JButton(name[i]);
            jButtons[i].addActionListener(actionListener);//为按钮添加到监视器
            jButtons[i].setBackground(Color.lightGray);
            if(name[i].equals("="))
                jButtons[i].setBackground(Color.RED);
            else if((int)name[i].charAt(0)>=48 && (int)name[i].charAt(0)<=57
                    && name[i].length() == 1)
                jButtons[i].setBackground(Color.WHITE);
            else if(name[i].equals("Back"))
                jButtons[i].setBackground(Color.GRAY);
            jButtons[i].setForeground(Color.black);
            jButtons[i].setFont(new Font("Arial", Font.PLAIN, 15));
//            jButtons[i].addActionListener(new MyActionListener());
            jPanel.add(jButtons[i],"Center");
        }

        // 创建历史记录列表模型
        historyModel = new DefaultListModel<>();

        // 创建历史记录列表组件
        historyList = new JList<>(historyModel);
        // 设置列表的可见行数
        historyList.setVisibleRowCount(30);
        // 设置列表的背景色
//        historyList.setBackground(Color.LIGHT_GRAY);
        // 设置列表的字体大小
        Font listFont = new Font("Arial", Font.PLAIN, 20);
        historyList.setFont(listFont);
//        设置列表的大小
//        historyList.setPreferredSize(new Dimension(300, 500));

        // 设置自定义的 ListCellRenderer 以增加间距和设置间距颜色
        historyList.setCellRenderer(new HistoryListCellRenderer());
        JScrollPane scrollPane = new JScrollPane(historyList);
        // 设置滚动条的大小
        scrollPane.setPreferredSize(new Dimension(300, 500));

        this.add(scrollPane, "East");
        this.add(jPanel);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }
    //事件监视器
    class MyActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String input=e.getActionCommand();
            boolean isWrong=false;
            String wrongMessage="";
            double ans = 0;
            if(jTextField.getText().equals("Grammar error")||jTextField.getText().equals("Answer is not existing")) {
                if(input.equals("C")){
                    preExpression="";
                    prePosition=0;
                    jTextField.setText(Expre.transition(preExpression,prePosition));
                }
                else if(input.equals("Back")){
                    jTextField.setText(Expre.transition(preExpression,prePosition));
                }

            }
            else {
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
                else if(input.equals("=")){
                    if(Expre.isLegal(preExpression)){
                        try{
                            ans=Expre.count(Expre.turnIntoExpression(preExpression));
                            historyModel.addElement(Expre.transitionWithOutCursor(preExpression,prePosition)+" = "+Double.toString(ans));
                            prePosition=0;
                            preExpression="";
                        }catch (RuntimeException o){
                            isWrong=true;
                            wrongMessage="Answer is not existing";
                        }
                    }
                    else{
                        isWrong=true;
                        wrongMessage="Grammar error";
                    }
                }
                else if(input.equals("<<")){
                    prePosition=0;
                }
                else if(input.equals(">>")){
                    prePosition=preExpression.length();
                }
                else{
                    preExpression=preExpression.substring(0,prePosition)+input+preExpression.substring(prePosition);
                    prePosition++;
                }
                if(isWrong==true){
                    jTextField.setText(wrongMessage);
                }
                else {
                    jTextField.setText(Expre.transition(preExpression,prePosition));
                }

            }

        }
    }

    public static void main(String[] args){
        ScientificCalculator scientificCalculator = new ScientificCalculator();
    }



static class HistoryListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        // 设置背景颜色
        c.setBackground(Color.WHITE);
        // 为每个元素添加底部边框作为间距，并设置为白色
        ((JComponent) c).setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, Color.WHITE));
        return c;
    }
}

}
