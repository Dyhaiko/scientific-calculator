package firstcalculator.FunctionCalculator;

import javax.swing.*;
import java.io.File;

public class openfile {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 创建一个文件选择器
        JFileChooser fileChooser = new JFileChooser();

        // 设置对话框标题
        fileChooser.setDialogTitle("选择一个文件");

        // 显示打开文件对话框
        int result = fileChooser.showOpenDialog(null);

        // 检查用户是否点击了“打开”按钮
        if (result == JFileChooser.APPROVE_OPTION) {
            // 获取用户选择的文件
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("您选择了文件: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("您取消了选择");
        }
    }
}
