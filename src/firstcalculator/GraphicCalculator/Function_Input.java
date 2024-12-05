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
        jTextField.setText("");
        jTextField.setEditable(false);
        jTextField.setBorder(margain);
        jTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(jTextField, "North");

        jPanel.setLayout(new GridLayout(7, 6, 3, 3));
        String name[] = {
                "x^2", "PI", "e", "C", "Back","y",
                "√x", "1/x", "sin", "cos", "tan","x",
                "x^y", "(", ")", "|x|", "/","a",
                "10^x", "7", "8", "9", "*","b",
                "log", "4", "5", "6", "-","c",
                "lg", "1", "2", "3", "+","Draw",
                "ln", "[x]", "0", ".", "=","save"
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
        public void actionPerformed(ActionEvent e) {
            String input = e.getActionCommand();
            int cursorPosition = jTextField.getCaretPosition();
            //更新历史记录
            //优化鼠标位置
            if (cursorPosition != 0) {
                cursorPosition = Expre.updateCursorPosition(jTextField.getText(), cursorPosition);
            }
            //根据输入改变展示栏
            if (input.equals("C")) {

                jTextField.setText("");
                cursorPosition = 0;
            } else if (input.equals("=")) {
                String temp = jTextField.getText();
                temp = Expre.translate(temp);
                try {
                    jTextField.setText(String.valueOf(Expre.count(temp)));
                } catch (StringIndexOutOfBoundsException exception) {
                    jTextField.setText("Grammar error");
                } catch (Exception exception) {
                    jTextField.setText("There is something wrong!");
                }
                cursorPosition = jTextField.getText().length();

                //这里的代码实现逻辑我不太懂 记得改改！！！！！！！！！！！！！！！！！！！！！！！！！！！
            }else if(input.equals("Save")){
                function_expression = jTextField.getText();
            }else if(input.equals("Draw")){
                // 在这里调用绘图函数
                Funcion_Draw graphicDrawer = new Funcion_Draw();
                graphicDrawer.init();
                setVisible(false);
            }
            else if (input.equals("√x")) {
                jTextField.setText(jTextField.getText().substring(0, cursorPosition) + "√()" + jTextField.getText().substring(cursorPosition));
                cursorPosition += 2;
            } else if (input.equals("x^y")) {
                jTextField.setText(jTextField.getText().substring(0, cursorPosition) + "()^()" + jTextField.getText().substring(cursorPosition));
                cursorPosition += 1;
            } else if (input.equals("x^2")) {
                jTextField.setText(jTextField.getText().substring(0, cursorPosition) + "()^2" + jTextField.getText().substring(cursorPosition));
                cursorPosition += 1;
            } else if (input.equals("10^x")) {
                jTextField.setText(jTextField.getText().substring(0, cursorPosition) + "10^()" + jTextField.getText().substring(cursorPosition));
                cursorPosition += 4;
            } else if (input.equals("1/x")) {
                jTextField.setText(jTextField.getText().substring(0, cursorPosition) + "1/()" + jTextField.getText().substring(cursorPosition));
                cursorPosition += 3;
            } else if (input.equals("sin") || input.equals("cos") || input.equals("tan") || input.equals("ln") || input.equals("lg")) {
                jTextField.setText(jTextField.getText().substring(0, cursorPosition) + " " + input + " ()" + jTextField.getText().substring(cursorPosition));
                cursorPosition += input.length() + 3;
            } else if (input.equals("Back")) {
                int temp = Expre.getPosition(jTextField.getText(), cursorPosition);
                jTextField.setText(Expre.doBack(jTextField.getText(), cursorPosition));
                cursorPosition = temp;
            } else if (input.equals("|x|")) {
                jTextField.setText(jTextField.getText().substring(0, cursorPosition) + "|()|" + jTextField.getText().substring(cursorPosition));
                cursorPosition += 2;
            } else if (input.equals("log")) {
                jTextField.setText(jTextField.getText().substring(0, cursorPosition) + " log (,)" + jTextField.getText().substring(cursorPosition));
                cursorPosition += 6;
            } else if (input.equals("[x]")) {
                jTextField.setText(jTextField.getText().substring(0, cursorPosition) + "[]" + jTextField.getText().substring(cursorPosition));
                cursorPosition += 1;
            } else if (input.equals("PI")) {
                jTextField.setText(jTextField.getText().substring(0, cursorPosition) + input + jTextField.getText().substring(cursorPosition));
                cursorPosition += 2;
            } else {
                jTextField.setText(jTextField.getText().substring(0, cursorPosition) + input + jTextField.getText().substring(cursorPosition));
                cursorPosition += 1;
            }
            jTextField.setCaretPosition(cursorPosition);
        }


    }
    public static void main(String[] args){
        Function_Input fi = new Function_Input();
    }
}
