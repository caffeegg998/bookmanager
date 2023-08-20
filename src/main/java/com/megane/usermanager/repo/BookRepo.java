package com.megane.usermanager.repo;

import com.megane.usermanager.dto.BookDTO;
import com.megane.usermanager.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BookRepo extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE b.title LIKE :b OR b.author LIKE :b")
    Page<Book> findByTitleOrAuthorContaining(@Param("b") String s, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.bookCreator = :bookCreatorValue")
    List<Book> findByBookCreator(@Param("bookCreatorValue") String bookCreatorValue);

    Book findByBookUrl(String bookurl);



}
