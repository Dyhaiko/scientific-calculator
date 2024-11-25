package firstcalculator;

import function.Expre;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class GraphicCalculator extends JFrame{
    private JTextField jTextField;
    private JPanel jPanel = new JPanel();
    private JButton[] jButtons;

    public GraphicCalculator() {
        //切换页面
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu1 = new JMenu("切换");
        menuBar.add(menu1);

        JMenuItem item1 = new JMenuItem("科学计算器");
        JMenuItem item2 = new JMenuItem("绘图计算器");
        menu1.add(item1);
        menu1.add(item2);
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestCalculator testCalculator = new TestCalculator();
                testCalculator.setVisible(true);
                GraphicCalculator.this.setVisible(false);
            }
        });
        JMenu menu2 = new JMenu("功能");
        menuBar.add(menu2);
        JMenuItem item3 = new JMenuItem("函数表达式");
        JMenuItem item4 = new JMenuItem("绘制");
        menu2.add(item3);
        menu2.add(item4);
        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyCalculator myCalculator = new MyCalculator();
                myCalculator.setVisible(true);
                GraphicCalculator.this.setVisible(false);
            }
        });

        this.setTitle("科学计算器");
        this.setSize(400, 600);
        this.setLocationRelativeTo(null);

        Border margain = new EmptyBorder(new Insets(100, 0, 100, 0));
        jTextField = new JTextField(30);
        jTextField.setText("Please enter");
        jTextField.setEditable(false);
        jTextField.setBorder(margain);
        jTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(jTextField, "North");

        jPanel.setLayout(new GridLayout(7, 5, 3, 3));
        String name[] = {
                "sin", "PI", "e", "C", "Back",
                "cos", "1/x", "|x|", "x", "y",
                "tan", "(", ")", "=", "/",
                "√x", "7", "8", "9", "*",
                "x^2", "4", "5", "6", "-",
                "log", "1", "2", "3", "+",
                "ln", "+/-", "0", ".", "enter"
        };
        jButtons = new JButton[name.length];

        MyActionListener actionListener = new MyActionListener();

        for(int i = 0;i < name.length;i++){
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
            //注意这里 海哥注释了这一行也能输入 但是MyCalculator中有这一行 是检测按键是否按下的 可以看看要不要保留
            jButtons[i].addActionListener(new MyActionListener());
            jPanel.add(jButtons[i],"Center");
        }

        this.add(jPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);


    }



    public static void main(String args[]){
        GraphicCalculator graphicCalculator = new GraphicCalculator();
    }

    class MyActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){

        }
    }
}
