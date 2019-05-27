package supports;

import java.io.File;

import configurations.ConfigureTest;
import org.apache.commons.io.FileUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ReportConfigurations implements ITestListener {

    private int numberPassedTests = 0;
    private int numberFailedTests = 0;
    private int numberSkipTest = 0;
    private long durations = 0;
    private String outputsPath = System.getProperty("user.home") + "/automation_outputs/";
    private String reportsPath = outputsPath + "reports/";
    private String screenshotsPath = outputsPath + "screenshots/";
    private String logPath = outputsPath + "log/";

    private Logger logger = Logger.getLogger("rootLogger");
    private HashMap<String, HashMap<String, String>> suiteResult = new HashMap<>();

    public void onTestStart(ITestResult result) {
        logger.info(result.getTestClass().getName() + "_" + result.getMethod().getMethodName() + " was STARTED!");
    }

    public void onTestSuccess(ITestResult result) {
        logger.info(result.getMethod().getMethodName() + " was PASSED!");

        long tcDuration = result.getEndMillis() - result.getStartMillis();
        numberPassedTests++;
        durations += tcDuration;

        int hours = (int) (tcDuration / (1000 * 60 * 60));
        int minutes = (int) (tcDuration / (1000 * 60)) % 60;
        int seconds = (int) (tcDuration / 1000) % 60;
        int milliseconds = (int) tcDuration % 1000;

        HashMap<String, String> testResult = new HashMap<>();
        testResult.put("Class_Name", getClassName(result));
        testResult.put("TC_Name", getTestMethodName(result));
        testResult.put("Result", "Passed");
        testResult.put("Durations", hours + ":" + minutes + ":" + seconds + ":" + milliseconds);
        suiteResult.put(getTestMethodName(result), testResult);
    }

    public void onTestFailure(ITestResult result) {
        logger.info(result.getMethod().getMethodName() + " was FAILED!");

        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        long tcDuration = result.getEndMillis() - result.getStartMillis();
        numberFailedTests++;
        durations += tcDuration;

        int hours = (int) (tcDuration / (1000 * 60 * 60));
        int minutes = (int) (tcDuration / (1000 * 60)) % 60;
        int seconds = (int) (tcDuration / 1000) % 60;
        int milliseconds = (int) tcDuration % 1000;

        HashMap<String, String> testResult = new HashMap<>();
        testResult.put("Class_Name", getClassName(result));
        testResult.put("TC_Name", getTestMethodName(result));
        testResult.put("Result", "Failed");
        testResult.put("Result_Log", result.getThrowable().toString());
        testResult.put("Durations", hours + ":" + minutes + ":" + seconds + ":" + milliseconds);
        suiteResult.put(getTestMethodName(result), testResult);

        String className = result.getTestClass().getName();
        String[] reqClassName = className.split("\\.");
        int i = reqClassName.length - 1;

        String imageFolder = screenshotsPath + reqClassName[i] + "/";
        String testName = result.getMethod().getMethodName();
        String imageName = testName + "_" + time + ".png";

        try {
            takeSnapShot(imageFolder + imageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result) {
        logger.info(result.getMethod().getMethodName() + " was SKIPPED!");

        long tcDuration = result.getEndMillis() - result.getStartMillis();
        numberSkipTest++;
        durations += tcDuration;

        int hours = (int) (tcDuration / (1000 * 60 * 60));
        int minutes = (int) (tcDuration / (1000 * 60)) % 60;
        int seconds = (int) (tcDuration / 1000) % 60;
        int milliseconds = (int) tcDuration % 1000;

        HashMap<String, String> testResult = new HashMap<>();
        testResult.put("Class_Name", getClassName(result));
        testResult.put("TC_Name", getTestMethodName(result));
        testResult.put("Result", "Skipped");
        testResult.put("Result_Log", result.getThrowable().toString());
        testResult.put("Durations", hours + ":" + minutes + ":" + seconds + ":" + milliseconds);
        suiteResult.put(getTestMethodName(result), testResult);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {
//        Create outputs folders (log, reports, screenshots) before executing tests
        createFolder(outputsPath);
        createFolder(logPath);
        createFolder(reportsPath);
        createFolder(screenshotsPath);
        logger.info("-----The execution was STARTED!-----");
    }

    public void onFinish(ITestContext context) {
        logger.info("-----The execution was FINISHED!-----");

        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String fileName = reportsPath + context.getCurrentXmlTest().getSuite().getName() + "_" + context.getName()+ "_" + time + ".xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create sheet Result
        XSSFSheet sheet = workbook.createSheet("RESULTS");

        // Create format for cell style
        XSSFCellStyle cellStyle = createCellStyle(sheet);

        // Failed test format
        XSSFCellStyle resultFailedStyle = sheet.getWorkbook().createCellStyle();
        XSSFFont resultFailedfont = sheet.getWorkbook().createFont();
        resultFailedfont.setBold(true);
        resultFailedfont.setColor(IndexedColors.RED.getIndex());
        resultFailedfont.setFontHeightInPoints((short) 13);
        resultFailedStyle.setFont(resultFailedfont);
        resultFailedStyle.setAlignment(HorizontalAlignment.CENTER);
        resultFailedStyle.setBorderBottom(CellStyle.BORDER_THIN);
        resultFailedStyle.setBorderTop(CellStyle.BORDER_THIN);
        resultFailedStyle.setBorderLeft(CellStyle.BORDER_THIN);
        resultFailedStyle.setBorderRight(CellStyle.BORDER_THIN);


        // Passed test format
        XSSFCellStyle resultPassedStyle = sheet.getWorkbook().createCellStyle();
        XSSFFont resultPassedfont = sheet.getWorkbook().createFont();
        resultPassedfont.setBold(true);
        resultPassedfont.setColor(IndexedColors.GREEN.getIndex());
        resultPassedfont.setFontHeightInPoints((short) 13);
        resultPassedStyle.setFont(resultPassedfont);
        resultPassedStyle.setAlignment(HorizontalAlignment.CENTER);
        resultPassedStyle.setBorderBottom(CellStyle.BORDER_THIN);
        resultPassedStyle.setBorderTop(CellStyle.BORDER_THIN);
        resultPassedStyle.setBorderLeft(CellStyle.BORDER_THIN);
        resultPassedStyle.setBorderRight(CellStyle.BORDER_THIN);

        // Result Logs format
        XSSFCellStyle resultLogsStyle = sheet.getWorkbook().createCellStyle();
        XSSFFont resultLogsfont = sheet.getWorkbook().createFont();
        resultLogsfont.setBold(true);
        resultLogsfont.setColor(IndexedColors.RED.getIndex());
        resultLogsfont.setFontHeightInPoints((short) 13);
        resultLogsStyle.setFont(resultLogsfont);
        resultLogsStyle.setAlignment(HorizontalAlignment.LEFT);
        resultLogsStyle.setBorderBottom(CellStyle.BORDER_THIN);
        resultLogsStyle.setBorderTop(CellStyle.BORDER_THIN);
        resultLogsStyle.setBorderLeft(CellStyle.BORDER_THIN);
        resultLogsStyle.setBorderRight(CellStyle.BORDER_THIN);


        // Center text format
        XSSFCellStyle centerStyle = sheet.getWorkbook().createCellStyle();
        XSSFFont centerFont = sheet.getWorkbook().createFont();
        centerFont.setBold(true);
        resultPassedfont.setColor(IndexedColors.BLACK.getIndex());
        resultPassedfont.setFontHeightInPoints((short) 13);
        centerStyle.setFont(resultPassedfont);
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        centerStyle.setBorderBottom(CellStyle.BORDER_THIN);
        centerStyle.setBorderTop(CellStyle.BORDER_THIN);
        centerStyle.setBorderLeft(CellStyle.BORDER_THIN);
        centerStyle.setBorderRight(CellStyle.BORDER_THIN);

        // Left text format
        XSSFCellStyle leftStyle = sheet.getWorkbook().createCellStyle();
        XSSFFont leftFont = sheet.getWorkbook().createFont();
        leftFont.setBold(true);
        resultPassedfont.setColor(IndexedColors.BLACK.getIndex());
        resultPassedfont.setFontHeightInPoints((short) 13);
        leftStyle.setFont(resultPassedfont);
        leftStyle.setAlignment(HorizontalAlignment.LEFT);
        leftStyle.setBorderBottom(CellStyle.BORDER_THIN);
        leftStyle.setBorderTop(CellStyle.BORDER_THIN);
        leftStyle.setBorderLeft(CellStyle.BORDER_THIN);
        leftStyle.setBorderRight(CellStyle.BORDER_THIN);

        sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
        Row titleRow = sheet.createRow(0);
        Cell cellTitle = titleRow.createCell(0);
        cellTitle.setCellStyle(cellStyle);
        cellTitle.setCellValue("SUMMARY");

        Row summaryRow = sheet.createRow(1);
        Row summaryRowDetails = sheet.createRow(2);

        Cell totalCell = summaryRow.createCell(0);
        totalCell.setCellStyle(cellStyle);
        totalCell.setCellValue("TOTAL");
        Cell totalTests = summaryRowDetails.createCell(0);
        totalTests.setCellStyle(centerStyle);
        totalTests.setCellValue(numberPassedTests + numberFailedTests + numberSkipTest);

        Cell passedCell = summaryRow.createCell(1);
        passedCell.setCellStyle(cellStyle);
        passedCell.setCellValue("PASSED");
        Cell numberPassTests = summaryRowDetails.createCell(1);
        numberPassTests.setCellStyle(centerStyle);
        numberPassTests.setCellValue(numberPassedTests);

        Cell failedCell = summaryRow.createCell(2);
        failedCell.setCellStyle(cellStyle);
        failedCell.setCellValue("FAILED");
        Cell numberFailTests = summaryRowDetails.createCell(2);
        numberFailTests.setCellStyle(centerStyle);
        numberFailTests.setCellValue(numberFailedTests);

        Cell NACell = summaryRow.createCell(3);
        NACell.setCellStyle(cellStyle);
        NACell.setCellValue("SKIPPED");
        Cell numberSkipTests = summaryRowDetails.createCell(3);
        numberSkipTests.setCellStyle(centerStyle);
        numberSkipTests.setCellValue(numberSkipTest);


        Cell passRateCell = summaryRow.createCell(4);
        passRateCell.setCellStyle(cellStyle);
        passRateCell.setCellValue("PASS RATE");

        XSSFCellStyle passRate = sheet.getWorkbook().createCellStyle();
        passRate.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
        passRate.setBorderBottom(CellStyle.BORDER_THIN);
        passRate.setBorderTop(CellStyle.BORDER_THIN);
        passRate.setBorderLeft(CellStyle.BORDER_THIN);
        passRate.setBorderRight(CellStyle.BORDER_THIN);
        Cell passRateValue = summaryRowDetails.createCell(4);
        passRateValue.setCellStyle(passRate);
        passRateValue.setCellType(XSSFCell.CELL_TYPE_FORMULA);
        passRateValue.setCellFormula("B3/A3");


        Cell durationsCell = summaryRow.createCell(5);
        durationsCell.setCellStyle(cellStyle);
        durationsCell.setCellValue("DURATION(HH:MM:SS:MS)");
        Cell durationsTime = summaryRowDetails.createCell(5);
        durationsTime.setCellStyle(centerStyle);

        int hours   = (int) (durations / (1000*60*60));
        int minutes = (int) (durations / (1000*60)) % 60;
        int seconds = (int) (durations / 1000) % 60 ;
        int milliseconds = (int) durations % 1000;

        durationsTime.setCellValue(hours + ":" + minutes + ":" + seconds + ":" + milliseconds);

//	        Detail results
        sheet.addMergedRegion(new CellRangeAddress(4,4,0,5));
        titleRow = sheet.createRow(4);
        cellTitle = titleRow.createCell(0);
        cellTitle.setCellStyle(cellStyle);
        cellTitle.setCellValue("DETAILS");

        Row detailRow = sheet.createRow(5);


        Cell oderCell = detailRow.createCell(0);
        oderCell.setCellStyle(cellStyle);
        oderCell.setCellValue("No.");

        Cell featureCell = detailRow.createCell(1);
        featureCell.setCellStyle(cellStyle);
        featureCell.setCellValue("Class Name");

        Cell TCName = detailRow.createCell(2);
        TCName.setCellStyle(cellStyle);
        TCName.setCellValue("Test Case Name");


        Cell result = detailRow.createCell(3);
        result.setCellStyle(cellStyle);
        result.setCellValue("Result");

        Cell log = detailRow.createCell(4);
        log.setCellStyle(cellStyle);
        log.setCellValue("Result Logs");

        Cell durations = detailRow.createCell(5);
        durations.setCellStyle(cellStyle);
        durations.setCellValue("Duration(hh:mm:ss:ms)");

        int noNum = 1;
        int firstRow = 6;

        for(String key: suiteResult.keySet()){
            Row tcDetails = sheet.createRow(firstRow);
            Cell noCell = tcDetails.createCell(0);
            noCell.setCellStyle(centerStyle);
            noCell.setCellValue(noNum);

            Cell feaCell = tcDetails.createCell(1);
            feaCell.setCellStyle(leftStyle);
            feaCell.setCellValue(suiteResult.get(key).get("Class_Name"));

            Cell testName = tcDetails.createCell(2);
            testName.setCellStyle(leftStyle);
            testName.setCellValue(suiteResult.get(key).get("TC_Name"));

            Cell cellResult = tcDetails.createCell(3);
            cellResult.setCellStyle(centerStyle);

            Cell cellErrorLog = tcDetails.createCell(4);
            cellErrorLog.setCellStyle(leftStyle);

            if (suiteResult.get(key).get("Result").equals("Failed") | suiteResult.get(key).get("Result").equals("Skipped")){
                cellResult.setCellStyle(resultFailedStyle);
                cellResult.setCellValue(suiteResult.get(key).get("Result"));
                cellErrorLog.setCellStyle(resultLogsStyle);
                cellErrorLog.setCellValue(suiteResult.get(key).get("Result_Log"));
            }
            else{
                cellResult.setCellStyle(resultPassedStyle);
                cellResult.setCellValue(suiteResult.get(key).get("Result"));

            }

            Cell durationCell = tcDetails.createCell(5);
            durationCell.setCellStyle(centerStyle);
            durationCell.setCellValue(suiteResult.get(key).get("Durations"));

            noNum ++;
            firstRow ++;
        }

        try  {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private XSSFCellStyle createCellStyle(XSSFSheet sheet) {
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        XSSFFont font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 13);
        cellStyle.setFont(font);
        XSSFColor Header = new XSSFColor(new java.awt.Color(53, 138, 65)); // #f2dcd
        cellStyle.setFillForegroundColor(Header);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        return cellStyle;
    }

    private static String getClassName(ITestResult result){
        return result.getTestClass().getRealClass().getSimpleName();
    }

    private static String getTestMethodName(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getName();
    }

    private void createFolder(String folderPath){
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private void takeSnapShot(String filePath) throws Exception{

//        Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) ConfigureTest.driver);

//        Call getScreenshotAs method to create image file
        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);

//        Move image file to new destination
        File destFile = new File(filePath);

//        Copy file at destination
        FileUtils.copyFile(srcFile, destFile);
    }
}