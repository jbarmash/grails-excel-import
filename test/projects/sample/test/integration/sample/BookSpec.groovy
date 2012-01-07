package sample

import sample.*
import grails.test.*
import grails.plugin.spock.*
import org.grails.plugins.excelimport.*


/*
	void testCells() {
		def singleBook = importer.getOneMoreBookParams()
		System.out.println "$singleBook"
		assertEquals singleBook.title, 'Romeo & Juliet'
		assertEquals singleBook.author, 'Shakespeare'
		assertNotNull singleBook.dateIssued 
		assertNotNull singleBook.dateIssuedError
		System.out.println "dateIssuedError: ${singleBook.dateIssuedError.class}"
		System.out.println "dateIssued: ${singleBook.dateIssued.class}"
	}

*/


class BookSpec extends UnitSpec { 

	BookExcelImporter importer 
	def newBook = null

	def setup() {
		mockDomain(Book)
		String fileName = "./test-data/books.xls"
		importer = new BookExcelImporter(fileName);
	}

	def "Check Error Reporter"() { 
		when:   
			def booksMapList = importer.getBooks();
			//println booksMapList

		then: 
	
			//println "CELL REPORTER ${importer.cellReporter.messagesBySeverityLevel}"
			importer.cellReporter.messagesBySeverityLevel?.size()==3
			importer.cellReporter.messagesBySeverityLevel[ImportSeverityLevelEnum.Error].isEmpty() == false
	} 


	def "Load Data From Columns"() { 
		when:   
			def booksMapList = importer.getBooks();
			//println booksMapList
		and:  "Bind Imported Excel Data To Book Object"
			booksMapList.each { Map bookParams ->
				//println "PARAMS $bookParams"
				def newBook = new Book(bookParams)
				if (!newBook.save()) {
					//println "Book not saved, errors = ${newBook.errors}"
					assertTrue false
				}
			}

		then: 
			Book.count() == 3	
			Book.findByAuthorAndTitle (author, title)?.numSold ==quantity

		where:  
		     title        			      |  author            | quantity
		     "How to Win Friends and Influence People"| "Dale Carnegie"    | 10
		     "Oliver Twist"			      | "Charles Dickens"  | 1 //defaultValue
		     "War and Peace"			      | "Leo Tolstoy"      | 5

	} 


	

/*
	pcc = pcc ?: NoopImportCellCollector.NoopInstance
*/

	def "Load Data From Cells"() { 
		when: 
			def singleBook = importer.getOneMoreBookParams()
			//System.out.println "$singleBook"
			//System.out.println "dateIssuedError: ${singleBook.dateIssuedError.class}"
			//System.out.println "dateIssued: ${singleBook.dateIssued.class}"
		then: 
			singleBook.title == 'Romeo & Juliet'
			singleBook.author == 'Shakespeare'
			singleBook.dateIssued !=null
			singleBook.dateIssuedError != null
	}

}

