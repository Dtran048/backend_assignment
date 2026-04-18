package com.example.bookstore_mongodb.controller;

import com.example.bookstore_mongodb.entity.Book;
import com.example.bookstore_mongodb.repository.BookRepository;
import com.example.bookstore_mongodb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeBook(@PathVariable String id){
        if(bookRepository.findById(id).isPresent()){
            bookService.deleteBook(id);

            return ResponseEntity.status(200).body(Map.of("message", "Book deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Book not found"));
        }





    }

}
