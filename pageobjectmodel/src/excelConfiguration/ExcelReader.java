package excelConfiguration;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	private static XSSFSheet ExcelWSheet;
	  private static XSSFWorkbook ExcelWBook;
	  private static XSSFCell Cell;
	  
	  public ExcelReader() {}
	  
	  public static void setExcelFile(String Path, String SheetName)
	    throws Exception
	  {
	    FileInputStream ExcelFile = new FileInputStream(Path);
	    ExcelWBook = new XSSFWorkbook(ExcelFile);
	    ExcelWSheet = ExcelWBook.getSheet(SheetName);
	  }
	  

	  public static String getCellData(int RowNum, int ColNum)
	    throws Exception
	  {
	    Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
	    String CellData = Cell.getStringCellValue();
	    return CellData;
	  }
	  


	  public static int RowCount()
	  {
	    int countofrow = ExcelWSheet.getLastRowNum();
	    if ((countofrow > 0) || (ExcelWSheet.getPhysicalNumberOfRows() > 0))
	    {
	      countofrow++;
	    }
	    return countofrow;
	  }
	  
	  public static int ColumnCount()
	  {
	    int countofColumn = ExcelWSheet.getRow(3).getLastCellNum();
	    
	    return countofColumn;
	  }
}
