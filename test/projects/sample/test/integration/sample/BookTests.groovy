package sample

import sample.*
import grails.test.*

class BookTests extends GrailsUnitTestCase {

	BookExcelImporter bookImporter 

	protected void setUp() {
		super.setUp()
		bookImporter.read( "./test-data/books.xls" )
	}


	protected void tearDown() {
		super.tearDown()
	}

	void testColumns() {
		mockDomain(Book)

		def booksMapList = bookImporter.getBooks();
		println booksMapList

		booksMapList.each { Map bookParams ->
			def newBook = new Book(bookParams)
			if (!newBook.save()) {
				println "Book not saved, errors = ${newBook.errors}"
				assertTrue false
			}
		}
	}

	void testCells() {
		def singleBook = bookImporter.getOneMoreBookParams()
		System.out.println "$singleBook"
		assertEquals singleBook.title, 'Romeo & Juliet'
		assertEquals singleBook.author, 'Shakespeare'
		assertNotNull singleBook.dateIssued 
		assertNotNull singleBook.dateIssuedError
		System.out.println "dateIssuedError: ${singleBook.dateIssuedError.class}"
		System.out.println "dateIssued: ${singleBook.dateIssued.class}"
	}

}
