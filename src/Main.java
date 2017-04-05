
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * 直接写一个小助手，清理kindle里面的残余文件
 * kindle里面的书籍在你在kindle删除之后依然会有残留的文件夹
 * 然后看着特别心烦，于是还是写一个小助手帮忙删除吧
 * 不过因为害怕删掉了什么重要的东西所有这个小程序会先把那些要删除的文件备份下来
 * Created by ericwyn on 17-4-2.
 */
public class Main {
    private static Date date=new Date();
    public static void main(String [] args){
        String path="/media";
        String kindlePath=findKindleDir(path);
        backupSDR(kindlePath);
        System.out.println("------------");
        System.out.println("------------");
        System.out.println("------------");
        deleteSDR(kindlePath);
    }

    public static String findKindleDir(String pathFrom){
        File file=new File(pathFrom);
        File[] files=file.listFiles();
        for(File fileFlag:files){
            if(fileFlag.isDirectory()){
                String absoultName=fileFlag.getAbsolutePath();
                String nameLeve[]=absoultName.split("/");
                if(nameLeve.length<=4){
                    if(nameLeve[nameLeve.length-1].equals("Kindle")){
                        return absoultName;
                    }else {
                        return findKindleDir(fileFlag.getAbsolutePath());
                    }
                }
            }
        }
        return "null";
    }


    public static ArrayList<String> getBookList(String path){
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

    public static ArrayList<String> getDirList(String path){
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

    public static int backupSDR(String kindlePath){
        int backCode=0;
        File backDir=new File(date.toString());
        if(!backDir.isDirectory()){
            backDir.mkdir();
        }
        String path=kindlePath+"/documents";
        ArrayList<String> dirList=getDirList(path);
        ArrayList<String> bookList=getBookList(path);
        System.out.println("开始备份文件夹");
        System.out.println("----------------------------");
        for(String str:dirList){
            if(!bookList.contains(str)){
                String pathFrom=kindlePath+"/documents/"+str+".sdr";
                String pathTo=date.toString()+"/"+str+".sdr";
                System.out.println("来源目标："+pathFrom);
                System.out.println("复制目标："+pathTo);
                FileUtils.copyDie(pathFrom,pathTo);
            }
        }
        return backCode;
    }

    public static int deleteSDR(String kindlePath){
        int backCode=0;
        Date date=new Date();
        File backDir=new File(date.toString());
        if(!backDir.isDirectory()){
            backDir.mkdir();
        }
        String path=kindlePath+"/documents";
        ArrayList<String> dirList=getDirList(path);
        ArrayList<String> bookList=getBookList(path);
        System.out.println("开始清理残余文件夹");
        System.out.println("----------------------------");
        for(String str:dirList){
            if(!bookList.contains(str)){
                String pathFrom=kindlePath+"/documents/"+str+".sdr";
                System.out.println("开始删除："+pathFrom);
                if(FileUtils.deleteDir(new File(pathFrom))){
                    System.out.println("删除成功");
                }else {
                    System.out.println("删除失败");
                }

            }
        }
        return backCode;
    }

}
