
package src.firstcalculator.GraphicCalculator;

import src.function.Expre;
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

    private double offsetx = 0;
    private double offsety = 0;

    public String[] function = new String[3];
    {
        Arrays.fill(function, "");
    }
//    Choice functionChooser = new Choice();

    public boolean[] canDraw = new boolean[3];
    {
        Arrays.fill(canDraw,false);
    }

    public JFrame jf = new JFrame("函数图像");

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
//                    functionField.setText("");
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
        scaleField.setColumns(15);

        JPanel jPanel1 = new JPanel();
//        jPanel1.add(functionChooser);
//        jPanel1.add(functionField);
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
        JTextField inputField1 = new JTextField(10);
        JTextField inputField2 = new JTextField(10);

// 添加一个JButton用于获取输入框数值
        JButton getValuesButton = new JButton("设置偏移");

// 为按钮添加ActionListener
        getValuesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取输入框中的数值
                try{
                    String value1 = inputField1.getText();
                    String value2 = inputField2.getText();

                    double centerX = Double.parseDouble(value1);
                    double centerY = Double.parseDouble(value2);

                    // 计算偏移量
                     offsetx = centerX;
                     offsety = centerY ;
                     mc.setOffset(offsetx,offsety);
                     mc.repaint();
                }catch (Exception ex){
                    sd.showEmptyOffsetDialog(jf);
                }


            }
        });

// 将输入框和按钮添加到相应的容器中
        jPanel3.add(inputField1);
        jPanel3.add(inputField2);
        jPanel3.add(getValuesButton);


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

        private double offsetX;
        private double offsetY;
        public void setOffset(double offsetX,double offsetY){
            this.offsetX = offsetX;
            this.offsetY = offsetY;
        }

        @Override
        public void paint(Graphics g){
            scaleField.setText(Double.toString(myScale));
            //获取画笔
            Graphics2D g2  = (Graphics2D) g;

            g2.translate((double) MYWIDTH / 2-offsetX*20/myScale, (double) MYHEIGHT / 2 + 30 + offsetY*20/myScale);
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
            g2.setColor(Color.RED);

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if(canDraw[0]){
                for (int x = -MAXSIZE * 2; x <= MAXSIZE * 2; x++) {
                    double y1 = Expre.count(Expre.turnIntoExpression(pre[0]), (double) x /100);
                    double y2 = Expre.count(Expre.turnIntoExpression(pre[0]), (double) (x + 1) /100);
                    if (y1 >= (double) -MAXSIZE / 2 && y1 <= (double) MAXSIZE / 2 &&
                            y2 >= (double) -MAXSIZE / 2 && y2 <= (double) MAXSIZE / 2) {
                        g2.drawLine(x/5, (int) -(y1 * 100)/5 , (x+1)/5 , (int) -(y2 * 100)/5 );
                        //区分x,y的实际值和图像的值
                        //y的计算处/100之后又*20相当于x/5，保持了一样的缩放比例
                    }

                }
            }
            g2.setColor(Color.BLUE);
             if(canDraw[1]){
                for (int x = -MAXSIZE * 2; x <= MAXSIZE * 2; x++) {
                    double y1 = Expre.count(Expre.turnIntoExpression(pre[1]), (double) x /100);
                    double y2 = Expre.count(Expre.turnIntoExpression(pre[1]), (double) (x + 1) /100);
                    if (y1 >= (double) -MAXSIZE / 2 && y1 <= (double) MAXSIZE / 2 &&
                            y2 >= (double) -MAXSIZE / 2 && y2 <= (double) MAXSIZE / 2) {
                        g2.drawLine(x/5, (int) -(y1 * 100)/5, (x+1)/5 , (int) -(y2 * 100)/5);
                        //区分x,y的实际值和图像的值
                        //y的计算处/100之后又*20相当于x/5，保持了一样的缩放比例
                    }
                }
            }
            g2.setColor(Color.GREEN);
            if(canDraw[2]){
                for (int x = -MAXSIZE * 2; x <= MAXSIZE * 2; x++) {
                    double y1 = Expre.count(Expre.turnIntoExpression(pre[2]), (double) x /100);
                    double y2 = Expre.count(Expre.turnIntoExpression(pre[2]), (double) (x + 1) /100);
                    if (y1 >= (double) -MAXSIZE / 2 && y1 <= (double) MAXSIZE / 2 &&
                            y2 >= (double) -MAXSIZE / 2 && y2 <= (double) MAXSIZE / 2) {
                        g2.drawLine(x/5, (int) -(y1 * 100)/5, (x+1)/5 , (int) -(y2 * 100)/5);
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
//                    functionField.setText("");
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