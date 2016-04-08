package org.grails.plugins.excelimport

import org.apache.poi.ss.usermodel.Cell

class NoopImportCellCollector implements ImportCellCollector {

	@Override
	void reportCell(Cell cell, Object propertyConfiguration) {
	}

	@Override
	void checkReportRow(Object row, Object config, Object excelImportService) {
	}

	@Override
	boolean checkReportValue(Object value, Cell cell, Object propertyConfiguration) {
		return false
	}

	@Override
	void report(Object message) {
	}

	@Override
	void reportPrepend(Object message) {
	}

	public static final NoopInstance = new NoopImportCellCollector()

}
