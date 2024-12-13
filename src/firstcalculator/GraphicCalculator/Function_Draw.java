
package firstcalculator.GraphicCalculator;

import firstcalculator.FunctionCalculator.Definite_Integral_Calculator;
import firstcalculator.FunctionCalculator.UncertaintyCalculator;
import firstcalculator.ScientificCalculator;
import function.Expre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Function_Draw {
    //窗口尺寸
    public static final int MYWIDTH = 1000;
    public static final int MYHEIGHT = 800;

    //缩放需求
    public static final int MAXSIZE = 5000;

    public String[] function = new String[3];
    {
        Arrays.fill(function, "");
    }
    Choice functionChooser = new Choice();

    public boolean[] canDraw = new boolean[3];
    {
        Arrays.fill(canDraw,false);
    }

    public JFrame jf = new JFrame("绘图计算器");
    TextField tips = new TextField("请输入函数表达式，格式为y=... 回车保存");
    TextField functionField = new TextField();

    TextField scaleField = new TextField();

    Button actionButton = new Button("Draw");
    JButton enlargerButton = new JButton("Enlarger");
    JButton reduceButton = new JButton("Reduce");
    JButton clearButton = new JButton("Clear");
    JButton backButton = new JButton("Back");

    public double myScale = 1;

    MyCanvas mc = new MyCanvas();
    ShowDialog sd = new ShowDialog();
    String[] pre = new String[3];{
        for(int i = 0;i < 3;i++){
            pre[i] = "";
        }
    }

    public Function_Draw(){
        for(int i = 0;i < 3;i++){
            function[i] = "";
        }
        this.init();
    }

    public Function_Draw(String[] input,String[] pre) {
        for(int i = 0;i < 3;i++){
            this.function[i] =  input[i];
            this.pre[i] = pre[i];
            System.out.println(this.pre[i]);
        }
        for(int i = 0;i < 3;i++){
            if(function[i].isEmpty()){
                sd.showEmptyWarningDialog(jf);
                canDraw[i] = false;
                function[i] = "";
            }else{
                if(!Expre.isLegal(pre[i])){
                    canDraw[i] = false;
                    sd.showEnterWarningDialog(jf);
                    functionField.setText("");
                    function[i] = "";
                }else{
                    canDraw[i] = true;
                    sd.showFunctionSavedDialog(jf,function[i]);
                }
            }

        }
        this.init();
    }
    public void init(){

        functionField.setEditable(false);
        functionField.setColumns(20);
        functionField.setText(function[0]);
        scaleField.setColumns(15);
        //将三个函数表达式添加到下拉框中
        functionChooser.add("function1");
        functionChooser.add("function2");
        functionChooser.add("function3");

        tips.setColumns(20);
        tips.setEditable(false);
        tips.setForeground(Color.RED);

        JPanel jPanel1 = new JPanel();
        jPanel1.add(tips);
        jPanel1.add(functionChooser);
        jPanel1.add(functionField);
        actionButton.setBackground(Color.GREEN);
        jf.setLayout(new BorderLayout());
        jf.add(jPanel1, BorderLayout.SOUTH);

        //添加画布
        JPanel jPanel2 = new JPanel();
        jPanel2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(mc, BorderLayout.CENTER);
        jf.add(jPanel2, BorderLayout.CENTER);

        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout(new FlowLayout());
        //设置布局管理器：顺序水平放置

        enlargerButton.setBackground(Color.LIGHT_GRAY);
        reduceButton.setBackground(Color.LIGHT_GRAY);
        clearButton.setBackground(Color.LIGHT_GRAY);
        backButton.setBackground(Color.LIGHT_GRAY);

        enlargerButton.setActionCommand("Enlarger");
        reduceButton.setActionCommand("Reduce");
        clearButton.setActionCommand("Clear");
        backButton.setActionCommand("Back");

        addButtonListener(enlargerButton);
        addButtonListener(reduceButton);
        addButtonListener(clearButton);
        addButtonListener(backButton);

        jPanel3.add(enlargerButton);
        jPanel3.add(reduceButton);
        jPanel3.add(clearButton);
        jPanel3.add(backButton);

        JLabel label = new JLabel(" scale:");
        jPanel3.add(label);
        jPanel3.add(scaleField);
        jPanel1.add(jPanel3);

        //设置主窗口的大小，可见，默认关闭方式，位于windows窗口中间
        jf.setSize(MYWIDTH,MYHEIGHT);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        //从三个函数中选择一个输入文本框中
        functionChooser.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //toString 调用去除
                String chosenFunction = functionChooser.getSelectedItem();
                if(chosenFunction.equals("function1")){
                    functionField.setText(function[0]);
                }else if(chosenFunction.equals("function2")){
                    functionField.setText(function[1]);
                }else if(chosenFunction.equals("function3")){
                    functionField.setText(function[2]);
                }
            }
        });

        //监听键盘，实现回车解析，ok保存，上下左右键切换选择框
        functionField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if(e.getKeyCode() == KeyEvent.VK_UP){
                    int crtIndex = functionChooser.getSelectedIndex();
                    if(crtIndex > 0){
                        functionChooser.select(crtIndex-1);
                        if(crtIndex == 1){
                            functionField.setText(function[0]);
                        }
                        if(crtIndex == 2){
                            functionField.setText(function[1]);
                        }
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    int currentIndex = functionChooser.getSelectedIndex();
                    if (currentIndex < functionChooser.getItemCount() - 1) {
                        functionChooser.select(currentIndex + 1);
                        if (currentIndex == 0) {
                            functionField.setText(function[1]);
                        }
                        if (currentIndex == 1) {
                            functionField.setText(function[2]);
                        }
                    }
                    // 当按下下键时，如果当前选项不是最后一个选项，那么选项向下移动一位
                }
            }
        });

        //Button?JButton? JButton应该是Button的子类吧

        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(function[0].isEmpty() && function[1].isEmpty() && function[2].isEmpty()){
                    mc.repaint();
                    sd.showThreeFunctionsEmpty(jf);
                }else{
                    mc.repaint();
                }
            }
        });

        jf.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                if (notches < 0) {
                    if (myScale > 5.0 || myScale < 0.25) {
                        myScale = 1;
                        sd.showScaleMessageDialog(jf);
                    } else myScale *= 1.1;
                } else {
                    if (myScale > 5.0 || myScale < 0.25) {
                        myScale = 1;
                        sd.showScaleMessageDialog(jf);
                    } else
                        myScale /= 1.1;
                }
                mc.repaint();
            }
        });
    }

    //内部类，画布
    class MyCanvas extends Canvas{
        @Override
        public void paint(Graphics g){
            scaleField.setText(Double.toString(myScale));

            //获取画笔
            Graphics2D g2  = (Graphics2D) g;
            g2.translate(MYWIDTH / 2,MYHEIGHT / 2 + 30);
            g2.setColor(Color.LIGHT_GRAY);
            //绘制方格图
            for (int i = -MAXSIZE; i <= MAXSIZE; i += 20) {
                g2.drawLine(i, -MAXSIZE / 2, i, MAXSIZE / 2);  //-25到25左右
            }
            for (int i = -MAXSIZE / 2; i <= MAXSIZE / 2; i += 20) {
                g2.drawLine(-MAXSIZE / 2, i, MAXSIZE / 2, i);  //-19到19左右
            }

            g2.scale(myScale,myScale);
            g2.setColor(Color.BLACK);

            g2.drawLine(-MAXSIZE / 2, 0, MAXSIZE / 2, 0);
            g2.drawLine(0, -MAXSIZE / 2, 0, MAXSIZE / 2);

            //绘制刻度
            for (int i = -MAXSIZE / 2; i <= MAXSIZE / 2; i += 20) {
                g2.drawLine(i, -5, i, 5);
                g2.drawString(Integer.toString(i / 20), i, -10);
                //每20个像素点为1个刻度单位
            }
            for (int i = -MAXSIZE / 2; i <= MAXSIZE / 2; i += 20) {
                g2.drawLine(-5, i, 5, i);
                g2.drawString(Integer.toString(-i / 20), 10, i);
            }

            //绘制原点，用8x8的实心矩形覆盖(0,0) 实现坐标原点的绘制
            g2.setColor(Color.BLACK);
            g2.fillArc(-4, -4, 8, 8, 0, 360);
            if(canDraw[0]){
                for (int x = -MAXSIZE * 2; x <= MAXSIZE * 2; x++) {
                    double y = Expre.count(Expre.turnIntoExpression(pre[0]), (double) x /100);  //该函数这里传入的是一个double的值
                    if (y >= (double) -MAXSIZE / 2 && y <= (double) MAXSIZE / 2) {
                        g2.fillOval(x / 5, -(int) (y * 100) / 5, 2, 2);
                        //区分x,y的实际值和图像的值
                        //y的计算处/100之后又*20相当于x/5，保持了一样的缩放比例
                    }
                }
            }
            g2.setColor(Color.BLUE);
             if(canDraw[1]){
                for (int x = -MAXSIZE * 2; x <= MAXSIZE * 2; x++) {
                    double y = Expre.count(Expre.turnIntoExpression(pre[1]), (double) x /100);  //该函数这里传入的是一个double的值
                    if (y >= (double) -MAXSIZE / 2 && y <= (double) MAXSIZE / 2) {
                        g2.fillOval(x / 5, -(int) (y * 100) / 5, 2, 2);
                        //区分x,y的实际值和图像的值
                        //y的计算处/100之后又*20相当于x/5，保持了一样的缩放比例
                    }
                }
            }
            g2.setColor(Color.GREEN);
            if(canDraw[2]){
                for (int x = -MAXSIZE * 2; x <= MAXSIZE * 2; x++) {
                    double y = Expre.count(Expre.turnIntoExpression(pre[2]), (double) x /100);  //该函数这里传入的是一个double的值
                    if (y >= (double) -MAXSIZE / 2 && y <= (double) MAXSIZE / 2) {
                        g2.fillOval(x / 5, -(int) (y * 100) / 5, 2, 2);
                        //区分x,y的实际值和图像的值
                        //y的计算处/100之后又*20相当于x/5，保持了一样的缩放比例
                    }
                }
            }
            //三个函数图像并行绘制
        }
    }

    public void addButtonListener(JButton b){
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Enlarger")) {
                    if (myScale > 5.0 || myScale < 0.25) {
                        myScale = 1;
                        sd.showScaleMessageDialog(jf);
                    } else
                        myScale *= 1.1;
                } else if (e.getActionCommand().equals("Reduce")) {
                    if (myScale > 5.0 || myScale < 0.25) {
                        myScale = 1;
                        sd.showScaleMessageDialog(jf);
                    } else
                        myScale /= 1.1;
                } else if (e.getActionCommand().equals("Clear")) {
                    myScale = 1;
                    functionField.setText("");
                    function[0] = "";
                    function[1] = "";
                    function[2] = "";
                    canDraw[0] = false;
                    canDraw[1] = false;
                    canDraw[2] = false;
                } else if(e.getActionCommand().equals("Back")){
                    jf.setVisible(false);
                    Function_Input fi = new Function_Input();
                    fi.set_Function(function,pre);
                }
                mc.repaint();
            }
        });
        //自定义方法，为放大，缩小清空按钮添加监控事件，用一个方法去处理，减少代码冗余
    }

    public static void main(String[] args){
        new Function_Draw();
    }
}

//每次都只绘制一个函数图像 保存pre的时候出错了？检查绘图的部分有没有出错
