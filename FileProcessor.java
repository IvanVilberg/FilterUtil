
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileProcessor {
    private String[] mergeArray;
    
    // Full description or short
    // Default - short
    private boolean fullDescription = false;
    private boolean shortDescription = false;
    
    // Path for output files
    private String newPath = new String();

    // Modification file names
    private String modName = new String();

    // Append to old file
    private boolean appendOld = false;

    // Object for sort
    private SortVariable sortVariable;

    private String[] names = new String[]{"integers.txt", "floats.txt", "strings.txt"}; 


    // Init
    FileProcessor(String[] files) throws IOException{
        try {
            List <String> List = new ArrayList<>();
            
            for (String file : files) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                List.addAll(Arrays.asList(fromFileToString(reader)));
            } 
            mergeArray = List.toArray(new String[0]);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Reading file
    private String[] fromFileToString(BufferedReader file){
        StringBuilder content = new StringBuilder();
        String line;
        boolean isEmpty = true;
        
        try {
            while ((line = file.readLine()) != null) {
                isEmpty = false;
                if (!line.trim().isEmpty()) {
                    content.append(line).append("\n");
                }
            }
    
            if (isEmpty) {
                throw new IOException("[ERROR] Один или оба файла пусты");
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return content.toString().split("\n");
    }

    // Record anyone array
    public void recordOutput(String path, String[] stringArray, boolean append) throws IOException{
        if(stringArray.length != 0){
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, append))) {
                bufferedWriter.write("[НАЧАЛО ФАЙЛА]");
                bufferedWriter.newLine();
                
                for (String string : stringArray) {
                    bufferedWriter.write(string);
                    bufferedWriter.newLine();
                }
            }
        }
    }

    // Run records to files
    public void run() throws IOException{
        if ((fullDescription && shortDescription) || (!fullDescription && !shortDescription)) {
            throw new IOException(
                "[ERROR] Должен быть установлен один вид статистики ");
        }

        sortVariable = new SortVariable(mergeArray);
        
        String modString = new String();

        if (newPath.length() != 0) {
            new File(newPath).mkdirs();
            modString += newPath + "/";
        }
        
        if (modName.length() != 0) {
            modString += modName;
        }        
    
        printAll(modString, appendOld);
        
        
    }

    public void printShortStatistic (String[] array, String name){
        System.out.println("[INFO] " + name);
        
        for (String string : array) {
            System.out.println(string);
        }

        System.out.println();
    }

    public void printFullStatistic(String[] array, String name){
        printShortStatistic(array, name);
        if (name.contains("strings.txt")) {
            System.out.println(sortVariable.getDetailsForStrings(array));
            
        } else {
            System.out.println(sortVariable.getDetailsForNumbers(array));
        }
        
    }

    public void printAll(String modString,  boolean appendOld) throws IOException{
        String[] array;

        for (String name : names) {
            if (name == names[0]) {
                array = sortVariable.getIntegers();
                
            } else if (name == names[1]) {
                array = sortVariable.getFloats();
                
            } else {
                array = sortVariable.getStrings();
            }

            if (shortDescription) {
                printShortStatistic(array, name);
                recordOutput(modString + name, array, appendOld);
            } else {
                recordOutput(modString + name, array, appendOld);
                if (name == names[2]) {
                    recordDetails(modString + name, array, 
                    sortVariable.getDetailsForStrings(array));
                } else {
                    recordDetails(modString + name, array, 
                    sortVariable.getDetailsForNumbers(array));
                }
                printFullStatistic(sortVariable.getIntegers(), name);
            }
        }

    }

    private void recordDetails(String path, String[] stringArray, String details) throws IOException{
        if(stringArray.length != 0){
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))) {
                bufferedWriter.write(details);
                bufferedWriter.newLine();
            }
        }
    }

    // Set keys
    public void setFullDescription(boolean fullDescription){
        this.fullDescription = fullDescription;
    }

    public void setShortDescription(boolean shortDescription){
        this.shortDescription = shortDescription;
    }

    public void setNewPath(String newPath){
        this.newPath += newPath;
    }
    
    public void setModName(String modName){
        this.modName += modName;
    }

    public void setAppendOld(boolean appendOld){
        this.appendOld = appendOld;
    }
}
