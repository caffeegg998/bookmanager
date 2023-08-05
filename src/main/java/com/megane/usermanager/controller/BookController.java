package com.megane.usermanager.controller;

import com.megane.usermanager.Jwt.JwtTokenService;
import com.megane.usermanager.dto.BookDTO;
import com.megane.usermanager.dto.ResponseDTO;
import com.megane.usermanager.dto.UserDTO;
import com.megane.usermanager.entity.Book;
import com.megane.usermanager.service.interf.BookService;
import com.megane.usermanager.service.interf.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/book/")
public class BookController {

    @Value("${upload.folder}")
    private  String UPLOAD_FOLDER;

    @Autowired
    JwtTokenService jwtTokenService;
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
//    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/member/add-book-file")
    public ResponseDTO<BookDTO>  add(@RequestHeader("Authorization") String authorizationHeader,@ModelAttribute @Valid BookDTO bookDTO) throws IllegalStateException, IOException {
        String bearerToken = authorizationHeader.replace("Bearer ", "");
        String username = jwtTokenService.getUsername(bearerToken);
        if (bookDTO.getFile() != null && !bookDTO.getFile().isEmpty()) {
            if (!(new File(UPLOAD_FOLDER).exists())) {
                new File(UPLOAD_FOLDER).mkdirs();
            }

            String filename = bookDTO.getFile().getOriginalFilename();
            // lay dinh dang file
            String extension = filename.substring(filename.lastIndexOf("."));
            // tao ten moi
            String newFilename = UUID.randomUUID().toString() + extension;

            File newFile = new File(UPLOAD_FOLDER + newFilename);

            bookDTO.getFile().transferTo(newFile);

            bookDTO.setBookUrl(newFilename);// save to db

        }
        if (bookDTO.getFile2() != null && !bookDTO.getFile2().isEmpty()) {
            if (!(new File(UPLOAD_FOLDER).exists())) {
                new File(UPLOAD_FOLDER).mkdirs();
            }

            String filename = bookDTO.getFile2().getOriginalFilename();
            // lay dinh dang file
            String extension = filename.substring(filename.lastIndexOf("."));
            // tao ten moi
            String newFilename = UUID.randomUUID().toString() + extension;

            File newFile = new File(UPLOAD_FOLDER + newFilename);

            bookDTO.getFile2().transferTo(newFile);

            bookDTO.setCoverUrl(newFilename);

        }
        bookDTO.setBookCreator(username);

        bookService.create(bookDTO);
        return ResponseDTO.<BookDTO>builder().status(200).whoDidIt(username).data(bookDTO).build();
    }
    @PutMapping("/member/update-book")
//    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseDTO<BookDTO> update(@ModelAttribute @Valid BookDTO bookDTO) throws IllegalStateException, IOException {
        if (bookDTO.getFile() != null && !bookDTO.getFile().isEmpty()) {
            String filename = bookDTO.getFile().getOriginalFilename();
            // lay dinh dang file
            String extension = filename.substring(filename.lastIndexOf("."));
            // tao ten moi
            String newFilename = UUID.randomUUID().toString() + extension;

            File newFile = new File(UPLOAD_FOLDER + newFilename);

            bookDTO.getFile().transferTo(newFile);

            bookDTO.setBookUrl(newFilename);// save to db
            bookDTO.setCoverUrl(newFilename);
        }
        if (bookDTO.getFile2() != null && !bookDTO.getFile2().isEmpty()) {
            if (!(new File(UPLOAD_FOLDER).exists())) {
                new File(UPLOAD_FOLDER).mkdirs();
            }

            String filename = bookDTO.getFile2().getOriginalFilename();
            // lay dinh dang file
            String extension = filename.substring(filename.lastIndexOf("."));
            // tao ten moi
            String newFilename = UUID.randomUUID().toString() + extension;

            File newFile = new File(UPLOAD_FOLDER + newFilename);

            bookDTO.getFile2().transferTo(newFile);

            bookDTO.setCoverUrl(newFilename);

        }

        bookService.update(bookDTO);

        return ResponseDTO.<BookDTO>builder().status(200).data(bookDTO).build();
    }
    @GetMapping("/download/{filename}")
    public void download(@PathVariable("filename") String filename, HttpServletResponse response) throws IOException {
        File file = new File(UPLOAD_FOLDER + filename);
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String fileExtension = filename.substring(filename.lastIndexOf(".") + 1);

        // Set các thông số cho response
        if ("jpeg".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension)) {
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        } else if ("png".equalsIgnoreCase(fileExtension)) {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        } else {
            // Nếu định dạng không hợp lệ, trả về lỗi không hỗ trợ định dạng này
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }
        response.setContentLengthLong(file.length()); // Kích thước của file
        response.setHeader("Content-Disposition", "inline; filename=" + filename); // Hiển thị ảnh trực tiếp trên trình duyệt
        Files.copy(file.toPath(), response.getOutputStream());
    }

//    @PostMapping("/add-book")
//    public ResponseDTO<Void> create(@ModelAttribute @Valid BookDTO bookDTO){
//        bookService.create(bookDTO);
//        return ResponseDTO.<Void>builder()
//                .status(200)
//                .msg("ok").build();
//    }

    @GetMapping("/list-book")
    public ResponseDTO<List<BookDTO>> list() {
        List<BookDTO> bookDTOs = bookService.getAll();
        return ResponseDTO.<List<BookDTO>>builder().status(200).data(bookDTOs).build();
    }

    @GetMapping("/list-book-by-creator/")
    public ResponseDTO<List<BookDTO>> listBookByCreator(@RequestParam("creator") String creator) {
        List<BookDTO> bookDTOs = bookService.getBooksByBookCreator(creator);
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
    @DeleteMapping("/member/delete/{id}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseDTO<Void> deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }
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
