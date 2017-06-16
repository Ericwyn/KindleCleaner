import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * GUI类别
 * Created by ericwyn on 17-4-5.
 */
public class MainGUI extends JFrame {
    private static Logger logger = Logger.getLogger(MainGUI.class);
    private static Font MY_FONT = loadFont("font/SourceHanSansK-Medium.ttf",18);
    private static Font MY_FONT2 = loadFont("font/SourceHanSansK-Medium.ttf",14);

    public static final int DEFAULT_WIDTH=800;
    public static final int DEFAULT_HEIGHT=250;
    private JPanel panel;
    private JLabel pathText;
    private JTextField pathWord;
    private JButton findKindleButton;
    private JButton clearButton;

    private String kindlePathFirst;


    public MainGUI(String kindlePath){
        this.kindlePathFirst=kindlePath;
        this.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Kindle清理助手");
        initView();
        this.add(panel);
    }


    private void initView(){
        panel=new JPanel();
        panel.setLayout(null);

        pathText=new JLabel("Kindle路径");
        pathText.setBounds(50,80,90,40);
        pathText.setFont(MY_FONT);
        pathText.setForeground(Color.black);
        panel.add(pathText);

        pathWord=new JTextField();
        pathWord.setBounds(170,80,460,40);
        if(kindlePathFirst!=null && !kindlePathFirst.equals("null")){
            pathWord.setText(kindlePathFirst);
        }else {
            pathWord.setText("请选择Kindle所在路径");
        }
        pathWord.setFont(MY_FONT);
        pathWord.setEditable(false);
        pathWord.setForeground(Color.black);

        panel.add(pathWord);

        findKindleButton=new JButton("选择");
        findKindleButton.setBounds(680,80,80,40);
        findKindleButton.setFont(MY_FONT);
        findKindleButton.setForeground(Color.black);
        findKindleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //开启一个文件选择器
                JFileChooser jfc=new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );
                jfc.setDialogTitle("选择Kindle的存储文件夹");
                jfc.showDialog(new JLabel(), "选择");
                jfc.setFont(MY_FONT);
                File file=jfc.getSelectedFile();
                if (file!=null){
                    pathWord.setText(file.getAbsolutePath());
                }
            }
        });
        panel.add(findKindleButton);

        pathText=new JLabel();
        pathText.setBounds(170,120,460,40);
        pathText.setFont(MY_FONT);
        panel.add(pathText);

        clearButton=new JButton("即刻清理");
        clearButton.setBounds(320,180,150,40);
        clearButton.setFont(MY_FONT);
        clearButton.setForeground(Color.black);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String kindlePath=pathWord.getText();
                if(SfUtils.isKindlePath(kindlePath)){
                    String backupDirName=SfUtils.backupSDR(kindlePath);
                    if(backupDirName.equals("hahahaha233")){
                        pathText.setText("此Kindle无需清理");
                    }else {
                        SfUtils.deleteSDR(kindlePath);
                        pathText.setText("清理完毕，缓存文件在cache/"+backupDirName);
                    }
                }else {
                    pathText.setText("Kindle路径不正确，请选择正确的路径");
                }
            }
        });
        panel.add(clearButton);

    }




    /**
     * 加载自定义字体，仅支持ttf
     * @param fontFileName 字体的名称
     * @param fontSize 字体的大小
     * @return 返回font
     */
    private static Font loadFont(String fontFileName, float fontSize) {
        try
        {
            File file = new File(fontFileName);
            FileInputStream aixing = new FileInputStream(file);
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);
            Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
            aixing.close();

            return dynamicFontPt;
        }
        catch(Exception e)//异常处理
        {
            e.printStackTrace();
            return new java.awt.Font("宋体", Font.PLAIN, 14);
        }
    }

}
