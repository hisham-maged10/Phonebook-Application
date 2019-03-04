import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.File;
public class FileHandler{

    public static <T> boolean writeFile(T[] arr,String filename, boolean append)
    {
        if(filename==null || arr==null)
            return false;
        try(PrintWriter printer=new PrintWriter(new FileWriter(filename,append)))
        {
            for(T e: arr)
                printer.println(e);

        }catch(IOException ex)
        {
            return false;
        }
        return true;
    }
    public static Contact[] loadFile(String filename)
    {
        if(filename==null || !new File(filename).exists())
            return new Contact[0];
        List<Contact> list=new ArrayList<>();
        try(BufferedReader input=new BufferedReader(new FileReader(filename)))
        {
            String line;
            String[] items;
            while((line=input.readLine())!=null)
            {
                try{
                    items=line.split(",");
                    list.add(new Contact(items[0].trim(),items[1].trim()));
                }catch(InputMismatchException ex)
                {
                    continue;
                }
            }
        }catch(IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return list.toArray(new Contact[0]);
    }

}