package AnalyticalTool;

import Model.LineC;
import Model.LineD;
import Parser.ParserTool;

import java.text.ParseException;
import java.util.List;

public class AnalyticalTool {

    public static String makeAnalyse(String inputString)  {

        ParserTool parserTool = new ParserTool(inputString);
        StringBuilder result = new StringBuilder("");

        for(LineD lineD : parserTool.getListLineD()){
            List<LineC> listC = parserTool.parseToListC(ParserTool.createTmplForSearchC(lineD));
            Integer sum = 0;
            Integer counter = 0;
            for(LineC lineC : listC) {
                if (lineD.isDateExist(lineC)) {
                    sum +=lineC.getTime();
                    counter++;
                }
            }
            result.append( (0 == counter ) ?  "-" : String.valueOf(sum/counter));
            result.append(" ");
        }
        return result.toString();
    }
}
