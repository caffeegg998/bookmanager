package com.megane.usermanager.service.itfmethod;

import com.megane.usermanager.dto.BookDTO;
import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.entity.Book;
import com.megane.usermanager.repo.BookRepo;
import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

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