package firstcalculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class TestCalculator extends JFrame {
    private String command = "=";
    private JTextField jTextField;
    private JPanel jPanel = new JPanel();
    private JButton[] jButtons;
    //用于存储历史记录的列表组件
    private JList<String> historyList;
    //存储历史记录的列表模型
    private DefaultListModel<String> historyModel;

    public TestCalculator(){
        this.setTitle("科学计算器");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);


        Border margain = new EmptyBorder(new Insets(100,0,100,0));
        jTextField = new JTextField(30);
        jTextField.setText("test input");
        jTextField.setEditable(false);
        jTextField.setBorder(margain);
        jTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(jTextField,"North");


        jPanel.setLayout(new GridLayout(7,5,3,3));
        String name[] = {
                "Draw","PI","e","C","Back",
                "x^2","1/x","sin","cos","tan",
                "√x","(",")","x","/",
                "x^y","7","8","9","X",
                "10^x","4","5","6","-",
                "log","1","2","3","+",
                "ln","+/-","0",".","="
        };
        jButtons = new JButton[name.length];
//        MyActionListener actionListener = new MyActionListener();
        for(int i = 0; i < name.length; i++){

            jButtons[i] = new JButton(name[i]);
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

        //模拟添加历史记录
        //如果要添加表达式到李时记录中，就将表达式与计算结果一起添加到historyModel中就好了 参照test是如何添加进去的
//
        for(int i = 1;i <= 10;i++){
            if(i % 2 == 1){
                historyModel.addElement("Expression" + (i/2+1));
            }else{
                historyModel.addElement("Outcome" + i/2);
            }
        }

        // 创建历史记录列表组件
        historyList = new JList<>(historyModel);
        // 设置列表的可见行数
        historyList.setVisibleRowCount(13);
        // 设置列表的背景色
//        historyList.setBackground(Color.LIGHT_GRAY);
        // 设置列表的字体大小
        Font listFont = new Font("Arial", Font.PLAIN, 20);
        historyList.setFont(listFont);
//        设置列表的大小
        historyList.setPreferredSize(new Dimension(300, 500));

        // 设置自定义的 ListCellRenderer 以增加间距和设置间距颜色
        historyList.setCellRenderer(new HistoryListCellRenderer());
//         将历史记录列表添加到界面右侧
        this.add(historyList, "East");





        this.add(jPanel);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

//    class MyActionListener implements ActionListener{
//
//    }

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
