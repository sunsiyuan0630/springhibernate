/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssy.havefun.f3d;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jsun
 */
@Repository("f3DDao")
public class F3DDaoImpl implements F3DDao{
    @Autowired
    private SessionFactory sessionFactory;
    private Session session;
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
//    private Session session;
//    public F3DDaoImpl(Session session){
//        this.session = session;
//    }
//    @Override
//    public List<F3DEntity> doImport() throws IOException{
//        List<F3DEntity> list = new ArrayList<>();
//        Workbook wb;
//        try {
//            wb = WorkbookFactory.create(new File("C:/Users/jsun/Downloads/2015.xls"));
//            Sheet sheet = wb.getSheetAt(0);
//            for(int i=0;i<sheet.getLastRowNum();i++){
//            Row row = sheet.getRow(i);
//            F3DEntity f3d = new F3DEntity();
//            for(int j=0;j<3;j++){
//                Cell cell = row.getCell(j);
//                String value = getCellValue(cell);                
//                switch(j){
//                    case 0:
//                        f3d.setsId(Integer.parseInt(value));
//                        break;
//                    case 1:
//                        String[] values = value.split(",");
//                        for(int k=0;k<3;k++){
//                            switch(k){
//                                case 0:
//                                    f3d.setFirst(Integer.parseInt(values[k]));
//                                    break;
//                                case 1:
//                                    f3d.setSecond(Integer.parseInt(values[k]));
//                                    break;
//                                case 2:
//                                    f3d.setThird(Integer.parseInt(values[k]));
//                                    break;                                   
//                            }                            
//                        }
//                        break;
//                    case 2:
//                        f3d.setDate(value.replaceAll("-", ""));
//                        break;
//                }
//            }
//            System.out.println(f3d.toString());
//            list.add(f3d);
//        }
//        } catch (InvalidFormatException ex) {
//            Logger.getLogger(F3DDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return list;
//    }
    
    @Override
    public List<F3DEntity> doImport() throws IOException{
        List<F3DEntity> list = new ArrayList<>();
        Workbook wb;
        try {
            wb = WorkbookFactory.create(new File("C:/Users/jsun/Downloads/2015.xls"));
            Sheet sheet = wb.getSheetAt(2);
            for(int i=0;i<sheet.getLastRowNum()+1;i++){
            Row row = sheet.getRow(i);
            F3DEntity f3d = new F3DEntity();
            for(int j=0;j<10;j++){
                Cell cell = row.getCell(j);
                String value = getCellValue(cell);                
                switch(j){
                    case 0:
                        f3d.setsId(Integer.parseInt(value));
                        break;
                    case 1:
                        f3d.setDate(value.replaceAll("-", "").substring(0, 8));
                        break;
                    case 2:
                        char[] values = value.toCharArray();
                        f3d.setFirst(Integer.parseInt(String.valueOf(values[0])));
                        f3d.setSecond(Integer.parseInt(String.valueOf(values[1])));
                        f3d.setThird(Integer.parseInt(String.valueOf(values[2])));
                        break;
                    case 3:
                        break;
                    case 4:
                        f3d.setSale(Integer.parseInt(value));
                        break;
                    case 5:
                        f3d.setDirect(Integer.parseInt(value));
                        break;
                    case 6:
                        break;
                    case 7:
                        f3d.setThirdCombination(Integer.parseInt(value));
                        break;
                    case 8:
                        break;
                    case 9:
                        f3d.setSixCombination(Integer.parseInt(value));
                        break;                    
                }
            }
            System.out.println(f3d.toString());
            list.add(f3d);
        }
        } catch (InvalidFormatException ex) {
            Logger.getLogger(F3DDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public String getCellValue(Cell cell) {  
        String ret = "";  
        switch (cell.getCellType()) {  
        case Cell.CELL_TYPE_BLANK:  
            ret = "";  
            break;  
        case Cell.CELL_TYPE_BOOLEAN:  
            ret = String.valueOf(cell.getBooleanCellValue());  
            break;  
        case Cell.CELL_TYPE_ERROR:  
            ret = null;  
            break;  
        case Cell.CELL_TYPE_FORMULA:  
            Workbook wb = cell.getSheet().getWorkbook();  
            CreationHelper crateHelper = wb.getCreationHelper();  
            FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();  
            ret = getCellValue(evaluator.evaluateInCell(cell));  
            break;  
        case Cell.CELL_TYPE_NUMERIC:  
            if (DateUtil.isCellDateFormatted(cell)) {   
//                Date theDate = cell.getDateCellValue();  
//                ret = simpleDateFormat.format(theDate);  
            } else {   
                ret = NumberToTextConverter.toText(cell.getNumericCellValue());  
            }  
            break;  
        case Cell.CELL_TYPE_STRING:  
            ret = cell.getRichStringCellValue().getString();  
            break;  
        default:  
            ret = null;  
        }  
          
        return ret; //有必要自行trim  
    }  
//    public String getCellStringValue(Cell cell) {      
//        String cellValue = "";      
//        switch (cell.getCellType()) {      
//        case Cell.CELL_TYPE_STRING://字符串类型   
//            cellValue = cell.getStringCellValue();      
//            if(cellValue.trim().equals("")||cellValue.trim().length()<=0)      
//                cellValue=" ";      
//            break;      
//        case Cell.CELL_TYPE_NUMERIC: //数值类型   
//            System.out.println(cell.getNumericCellValue());
//            cellValue = String.valueOf(cell.getNumericCellValue());      
//            break;      
//        case Cell.CELL_TYPE_FORMULA: //公式   
//            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);      
//            cellValue = String.valueOf(cell.getNumericCellValue());      
//            break;      
//        case Cell.CELL_TYPE_BLANK:      
//            cellValue=" ";      
//            break;      
//        case Cell.CELL_TYPE_BOOLEAN:      
//            break;      
//        case Cell.CELL_TYPE_ERROR:      
//            break;      
//        default:      
//            break;      
//        }      
//        return cellValue;      
//    }

    @Override
    public List<F3DEntity> findAll() {
        Query q = getCurrentSession().createQuery("from F3DEntity");
        List<F3DEntity> result = q.list();
        return result;
    }

    @Override
    public List<F3DEntity> findAllInRange(String start, String end) {
        String hql = "from F3DEntity f where f.date>=:start and f.date<=:end";
        Query q = getCurrentSession().createQuery(hql);
        q.setParameter("start", start);
        q.setParameter("end", end);
        List<F3DEntity> result = q.list();
        return result;
    }

    @Override
    public List<F3DEntity> findAllInRange(String start) {
        String hql = "from F3DEntity f where f.date>=:start";
        Query q = getCurrentSession().createQuery(hql);
        q.setParameter("start", start);
        List<F3DEntity> result = q.list();
        return result;
    }
}
