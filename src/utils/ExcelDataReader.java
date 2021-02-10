package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataReader {
	
	public ExcelDataReader(){
		
	}
	
	public void reqcolret(String colname) throws IOException{
		XSSFWorkbook wBook = new XSSFWorkbook("C:\\Users\\455493\\workspace\\Automation\\Data\\AVM_MAS_PT Name_Handbook_V2.0.xlsx");
		XSSFSheet sheet = wBook.getSheet("MAS - PT Mapping");
		ArrayList<String> activityName = new ArrayList<String>();
		
		
		int rowCount = sheet.getLastRowNum();
		XSSFRow hRow = sheet.getRow(0);
		for (int i = 0; i < rowCount; i++) {

			XSSFRow row = sheet.getRow(i);

			XSSFCell cell = row.getCell(columnIndexFinder(colname,rowCount,hRow));

			String mileValue = cell.getStringCellValue();

			if (mileValue.equals("Yes")
							  &&(sheet.getRow(i).getCell(1).getStringCellValue().
							  equals("Application Strengthening"))
							 ) {

				XSSFCell actCell = row.getCell(4);

				String actName = actCell.getStringCellValue().trim();

				activityName.add(actName);

				Collections.sort(activityName);

				System.out.println(actName);

			}
		}
		
		wBook.close();
	}

	private int columnIndexFinder(String colname, int rowCount, XSSFRow hRow) {
		int c = 0;
		for (int j = 0; j < rowCount; j++) {

			if (hRow.getCell(j).getStringCellValue().equals(colname)) {
				c = j;
				break;
				
			}
		}
		return c;
	}
	
	

	

}
