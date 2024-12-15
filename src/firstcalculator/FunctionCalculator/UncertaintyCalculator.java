package firstcalculator.FunctionCalculator;

import firstcalculator.GraphicCalculator.Function_Draw;
import firstcalculator.GraphicCalculator.Function_Input;
import firstcalculator.ScientificCalculator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
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
        // 创建分割面板并将左右面板添加进去
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jPanelLeft, jScrollPaneRight); // 创建分割面板
        jSplitPane.setDividerLocation(MYWIDTH / 2);
        con.add(jSplitPane);
        jf.setVisible(true);
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
        titleLabelLeft.setFont(new Font("SimSun", Font.BOLD, 35));
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
                    try {
                        A_dataList.add(Double.parseDouble(inputText));
                    } catch (NumberFormatException ex) {
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
                    try {
                        A_dataList.add(Double.parseDouble(inputText));
                    } catch (NumberFormatException ex) {
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
        jPanelRight.setLayout(new GridBagLayout()); // 使用 GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // 设置间距
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        gbc.anchor = GridBagConstraints.CENTER; // 居中对齐

        // 创建标题标签
        JLabel titleLabel = new JLabel("B类不确定度", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SimSun", Font.BOLD, 35)); // 设置字体样式
        titleLabel.setPreferredSize(new Dimension(MYWIDTH / 2, 50)); // 设置大小
        jPanelRight.add(titleLabel, gbc);

        // 初始化五个子面板
        JPanel panel1 = initCommonUncertaintyPanel();
        JPanel panel2 = initElectromagneticInstrumentPanel();
        JPanel panel3 = initDirectCurrentResistorPanel();
        JPanel panel4 = initDirectCurrentPotentiometerPanel();
        JPanel panel5 = initDirectCurrentBridgePanel();

        // 设置每个子面板的背景颜色以便区分
        panel1.setBackground(Color.decode("#FFDDDD"));
        panel2.setBackground(Color.decode("#DDFFDD"));
        panel3.setBackground(Color.decode("#DDDDFF"));
        panel4.setBackground(Color.decode("#FFFFDD"));
        panel5.setBackground(Color.decode("#DDFFFF"));

        // 添加子面板到右侧面板
        addSubPanel(panel1, 360, gbc); // 增加高度
        addSubPanel(panel2, 200, gbc);
        addSubPanel(panel3, 600, gbc);
        addSubPanel(panel4, 250, gbc);
        addSubPanel(panel5, 100, gbc);

        // 创建滚动面板并添加右侧面板
        jScrollPaneRight = new JScrollPane(jPanelRight); // 直接将面板添加到滚动面板中
        jScrollPaneRight.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // 设置垂直滚动条始终显示
        jScrollPaneRight.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // 设置水平滚动条从不显示
    }

    private void addSubPanel(JPanel panel, int height, GridBagConstraints gbc) {
        panel.setPreferredSize(new Dimension(MYWIDTH / 2, height)); // 设置子面板大小
        gbc.gridy++; // 增加行号
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        gbc.anchor = GridBagConstraints.CENTER; // 居中对齐
        jPanelRight.add(panel, gbc);
        jPanelRight.revalidate(); // 重新验证布局
        jPanelRight.repaint(); // 重新绘制面板
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
        gbc.insets = new Insets(0, 0, 0, 0); // 设置外边距
        panel.add(commonUncertaintyLabel, gbc);

        // 仪器误差限标签和输入框
        JLabel instrumentErrorLabel = new JLabel("仪器误差限:");
        instrumentErrorLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(instrumentErrorLabel, gbc);

        JTextField instrumentErrorField = new JTextField(15);
        instrumentErrorField.setFont(new Font("SimSun", Font.PLAIN, 20)); // 设置输入框字体大小
        instrumentErrorField.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // 添加边框
        instrumentErrorField.setBackground(Color.WHITE); // 设置背景色
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(instrumentErrorField, gbc);

        // 包含因子K标签和输入框
        JLabel inclusionFactorLabel = new JLabel("包含因子K:");
        inclusionFactorLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(inclusionFactorLabel, gbc);

        JTextField inclusionFactorField = new JTextField("默认为sqrt(3)", 15);
        inclusionFactorField.setFont(new Font("SimSun", Font.PLAIN, 20)); // 设置输入框字体大小
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
        outputArea.setFont(new Font("SimSun", Font.PLAIN, 20)); // 设置输出框字体大小
        outputArea.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // 添加边框
        outputArea.setBackground(Color.WHITE); // 设置背景色
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setPreferredSize(new Dimension(335, 200)); // 增加高度

        JScrollPane scrollPane = new JScrollPane(outputArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0; // 设置权重为1，使其能够占据剩余的空间
        gbc.insets = new Insets(0, 0, 0, 0); // 设置外边距
        panel.add(scrollPane, gbc);

        // 添加标签说明
        JLabel outputLabel = new JLabel("B类不确定度输出:", SwingConstants.LEFT);
        outputLabel.setFont(new Font("SimSun", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 3; // 将标签放置在输出框的上方
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0); // 设置外边距
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
                            double result = instrumentError / inclusionFactor;
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
        synthesizeButton.setFont(new Font("SimSun", Font.PLAIN, 20));
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
                    outputArea.setText(String.format("B类不确定度结果: %.20f\n合成不确定度结果: %.20f", BinitCommonUncertainty, combinedUncertainty));
                } catch (Exception ex) {
                    outputArea.setText("错误: 无法计算合成不确定度");
                }
            }
        });

        instrumentErrorField.getDocument().addDocumentListener(documentListener);
        inclusionFactorField.getDocument().addDocumentListener(documentListener);

        return panel;
    }



    Double deltaElectromagneticInstrument = 0.0;

    private JPanel initElectromagneticInstrumentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 第二个子面板命名为“电磁仪表仪器误差限”
        JLabel electromagneticInstrumentLabel = new JLabel("电磁仪表仪器误差限", SwingConstants.CENTER);
        electromagneticInstrumentLabel.setFont(new Font("SimSun", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(electromagneticInstrumentLabel, gbc);

        // 添加公式标签
        JLabel formulaLabel = new JLabel("Δ = a% · Nm", SwingConstants.CENTER);
        formulaLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(formulaLabel, gbc);

        // 准确度等级输入框
        JLabel accuracyLabel = new JLabel("准确度等级 (a%):", SwingConstants.RIGHT);
        accuracyLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(accuracyLabel, gbc);

        JTextField accuracyField = new JTextField();
        accuracyField.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(accuracyField, gbc);

        // 电表量程输入框
        JLabel rangeLabel = new JLabel("电表量程 (Nm):", SwingConstants.RIGHT);
        rangeLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(rangeLabel, gbc);

        JTextField rangeField = new JTextField();
        rangeField.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(rangeField, gbc);

        // 输出框
        JLabel resultLabel = new JLabel("结果: ", SwingConstants.RIGHT);
        resultLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(resultLabel, gbc);

        JTextField resultField = new JTextField();
        resultField.setEditable(false);
        resultField.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(resultField, gbc);

        // 添加文档变化事件监听器
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateResult();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateResult();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateResult();
            }

            private void updateResult() {
                String accuracyText = accuracyField.getText().trim();
                String rangeText = rangeField.getText().trim();

                if (accuracyText.isEmpty()) {
                    resultField.setText("a%未输入");
                } else if (rangeText.isEmpty()) {
                    resultField.setText("Nm未输入");
                } else {
                    try {
                        double accuracy = Double.parseDouble(accuracyText) / 100;
                        double range = Double.parseDouble(rangeText);
                        double result = accuracy * range;
                        deltaElectromagneticInstrument = result;
                        resultField.setText(String.format("%.5f", result));
                    } catch (NumberFormatException ex) {
                        resultField.setText("请输入有效的数字");
                    }
                }
            }
        };

        accuracyField.getDocument().addDocumentListener(documentListener);
        rangeField.getDocument().addDocumentListener(documentListener);

        return panel;
    }


    Double deltaDirectCurrentResistor = 0.0;

    private JPanel initDirectCurrentResistorPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 第三个子面板命名为“直流电阻器仪器误差限”
        JLabel directCurrentResistorLabel = new JLabel("直流电阻器仪器误差限", SwingConstants.CENTER);
        directCurrentResistorLabel.setFont(new Font("SimSun", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(directCurrentResistorLabel, gbc);

        // 添加公式标签
        JLabel formulaLabel = new JLabel("Δ = Sigma[ ai% * Ri ] + R0", SwingConstants.CENTER);
        formulaLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(formulaLabel, gbc);

        // 动态生成 ai 和 Ri 的输入框
        List<JTextField> aiFields = new ArrayList<>();
        List<JTextField> riFields = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            // ai 标签和输入框
            JLabel aiLabel = new JLabel("a" + (i + 1) + " (%):", SwingConstants.LEFT);
            aiLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
            gbc.gridx = 0;
            gbc.gridy = 3 + i * 2;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
            panel.add(aiLabel, gbc);

            JTextField aiField = new JTextField(10); // 设置列数为 10
            aiField.setFont(new Font("SimSun", Font.PLAIN, 20));
            gbc.gridx = 1;
            gbc.gridy = 3 + i * 2;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
            panel.add(aiField, gbc);
            aiFields.add(aiField);

            // Ri 标签和输入框
            JLabel riLabel = new JLabel("R" + (i + 1) + ":", SwingConstants.LEFT);
            riLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
            gbc.gridx = 0;
            gbc.gridy = 4 + i * 2;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
            panel.add(riLabel, gbc);

            JTextField riField = new JTextField(10); // 设置列数为 10
            riField.setFont(new Font("SimSun", Font.PLAIN, 20));
            gbc.gridx = 1;
            gbc.gridy = 4 + i * 2;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
            panel.add(riField, gbc);
            riFields.add(riField);
        }

        // 添加 R0 输入框
        JLabel r0Label = new JLabel("R0:", SwingConstants.RIGHT);
        r0Label.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(r0Label, gbc);

        JTextField r0Field = new JTextField(10); // 设置列数为 10
        r0Field.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.gridy = 15;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(r0Field, gbc);

        // 添加结果输出框
        JLabel resultLabel = new JLabel("结果:", SwingConstants.RIGHT);
        resultLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 16;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(resultLabel, gbc);

        JTextField resultField = new JTextField(20); // 设置列数为 10
        resultField.setEditable(false);
        resultField.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.gridy = 16;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(resultField, gbc);

        // 添加 DocumentListener 到所有输入框
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateResult();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateResult();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateResult();
            }

            private void calculateResult() {
                try {
                    double sum = 0;
                    boolean hasData = false;
                    boolean allPairsEmpty = true;

                    for (int i = 0; i < 6; i++) {
                        String aiText = aiFields.get(i).getText().trim();
                        String riText = riFields.get(i).getText().trim();

                        if (aiText.isEmpty() && riText.isEmpty()) {
                            continue; // 如果都为空，跳过
                        } else if (aiText.isEmpty()) {
                            resultField.setText( " a" + (i + 1) + " 数据为空");
                            return;
                        } else if (riText.isEmpty()) {
                            resultField.setText(" R" + (i + 1) + " 数据为空");
                            return;
                        } else {
                            double ai = Double.parseDouble(aiText) / 100;
                            double ri = Double.parseDouble(riText);
                            sum += ai * ri;

                            hasData = true;
                            allPairsEmpty = false;
                        }
                    }

                    String r0Text = r0Field.getText().trim();
                    if (!r0Text.isEmpty()) {
                        double r0 = Double.parseDouble(r0Text);
                        sum += r0;
                        hasData = true;
                    } else if (hasData) {
                        resultField.setText("R0 数据为空");
                        return;
                    }

                    if (allPairsEmpty && r0Text.isEmpty()) {
                        resultField.setText("");
                        return;
                    }

                    if (!hasData) {
                        resultField.setText("");
                        return;
                    }

                    deltaDirectCurrentResistor = sum;

                    resultField.setText(String.format("%.5f", sum));
                } catch (NumberFormatException ex) {
                    resultField.setText("请输入有效的数字");
                }
            }
        };

        for (JTextField field : aiFields) {
            field.getDocument().addDocumentListener(listener);
        }
        for (JTextField field : riFields) {
            field.getDocument().addDocumentListener(listener);
        }
        r0Field.getDocument().addDocumentListener(listener);

        return panel;
    }


    private JPanel initDirectCurrentPotentiometerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 第三个子面板命名为“直流电位差计仪器误差限”
        JLabel directCurrentResistorLabel = new JLabel("直流电位差计仪器误差限", SwingConstants.CENTER);
        directCurrentResistorLabel.setFont(new Font("SimSun", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(directCurrentResistorLabel, gbc);

        // 添加公式标签
        JLabel formulaLabel = new JLabel("Δ =  ai% * ( Ux + U0/10 )", SwingConstants.CENTER);
        formulaLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(formulaLabel, gbc);

        // 添加 a (%) 输入框
        JLabel aLabel = new JLabel("a (%):", SwingConstants.RIGHT);
        aLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(aLabel, gbc);

        JTextField aField = new JTextField(10); // 设置列数为 10
        aField.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(aField, gbc);

        // 添加 Ux 输入框
        JLabel uxLabel = new JLabel("Ux:", SwingConstants.RIGHT);
        uxLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(uxLabel, gbc);

        JTextField uxField = new JTextField(10); // 设置列数为 10
        uxField.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(uxField, gbc);

        // 添加 U0 输入框
        JLabel u0Label = new JLabel("U0:", SwingConstants.RIGHT);
        u0Label.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(u0Label, gbc);

        JTextField u0Field = new JTextField(10); // 设置列数为 10
        u0Field.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(u0Field, gbc);

        // 添加结果输出框
        JLabel resultLabel = new JLabel("结果:", SwingConstants.RIGHT);
        resultLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(resultLabel, gbc);

        JTextArea resultArea = new JTextArea(3, 20);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("SimSun", Font.PLAIN, 20));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(resultArea, gbc);

        // 添加 DocumentListener 到所有输入框
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateResult();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateResult();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateResult();
            }

            private void calculateResult() {
                try {
                    String aText = aField.getText().trim();
                    String uxText = uxField.getText().trim();
                    String u0Text = u0Field.getText().trim();

                    if (aText.isEmpty() && uxText.isEmpty() && u0Text.isEmpty()) {
                        resultArea.setText("");
                        return;
                    }

                    if(aText.isEmpty()&&uxText.isEmpty()){
                        resultArea.setText("a (%) 数据为空\nUx 数据为空");
                        return;
                    }

                    if(aText.isEmpty()&&u0Text.isEmpty()){
                        resultArea.setText("a (%) 数据为空\nU0 数据为空");
                        return;
                    }

                    if(uxText.isEmpty()&&u0Text.isEmpty()){
                        resultArea.setText("Ux 数据为空\nU0 数据为空");
                        return;
                    }

                    if (aText.isEmpty()) {
                        resultArea.setText("a (%) 数据为空");
                        return;
                    }
                    if (uxText.isEmpty()) {
                        resultArea.setText("Ux 数据为空");
                        return;
                    }
                    if (u0Text.isEmpty()) {
                        resultArea.setText("U0 数据为空");
                        return;
                    }
                    

                    double a = Double.parseDouble(aText) / 100;
                    double ux = Double.parseDouble(uxText);
                    double u0 = Double.parseDouble(u0Text);

                    double delta = a * (ux + u0 / 10);
                    resultArea.setText(String.format("Δ = %.5f", delta));
                } catch (NumberFormatException ex) {
                    resultArea.setText("输入不合法");
                }
            }
        };

        aField.getDocument().addDocumentListener(listener);
        uxField.getDocument().addDocumentListener(listener);
        u0Field.getDocument().addDocumentListener(listener);

        return panel;
    }


    private JPanel initDirectCurrentBridgePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 第三个子面板命名为“直流电阻器仪器误差限”
        JLabel directCurrentResistorLabel = new JLabel("直流电桥仪器误差限", SwingConstants.CENTER);
        directCurrentResistorLabel.setFont(new Font("SimSun", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(directCurrentResistorLabel, gbc);

        // 添加公式标签
        JLabel formulaLabel = new JLabel("Δ =  ai% * ( Rx + R0/10 )", SwingConstants.CENTER);
        formulaLabel.setFont(new Font("SimSun", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 5); // 设置外边距
        panel.add(formulaLabel, gbc);


        return panel;
    }

    public static void main(String[] args) {
        new UncertaintyCalculator().init();
    }
}
