
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args){
        String substring = ".txt";
        String[] keys = new String[]{"-o", "-p", "-s", "-f", "-a"};
        List<String> inputFiles = new ArrayList<>();


        try {
            for (String arg : args) {
                if (arg.contains(substring)) {
                    inputFiles.add(arg);
                }
            }
            
            if (!inputFiles.isEmpty()) {
                    FileProcessor fileProcessor = new FileProcessor(inputFiles.toArray(new String[0]));
    
                for (int i = 0; i < args.length; i++) {
                    if (args[i].equals(keys[0]) && 
                        !Arrays.asList(keys).contains(args[i + 1]) &&
                        !args[i+1].contains(substring)) 
                        fileProcessor.setNewPath(args[i + 1]);
                    else if (args[i].equals(keys[0])) throw new IOException("[ERROR] Отсутствует аргумет у ключа: " + args[i]);
    
                    if (args[i].equals(keys[1]) &&
                        !Arrays.asList(keys).contains(args[i + 1]) &&
                        !args[i+1].contains(substring)) 
                        fileProcessor.setModName(args[i + 1]);
                    else if (args[i].equals(keys[1])) throw new IOException("[ERROR] Отсутствует аргумет у ключа: " + args[i]);
    
                    if (args[i].equals(keys[2])) fileProcessor.setShortDescription(true);
    
                    if (args[i].equals(keys[3])) fileProcessor.setFullDescription(true);
    
                    if (args[i].equals(keys[4])) fileProcessor.setAppendOld(true);
                }
    
                fileProcessor.run();   
            } else {
                throw new IOException("[ERROR] Отсуствуют файлы для ввода");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}