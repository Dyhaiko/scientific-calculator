package firstcalculator.FunctionCalculator;

import firstcalculator.GraphicCalculator.Function_Draw;
import firstcalculator.GraphicCalculator.Function_Input;
import firstcalculator.ScientificCalculator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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


    public void uncertaintityCalculatorInitializer() {
        A_UncertaintyViewInit();
        B_uncertaintyViewInit();

    }

    private JPanel jPanelLeft; // 左侧面板
    private JPanel headerPanel; // 左侧标题面板
    int Acount = 0;
    private List<Double> A_dataList = new ArrayList<>();
    Double AuncertaintyResult = 0.0;
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
                    AuncertaintyResult = standardDeviation;
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

                    AuncertaintyResult = 0.0;

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


    private JPanel jPanelRight; // 右侧侧面板
    private JScrollPane jScrollPaneRight; // 右侧侧面板的滚动面板
    private JSplitPane jSplitPane; // 分割面板
    void B_uncertaintyViewInit() {
        jPanelRight = new JPanel(); // 创建右侧面板
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        jPanelRight.setLayout(gridBagLayout); // 使用 GridBagLayout 布局管理器

        // 创建标题标签
        JLabel titleLabel = new JLabel("B类不确定度", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SimSun", Font.BOLD, 20)); // 设置字体样式

        // 将标题标签添加到 jPanelRight 的顶部
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 占据1列
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // 设置外边距
        gridBagLayout.setConstraints(titleLabel, gbc);
        jPanelRight.add(titleLabel);

        // 初始化五个子面板
        JPanel panel1 = initCommonUncertaintyPanel();
        JPanel panel2 = new JPanel(); // 示例，实际应替换为具体的初始化方法
        JPanel panel3 = new JPanel(); // 示例，实际应替换为具体的初始化方法
        JPanel panel4 = new JPanel(); // 示例，实际应替换为具体的初始化方法
        JPanel panel5 = new JPanel(); // 示例，实际应替换为具体的初始化方法

        // 设置每个子面板的背景颜色以便区分
        panel1.setBackground(Color.decode("#FFDDDD"));
        panel2.setBackground(Color.decode("#DDFFDD"));
        panel3.setBackground(Color.decode("#DDDDFF"));
        panel4.setBackground(Color.decode("#FFFFDD"));
        panel5.setBackground(Color.decode("#DDFFFF"));

        // 添加子面板到右侧面板
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gridBagLayout.setConstraints(panel1, gbc);
        jPanelRight.add(panel1);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gridBagLayout.setConstraints(panel2, gbc);
        jPanelRight.add(panel2);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gridBagLayout.setConstraints(panel3, gbc);
        jPanelRight.add(panel3);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gridBagLayout.setConstraints(panel4, gbc);
        jPanelRight.add(panel4);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gridBagLayout.setConstraints(panel5, gbc);
        jPanelRight.add(panel5);

        // 设置右侧面板的首选大小
        jPanelRight.setPreferredSize(new Dimension(MYWIDTH / 2, MYHEIGHT));

        // 创建滚动面板并添加右侧面板
        jScrollPaneRight = new JScrollPane(jPanelRight); // 直接将面板添加到滚动面板中
        jScrollPaneRight.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // 设置垂直滚动条始终显示

        // 创建分割面板并将左右面板添加进去
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jPanelLeft, jScrollPaneRight); // 创建分割面板
        jSplitPane.setDividerLocation(MYWIDTH / 2);
        con.add(jSplitPane);
        jf.setVisible(true);
    }

    Double BinitCommonUncertainty=0.0;
    private JPanel initCommonUncertaintyPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 第一个子面板命名为“常见不确定度计算”
        JLabel commonUncertaintyLabel = new JLabel("常见不确定度计算", SwingConstants.CENTER);
        commonUncertaintyLabel.setFont(new Font("SimSun", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(commonUncertaintyLabel, gbc);

        // 仪器误差限标签和输入框
        JLabel instrumentErrorLabel = new JLabel("仪器误差限:");
        instrumentErrorLabel.setFont(new Font("SimSun", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(instrumentErrorLabel, gbc);

        JTextField instrumentErrorField = new JTextField(10);
        instrumentErrorField.setFont(new Font("SimSun", Font.PLAIN, 14)); // 设置输入框字体大小
        instrumentErrorField.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // 添加边框
        instrumentErrorField.setBackground(Color.WHITE); // 设置背景色
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(instrumentErrorField, gbc);

        // 包含因子K标签和输入框
        JLabel inclusionFactorLabel = new JLabel("包含因子K:");
        inclusionFactorLabel.setFont(new Font("SimSun", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(inclusionFactorLabel, gbc);

        JTextField inclusionFactorField = new JTextField("默认为sqrt(3)", 10);
        inclusionFactorField.setFont(new Font("SimSun", Font.PLAIN, 14)); // 设置输入框字体大小
        inclusionFactorField.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // 添加边框
        inclusionFactorField.setBackground(Color.WHITE); // 设置背景色
        inclusionFactorField.setForeground(Color.GRAY); // 设置提示词颜色
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(inclusionFactorField, gbc);

        // 添加 FocusListener 处理焦点事件
        inclusionFactorField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inclusionFactorField.getText().equals("默认为sqrt(3)")) {
                    inclusionFactorField.setText("");
                    inclusionFactorField.setForeground(Color.BLACK); // 恢复输入框颜色
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inclusionFactorField.getText().isEmpty()) {
                    inclusionFactorField.setText("默认为sqrt(3)");
                    inclusionFactorField.setForeground(Color.GRAY); // 设置提示词颜色
                }
            }
        });

        // 创建输出框
        JTextArea outputArea = new JTextArea("合成不确定度时：\n请不要清除A类不确定度的计算结果。\n如果A类或B类未计算，默认为0。");
        outputArea.setEditable(false);
        outputArea.setFont(new Font("SimSun", Font.PLAIN, 14)); // 设置输出框字体大小
        outputArea.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // 添加边框
        outputArea.setBackground(Color.WHITE); // 设置背景色
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setPreferredSize(new Dimension(200, 65)); // 设置输出框大小与输入框一致

        JScrollPane scrollPane = new JScrollPane(outputArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.0; // 设置权重为0，使其大小与输入框一致
        gbc.insets = new Insets(0, 5, 5, 5); // 减少上边距
        panel.add(scrollPane, gbc);

        // 添加标签说明
        JLabel outputLabel = new JLabel("B类不确定度输出:", SwingConstants.LEFT);
        outputLabel.setFont(new Font("SimSun", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3; // 将标签放置在输出框的上方
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 0, 5); // 减少下边距
        panel.add(outputLabel, gbc);

        // 添加 DocumentListener 监听输入框的变化
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateOutput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateOutput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateOutput();
            }

            private void updateOutput() {
                String instrumentErrorText = instrumentErrorField.getText();
                String inclusionFactorText = inclusionFactorField.getText();

                boolean instrumentErrorEmpty = instrumentErrorText.isEmpty();

                if (instrumentErrorEmpty) {
                    outputArea.setText("错误: 仪器误差限未输入");
                } else {
                    try {
                        double instrumentError = parseDouble(instrumentErrorText);
                        double inclusionFactor = parseDouble(inclusionFactorText);

                        if (instrumentError != 0) {
                            double result = instrumentError/inclusionFactor ;
                            BinitCommonUncertainty = result;
                            outputArea.setText(String.format("B类不确定度结果: %.20f", result));
                        } else {
                            outputArea.setText("错误: 仪器误差限不能为零");
                        }
                    } catch (NumberFormatException e) {
                        outputArea.setText("错误: 输入无效，请输入数字或 sqrt(数字)");
                    }
                }
            }

            private double parseDouble(String text) throws NumberFormatException {
                if (text.isEmpty() || text.equals("默认为sqrt(3)")) {
                    return Math.sqrt(3);
                }
                if (text.startsWith("sqrt(") && text.endsWith(")")) {
                    String numberStr = text.substring(5, text.length() - 1);
                    double number = Double.parseDouble(numberStr);
                    return Math.sqrt(number);
                } else {
                    return Double.parseDouble(text);
                }
            }
        };

        JButton synthesizeButton = new JButton("合成不确定度");
        synthesizeButton.setFont(new Font("SimSun", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 5; // 根据需要调整位置
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // 设置外边距
        panel.add(synthesizeButton, gbc);

        synthesizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double aUncertainty = AuncertaintyResult;
                    double bUncertainty = BinitCommonUncertainty;

                    double combinedUncertainty = Math.sqrt(aUncertainty * aUncertainty + bUncertainty * bUncertainty);
                    outputArea.setText(String.format("B类不确定度结果: %.20f\n合成不确定度结果: %.20f",BinitCommonUncertainty, combinedUncertainty));
                } catch (Exception ex) {
                    outputArea.setText("错误: 无法计算合成不确定度");
                }
            }
        });


        instrumentErrorField.getDocument().addDocumentListener(documentListener);
        inclusionFactorField.getDocument().addDocumentListener(documentListener);

        return panel;
    }




    public static void main(String[] args) {
        new UncertaintyCalculator().init();
    }
}
