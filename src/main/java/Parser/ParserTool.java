package Parser;

import Model.LineC;
import Model.LineD;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserTool {



    private static final String REGEX_SERVICE_ID = "\\d*(\\.\\d*)?";
    private static final String REGEX_QUESTION_TYPE_ID = "\\d*(\\.\\d*(\\.\\d*)?)?";
    private static final String REGEX_DATE = "\\d{1,2}\\.\\d{1,2}\\.\\d{4}";
    private static final String REGEX_LINE_C = "C " + REGEX_SERVICE_ID + " "
                                                    + REGEX_QUESTION_TYPE_ID + " (P|N) "
                                                    + REGEX_DATE+ " \\d*";
    private static final String REGEX_LINE_D = "D (" + REGEX_SERVICE_ID + "|\\*) ("
                                                     + REGEX_QUESTION_TYPE_ID + "|\\*) (P|N) "
                                                     + REGEX_DATE+ "(-" + REGEX_DATE+ ")?";
    private static final String REGEX_INPUT_STRING = "^\\d*(( " + REGEX_LINE_C +")|( " + REGEX_LINE_D + "))*";

    private final String sourceString;
    private List<LineD> linesD;




    public static boolean isTheD(String line) {
        return line.matches(REGEX_LINE_D);
    }

    public static boolean isTheC(String line) {
        return line.matches(REGEX_LINE_C);
    }

    public ParserTool(String inputString){
        sourceString = inputString.replaceAll("(\t|\n)", "")
                .replaceAll(" {2,}+"," ")
                .trim();
        if (!validInputString(sourceString)) {
            throw new IllegalArgumentException("Broken line structure");
        }

        linesD = parseToListD();
        int numberOfLines = getExpectedNumberOfLines(sourceString);
        int actualySize = parseToListC().size() + parseToListD().size();

        if ( numberOfLines != actualySize) {
            throw new IllegalArgumentException("Incorrect number of lines in input string! Expected: "
                    + numberOfLines + ", actualy: " + actualySize);
        }
    }

    private boolean validInputString(String inputString) {
        return inputString.matches(REGEX_INPUT_STRING);
    }

    private List<LineC> parseToListC(){
        return parseToListC(REGEX_LINE_C);
    }
    public List<LineC> parseToListC(String tmpl) {
        List<LineC> resultList = new ArrayList<>();

        Matcher matcher = Pattern.compile(tmpl)
                .matcher(sourceString);
        while (matcher.find()) {
            String itemLine = sourceString.substring(matcher.start(), matcher.end());
            resultList.add(new LineC(itemLine));
        }
        return resultList;
    }
    private List<LineD> parseToListD(){
        List<LineD> resultList = new ArrayList<>();

        Matcher matcher = Pattern.compile(REGEX_LINE_D)
                .matcher(sourceString);
        while (matcher.find()) {
            String itemLine = sourceString.substring(matcher.start(), matcher.end());
            resultList.add(new LineD(itemLine));
        }
        return resultList;
    }

    private int getExpectedNumberOfLines(String inputString) {
        int indexOfFirstSpace = inputString.indexOf(" ");
        return Integer.parseInt(inputString.substring(0,indexOfFirstSpace));
    }

    public static String createTmplForSearchC(LineD lineD) {
        StringBuilder tmpl = new StringBuilder("C ");
        if (lineD.getServiceId().equals("*")) {
            tmpl.append(REGEX_SERVICE_ID).append(" ");
        } else {
            if (lineD.getVariationId().equals("")) {
                tmpl.append(String.format("((%s)|(%s.\\d*)?) ", lineD.getServiceId(), lineD.getServiceId()));
            } else {
                tmpl.append(String.format("%s.%s ",lineD.getServiceId(), lineD.getVariationId()));
            }
        }

        if (lineD.getQuestionTypeId().equals("*")) {
            tmpl.append(REGEX_QUESTION_TYPE_ID).append(" ");
        } else {
            if (lineD.getCategoryId().equals("") && lineD.getSubCategoryId().equals("")) {
                tmpl.append(String.format("(%s(\\.\\d*(\\.\\d*)?)?) ", lineD.getQuestionTypeId()));
            } else if (lineD.getSubCategoryId().equals("")){
                tmpl.append(String.format("(%s(\\.%s(\\.\\d*)?)?) ", lineD.getQuestionTypeId(), lineD.getCategoryId()));
            } else {
                tmpl.append(String.format("(%s(\\.%s(\\.%s)?)?) ", lineD.getQuestionTypeId(),
                        lineD.getCategoryId(),
                        lineD.getSubCategoryId()));
            }
        }
        return tmpl.append("(P|N) ").append(REGEX_DATE).append(" \\d*").toString();
    }

    public List<LineD> getListLineD() {
        return this.linesD;
    }
}
