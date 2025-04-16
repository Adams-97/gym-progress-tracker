package org.project.scraper;

class ExcelScraper implements DataScraper {
    final String filePath;
    final Extension extension;

    public enum Extension {
        xls,
        xlsx,
        xlsm
    }

    ExcelScraper(String path) {
        filePath = path;
        extension = getExtension(filePath);
    }

    Extension getExtension(String path) {
        String ext = path.split("\\.")[1];
        switch (ext) {
            case "xls":
                return Extension.xls;
            case "xlsx":
                return Extension.xlsx;
            case "xlsm":
                return Extension.xlsm;
            default:
                throw new RuntimeException("Not a compatible file type\nOnly xls, xlsx, xlsm allowed");

        }
    }
    public static void main(String[] args) {
        ExcelScraper a = new ExcelScraper("excel.csv");
        System.out.printf("Path obtained: %s%n", a.filePath);
    }
}
