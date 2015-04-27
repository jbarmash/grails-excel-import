package org.grails.plugins.excelimport

/**
 * Created by IntelliJ IDEA.
 * User: sima
 * Date: 4/21/11 8:20 PM
 */
abstract class AbstractCsvImporter extends imexporter.AbstractImexporter {


	protected List tokensList


	@Override
	protected def read(InputStream inp) {
		tokensList = []
		inp.eachCsvLine {tokens ->
			tokensList << tokens
		}
	}

	@Override
	protected def write(OutputStream out) {
		throw new IllegalStateException("not implemented")
	}


	def getData(Map config) {
		getData(config.columnMap, config.startRow)
	}

	def getData(Map columnMap, int firstRow) {
		tokensList[[firstRow, tokensList.size()].min()..<tokensList.size()].collect {tokens ->
			columnMap.inject([:]) {acc, ent ->
				acc[ent.value] = (ent.key < tokens.length) ? tokens[ent.key] : null
				return acc
			}
		}
	}


	static def trivialColumnMapFromExcel(Map excelColumnMap) {
		excelColumnMap.inject([:]) {acc, ent ->
			def columnName = ent.key
			def propertyName = ent.value
			int colIndex = org.apache.poi.ss.util.CellReference.convertColStringToIndex(columnName)
			acc[colIndex] = propertyName
			return acc
		}.asImmutable()
	}

	static def trivialConfigMapFromExcel(Map excelConfigMap) {
		[
			startRow: excelConfigMap.startRow,
			columnMap: trivialColumnMapFromExcel(excelConfigMap.columnMap)
		].asImmutable()
	}

}
