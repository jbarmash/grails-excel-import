package imexporter

/**
 * Created by IntelliJ IDEA.
 * User: sima
 * Date: 4/21/11 8:06 PM
 */
public abstract class AbstractImexporter {


	protected abstract def read(InputStream inp)

	def readFromStream(inputStream) {
		inputStream.withStream(this.&read)
		return this
	}

	def readFromFile(String fileName) {
		readFromStream(new FileInputStream(fileName))
	}

	def readFromUrl(URL url) {
		readFromStream(url.openStream())
	}



	protected abstract def write(OutputStream out)

	def writeToStream(OutputStream outputStream) {
		outputStream.withStream(this.&write)
		return outputStream
	}

	def writeToFile(String fileName) {
		writeToStream(new FileOutputStream(fileName))
		return fileName
	}

	def writeToByteArray() {
		writeToStream(new ByteArrayOutputStream()).toByteArray()
	}


}
