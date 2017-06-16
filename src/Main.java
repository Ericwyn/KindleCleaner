
import org.apache.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * 直接写一个小助手，清理kindle里面的残余文件
 * kindle里面的书籍在你在kindle删除之后依然会有残留的文件夹
 * 然后看着特别心烦，于是还是写一个小助手帮忙删除吧
 * 不过因为害怕删掉了什么重要的东西所有这个小程序会先把那些要删除的文件备份下来
 * Created by ericwyn on 17-4-2.
 */
public class Main {
    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String [] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        }catch (Exception e){
            logger.info(e.toString());
        }

        String path="/media";
        String kindlePath=SfUtils.findKindleDir(path);
        JFrame jFrame=new MainGUI(kindlePath);
        jFrame.setVisible(true);
    }
}
