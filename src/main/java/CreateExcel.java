import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class CreateExcel {

    static String fileName;
    static String sheetName;
    static FileOutputStream fileOut;
    static int rowGap = 2;
    static HSSFWorkbook workbook;
    static HSSFSheet sheet;
    //set Style
    static CellStyle boldStyle;
    static CellStyle borderStyle;



    public CreateExcel() throws IOException {

    }

    public static void createFile(String inMonth, int enumMonth, int year, int workToDay) throws IOException {
        String month = createNameOfMonth(enumMonth);
        fileName = String.format("Rachunek, Agnieszka Mucha, %s", month);
        sheetName = month;
        int startRow = 7;
        setBaseSettings();
        createHeader(inMonth, year);
        createColumnName(startRow);
        startRow = createContent(startRow, enumMonth, workToDay, year);
        underlineUnderContent(startRow);
        createFooter(startRow);
        autoSizeColumnAndSaveInFile();
    }

    private static void setBaseSettings() {
        try {
            fileOut = new FileOutputStream(fileName + ".xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        //set Style
        boldStyle = createStyle(false);
        borderStyle = createStyle(true);
    }

    private static String createNameOfMonth(int enumMonth){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, enumMonth);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        SimpleDateFormat dateFormatForTest = new SimpleDateFormat("MMMM");
        return  dateFormatForTest.format(cal.getTime());
    }

    static void createCell(int column, int row, String content, CellStyle style) {
        HSSFRow row1 = (sheet.getRow(row) == null) ? sheet.createRow((short) row) : sheet.getRow(row);
        HSSFCell cell1 = row1.createCell(column);
        cell1.setCellStyle(style);
        cell1.setCellValue(content);
    }

    static void createCell(int column, int row, String content) {
        HSSFRow row1 = (sheet.getRow(row) == null) ? sheet.createRow((short) row) : sheet.getRow(row);
        HSSFCell cell1 = row1.createCell(column);
        cell1.setCellValue(content);
    }

    static void createCell(int column, int row, int content, CellStyle style) {
        HSSFRow row1 = (sheet.getRow(row) == null) ? sheet.createRow((short) row) : sheet.getRow(row);
        HSSFCell cell1 = row1.createCell(column);
        cell1.setCellStyle(style);
        cell1.setCellValue(content);
    }

    static int writeDay(int startColumn, int startRow, Day day, CellStyle style) {
        createCell(startColumn, startRow, day.date, style);
        int row = 0;
        CellStyle centerStyle = workbook.createCellStyle();
        centerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        for (Integer hour : day.workHours) {
            createCell(startColumn + 1, startRow + row, hour, centerStyle);
            ++row;
        }
        row = 0;
        for (String work : day.workNames) {
            createCell(startColumn + 2, startRow + row, work);
            ++row;
        }
        return row + startRow + rowGap;
    }

    static CellStyle createStyle(boolean border) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        font.setBold(true);
        style.setFont(font);
        if (border) {
            style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        }
        return style;
    }

    static void createHeader(String month, int year) {
        createCell(1, 1, "Agnieszka Mucha", boldStyle);
        createCell(1,2,String.format("Liczba godzin przepracowana w %s %dr.", month, year), boldStyle);
        //createCell(1, 2, "Liczba godzin przepracowana w " + month + " 2019r.", boldStyle);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));
    }

    static void createColumnName(int startRow) {
        createCell(1, startRow, "data", borderStyle);
        createCell(2, startRow, "liczba godzin", borderStyle);
        createCell(3, startRow, "opis", borderStyle);
    }

    static int createContent(int startRow, int enumMonth, int workToDay, int year) {
        startRow += rowGap;
        for (Day day : Mechanics
                .dayWithHourAndTask(Mechanics.hoursPerDay(80), Mechanics.myWorks(), Mechanics.workDays(enumMonth,  workToDay, year))) {
            startRow = writeDay(1, startRow, day, boldStyle);
        }
        return startRow;
    }

    static void underlineUnderContent(int startRow) {
        startRow -= rowGap;
        for (Integer column : Arrays.asList(1, 2, 3)) createCell(column, startRow, "", borderStyle);
    }

    static void createFooter(int startRow) {
        createCell(1, startRow, "razem", boldStyle);
        createSumOfWorkingHour(startRow);
        createCell(3, startRow, "godzin", boldStyle);
    }

    static void createSumOfWorkingHour(int startRow) {
        HSSFRow rowSum = (sheet.getRow(startRow) == null) ? sheet.createRow((short) startRow) : sheet.getRow(startRow);
        HSSFCell sumCell = rowSum.createCell(2);
        String strFormula = "SUM(C1:C" + --startRow + ")";
        sumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
        sumCell.setCellFormula(strFormula);
    }

    static void autoSizeColumnAndSaveInFile() throws IOException {
        Arrays.asList(1, 2, 3).forEach(sheet::autoSizeColumn);
        workbook.write(fileOut);
    }
}
