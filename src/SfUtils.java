import org.apache.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 应用的一些工具类
 * Created by Ericwyn on 17-6-16.
 */
public class SfUtils {
    private static Logger logger = Logger.getLogger(SfUtils.class);
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//    public static Date date=new Date();

    /**
     * 从哪个文件夹开始查找kindle目录，window下和linux下不一致
     * @param pathFrom  开始的目标
     * @return  返回的结果
     */
    public static String findKindleDir(String pathFrom){
        File file=new File(pathFrom);
        File[] files=file.listFiles();
        String reback="null";
        for (File fileFlag:files){
            if(fileFlag.isDirectory()){
                String absoultName=fileFlag.getAbsolutePath();
                int nameLeve=absoultName.split("/").length;
                //在给定目录下2级目录里面查找
                if(nameLeve<=4){
                    if(absoultName.endsWith("/Kindle")){
                        reback=absoultName;
                        break;
                    }else {
                        reback=findKindleDir(fileFlag.getAbsolutePath());
                    }
                }
            }
        }

        return reback;
    }

    /**
     * 得到书籍的列表
     * @param path  kindle的文件夹
     * @return  返回的数据
     */
    private static ArrayList<String> getBookList(String path){
        ArrayList<String> list=new ArrayList<>();
        File file=new File(path);

        File[] files=file.listFiles();
        for(File fileFlag:files){
            if(fileFlag.isFile()){
                list.add(fileFlag.getName()         //过滤书籍文件名称
                        .replace(".mobi","")
                        .replace(".MOBI","")
                        .replace(".azw3","")
                        .replace(".AZW3","")
                        .replace(".azw","")
                        .replace(".AZW","")
                        .replace(".txt","")
                        .replace(".TXT","")
                        .replace(".pdf","")
                        .replace(".PDF","")
                        .replace(".prc","")
                        .replace(".PRC","")
                );
            }
        }
        return list;
    }

    /**
     * 得到kindle的document里面的文件夹列表
     * @param path  kindle的存储文件夹
     * @return  返回的数据
     */
    private static ArrayList<String> getDirList(String path){
        ArrayList<String> list=new ArrayList<>();
        File file=new File(path);
        File[] files=file.listFiles();
        for(File fileFlag:files){
            if(fileFlag.isDirectory()){
                if(fileFlag.getName().matches("^(.*?)[.][s][d][r]$")){
                    list.add(fileFlag.getName().replace(".sdr",""));
                }
            }
        }
        return list;
    }

    /**
     * 备份的方法
     * @param kindlePath    kindle
     * @return  返回的处理代码
     */
    public static String backupSDR(String kindlePath){
        String backupDirName=sdf.format(new Date());

        String path=kindlePath+"/documents";
        ArrayList<String> dirList=getDirList(path);
        ArrayList<String> bookList=getBookList(path);
        ArrayList<String> needBackupDir=new ArrayList<>();

        for(String str:dirList){
            if(!bookList.contains(str)){
                needBackupDir.add(str);
            }
        }

        if(needBackupDir.size()!=0){
            logger.info("开始备份文件夹");
            logger.info("----------------------------");
            File backDir=new File("cache/"+backupDirName);
            if(!backDir.isDirectory()){
                backDir.mkdirs();
            }
            for (String str:needBackupDir){
                String pathFrom=kindlePath+"/documents/"+str+".sdr";
                String pathTo=backDir.getAbsolutePath()+"/"+str+".sdr";
                logger.info("来源目标："+pathFrom);
                logger.info("复制目标："+pathTo);
                FileUtils.copyDie(pathFrom,pathTo);
            }
            return backupDirName;
        }else {
            return "hahahaha233";
        }

    }

    /**
     * 清空缓存的方法
     * @param kindlePath kindle
     * @return  返回处理代码
     */
    public static int deleteSDR(String kindlePath){
        int backCode=0;
        String path=kindlePath+"/documents";
        ArrayList<String> dirList=getDirList(path);
        ArrayList<String> bookList=getBookList(path);
        logger.info("开始清理残余文件夹");
        logger.info("----------------------------");
        for(String str:dirList){
            if(!bookList.contains(str)){
                String pathFrom=kindlePath+"/documents/"+str+".sdr";
                logger.info("开始删除："+pathFrom);
                if(FileUtils.deleteDir(new File(pathFrom))){
                    logger.info("删除成功");
                }else {
                    logger.info("删除失败");
                }
            }
        }
        return backCode;
    }

    /**
     * 通过是否含有documents文件夹和system文件夹，来判断路径是否真的是kindle的路径
     * @param path
     * @return
     */
    public static boolean isKindlePath(String path){
        File file=new File(path);
        if(!file.isDirectory()){
            return false;
        }else {
            String[] filesName=file.list();
            return haveDocument(filesName,"documents") && haveDocument(filesName,"system");
        }
    }

    /**
     * 判断文件夹下面是否存在某些特定的文件夹，以此判断是否属于Kindle的路径
     * @param filesName
     * @return
     */
    private static boolean haveDocument(String[] filesName,String documentName){
        for (String nameFlag:filesName){
            if(nameFlag.equals(documentName)){
                return true;
            }
        }
        return false;
    }
}
