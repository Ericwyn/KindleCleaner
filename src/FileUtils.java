import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件工具类
 * Created by ericwyn on 17-4-2.
 */
public class FileUtils {
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
                    System.out.println("复制文件: "+fileFlag.getAbsolutePath()+"成功");
                    bufferedInputStream.close();
                    bufferedOutputStream.close();
                }catch (IOException ioe){
                    System.out.println("复制文件: "+fileFlag.getAbsolutePath()+"的时候出现了错误");
                    ioe.printStackTrace();
                }
            }else if(fileFlag.isDirectory()){
                String newDirName=pathTo+"/"+fileFlag.getName();
                new File(newDirName).mkdir();
                System.out.println("内层复制开始");
                System.out.println(fileFlag.getAbsolutePath());
                System.out.println(newDirName);
                copyDie(fileFlag.getAbsolutePath(),newDirName);
            }
        }
    }
}
