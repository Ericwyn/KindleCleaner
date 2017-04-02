import java.io.File;
import java.util.ArrayList;

/**
 * 直接写一个小助手，清理kindle里面的残余文件
 * kindle里面的书籍在你在kindle删除之后依然会有残留的文件夹
 * 然后看着特别心烦，于是还是写一个小助手帮忙删除吧
 * 不过因为害怕删掉了什么重要的东西所有这个小程序会先把那些要删除的文件备份下来
 * Created by ericwyn on 17-4-2.
 */
public class Main {
    public static void main(String [] args){
        String path="/media/ericwyn/Kindle/documents";
        ArrayList<String> dirList=getDirList(path);
        ArrayList<String> bookList=getBookList(path);
        System.out.println("即将删除");
        for(String str:dirList){
            if(!bookList.contains(str)){
                System.out.println(str);
            }
        }
    }

    public static ArrayList<String> getBookList(String path){
        ArrayList<String> list=new ArrayList<>();
        File file=new File(path);

        File[] files=file.listFiles();
        for(File fileFlag:files){
//            if(file.getName().matches("^(.*?)[.][j][a][v][a]$"))
            if(fileFlag.isFile()){
                list.add(fileFlag.getName()
                        .replace(".mobi","")
                        .replace(".azw3","")
                        .replace(".azw","")
                        .replace(".txt","")
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


}
