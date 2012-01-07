import sample.*

class BootStrap {

	def init = { servletContext ->
		testExcel('xls')
		testExcel('xlsx')
		testCsv()
	}

	def testExcel(fileExt) {
		println "==== testExcel = ${fileExt}"

		BookExcelImporter importer = new BookExcelImporter().readFromUrl("file:test-data/books.${fileExt}".toURL())
		println "workbook = ${importer.workbook.getClass().name} - ${importer.workbook}"

		def booksMapList = importer.getBooks()
		println "booksMapList = ${booksMapList}"
		booksMapList.each { Map bookParams ->
			def newBook = new Book(bookParams)
			if (!newBook.save()) {
				println "Book1 not saved, errors = ${newBook.errors}"
			}
		}

		def bookParams = importer.getOneMoreBookParams()
		println "bookParams = ${bookParams}"
		def anotherNewBook = new Book(bookParams)
		if (!anotherNewBook.save()) {
			println "Book2 not saved, errors = ${anotherNewBook.errors}"
		}

		

		BookExcelImporter exporter = new BookExcelImporter().readFromUrl("file:test-data/books-empty.${fileExt}".toURL())

		exporter.setBooks(Book.list())
		exporter.setOneMoreBookParams([*: Book.get(3L).properties])

		String exportName = exporter.writeToFile(File.createTempFile('book-', ".${fileExt}").absolutePath)
		println "exported to file = ${exportName}"

//		some issues with reading from just exported model in xlsx format
//		println "booksMapList = ${exporter.getBooks()}"
//		println "bookParams = ${exporter.getOneMoreBookParams()}"

	}


	def testCsv() {
		println "==== testCsv"
		BookExcelImporter.BookCsvImporter csvImporter = new BookExcelImporter.BookCsvImporter().readFromUrl("file:test-data/books.csv".toURL())

		def booksMapList = csvImporter.getBooks()
		println "booksMapList = ${booksMapList}"

		BookExcelImporter excelExporter =  csvImporter.copyToExcel(new BookExcelImporter().readFromUrl("file:test-data/books-empty.xls".toURL()))
		String exportName = excelExporter.writeToFile(File.createTempFile('book-', ".xls").absolutePath)
		println "exported to file = ${exportName}"
		
	}



	def destroy = {
	}
}
