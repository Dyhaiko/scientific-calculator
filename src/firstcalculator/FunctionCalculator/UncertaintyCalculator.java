package firstcalculator.FunctionCalculator;

import firstcalculator.GraphicCalculator.Function_Draw;
import firstcalculator.GraphicCalculator.Function_Input;
import firstcalculator.ScientificCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UncertaintyCalculator {

    private JFrame jf = new JFrame("不确定度计算器");
    private Container con = jf.getContentPane();
    // 窗口尺寸可以修改 请自行设计
    private static final int MYWIDTH = 1000;
    private static final int MYHEIGHT = 800;

    public void init() {
        // 设置菜单栏
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
        item5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建绘图界面
                Definite_Integral_Calculator definite_Integral_Calculator = new Definite_Integral_Calculator();
                definite_Integral_Calculator.init();
                // 隐藏当前界面
                jf.setVisible(false);
            }
        });

        // 初始化不确定度计算器的内容
        uncertaintityCalculatorInitializer();

        // 设置主窗口的大小，可见，默认关闭方式，位于windows窗口中间
        jf.setSize(MYWIDTH, MYHEIGHT);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
    }

    private JPanel jPanelRight; // 右侧侧面板
    private JScrollPane jScrollPaneRight; // 右侧侧面板的滚动面板
    private JSplitPane jSplitPane; // 分割面板

    public void uncertaintityCalculatorInitializer() {
        A_UncertaintyViewInit();

        jPanelRight = new JPanel(); // 创建右侧面板
        jPanelRight.setLayout(new BorderLayout()); // 使用 BorderLayout 布局管理器

        // 创建标题标签
        JLabel titleLabel = new JLabel("B类不确定度", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SimSun", Font.BOLD, 20)); // 设置字体样式

        // 将标题标签添加到 jPanelRight 的顶部
        jPanelRight.add(titleLabel, BorderLayout.NORTH);

        // 创建一个新的 JPanel 用于存放原有内容
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // 使用 BoxLayout 垂直排列

        // 将原有内容添加到 contentPanel
        // 这里假设原有内容是通过循环添加的 JLabel
        for (int i = 0; i < 5; i++) {
            contentPanel.add(new JLabel("内容 " + (i + 1)));
        }

        // 将 contentPanel 添加到 jPanelRight 的中心
        jPanelRight.add(contentPanel, BorderLayout.CENTER);

        jPanelRight.setPreferredSize(new Dimension(MYWIDTH / 2, MYHEIGHT));

        jScrollPaneRight = new JScrollPane(jPanelRight); // 直接将面板添加到滚动面板中
        jScrollPaneRight.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // 设置垂直滚动条始终显示

        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jPanelLeft, jScrollPaneRight); // 创建分割面板
        jSplitPane.setDividerLocation(MYWIDTH / 2);
        con.add(jSplitPane);
    }

    private JPanel jPanelLeft; // 左侧面板
    private JPanel headerPanel; // 左侧标题面板
    int Acount = 0;
    private List<Double> A_dataList = new ArrayList<>();

    public void A_UncertaintyViewInit() {
        jPanelLeft = new JPanel(); // 创建左侧面板
        jPanelLeft.setLayout(new BorderLayout());
        jPanelLeft.setBackground(Color.LIGHT_GRAY);
        jPanelLeft.setPreferredSize(new Dimension(MYWIDTH / 2, MYHEIGHT));

    /*
      创建标题面板
     */
        headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(MYWIDTH / 2, 50));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 使用 FlowLayout 并设置居中对齐

        // 创建标题标签
        JLabel titleLabelLeft = new JLabel("A类不确定度", SwingConstants.CENTER);
        titleLabelLeft.setFont(new Font("SimSun", Font.BOLD, 20));
        headerPanel.add(titleLabelLeft);

        jPanelLeft.add(headerPanel, BorderLayout.NORTH);

    /*
      创建输入面板
     */
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JTextField inputField = new JTextField(20);
        inputField.setFont(new Font("SimSun", Font.PLAIN, 20)); // 设置输入框字体大小
        JButton inputButton = new JButton("输入");
        JButton calculateButton = new JButton("计算");
        JButton clearButton = new JButton("清除");


        inputPanel.add(clearButton);
        inputPanel.add(inputField);
        inputPanel.add(inputButton);
        inputPanel.add(calculateButton);

        // 存储输入的数据
        JTextArea outputArea = new JTextArea(); // 不设置行数，让其自动调整
        outputArea.setEditable(false);
        outputArea.setFont(new Font("SimSun", Font.PLAIN, 20)); // 设置输出框字体大小
        outputArea.setLineWrap(true); // 启用自动换行
        outputArea.setWrapStyleWord(true); // 按单词换行

        JScrollPane scrollPane = new JScrollPane(outputArea); // 将 JTextArea 放在 JScrollPane 中

        // 输入按钮事件监听器
        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                if (!inputText.isEmpty()) {
                    try{
                    A_dataList.add(Double.parseDouble(inputText));}
                    catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "请输入数字！", "错误", JOptionPane.ERROR_MESSAGE);
                        inputField.setText(""); // 清空输入框
                        return;
                    }
                    Acount++;
                    outputArea.append("第" + Acount + "个数据：" + inputText + "\n");
                    inputField.setText(""); // 清空输入框
                }
            }
        });

        // 输入框回车事件监听器
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                if (!inputText.isEmpty()) {
                    try{
                        A_dataList.add(Double.parseDouble(inputText));}
                    catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "请输入数字！", "错误", JOptionPane.ERROR_MESSAGE);
                        inputField.setText(""); // 清空输入框
                        return;
                    }
                    Acount++;
                    outputArea.append("第" + Acount + "个数据：" + inputText + "\n");
                    inputField.setText(""); // 清空输入框
                }
            }
        });

        // 计算按钮事件监听器
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Acount == 0) {
                    outputArea.append("没有输入数据。\n");
                    return;
                }

                outputArea.append("输入数据的个数: " + Acount + "\n");

                // 计算平均值
                Double sum = 0.0;
                for (Double num : A_dataList) {
                    sum += num;
                }
                Double mean = sum / Acount;
                outputArea.append("平均值: " + mean + "\n");

                // 计算标准偏差
                if (Acount <= 1) {
                    outputArea.append("A类不确定度 (标准偏差): 无法计算，数据量必须大于1\n");
                } else {
                    Double sumOfSquaredDifferences = 0.0;
                    for (Double num : A_dataList) {
                        sumOfSquaredDifferences += Math.pow(num - mean, 2);
                    }
                    Double standardDeviation = Math.sqrt(sumOfSquaredDifferences / Acount / (Acount - 1));
                    outputArea.append("A类不确定度 (标准偏差): " + standardDeviation + "\n");
                }

                outputArea.append("\n" + "提示：继续输入可以累加计算" + "\n");
            }
        });

        // 清除按钮事件监听器
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示确认对话框
                int response = JOptionPane.showConfirmDialog(
                        jf,
                        "确定要清除所有数据吗？",
                        "确认清除",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                // 如果用户点击了“确定”
                if (response == JOptionPane.YES_OPTION) {
                    // 清空输出区域
                    outputArea.setText("");

                    // 重置计数器
                    Acount = 0;

                    // 清空数据列表
                    A_dataList.clear();

                    // 提示信息
                    outputArea.append("所有数据已清除，准备进行新计算。\n");
                }
            }
        });


    /*
      创建输出面板
     */
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        // 将输入和输出面板添加到左侧面板
        jPanelLeft.add(inputPanel, BorderLayout.SOUTH);
        jPanelLeft.add(outputPanel, BorderLayout.CENTER);
    }


    public static void main(String[] args) {
        new UncertaintyCalculator().init();
    }
}
