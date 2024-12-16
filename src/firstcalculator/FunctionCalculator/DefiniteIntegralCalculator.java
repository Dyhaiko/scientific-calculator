package firstcalculator.FunctionCalculator;

import firstcalculator.GraphicCalculator.Function_Input;
import firstcalculator.ScientificCalculator;
import function.Expre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DefiniteIntegralCalculator {
    private JFrame jf = new JFrame("定积分计算器");
    private static final int MYWIDTH = 1000;
    private static final int MYHEIGHT = 800;
    int cnt = 0;

    private String[] pre = new String[3];{
        for(int i = 0;i < 3;i++){
            pre[i] = "";
        }
    }

    public void DefiniteIntegralCalculatorStarter(String[] pre){
        this.pre = pre;
        init();
    }


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
        DefiniteIntegralInitializer();
        jf.add(jp);
        jf.setVisible(true);

    }
    private JPanel jp;

    public void DefiniteIntegralInitializer() {
        jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        jp = new JPanel();
        jp.setLayout(new GridLayout(3, 1)); // 使用 GridLayout，3 行 1 列
        jp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 添加空隙

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.LIGHT_GRAY);
        panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 添加黑色线条
        jp.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.GRAY);
        panel2.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 添加黑色线条
        jp.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.LIGHT_GRAY);
        panel3.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 添加黑色线条
        jp.add(panel3);
        addComponent(panel1);
        addComponent(panel2);
        addComponent(panel3);
    }

    private void addComponent(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5); // 添加间距

        // 第一行：函数表达式标签
        gbc.gridx = 0;
        gbc.gridy = 0;
        //TODO

        JLabel label = new JLabel("函数表达式：f(x) =" + " " + Expre.turnIntoExpression(pre[cnt++]));//请直接在这里添加传入的函数表达式
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 16)); // 调大字体
        panel.add(label, gbc);

        // 第二行：上限标签
        gbc.gridx = 1;
        gbc.gridy = 0;  // 设置行号为0
        JLabel upperLimitLabel = new JLabel("上限：");
        upperLimitLabel.setFont(new Font(upperLimitLabel.getFont().getName(), upperLimitLabel.getFont().getStyle(), 16)); // 调大字体
        panel.add(upperLimitLabel, gbc);

        // 第二行：上限输入框
        gbc.gridx = 2;  // 继续在这一行
        gbc.gridwidth = 1;
        JTextField upperLimitField = new JTextField(10);
        upperLimitField.setFont(new Font(upperLimitField.getFont().getName(), upperLimitField.getFont().getStyle(), 16)); // 调大字体
        panel.add(upperLimitField, gbc);

        // 第三行：下限标签
        gbc.gridx = 3;
        gbc.gridy = 0; // 设置行号为 0
        gbc.gridwidth = 1;
        JLabel lowerLimitLabel = new JLabel("下限：");
        lowerLimitLabel.setFont(new Font(lowerLimitLabel.getFont().getName(), lowerLimitLabel.getFont().getStyle(), 16)); // 调大字体
        panel.add(lowerLimitLabel, gbc);

        // 第四行：下限输入框
        gbc.gridx = 4; // 继续在这一行
        gbc.gridwidth = 1;
        JTextField lowerLimitField = new JTextField(10);
        lowerLimitField.setFont(new Font(lowerLimitField.getFont().getName(), lowerLimitField.getFont().getStyle(), 16)); // 调大字体
        panel.add(lowerLimitField, gbc);

        // 第二行：结果标签
        gbc.gridx = 0;
        gbc.gridy = 2;  // 设定新的行号
        JLabel label1 = new JLabel("outcome:");
        label1.setFont(new Font(label1.getFont().getName(), label1.getFont().getStyle(), 16)); // 调大字体
        panel.add(label1, gbc);

        // 第三行：输出区域，跨越5列
        gbc.gridx = 1;
        gbc.gridy = 2;  // 设置行号为2
        gbc.gridwidth = 4;
        JTextArea outputArea = new JTextArea(1, 20); // 调整输出框的大小
        outputArea.setFont(new Font(outputArea.getFont().getName(), outputArea.getFont().getStyle(), 16)); // 调大字体
        panel.add(outputArea, gbc);
        JButton button = new JButton("计算");
        gbc.gridx = 6; // 按钮放在输出区域的右边
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(button, gbc);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 在这里编写按钮点击时的处理逻辑
                System.out.println("lol");
            }
        });

        if(cnt == 3){
            JButton button1 = new JButton("Back");
            gbc.gridx = 7; // 按钮放在输出区域的右边
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            panel.add(button1, gbc);
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 在这里编写按钮点击时的处理逻辑
                    jf.setVisible(false);

                    Function_Input fi = new Function_Input();
                    String[] func = new String[3];
                    for(int i = 0;i < 3;i++){
                        func[i] = Expre.turnIntoExpression(pre[i]);
                    }
                    fi.set_Function(func,pre);
                }
            });
        }
    }


    public static void main(String[] args){
        new DefiniteIntegralCalculator().init();
    }
}
