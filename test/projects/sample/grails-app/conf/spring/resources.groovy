// Place your Spring DSL code here
beans = {

	bookImporter(sample.BookExcelImporter) { bean ->
		excelImportService = ref ('excelImportService')	
		bean.scope = 'prototype'
	} 

}
