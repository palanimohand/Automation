package pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ZExcelRead {

	public ArrayList<String> brdreader() throws IOException {

		XSSFWorkbook wBook = new XSSFWorkbook(System.getProperty("user.dir") + "\\Data\\Cloud Migration Hybrid V2.3.1.xlsx");
		XSSFSheet sheet = wBook.getSheet("Process Flow");

		int rowCount = sheet.getLastRowNum();

		XSSFRow hRow = sheet.getRow(0);

		ArrayList<String> activityName = new ArrayList<String>();

		ArrayList<String> reqColumns = new ArrayList<String>();
		reqColumns.add("Milestone");
		reqColumns.add("Flags");
		reqColumns.add("Capability");
		reqColumns.add("Phase");
		reqColumns.add("Activity Code Mapping");
		reqColumns.add("Activity");

		LinkedHashMap<String, Integer> allindex = reqColIndexFinder(reqColumns, hRow);

		for (int i = 1; i <= rowCount; i++) {

			XSSFRow row = sheet.getRow(i);

			if (row.getCell(allindex.get("Milestone")).getStringCellValue().equals("Yes")
					&& (row.getCell(allindex.get("Capability")).getStringCellValue().equals("Cloud Pre-Migration"))
					&& (row.getCell(allindex.get("Flags")).getStringCellValue().contains("NA"))) {

				String phaseName = row.getCell(allindex.get("Phase")).getStringCellValue().trim();
				String acodeName = row.getCell(allindex.get("Activity Code Mapping")).getStringCellValue().trim();
				String actName = row.getCell(allindex.get("Activity")).getStringCellValue().trim();

				String concatBrdData = (phaseName.replaceAll("\\s", "") + " - " + acodeName.replaceAll("\\s", "") + " - " + actName.replaceAll("\\s", ""));
				if(activityName.contains(concatBrdData)){
					System.out.println("Duplicate");
				}else{
					System.out.println(concatBrdData);
					activityName.add(concatBrdData);
				}
			}
		}

		wBook.close();
		return activityName;

	}

	public static LinkedHashMap<String, Integer> reqColIndexFinder(ArrayList<String> reqColumns, XSSFRow hRow) {
		LinkedHashMap<String, Integer> allcolumnindex = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < reqColumns.size(); i++) {
			for (int j = 0; j < hRow.getLastCellNum(); j++) {
				if (reqColumns.get(i).equals(hRow.getCell(j).getStringCellValue())) {
					allcolumnindex.put(reqColumns.get(i), j);
				}
			}
		}
		return allcolumnindex;
	}

	public void comparisionMethod(XSSFSheet wpsheet, XSSFSheet efromsheet, String wpId, ArrayList<String> reqColumns,
			ArrayList<String> generalreqColumns, String wpStatus) {
		
		// Comparison logic
		System.out.printf("%n");
		System.out.println("COMPARISON OF WORKPACKAGE AND MILESTONE E-FORM MILESTONES");
		
		for (int y = 1; y <= wpsheet.getLastRowNum(); y++) {
			String name = wpsheet.getRow(y).getCell(reqColumns.indexOf("Name")).getStringCellValue();
			int k = 0;
			System.out.println(name + " :");
			
			for (int j = 1; j <= efromsheet.getLastRowNum(); j++) {
				
				// name comparison
				while (efromsheet.getRow(j).getCell(generalreqColumns.indexOf("Name")).getStringCellValue().equals(wpId + " - " + name)) {
					System.out.println("Name match");
					k = j;
					break;
				}
			}
			
			String af = wpsheet.getRow(y).getCell(reqColumns.indexOf("Actual Finish")).getStringCellValue();
			String am = efromsheet.getRow(k).getCell(generalreqColumns.indexOf("Actual Milestone End Date")).getStringCellValue();
			
			String pf = wpsheet.getRow(y).getCell(reqColumns.indexOf("Planned Finish")).getStringCellValue();
			String bf = wpsheet.getRow(y).getCell(reqColumns.indexOf("Baseline Finish")).getStringCellValue();
			String mdd = efromsheet.getRow(k).getCell(generalreqColumns.indexOf("Milestone Due Date")).getStringCellValue();
			String pbf = bf.equals("Blank") ? pf : bf;

			String ws = wpsheet.getRow(y).getCell(reqColumns.indexOf("Overall Status")).getStringCellValue();
			String ms = efromsheet.getRow(k).getCell(generalreqColumns.indexOf("Status")).getStringCellValue();
			
			// Actual Milestone End Date comparison
			if (af.equals(am)) {
				System.out.println("Actual Finish match: WP - " + af + " || ME - " + am);
			} else {
				System.out.println("Actual Finish mismatch: WP - " + af + " || ME - " + am);
			}
			
			// Milestone Due Data comparison
			if (pbf.equals(mdd)) {
				System.out.println("Planned Finish match: WP - " + pbf + " || ME - " + mdd);
			} else {
				System.out.println("Planned Finish mismatch: WP - " + pbf + " || ME - " + mdd);
			}
			
			String cstatus = null;
			if ((wpStatus.equals("Open")) && (ws.equals("Open"))) {
				cstatus = "Yet to Start";
			} else if ((wpStatus.equals("In Progress")) && (ws.equals("Open"))) {
				cstatus = "Yet to Start";
			} else if ((wpStatus.equals("On Hold")) && (ws.equals("Open"))) {
				cstatus = "On Hold";
			} else if ((wpStatus.equals("Open")) && (ws.equals("Closed"))) {
				cstatus = "Completed";
			} else if ((wpStatus.equals("In Progress")) && (ws.equals("Closed"))) {
				cstatus = "Completed";
			} else if ((wpStatus.equals("On Hold")) && (ws.equals("Closed"))) {
				cstatus = "Completed";
			} else if ((wpStatus.equals("Completed")) && (ws.equals("Closed"))) {
				cstatus = "Completed";
			} else if ((wpStatus.equals("Cancelled")) && (ws.equals("Closed"))) {
				cstatus = "Completed";
			}

			// status comparison
			if (cstatus.equals(ms)) {
				System.out.println("Status match: WP - " + wpStatus + " || LI - " + ws + " || CS - " + cstatus + " || ME - " + ms);
			} else {
				System.out.println("Status mismatch: WP - " + wpStatus + " || LI - " + ws + " || CS - " + cstatus + " || ME - " + ms);
			}
		}
	}
}
