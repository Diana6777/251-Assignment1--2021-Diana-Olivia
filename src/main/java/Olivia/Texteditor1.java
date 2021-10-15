package Olivia;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;


//记事本class继承JFrame并使用ActionListener接口
public class Texteditor1 extends JFrame implements ActionListener {
    //属性 规定菜单栏 菜单组成 和 JMenuItem下拉框
    JMenuBar menubar; //菜单栏
    JMenu file, search,format, view,help;  //菜单栏项目
    JMenuItem create,open,save,saveto,set,print,exit;  //菜单栏项目下拉框 file
   //菜单栏项目下拉框 search
    JMenuItem cut,copy,paste, searchtext, selectall, time;   //菜单栏项目下拉框 search
    //JMenuItem cx,cut,copy,paste,delete,cz,th,zd,qx,rq;
    JMenuItem font,ztl,ckbz, about;  //菜单栏项目下拉框format
    JCheckBoxMenuItem zdhh;  //菜单栏查看按钮
    JTextArea panel;   //文本域
    JScrollPane jsp;    //滚动条
    private static String name;
    public Clipboard clipboard = null;
    JFileChooser filechooser;   //文件选择


    //Texteditor 构造 方法
    public Texteditor1(){
        super("Text Editor");
        //实例化
        menubar=new JMenuBar();//菜单栏
        filechooser=new JFileChooser();
        //菜单栏项目
        file=new JMenu("File");
        search =new JMenu("Search");
        format=new JMenu("Format");
        view =new JMenu("View");
        help=new JMenu("Help");
        //菜单栏项目下拉表
        create=new JMenuItem("New");
        open=new JMenuItem("Open");
        save=new JMenuItem("Save");
        saveto=new JMenuItem("saveas");
        set=new JMenuItem("页面设置");
        print=new JMenuItem("打印");
        exit=new JMenuItem("退出");

        cut=new JMenuItem("cut");
        copy=new JMenuItem("copy");
        paste=new JMenuItem("paste");
        searchtext =new JMenuItem("searchtext");
        selectall =new JMenuItem("selectall");
        time =new JMenuItem("date & time");
        zdhh=new JCheckBoxMenuItem("自动换行");
        font=new JMenuItem("字体");
        ztl=new JMenuItem("状态栏");
        ckbz=new JMenuItem("查看帮助");
        about =new JMenuItem("关于记事本");

        //菜单栏项目加到菜单栏
        menubar.add(file);
        menubar.add(search);
        menubar.add(format);
        menubar.add(view);
        menubar.add(help);
        //catch block自动生成
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedLookAndFeelException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        //下拉框列表放入菜单栏项目file
        file.add(create);
        file.add(open);
        file.add(save);
        file.add(saveto);
        file.addSeparator();
        file.add(set);
        file.add(print);
        file.addSeparator();
        file.add(exit);

        search.add(cut);
        search.add(copy);
        search.add(paste);
        search.add(searchtext);
        search.add(selectall);
        search.add(time);

        format.add(zdhh);
        format.add(font);

        view.add(ztl);

        help.add(ckbz);
        help.add(about);

        //添加事件监听器
        create.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        saveto.addActionListener(this);
        set.addActionListener(this);
        exit.addActionListener(this);
        about.addActionListener(this);
        help.addActionListener(this);
        zdhh.addActionListener(this);
        font.addActionListener(this);
        searchtext.addActionListener(this);//实现查找功能
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectall.addActionListener(this);
        time.addActionListener(this);
        print.setEnabled(false);   //实现打印功能

        this.setJMenuBar(menubar);           //Texteditor设置菜单栏
        panel=new JTextArea();                 //新建文本域
        panel.setBackground(Color.pink);

        //panel.setLayout(new FlowLayout());      //设置流式布局

        //滚动条
        jsp=new JScrollPane(panel);            //new一个滚动条
        jsp.setPreferredSize(new Dimension(780,550));  //超出范围会出现滚动条
        this.add(jsp,BorderLayout.CENTER);         //加入到Texteditor

        //this.add(panel);
        this.setBounds(600, 300, 600, 400);    //设置大小
        this.setVisible(true);                                       //设置可见性


        //添加窗口监听器
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { System.exit(0); }  //实现关闭功能exit
        });

    }


    //主函数 实例化Texteditor
    public static void main(String[] args) {
        Texteditor1 note=new Texteditor1();
    }

    //事件监听动作监听
    @Override
    public void actionPerformed(ActionEvent e) {
        //e 为create新文件调用方法执行创建新文件
        if(e.getSource()==create){ createFile(); }
        //退出
        if(e.getSource()==exit){ System.exit(0); }
        //打开新文件
        if(e.getSource()==open){
            filechooser.showOpenDialog(this);  //选择文件
            panel.setText(openFile()); }
        //保存和另存为功能
        if(e.getSource()==save||e.getSource()==saveto){
            filechooser.showSaveDialog(this);
            File file=filechooser.getSelectedFile(); //选择的文件夹
            saveFile(file); }
        //设置
        if(e.getSource()==set){
            String s=JOptionPane.showInputDialog(this,"请输入宽、高：","默认：600 400");
            String[] str=new String[2];
            str=s.split(" ");
            int wigth=Integer.parseInt(str[0]);
            int height=Integer.parseInt(str[1]);
            this.setSize(wigth, height); }
        if (e.getSource()==print){
            print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
            print.addActionListener(this);
        }
        if(e.getSource()==time){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
            // 追加到内容里
            panel.append(date);
//            df.format(new Date());
//            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

//            JOptionPane.showMessageDialog(this, df.format(new Date()));
        }
        if(e.getSource()==about){
            JOptionPane.showMessageDialog(this, "member1:Yang Wandi 20008106;menber2:Ma Yihan 20008098\nLove learning!");
        }
        //选择
        if(e.getSource()==selectall){ panel.selectAll(); }
        //复制
        if(e.getSource()==copy){ panel.copy(); }
        //剪切
        if(e.getSource()==cut){ panel.cut();
            panel.requestFocus();
            String text=panel.getSelectedText();
            StringSelection selection=new StringSelection(text);//为了配合剪切板调用的
            clipboard.setContents(selection, null);
        }
        //粘贴
        if(e.getSource()==paste){ panel.paste(); }
        //查找
       if(e.getSource()==searchtext){
           new Chazhao(); }

        //时间
//
        //font        if(e.getSource()== time){
        ////            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ////            JOptionPane.showMessageDialog(this, df.format(new Date())); }
        if(e.getSource()==font){
            String s=JOptionPane.showInputDialog(this,"请输入字体名称：","宋体");
            panel.setFont(new Font(s,Font.TYPE1_FONT,20)); }
        //about
//        if(e.getSource()== about){ JOptionPane.showMessageDialog(this, "number: MaYihan 20008098 YangWandi"); }
        //help
        if(e.getSource()==help){
            String web="www.baidu.com";
            Desktop desktop=Desktop.getDesktop();
            try {
                desktop.browse(new URI(web));
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        //auto-nextline
        if(e.getSource()==zdhh){
            if(zdhh.isSelected()){
                panel.setLineWrap(true);
            }else{
                panel.setLineWrap(false );
            }
        }
    }

    @SuppressWarnings("finally")


    // openFile function
    private String openFile() {
        FileInputStream is=null;
        StringBuilder sb=null;
        //FileOutputStream fo=null;
        try {
            is=new FileInputStream(filechooser.getSelectedFile());
            sb=new StringBuilder();
            byte[] data=new byte[1024];
            int len=-1;
            try {
                while((len=is.read(data))!=-1){
                    String str=new String(data,0,len);
                    sb.append(str);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            name = filechooser.getSelectedFile().getName();
            this.setTitle(name + " - 记事本");
            return sb.toString();
        }

    }

    //function to create a new File
    private void createFile() {
        // String name = null;
        File file = null;
        // 选择保存或取消
        if (filechooser.showSaveDialog(Texteditor1.this) == JFileChooser.APPROVE_OPTION) {
            file = filechooser.getSelectedFile();// 获取选中的文件
        } else {
            return;
        }
        name = filechooser.getName(file);// 获取输入的文件名
        if (file.exists()) { // 若选择已有文件----询问是否要覆盖
            int i = JOptionPane.showConfirmDialog(null, "该文件已存在,是否覆盖原文件", "确认", JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                saveFile(file);
            } else {
                filechooser.showSaveDialog(Texteditor1.this);// 重新选择
            }
        } else {//文件不存在，则直接保存
            saveFile(file);
        }
    }



    //function to create a new File
    private void saveFile(File file) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(panel.getText());//写入文件
            bw.flush();
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(Texteditor1.this, "文件保存出错" + e1.getMessage());
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e1) {
            }
        }
    }
//    private String cut(){}  cut copy paste searchtext selectall


    public void copy(){
        String temp = panel.getSelectedText();
        StringSelection text = new StringSelection(temp);
        clipboard.setContents(text, null);
    }
    public void paste() {
        //Transferable，把剪贴板的内容转换成数据
        //use Transferable object to make the content of clipboard into data
        Transferable contents =  clipboard.getContents(this);
        //DataFalvor
        DataFlavor flavor = DataFlavor.stringFlavor;
        //如果可以转换
        //if can be converted
        if (contents.isDataFlavorSupported(flavor)) {
            String str;
            try {//开始转换
                //start to convert
                str = (String) contents.getTransferData(flavor);
                //如果要粘贴时，鼠标已经选中了一些字符
                //if paste when the mouse has selected some string things
                if (panel.getSelectedText() != null) {
                    //定位被选中字符的开始位置
                    //sign the selecting start position
                    int start = panel.getSelectionStart();
                    //定位被选中字符的末尾位置
                    //sign the selecting end position
                    int end = panel.getSelectionEnd();
                    //把粘贴的内容替换成被选中的内容
                    //paste the real string things between the selecting start and end position
                    panel.replaceRange(str, start, end);

                } else {
                    //获取鼠标所在TextArea的位置
                    //get the mouse focus position in TextArea
                    int mouse = panel.getCaretPosition();
                    //在鼠标所在的位置粘贴内容
                    //paste the real things to the position
                    panel.insert(str, mouse);
                }

            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public	class Chazhao implements ActionListener{
        JFrame frame;
        JLabel label1;
        JTextField fiel;
        JButton but1;
        Chazhao(){
            frame=new JFrame();
            frame.setSize(400,160);
            frame.setLocation(500, 400);
            frame.setResizable(false);
            frame.setTitle("查找");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel jpanel=new JPanel();
            label1=new JLabel("查找内容(N)");
            fiel=new JTextField(10);
            but1=new JButton("查找 下一 个(F)");
            but1.addActionListener(this);
            JButton but2=new JButton("取消");
            JCheckBox box=new JCheckBox("区分大小写(C)");
            JLabel label2=new JLabel("方向");
            ButtonGroup group=new ButtonGroup();
            JRadioButton jradio1=new JRadioButton("向上");
            JRadioButton jradio2=new JRadioButton("向下");
            jpanel.add(label1);
            jpanel.add(fiel);
            jpanel.add(but1);
            jpanel.add(box);
            jpanel.add(label2);
            jpanel.add(label2);
            group.add(jradio1);
            jpanel.add(jradio1);
            group.add(jradio2);
            jpanel.add(jradio2);
            jpanel.add(but2);
            frame.add(jpanel);
            frame.setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            String s=panel.getText();			//获取文本框text的内容
            String m=fiel.getText();			//获取差债文本框里的内容
            int x=s.indexOf(m,panel.getSelectionEnd());  //indexOf方法获取字符串m在text中的首位置  返回为int类型
            int n=x+m.length();					//获取第一个字符串的尾位置
            panel.select(x,n);					//显示在text文本框中
            int next=n;
            panel.select(next,s.length());		//获取剩下的所有文本
            String ss=panel.getSelectedText();
            int newstartp=ss.indexOf(fiel.getText())+next;	//获取剩下的file文本在text文本中的首位置
            int newendp=newstartp+fiel.getText().length();		//尾位置
            panel.select(newstartp, newendp);			//显示
            if(x==-1) {
                JOptionPane.showConfirmDialog(null, "找到第一个 " +m+"点击下一个继续");
            }
        }


    }
}



























