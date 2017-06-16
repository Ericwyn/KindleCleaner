import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件工具类
 * Created by ericwyn on 17-4-2.
 */
public class FileUtils {
    private static Logger logger = Logger.getLogger(FileUtils.class);
    /**
     * 多层文件夹的复制
     * @param pathFrom  来源文件夹的路径
     * @param pathTo    目标文件夹的路径
     */
    public static void copyDie(String pathFrom,String pathTo){
        File file=new File(pathFrom);
        File[] files=file.listFiles();
        if(!new File(pathTo).isDirectory()){
            new File(pathTo).mkdir();
        }
        for(File fileFlag:files){
            if(fileFlag.isFile()){
                try {
                    BufferedInputStream bufferedInputStream=
                            new BufferedInputStream(
                                    new FileInputStream(fileFlag.getAbsolutePath()));
                    BufferedOutputStream bufferedOutputStream=
                            new BufferedOutputStream(
                                    new FileOutputStream(pathTo+"/"+fileFlag.getName()));
                    byte[] bytes=new byte[1024];
                    while (bufferedInputStream.read(bytes)!=-1){
                        bufferedOutputStream.write(bytes);
                    }
                    logger.info("复制文件: "+fileFlag.getAbsolutePath()+"成功");
                    bufferedInputStream.close();
                    bufferedOutputStream.close();
                }catch (IOException ioe){
                    logger.info("复制文件: "+fileFlag.getAbsolutePath()+"的时候出现了错误");
                    ioe.printStackTrace();
                }
            }else if(fileFlag.isDirectory()){
                String newDirName=pathTo+"/"+fileFlag.getName();
                new File(newDirName).mkdir();
                logger.info("内层复制开始");
                logger.info(fileFlag.getAbsolutePath());
                logger.info(newDirName);
                copyDie(fileFlag.getAbsolutePath(),newDirName);
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}
