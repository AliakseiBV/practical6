import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class TestRunner {
    private final static String BEFORE_SIGN = " ";
    private final static String AFTER_SIGN = " ";
    private final static String PLUS = BEFORE_SIGN + "+" + AFTER_SIGN;
    private final static String MINUS = BEFORE_SIGN + "-" + AFTER_SIGN;
    private final static String DELIMITER = ";";
    private final static String RESULT_HEAD = "result(";
    private final static String RESULT_TAIL = ") = ";
    private final static String LINES_HEAD = "error-lines = ";


    public static int getResult(String csvName, StringBuilder strResult) throws FileNotFoundException {
        double numResult = 0;
        int errorLines = 0;
        double nowResult = 0;

        try(Scanner scanner = new Scanner(new FileReader(csvName))){
            while (scanner.hasNextLine()){
                String[] words = scanner.nextLine().split(DELIMITER);

                try {
                    nowResult = Double.parseDouble(words[Integer.parseInt(words[0])]);
                    numResult += nowResult;
                    String sign = (nowResult >= 0) ? PLUS : MINUS;
                    strResult.append(sign).append(Math.abs(nowResult));

                    if(strResult.charAt(1) == PLUS.charAt(1)){
                        strResult.delete(0,3);
                    } else if (strResult.charAt(1) == MINUS.charAt(1)) {
                        strResult.delete(0,2).setCharAt(0, '-');
                    }
                }
                catch(IndexOutOfBoundsException | NumberFormatException e){
                    errorLines++;
                }
            }

        }
        catch (FileNotFoundException e){
            System.out.println(e);
        }

        System.out.println(strResult.insert(0,RESULT_HEAD).append(RESULT_TAIL).append(numResult));
        System.out.println(LINES_HEAD + errorLines);
        return errorLines;
    }


    @Test
    public void testScenarioIn1() throws FileNotFoundException{
        StringBuilder result = new StringBuilder();
        int errorLines = getResult("/home/ave/university-2/practical6/src/in1", result);
        Assert.assertEquals(3, errorLines);

        String expectedIn1 = RESULT_HEAD + "5.2" + MINUS + "3.14" + PLUS + "0.0" + RESULT_TAIL + "2.06";
        Assert.assertEquals(expectedIn1, result.toString());
    }

    @Test
    public void testScenarioIn2() throws FileNotFoundException{
        StringBuilder result = new StringBuilder();
        int errorLines = getResult("/home/ave/university-2/practical6/src/in2", result);
        Assert.assertEquals(0, errorLines);

        String expectedIn2 = RESULT_HEAD + "-3.1" + MINUS + "1.0" + RESULT_TAIL + "-4.1";
        Assert.assertEquals(expectedIn2, result.toString());
    }

    @Test
    public void testScenarioIn3() throws FileNotFoundException{
        StringBuilder result = new StringBuilder();
        int errorLines = getResult("/home/ave/university-2/practical6/src/in3", result);
        Assert.assertEquals(0, errorLines);

        String expectedIn3 = RESULT_HEAD + "0.75"  + RESULT_TAIL + "0.75";
        Assert.assertEquals(expectedIn3, result.toString());
    }

    @Test
    public void testScenarioIn4() throws FileNotFoundException{
        StringBuilder result = new StringBuilder();
        int errorLines = getResult("/home/ave/university-2/practical6/src/in4", result);
        Assert.assertEquals(0, errorLines);

        String expectedIn4 = RESULT_HEAD + "0.0" + RESULT_TAIL + "0.0";
        Assert.assertEquals(expectedIn4, result.toString());
    }

    @Test
    public void testScenarioIn5() throws FileNotFoundException{
        StringBuilder result = new StringBuilder();
        int errorLines = getResult("/home/ave/university-2/practical6/src/in5", result);
        Assert.assertEquals(1, errorLines);

        String expectedIn5 = RESULT_HEAD + RESULT_TAIL + "0.0";
        Assert.assertEquals(expectedIn5, result.toString());
    }


}

