/**  

* 创建时间：2018年12月13日 下午2:30:29  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：ExportExcelFileUtils.java  

* 类说明：  导出xlsx工具类

*/

package edu.swu.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.swu.model.SubmissionVo;

public class ExportExcelFileUtils {

	static List<String> SHEET_HEADER = new ArrayList<>();
	
	static {
		SHEET_HEADER.add("学号");
		SHEET_HEADER.add("姓名");
		SHEET_HEADER.add("成绩");
	}

	public static ByteArrayOutputStream getByteArrayOutputStream(List<SubmissionVo> data) {

		ByteArrayOutputStream os = null;
		
		// 1. create excel document
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		// 2. create a sheet
		XSSFSheet sheet = workbook.createSheet("成绩表");
		
		// 3. set header of the sheet
		XSSFRow header = sheet.createRow(0);
		int col = 0;
		for (String name : SHEET_HEADER) {
			XSSFCell cell = header.createCell(col++);
			cell.setCellValue(name);
		}
		
		// 4. fill data
		int row = 0;
		for (SubmissionVo submissionVo : data) {
			XSSFRow datarow = sheet.createRow(++row);
			datarow.createCell(0).setCellValue(submissionVo.getSsid());
			datarow.createCell(1).setCellValue(submissionVo.getName());
			datarow.createCell(2).setCellValue(submissionVo.getGrade());
		}
		
		try {
			os = new ByteArrayOutputStream();
			workbook.write(os);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return os;
	}
	
//	public static void main(String[] args) {
//		SubmissionVo submissionVo_0 = new SubmissionVo();
//		SubmissionVo submissionVo_1 = new SubmissionVo();
//		SubmissionVo submissionVo_2 = new SubmissionVo();
//		submissionVo_0.setSsid(3);
//		submissionVo_1.setSsid(1);
//		submissionVo_2.setSsid(2);
//		List<SubmissionVo> list = new ArrayList<>();
//		list.add(submissionVo_0);
//		list.add(submissionVo_1);
//		list.add(submissionVo_2);
//		System.out.println(list);
//		Collections.sort(list,new Comparator<SubmissionVo>() {
//			@Override
//			public int compare(SubmissionVo o1, SubmissionVo o2) {
//				if(o1.getSsid() < o2.getSsid()) return -1;
//				else if(o1.getSsid() == o2.getSsid()) return 0;
//				else return 1;
//			}
//
//		});
//		System.out.println(list);
//	}
}
