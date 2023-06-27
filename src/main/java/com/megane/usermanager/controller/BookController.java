package com.megane.usermanager.controller;

import com.megane.usermanager.dto.BookDTO;
import com.megane.usermanager.dto.ResponseDTO;
import com.megane.usermanager.service.itfmethod.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    BookService bookService;


//    @GetMapping
//    public List<BookDTO> getAllBooks() {
//        List<Book> books = bookService.getAllBooks();
//        return books.stream()
//                .map(BookDTO::new)
//                .collect(Collectors.toList());
//    }

//    @GetMapping("/{bookId}")
//    public BookDTO getBookById(@PathVariable Long bookId) {
//        Book book = bookService.getBookById(bookId);
//        if (book == null) {
//            throw new ResourceNotFoundException("Book not found with id: " + bookId);
//        }
//        return new BookDTO(book);
//    }

    @PostMapping("/")
    public ResponseDTO<Void> create(@ModelAttribute @Valid BookDTO bookDTO){
        bookService.create(bookDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("ok").build();
    }

    @GetMapping("/list")
    // @RolesAllowed({"ROLE_ADMIN","ROLE_SYSADMIN"})		  //
    // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseDTO<List<BookDTO>> list() {
        List<BookDTO> bookDTOs = bookService.getAll();
        return ResponseDTO.<List<BookDTO>>builder().status(200).data(bookDTOs).build();
    }

    @GetMapping("/") // ?id=1000
    @ResponseStatus(code = HttpStatus.OK)
    // @Secured({"ROLE_ADMIN","ROLE_SYSADMIN"}) //ROLE_   //hAI dONG NAY GIONG NHAU //Bao mat tren ham
    // @RolesAllowed({"ROLE_ADMIN","ROLE_SYSADMIN"})		  //
    // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")		  //
    //@PreAuthorize("isAuthenticated()")			      //
    public ResponseDTO<BookDTO> get(
            @RequestParam("id") int id) {
        return ResponseDTO.<BookDTO>builder()
                .status(200)
                .msg("OK!")
                .data(bookService.getById(id))
                .build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id, @RequestBody BookDTO bookDTO) {
//        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
//        if (updatedBook != null) {
//            return ResponseEntity.ok(updatedBook);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
//        boolean deleted = bookService.deleteBook(id);
//        if (deleted) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @GetMapping("/search")
//    public List<BookDTO> searchBooks(@RequestParam(required = false) String keyword) {
//        List<Book> books = bookService.getBooksByTitleOrAuthor(keyword);
//        return books.stream()
//                .map((Book t) -> new BookDTO(book))
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping("/paginated")
//    public Page<BookDTO> getPaginatedBooks(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        Page<Book> bookPage = bookService.getPaginatedBooks(page, size);
//        return bookPage.map(BookDTO::new);
//    }
}
