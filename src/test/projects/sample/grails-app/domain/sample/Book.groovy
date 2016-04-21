package sample

import org.joda.time.LocalDate


class Book {

    String title
    String author
    int numSold
	LocalDate dateIssued

    static constraints = {
	    dateIssued nullable: true
    }
}
