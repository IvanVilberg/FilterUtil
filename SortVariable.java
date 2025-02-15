
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortVariable {
    private List<String> integers = new ArrayList<>();
    private List<String> floats = new ArrayList<>();
    private List<String> strings = new ArrayList<>();

    SortVariable(String[] file){
        for (int i = 0; i < file.length; i++) {
            if (tryParseInteger(file[i])) {
                integers.add(file[i]);
            } else if (tryParseFloat(file[i])) {
                floats.add(file[i]);
            } else {
                strings.add(file[i]);
            }
        }
    }

    public String[] getIntegers(){
        String[] array = integers.toArray(String[]::new);
        return array;
    }

    public String[] getFloats(){
        String[] array = floats.toArray(String[]::new);
        return array;
    }

    public String[] getStrings(){
        String[] array = strings.toArray(String[]::new);
        return array;
    }


    public boolean tryParseInteger(String string){
        try {
            Long.parseLong(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean tryParseFloat(String string){
        try {
            Float.parseFloat(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getDetailsForNumbers(String[] array) {
        if (array.length == 0) {
            return null;
        }
        
        BigDecimal[] numbers = new BigDecimal[array.length];

        for (int i = 0; i < array.length; i++) {
            numbers[i] = new BigDecimal(array[i]);
        }

        BigDecimal max = Arrays.stream(numbers).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        BigDecimal min = Arrays.stream(numbers).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        BigDecimal sum = Arrays.stream(numbers).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal average = sum.divide(BigDecimal.valueOf(numbers.length), 10, RoundingMode.HALF_UP);

        String result = "\n[СТАТИСТИКА]" +
            "\nМинимальное значение: " + min +
            "\nМаксимальное значение: " + max +
            "\nСумма: " + sum + 
            "\nСреднее: " + average + "\n";

        return result;
    }

    public String getDetailsForStrings(String[] array){
        if (array.length == 0) {
            return null;
        }
        
        String max = array[0];
        String min = array[0];

        for (String string : array) {
            if (string.length() > max.length()) {
                max = string;
            }

            if (string.length() < min.length()) {
                min = string;
            }
        }
        
        String result = "\n[СТАТИСТИКА]" +
            "\nСамая длинная строка: " + max +
            "\nСамая короткая строка: " + min + "\n";

        return result;
    }    
}
