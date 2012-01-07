Excel-Import plugin uses Apache POI [http://poi.apache.org/] library (v 3.6) to parse Excel files.  

It's useful for either bootstrapping data, or when you want to allow your users to enter some data using Excel spreadsheets. 

Usage:  

The core of the plugin is a utilities class, which contains a number of useful methods for dealing with Excel.    
   org.grails.plugins.excelimport.ExcelImportUtils 

There is also an AbstractExcelImporter, which is a class you can extend - it opens and stores the workbook reference.  


Reading Information Contained in Rows: 

The plugin is designed to parse data out of spreadsheets into format that can then be used to create objects.  For example, if you have data like this:

B                    C                D
Author             Book           # Sold
Shakespeare        King Lear        1000
Shakespeare        Hamlet            10000
Tolstoy            War and Peace   200


You can pass in parameters map that contains the name of the sheet, row where to start, and how the different columns map to properties of the object you are planning to populate (i.e. values in column B map to title property).  


static Map CONFIG_BOOK_COLUMN_MAP = [
          sheet:'Sheet1', 
			 startRow: 2,
          columnMap:  [
                  'B':'title',
                  'C':'author',
                  'D':'numSold',
          ]
  ]

List bookList = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_BOOK_COLUMN_MAP)

And you'll get back a list of maps:

assert bookParamsList, [
  [AuthorName:'Shakespeare', BookName:'King Lear', NumSold:1000],
  [AuthorName:'Shakespeare', BookName:'Hamlet', NumSold:10000],
  [AuthorName:'Tolstoy', BookName:'War and Peace', NumSold:200],
]

You can then pass the maps to constructors to create a bunch of objects, i.e. bookParamsList
  bookParamsList.each { Map bookParamMap ->
    new Book (bookParamMap).save()
  }


Reading Information contained in Individual Cells: 

  static Map CONFIG_BOOK_CELL_MAP = [ 
     sheet:'Sheet2', 
     cellMap: [ 'D3':'title',
        'D4':'author',
        'D6':'numSold',
	  ]
  ] 

Map bookParams = ExcelImportUtils.convertFromCellMapToMapWithValues(workbook, CONFIG_BOOK_CELL_MAP )


There is also ability to handle type errors, empty cells, evaluate formulas, etc.   There is also ability to pass in configuration information, i.e. to specify what to do if a cell is empty (i.e. to provide a default value), to make sure a cell is of expected type, etc.  

Also, you can do similar things for individual cells, when that is the format, i.e. C10 maps to key "Author", D12 to "AuthorYearBorn', etc.   Between targetting individual cells and columns / rows, that satisfied quite many requirements.  


Sample Application: 

For a sample of usage, please see a sample application you can download from the Plugins SVN (code below is from Bootstrap.groovy)




import org.grails.plugins.excelimport.ExcelImportUtils
import org.grails.plugins.excelimport.*
import sample.*


class BookExcelImporter extends AbstractExcelImporter {

  static Map CONFIG_BOOK_CELL_MAP = [ 
     sheet:'Sheet2', 
     cellMap: [ 'D3':'title',
        'D4':'author',
        'D6':'numSold',
	  ]
  ] 
 
  static Map CONFIG_BOOK_COLUMN_MAP = [
          sheet:'Sheet1', 
			 startRow: 2,
          columnMap:  [
                  'B':'title',
                  'C':'author',
                  'D':'numSold',
          ]
  ]

  public BookExcelImporter(fileName) {
    super(fileName)
  }

  List<Map> getBooks() {
    List bookList = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_BOOK_COLUMN_MAP)
  }


  Map getOneMoreBookParams() {
    Map bookParams = ExcelImportUtils.convertFromCellMapToMapWithValues(workbook, CONFIG_BOOK_CELL_MAP )
  }

}




class BootStrap {

    def init = { servletContext ->

    //String fileName = "c:\\dev\\HEAD\\plugins\\excel-import\\test\\projects\\sample\\test-data\\books.xls"
    String fileName = /.\test-data\books.xls/
	 BookExcelImporter importer = new BookExcelImporter(fileName);

	 def booksMapList = importer.getBooks();
	 println booksMapList

	 booksMapList.each { Map bookParams ->
	  def newBook = new Book(bookParams)
	  if (!newBook.save()) {
	    println "Book not saved, errors = ${newBook.errors}"
	  }
	 }

    new Book(importer.getOneMoreBookParams()).save()

	 }




Note: Apache POI 3.6 Supports Excel 2007 Files, but I haven't tested those. 
      Also, while formulas evaluation works for most cases, in some complicated cases it does not (i..e when you have a lookup of a looked up value using some unsupported formulas). 


The plugin is licensed under Apache V2.

Release History: 

June 21st, 2010 - Initial Release
