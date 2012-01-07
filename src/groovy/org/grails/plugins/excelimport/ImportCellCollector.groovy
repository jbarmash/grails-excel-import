package org.grails.plugins.excelimport

import org.apache.poi.ss.usermodel.Cell


public interface ImportCellCollector {

	void reportCell(Cell cell, propertyConfiguration)

	void checkReportRow(row, config, excelImportService)

	boolean checkReportValue(Object value, Cell cell, Object propertyConfiguration)

	void report(message)

	void reportPrepend(message)

}
