package sample

import static org.grails.plugins.excelimport.ExpectedPropertyType.*
import org.grails.plugins.excelimport.*
import org.grails.plugins.excelimport.ExcelImportService
import sample.*

class BookExcelImporter extends AbstractExcelImporter  {

	def static cellReporter = new DefaultImportCellCollector()

	def excelImportService

	static Map configuratiomMap = [
			dateIssued: ([expectedType: DateType, defaultValue: null]),
			dateIssuedError: ([expectedType: DateType, defaultValue: null]),
			numSold: ([expectedType: IntType, defaultValue: 1, severityMapping: ImportSeverityMappingEnum.ErrorAll, userViewableName:'Quantity']),
	]


	static Map CONFIG_BOOK_CELL_MAP = [
			sheet: 'Sheet2',
			cellMap: ['D3': 'title',
					'D4': 'author',
					'D6': 'numSold',
					'D8': 'dateIssued',
					'D9': 'dateIssuedError',
			]
	]

	static Map CONFIG_BOOK_COLUMN_MAP = [
			sheet: 'Sheet1',
			startRow: 2,
			columnMap: [
					'B': 'title',
					'C': 'author',
					'D': 'numSold',
			]
	]

	public BookExcelImporter(fileName) {
		super(fileName)
	}

	public BookExcelImporter() {
		super()
	}

	List<Map> getBooks() {
		excelImportService.columns(
			workbook,
			CONFIG_BOOK_COLUMN_MAP,
			cellReporter,
			configuratiomMap
		)
	}

	/**
	 * dual getBooks(
	 */
	void setBooks(bookList) {
		excelImportService.setColumns(
			bookList,
			workbook,
			CONFIG_BOOK_COLUMN_MAP,
			configuratiomMap
		)
	}


	Map getOneMoreBookParams() {
		excelImportService.cells (
			workbook,
			CONFIG_BOOK_CELL_MAP,
			cellReporter,
			configuratiomMap
		)
	}

	/**
	 * dual getOneMoreBookParams(
	 */
	void setOneMoreBookParams(Map bookParams) {
		excelImportService.setValues(
			bookParams,
			workbook,
			CONFIG_BOOK_CELL_MAP,
			configuratiomMap
		)
	}


	static class BookCsvImporter extends AbstractCsvImporter {

		static Map configMap = [
			startRow: 1,
			columnMap: [
				0: 'title',
				1: 'author',
				2: 'numSold'
			]
		]

		List<Map> getBooks() {
			getData(configMap)
		}

		def copyToExcel(BookExcelImporter excelExporter) {
			def booksMapList = this.getBooks()
			excelExporter.setBooks(booksMapList)
			return excelExporter
		}
	}


def copyFromCsv(csvFormat) {
		def currentSheet = workbook.getSheet('Sheet1')
		csvFormat.tokensList.eachWithIndex {tokens, rowIndex ->
			def row = currentSheet.getRow(rowIndex) ?: currentSheet.createRow(rowIndex)
			tokens.eachWithIndex {value, colIndex ->
				excelImportService.setCellValueByColIndex(value, row, colIndex)
			}
		}
		return this
	}


}


