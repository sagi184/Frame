package excelConfiguration;

import configuration.BaseClass;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
	private static String str_todaysDateTimeStamp = new SimpleDateFormat("dd-MMM-YYYY HH-mm-ss").format(new Date());
	  

	  private static Workbook workbook;
	  

	  private static Sheet sheet;
	  

	  private static String[] columns;
	  

	  private static String endCellAlphabet;
	  

	  private static String endCellNo;
	  


	  public ExcelWriter() {}
	  


	  public static String toAlphabetic(int i)
	  {
	    if (i < 0) {
	      return "-" + toAlphabetic(-i - 1);
	    }
	    
	    int quot = i / 26;
	    int rem = i % 26;
	    char letter = (char) (65 + rem);
	    if (quot == 0) {
	     // return letter;
	    }
	    return toAlphabetic(quot - 1) + letter;
	  }
	  







	  public static void createWorkbook()
	  {
	    workbook = new XSSFWorkbook();
	  }
	  


	  public static void setData(String SheetName, String SheetDescription, String[] ColumnNames, List<String> listName)
	  {
	    endCellAlphabet = toAlphabetic(ColumnNames.length - 1);
	    
	    endCellNo = Integer.toString(listName.size() / ColumnNames.length + 5);
	    


	    columns = ColumnNames;
	    


	    int sheetNo = workbook.getNumberOfSheets() + 1;
	    sheet = workbook.createSheet(sheetNo + "-" + SheetName);
	    


	    Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short)12);
	    headerFont.setColor(IndexedColors.BLACK.getIndex());
	    

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    

	    headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
	    headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    

	    headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
	    

	    headerCellStyle.setFont(headerFont);
	    
	    Row headerRow = sheet.createRow(0);
	    Cell headercell = headerRow.createCell(0);
	    headercell.setCellValue(SheetDescription);
	    headercell.setCellStyle(headerCellStyle);
	    
	    int RwNum = 1;
	    for (int x = 0; x < 4; x++)
	    {
	      Row descriptionRow = sheet.createRow(RwNum++);
	      for (int y = 0; y < ColumnNames.length; y++)
	      {
	        Cell descriptioncell = descriptionRow.createCell(y);
	        descriptioncell.setCellValue("");
	      }
	    }
	    

	    sheet.addMergedRegion(CellRangeAddress.valueOf("A1:" + endCellAlphabet + "4"));
	    






	    CellStyle titleCellStyle = workbook.createCellStyle();
	    

	    titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
	    titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    

	    titleCellStyle.setBorderLeft(BorderStyle.THIN);
	    titleCellStyle.setBorderRight(BorderStyle.THIN);
	    titleCellStyle.setBorderTop(BorderStyle.THIN);
	    titleCellStyle.setBorderBottom(BorderStyle.THIN);
	    

	    titleCellStyle.setFillPattern(FillPatternType.FINE_DOTS);
	    titleCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    titleCellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    
	    titleCellStyle.setFont(headerFont);
	    

	    Row titleRow = sheet.createRow(4);
	    

	    for (int i = 0; i < ColumnNames.length; i++) {
	      Cell cell = titleRow.createCell(i);
	      cell.setCellValue(columns[i]);
	      cell.setCellStyle(titleCellStyle);
	    }
	    

	    sheet.createFreezePane(0, 5);
	    



	    CellStyle borderCellStyle = workbook.createCellStyle();
	    borderCellStyle.setBorderLeft(BorderStyle.THIN);
	    borderCellStyle.setBorderRight(BorderStyle.THIN);
	    borderCellStyle.setBorderTop(BorderStyle.THIN);
	    borderCellStyle.setBorderBottom(BorderStyle.THIN);
	    

	    int rowNum = 5;
	    


	    for (int i = 0; i < listName.size(); i += columns.length)
	    {
	      Row row = sheet.createRow(rowNum++);
	      for (int j = 0; j < ColumnNames.length; j++)
	      {
	        Cell cell1 = row.createCell(j);
	        cell1.setCellValue((String)listName.get(i + j));
	        cell1.setCellStyle(borderCellStyle);
	      }
	    }
	    











	    for (int i = 0; i < ColumnNames.length; i++) {
	      sheet.autoSizeColumn(i);
	    }
	  }
	  
























	  public static void conditionalFormatting(String highlightString)
	  {
	    SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();
	    


	    String form = "=COUNTIF(1:1,\"*" + highlightString + "*\")";
	    
	    ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(form);
	    PatternFormatting fill1 = rule1.createPatternFormatting();
	    fill1.setFillBackgroundColor(IndexedColors.CORAL.getIndex());
	    fill1.setFillPattern((short)1);
	    
	    CellRangeAddress[] regions = {
	    
	      CellRangeAddress.valueOf("A1:" + endCellAlphabet + endCellNo) };
	    
	    sheetCF.addConditionalFormatting(regions, rule1);
	  }
	  


	  public static void validConditionalFormatting(String highlightString)
	  {
	    SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();
	    


	    String form = "=COUNTIF(1:1,\"*" + highlightString + "*\")";
	    
	    ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(form);
	    PatternFormatting fill1 = rule1.createPatternFormatting();
	    fill1.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
	    fill1.setFillPattern((short)1);
	    
	    CellRangeAddress[] regions = {
	    
	      CellRangeAddress.valueOf("A1:" + endCellAlphabet + endCellNo) };
	    
	    sheetCF.addConditionalFormatting(regions, rule1);
	  }
	  













	  public static void closeWorkbook(String Name)
	  {
	    if (workbook.getNumberOfSheets() > 0)
	    {



	      try
	      {



	        String newExcelPath = BaseClass.createNewDirectory(BaseClass.parentResultDirectory + "Excels_");
	        System.out.println("New Excel Path>>>" + newExcelPath);
	        FileOutputStream fileOut = new FileOutputStream(newExcelPath + Name + "_" + str_todaysDateTimeStamp + ".xlsx");
	        
	        workbook.write(fileOut);
	        workbook.close();
	        fileOut.close();
	      }
	      catch (Exception e)
	      {
	        e.printStackTrace();
	      }
	    }
	  }
}
