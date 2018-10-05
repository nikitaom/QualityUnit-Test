package Model;

import Parser.ParserTool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LineC {
    private String serviceId;
    private String variationId = "";
    private String questionTypeId;
    private String categoryId = "";
    private String subCategoryId = "";
    private int time;

    private String responseType;
    private Date dateFrom;

    private final String datePattern = "dd.MM.yyyy";

    public LineC(String line) {
        super();
        line = line.trim();
        if (!ParserTool.isTheC(line)) {
            throw new IllegalArgumentException("Incorrect line format!");
        }
        String[] parameters = line.split(" ");

        this.setService(parameters[1]);
       this.setQuestion(parameters[2]);
        this.setDate(parameters[4]);

        responseType = parameters[3];
        time = Integer.parseInt(parameters[5]);
    }

    void setService(String service) {
        String[] serviceType = service.split("\\.");
        if (serviceType.length == 2) {
            variationId = serviceType[1];
        }
        serviceId = serviceType[0];
    }

    void setQuestion(String question) {

        String[] questionType = question.split("\\.");
        if (questionType.length == 3) {
            subCategoryId = questionType[2];
        }
        if (questionType.length > 1 ) {
            categoryId = questionType[1];
        }
        questionTypeId = questionType[0];
    }

    void setDate(String dateIn){
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        try {
            dateFrom = dateFormat.parse(dateIn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public String getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(String questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDatePattern() {
        return datePattern;
    }
}
