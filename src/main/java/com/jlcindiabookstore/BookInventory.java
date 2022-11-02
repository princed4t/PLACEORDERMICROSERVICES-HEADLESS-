package com.jlcindiabookstore;


import javax.persistence.*;

import io.swagger.annotations.ApiModel;
/*
* @Author : prince
* @company : prince project
* */
@Entity
@Table(name="mybookinventory",schema = "jlcordersdb")
@ApiModel("BookInventory contains Inevntory Details")
public class BookInventory {
@Id
@Column(name="book_id")
private Integer bookId;
@Column(name="books_available")
private int booksAvailable;
public BookInventory(Integer bookId, int booksAvailable) {
	super();
	this.bookId = bookId;
	this.booksAvailable = booksAvailable;
}
public BookInventory() {
	super();
	// TODO Auto-generated constructor stub
}
public Integer getBookId() {
	return bookId;
}
public void setBookId(Integer bookId) {
	this.bookId = bookId;
}
public int getBooksAvailable() {
	return booksAvailable;
}
public void setBooksAvailable(int booksAvailable) {
	this.booksAvailable = booksAvailable;
}
@Override
public String toString() {
	return "BookInventory [bookId=" + bookId + ", booksAvailable=" + booksAvailable + "]";
}

}
