package org.gym.tracker.db;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelStore implements ExcelInterface {





    @Override
    public AreaReference getTableAreaReference(InputStream excelFile, String sheetName, String tableName) {
        return null;
    }

    @Override
    public List<Integer> getColIndexes(AreaReference tableArea) {

        //
        int lastColNum = tableArea.getLastCell().getCol();

        //
        int firstColNum = tableArea.getFirstCell().getCol();

        List<Integer> colIndexList = new ArrayList<>();

           //populates the list with all the numbers leading up to the final column number creating a list of indexes
           for (int i = firstColNum; i < lastColNum; i++){
               colIndexList.add(i);
           }

        return colIndexList;
    }

    @Override
    public List<Integer> getRowIndexes(AreaReference tableArea) {
//
        int lastRolNum = tableArea.getLastCell().getRow();
        //
        int firstRowNum = tableArea.getFirstCell().getRow();

        List<Integer> rowIndexList = new ArrayList<>();

        //populates the list with all the numbers leading up to the final column number creating a list of indexes
        for (int i = firstRowNum; i < lastRolNum; i++){
            rowIndexList.add(i);
        }

        return rowIndexList;
    }



    @Override
    public List<GymRecord> parseTable(List<Integer> rowIndexes, List<Integer> colIndexes) {
        return null;
    }

    @Override
    public int writeLineOfGymData(GymRecord row) {
        return 0;
    }

    @Override
    public List<Integer> writeLinesOfGymData(List<GymRecord> rows) {
        return ExcelInterface.super.writeLinesOfGymData(rows);
    }
}
