package firstcalculator;

import function.Expre;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class TestCalculator extends JFrame {
    private JTextField jTextField;
    private JPanel jPanel = new JPanel();
    private JButton[] jButtons;
    //用于存储历史记录的列表组件
    private JList<String> historyList;
    //存储历史记录的列表模型
    private DefaultListModel<String> historyModel;
    private int cursorPosition;//用于存放鼠标光标的位置
    private int historyNum;
//    private CardLayout cardLayout = new CardLayout();
//    private JPanel cardPanel = new JPanel();

    public TestCalculator(){
        //切换界面
        historyNum=0;
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("切换");
        menuBar.add(menu);
        JMenuItem item1 = new JMenuItem("科学计算器");
        JMenuItem item2 = new JMenuItem("绘图计算器");
        menu.add(item1);
        menu.add(item2);
        cursorPosition=0;
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建绘图界面
                GraphicCalculator graphicCalculator = new GraphicCalculator();
                graphicCalculator.setVisible(true);
                // 隐藏当前界面
                setVisible(false);
            }
        });

        this.setTitle("科学计算器");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);

        Border margain = new EmptyBorder(new Insets(100,0,100,0));
        jTextField = new JTextField(30);
        jTextField.setText("");
        jTextField.setEditable(false);
        jTextField.setBorder(margain);
        jTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(jTextField,"North");

        jPanel.setLayout(new GridLayout(7,5,3,3));
        String name[] = {
                "x^2","PI","e","C","Back",
                "√x","1/x","sin","cos","tan",
                "x^y","(",")","|x|","/",
                "10^x","7","8","9","*",
                "log","4","5","6","-",
                "lg","1","2","3","+",
                "ln","[x]","0",".","="
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

//        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
    // 设置垂直滚动条的滚动幅度
//        verticalScrollBar.setUnitIncrement(20);
// 获取水平滚动条
//        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
// 设置水平滚动条的滚动幅度
//        horizontalScrollBar.setUnitIncrement(20);

//         将历史记录列表（包含滚动条）添加到界面右侧
        this.add(scrollPane, "East");
        this.add(jPanel);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }
    //事件监视器
    class MyActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String input = e.getActionCommand();
            int cursorPosition=jTextField.getCaretPosition();
            //更新历史记录
            //优化鼠标位置
            if(cursorPosition!=0){
                cursorPosition=Expre.updateCursorPosition(jTextField.getText(),cursorPosition);
            }
            //根据输入改变展示栏
            if(input.equals("C")) {

                jTextField.setText("");
                cursorPosition=0;
            }
            else if(input.equals("=")){
                String temp=jTextField.getText();
                //在历史记录中添加表达式
                historyModel.addElement(temp);
                temp=Expre.translate(temp);
                try{
                    jTextField.setText(String.valueOf(Expre.count(temp)));
                }
                catch(StringIndexOutOfBoundsException exception) {
                    jTextField.setText("Grammar error");
                }
                catch (Exception exception){
                    jTextField.setText("There is something wrong!");
                }
                //在历史记录中添加答案
                historyModel.addElement(jTextField.getText());
                cursorPosition=jTextField.getText().length();
            }
            else if(input.equals("√x")) {
                jTextField.setText(jTextField.getText().substring(0,cursorPosition)+"√()"+jTextField.getText().substring(cursorPosition));
                cursorPosition+=2;
            }
            else if(input.equals("x^y")){
                jTextField.setText(jTextField.getText().substring(0,cursorPosition)+"()^()"+jTextField.getText().substring(cursorPosition));
                cursorPosition+=1;
            }
            else if(input.equals("x^2")){
                jTextField.setText(jTextField.getText().substring(0,cursorPosition)+"()^2"+jTextField.getText().substring(cursorPosition));
                cursorPosition+=1;
            }
            else if(input.equals("10^x")) {
                jTextField.setText(jTextField.getText().substring(0,cursorPosition)+"10^()"+jTextField.getText().substring(cursorPosition));
                cursorPosition+=4;
            }
            else if(input.equals("1/x")){
                jTextField.setText(jTextField.getText().substring(0,cursorPosition)+"1/()"+jTextField.getText().substring(cursorPosition));
                cursorPosition+=3;
            }
            else if(input.equals("sin")||input.equals("cos")||input.equals("tan")||input.equals("ln")||input.equals("lg")){
                jTextField.setText(jTextField.getText().substring(0,cursorPosition)+" "+input+" ()"+jTextField.getText().substring(cursorPosition));
                cursorPosition+=input.length()+3;
            }
            else if(input.equals("Back")) {
                int temp=Expre.getPosition(jTextField.getText(),cursorPosition);
                jTextField.setText(Expre.doBack(jTextField.getText(),cursorPosition));
                cursorPosition=temp;
            }
            else if(input.equals("|x|")){
                jTextField.setText(jTextField.getText().substring(0,cursorPosition)+"|()|"+jTextField.getText().substring(cursorPosition));
                cursorPosition+=2;
            }
            else if(input.equals("log")){
                jTextField.setText(jTextField.getText().substring(0,cursorPosition)+" log (,)"+jTextField.getText().substring(cursorPosition));
                cursorPosition+=6;
            }
            else if(input.equals("[x]")) {
                jTextField.setText(jTextField.getText().substring(0,cursorPosition)+"[]"+jTextField.getText().substring(cursorPosition));
                cursorPosition+=1;
            }
            else if(input.equals("PI")){
                jTextField.setText(jTextField.getText().substring(0,cursorPosition)+input+jTextField.getText().substring(cursorPosition));
                cursorPosition+=2;
            }
            else{
                jTextField.setText(jTextField.getText().substring(0,cursorPosition)+input+jTextField.getText().substring(cursorPosition));
                cursorPosition+=1;
            }
            jTextField.setCaretPosition(cursorPosition);
        }
    }

    public static void main(String[] args){
        TestCalculator testCalculator = new TestCalculator();
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
