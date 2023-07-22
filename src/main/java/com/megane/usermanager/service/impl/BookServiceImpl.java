package com.megane.usermanager.service.impl;

import com.megane.usermanager.dto.BookDTO;
import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.entity.Book;
import com.megane.usermanager.repo.BookRepo;
import com.megane.usermanager.service.interf.BookService;
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

@Service
class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;
    @Override
    public void create(BookDTO bookDTO) {
        Book book = new ModelMapper().map(bookDTO,Book.class);
        bookRepo.save(book);
    }
    @Override
    public void update(BookDTO bookDTO) {
        Book currentBook = bookRepo.findById(bookDTO.getId()).orElse(null);
        if(currentBook != null){
            currentBook.setTitle(bookDTO.getTitle());
            currentBook.setAuthor(bookDTO.getAuthor());
            currentBook.setPublisher(bookDTO.getPublisher());
            currentBook.setPublicationYear(bookDTO.getPublicationYear());
            currentBook.setSubject(bookDTO.getSubject());
            currentBook.setDescription(bookDTO.getDescription());
            currentBook.setFormat(bookDTO.getFormat());
            currentBook.setSeries(bookDTO.getSeries());
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public BookDTO getById(int id) {
        Book book = bookRepo.findById(id).orElseThrow(NoResultException::new);
        return convert(book);
    }

    private BookDTO convert(Book book) {
        return new ModelMapper().map(book, BookDTO.class);
    }

    @Override
    public PageDTO<List<BookDTO>> search(SearchDTO searchDTO) {
        return null;
    }

    @Override
    public PageDTO<List<BookDTO>> getBooksByTitleOrAuthor(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("title").ascending().and(Sort.by("author").descending());

        if (StringUtils.hasText(String.valueOf(searchDTO.getSortedField()))) {
            sortBy = Sort.by(String.valueOf(searchDTO.getSortedField())).ascending();
        }

        if (searchDTO.getCurrentPage() == null)
            searchDTO.setCurrentPage(0);

        if (searchDTO.getSize() == null)
            searchDTO.setSize(5);

        PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);

        Page<Book> page = bookRepo.findByTitleOrAuthorContaining("%" + searchDTO.getKeyword() + "%", pageRequest);

        // T: List<UserDTO>
        PageDTO<List<BookDTO>> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(page.getTotalPages());
        pageDTO.setTotalElements(page.getTotalElements());

        // List<User> users = page.getContent();
        List<BookDTO> userDTOs = page.get().map(b -> convert(b)).collect(Collectors.toList());

        // T: List<UserDTO>
        pageDTO.setData(userDTOs);

        return pageDTO;
    }
    public List<BookDTO> getAll() {
        List<Book> bookList = bookRepo.findAll();
        return bookList.stream().map(b -> convert(b))
                .collect(Collectors.toList());
    }

//    @Override
//    public void sellBook(Book book, int quantity) {
//        // Kiểm tra số lượng sách trong kho đủ để bán không
//        if (book.getQuantityInStock() >= quantity) {
//            // Cập nhật số lượng sách trong kho và số lượng đã bán
//            int newQuantityInStock = book.getQuantityInStock() - quantity;
//            int newQuantitySold = book.getQuantitySold() + quantity;
//            book.setQuantityInStock(newQuantityInStock);
//            book.setQuantitySold(newQuantitySold);
//            // Lưu thông tin cập nhật vào CSDL
//            bookRepo.save(book);
//        } else {
//            // Xử lý logic khi số lượng sách trong kho không đủ để bán
//        }
//    }
//    @Override
//    public void checkStockLevel(Book book) {
//        int minStockLevel = 10; // Số lượng tối thiểu để cảnh báo
//        if (book.getQuantityInStock() < minStockLevel) {
//            // Gửi cảnh báo hoặc xử lý logic tương ứng
//        }
//    }
}
