package com.megane.usermanager.controller;

import com.megane.usermanager.dto.BookDTO;
import com.megane.usermanager.dto.ResponseDTO;
import com.megane.usermanager.dto.UserDTO;
import com.megane.usermanager.service.interf.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookmanager/")
public class BookController {

    @Value("${upload.folder}")
    private  String UPLOAD_FOLDER;

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

    @PostMapping("/add-book-with-metadata")
    public ResponseDTO<BookDTO>  add(@ModelAttribute @Valid BookDTO bookDTO) throws IllegalStateException, IOException {
        if (bookDTO.getFile() != null && !bookDTO.getFile().isEmpty()) {
            if (!(new File(UPLOAD_FOLDER).exists())) {
                new File(UPLOAD_FOLDER).mkdirs();
            }
            String filename = bookDTO.getFile().getOriginalFilename();
            // lay dinh dang file
            String extension = filename.substring(filename.lastIndexOf("."));
            // tao ten moi
            String newFilename = UUID.randomUUID().toString() + extension;

            File newFile = new File(UPLOAD_FOLDER + filename + extension);

            bookDTO.getFile().transferTo(newFile);

            bookDTO.setBookFilePath(newFilename);// save to db
        }

        bookService.create(bookDTO);
        return ResponseDTO.<BookDTO>builder().status(200).data(bookDTO).build();
    }
    @PostMapping("/add-book")
    public ResponseDTO<Void> create(@ModelAttribute @Valid BookDTO bookDTO){
        bookService.create(bookDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("ok").build();
    }

    @GetMapping("/list-book")
    public ResponseDTO<List<BookDTO>> list() {
        List<BookDTO> bookDTOs = bookService.getAll();
        return ResponseDTO.<List<BookDTO>>builder().status(200).data(bookDTOs).build();
    }

    @GetMapping("/get-book/") // ?id=1000
    @ResponseStatus(code = HttpStatus.OK)
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
