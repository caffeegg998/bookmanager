package com.megane.usermanager.service.itfmethod;

import com.megane.usermanager.dto.BookDTO;
import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.entity.Book;

import java.util.List;

public interface BookService {
    void create(BookDTO bookDTO);
    void update(BookDTO bookDTO);
    void delete(int id);
    BookDTO getById(int id);

    PageDTO<List<BookDTO>> search(SearchDTO searchDTO);
    PageDTO<List<BookDTO>> getBooksByTitleOrAuthor(SearchDTO searchDTO);
    void sellBook(Book book, int quantity);
    void checkStockLevel(Book book);
    List<BookDTO> getAll();
}