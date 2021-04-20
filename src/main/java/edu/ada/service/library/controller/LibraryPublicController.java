package edu.ada.service.library.controller;

import edu.ada.service.library.model.requestAndResponse.*;
import edu.ada.service.library.service.LibraryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/library/public")
public class LibraryPublicController {
    private LibraryService libraryService;

    public LibraryPublicController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping(value="/categories")
    public List<CategoryDto> listCategories() {
        return libraryService.listCategories();
    }

    @GetMapping(value="/books")
    public List<BookDto> listBooks() {
        return libraryService.listBooks();
    }

    @GetMapping(value="/searchBooks")
    public List<BookDto> searBooks(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "searchAndOr", required = false) SearchAndOr searchAndOr
    ) {
        return libraryService.searchBooks(
                SearchBookParams.builder().name(name).categoryId(categoryId).author(author).searchAndOr(searchAndOr).build()
        );
    }

//added for assignment 4
//   function shows comments that are written to corresponding books and all book information
    @GetMapping("/books/{book_id}")
    public BookResponseDto getBookById(@PathVariable("book_id") Long bookId) {
        return libraryService.getBookById(bookId);
    }





}
