package com.example.webbackend.controller;

import com.example.webbackend.entity.Book;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookController {

    private List<Book> books = new ArrayList<>();

    private Long nextId = 1L;

    public BookController() {
        // Add 15 books with varied data for testing
        books.add(new Book(nextId++, "Spring Boot in Action", "Craig Walls", 39.99));
        books.add(new Book(nextId++, "Effective Java", "Joshua Bloch", 45.00));
        books.add(new Book(nextId++, "Clean Code", "Robert Martin", 42.50));
        books.add(new Book(nextId++, "Java Concurrency in Practice", "Brian Goetz", 49.99));
        books.add(new Book(nextId++, "Design Patterns", "Gang of Four", 54.99));
        books.add(new Book(nextId++, "Head First Java", "Kathy Sierra", 35.00));
        books.add(new Book(nextId++, "Spring in Action", "Craig Walls", 44.99));
        books.add(new Book(nextId++, "Clean Architecture", "Robert Martin", 39.99));
        books.add(new Book(nextId++, "Refactoring", "Martin Fowler", 47.50));
        books.add(new Book(nextId++, "The Pragmatic Programmer", "Andrew Hunt", 41.99));
        books.add(new Book(nextId++, "You Don't Know JS", "Kyle Simpson", 29.99));
        books.add(new Book(nextId++, "JavaScript: The Good Parts", "Douglas Crockford", 32.50));
        books.add(new Book(nextId++, "Eloquent JavaScript", "Marijn Haverbeke", 27.99));
        books.add(new Book(nextId++, "Python Crash Course", "Eric Matthes", 38.00));
        books.add(new Book(nextId++, "Automate the Boring Stuff", "Al Sweigart", 33.50));
    }

    // get all books - /api/books
    @GetMapping("/books")
    public List<Book> getBooks() {
        return books;
    }

    // get book by id
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Long id) {
        return books.stream().filter(book -> book.getId().equals(id))
                .findFirst().orElse(null);
    }

    // create a new book
    @PostMapping("/books")
    public List<Book> createBook(@RequestBody Book book) {
        books.add(book);
        return books;
    }

    @PutMapping("{id}")
    public Book updatebook(@PathVariable Long id, @RequestBody Book bok){
        for(Book curbook : books){
            if(curbook.getId().equals(id)){
                curbook.setId(bok.getId());
                curbook.setAuthor(bok.getAuthor());
                curbook.setPrice(bok.getPrice());
                curbook.setTitle(bok.getTitle());
                return curbook;
            }
        }
        return null;
    }

    @PatchMapping("{id}")
    public Book patchbook(@PathVariable Long id, @RequestBody Book bok){
        for(Book curbook : books){
            if(curbook.getId().equals(id)){
                if(bok.getId() != null){
                    curbook.setId(bok.getId());
                }
                if(bok.getAuthor() != null){
                    curbook.setAuthor(bok.getAuthor());
                }
                if(bok.getPrice() != null){
                    curbook.setPrice(bok.getPrice());
                }
                if(bok.getTitle() != null){
                    curbook.setTitle(bok.getTitle());
                }
                return curbook;
            }
        }
        return null;
    }
    @DeleteMapping("{id}")
    public List<Book> removebook(@PathVariable Long id){
        books.removeIf(curbook -> curbook.getId().equals(id));
        return books;
    }

    // get book by id
    @GetMapping("/books/pages")
    public List<Book> paging(@RequestParam(required = false, defaultValue = "1") int page) {
        return books.stream().skip((page-1) * 4L).limit(4).collect(Collectors.toList());
    }

    @GetMapping("/books/adv_pages")
    public List<Book> adv_paging(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "title") String sortby,
            @RequestParam(required = false, defaultValue = "1") double price
    ) {
        Comparator<Book> comparator;
        switch(sortby.toLowerCase()) {
            case "author":
                comparator = Comparator.comparing(Book::getAuthor);
                break;
            case "title":
                comparator = Comparator.comparing(Book::getTitle);
                break;
            case "price":
                comparator = Comparator.comparing(Book::getPrice);
                break;
            case "id":
                comparator = Comparator.comparing(Book::getId);
                break;
            default:
                comparator = Comparator.comparing(Book::getId);
                break;
        }

        return books.stream()
                .filter(book -> book.getPrice()>price)
                .sorted(comparator)
                .skip((page-1) * 4L).limit(4).collect(Collectors.toList());
    }

}