package org.gym.tracker.spreadsheet;

import org.apache.poi.ss.util.AreaReference;
import org.gym.tracker.db.ExcelInterface;
import org.gym.tracker.GymRecord;

import java.io.InputStream;
import java.util.List;

public class ExcelStore implements ISpreadsheet {

    @Override
    public AreaReference getTableAreaReference(InputStream excelFile, String sheetName, String tableName) {
        return null;
    }

    @Override
    public List<Integer> getColIndexes(AreaReference tableArea) {
        return null;
    }

    @Override
    public List<Integer> getRowIndexes(AreaReference tableArea) {
        return null;
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
        return ISpreadsheet.super.writeLinesOfGymData(rows);
    }
}
