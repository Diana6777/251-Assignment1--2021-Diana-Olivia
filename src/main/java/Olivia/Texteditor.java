package Olivia;

        import java.awt.BorderLayout;
        import java.awt.Desktop;
        import java.awt.Dimension;
        import java.awt.Font;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.event.WindowAdapter;
        import java.awt.event.WindowEvent;
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
        import java.awt.event.KeyEvent;
        import java.awt.event.InputEvent;
        import javax.swing.*;


        public class Texteditor extends JFrame implements ActionListener {
    private JMenuItem itemPrint;
    JMenuBar menubar;
    JMenu file, search,watch,manage,help;
    JMenuItem create,open,save,saveto,set,print,exit;
    JMenuItem cx,cut,copy,paste,delete,cz,th,zd,qx,rq;
    JMenuItem font,ztl,ckbz,gy;
    JCheckBoxMenuItem zdhh;
    JTextArea panel;
    JScrollPane jsp;
    private static String name;
    JFileChooser filechooser;
    public Texteditor(){
        super("Text Editor");
        menubar=new JMenuBar();
        filechooser=new JFileChooser();
        file=new JMenu("File");
        search =new JMenu("Search");
        watch=new JMenu("View");
        manage=new JMenu("Manage");
        help=new JMenu("Help");

        create=new JMenuItem("New");
        open=new JMenuItem("Open");
        save=new JMenuItem("Save");
        saveto=new JMenuItem("另存为");
        set=new JMenuItem("页面设置");
        print=new JMenuItem("Print");
        exit=new JMenuItem("退出");
        cx=new JMenuItem("撤销");cut=new JMenuItem("剪切");copy=new JMenuItem("拷贝");paste=new JMenuItem("粘贴");
        delete=new JMenuItem("删除");
        cz=new JMenuItem("查找");
        th=new JMenuItem("替换");
        zd=new JMenuItem("转到");
        qx=new JMenuItem("全选");
        rq=new JMenuItem("日期/时间");
        zdhh=new JCheckBoxMenuItem("自动换行");
        font=new JMenuItem("字体");
        ztl=new JMenuItem("状态栏");
        gy=new JMenuItem("About");
        menubar.add(file);
        menubar.add(search);
        menubar.add(manage);
        menubar.add(watch);
        menubar.add(help);
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
        file.add(create);file.add(open);file.add(save);file.add(saveto);file.addSeparator();file.add(set);file.add(print);file.addSeparator();file.add(exit);
        search.add(cx);
        search.addSeparator();
        search.add(cut);
        search.add(copy);
        search.add(paste);
        search.add(delete);
        search.addSeparator();
        search.add(cz);
        search.add(th);
        search.add(zd);
        search.addSeparator();
        search.add(qx);
        search.add(rq);
        cx.setEnabled(false);cut.setEnabled(false);copy.setEnabled(false);paste.setEnabled(false);delete.setEnabled(false);cz.setEnabled(false);th.setEnabled(false);
        zd.setEnabled(false);qx.setEnabled(false);
        manage.add(zdhh);manage.add(font);
        watch.add(ztl);help.addSeparator();help.add(gy);
        create.addActionListener(this);open.addActionListener(this);save.addActionListener(this);print.addActionListener(this);
        saveto.addActionListener(this);set.addActionListener(this);exit.addActionListener(this);gy.addActionListener(this);help.addActionListener(this);
        zdhh.addActionListener(this);font.addActionListener(this);rq.addActionListener(this);
        print.setEnabled(false);
        this.setJMenuBar(menubar);
        panel=new JTextArea();
        //panel.setLayout(new FlowLayout());
        jsp=new JScrollPane(panel);
        jsp.setPreferredSize(new Dimension(780,550));
        this.add(jsp,BorderLayout.CENTER);
        //this.add(panel);
        this.setBounds(600, 300, 600, 400);
        this.setVisible(true);
        this.setIconImage(new ImageIcon("src\\1.ico").getImage());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    public static void main(String[] args) {
        Texteditor note=new Texteditor();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==create){
            createFile();
        }
        if(e.getSource()==exit){
            System.exit(0);
        }
        if(e.getSource()==open){
            filechooser.showOpenDialog(this);
            panel.setText(openFile());
        }
        if(e.getSource()==save||e.getSource()==saveto){
            filechooser.showSaveDialog(this);
            File file=filechooser.getSelectedFile();
            saveFile(file);
        }
        if(e.getSource()==set){
            String s=JOptionPane.showInputDialog(this,"请输入宽、高：","默认：600 400");
            String[] str=new String[2];
            str=s.split(" ");
            int wigth=Integer.parseInt(str[0]);
            int height=Integer.parseInt(str[1]);
            this.setSize(wigth, height);
        }
        if (e.getSource()==print){
            print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
            print.addActionListener(this);
        }
        if(e.getSource()==copy){
            panel.copy();
        }
        if(e.getSource()==cut){
            panel.cut();
        }
        if(e.getSource()==paste){
            panel.paste();
        }
        if(e.getSource()==rq){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
            // 追加到内容里
            panel.append(date);
//            df.format(new Date());
//            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

//            JOptionPane.showMessageDialog(this, df.format(new Date()));
        }
        if(e.getSource()==font){
            String s=JOptionPane.showInputDialog(this,"请输入字体名称：","宋体");
            panel.setFont(new Font(s,Font.TYPE1_FONT,20));
        }
        if(e.getSource()==gy){
            JOptionPane.showMessageDialog(this, "member1:Yang Wandi 20008106;menber2:Ma Yihan 20008098\nLove learning!");
        }
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
        if(e.getSource()==zdhh){
            if(zdhh.isSelected()){
                panel.setLineWrap(true);
            }else{
                panel.setLineWrap(false );
            }
        }
    }
    @SuppressWarnings("finally")
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

    private void createFile() {
        // String name = null;
        File file = null;
        // 选择保存或取消
        if (filechooser.showSaveDialog(Texteditor.this) == JFileChooser.APPROVE_OPTION) {
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
                filechooser.showSaveDialog(Texteditor.this);// 重新选择
            }
        } else {//文件不存在，则直接保存
            saveFile(file);
        }
    }
    private void saveFile(File file) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(panel.getText());//写入文件
            bw.flush();
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(Texteditor.this, "文件保存出错" + e1.getMessage());
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
    private void Print(){

    }

}

