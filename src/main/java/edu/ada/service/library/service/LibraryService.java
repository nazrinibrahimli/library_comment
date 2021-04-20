package edu.ada.service.library.service;

import edu.ada.service.library.model.entity.UserEntity;

import edu.ada.service.library.model.requestAndResponse.PickupRequestDto;
import edu.ada.service.library.model.requestAndResponse.SearchBookParams;
import edu.ada.service.library.model.requestAndResponse.BookDto;
import edu.ada.service.library.model.requestAndResponse.CategoryDto;
import edu.ada.service.library.model.requestAndResponse.PickupDto;
import edu.ada.service.library.model.requestAndResponse.BookResponseDto;
import edu.ada.service.library.model.requestAndResponse.CommentRequestDto;
import edu.ada.service.library.model.requestAndResponse.CommentResponseDto;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LibraryService {
    List<CategoryDto> listCategories();
    List<BookDto> listBooks();
    List<BookDto> searchBooks(SearchBookParams params);
    List<PickupDto> listPickupAndDropOffs(UserEntity userEntity);
    List<PickupDto> listCurrentPickups(UserEntity userEntity);

//    new
BookResponseDto getBookById(Long bookId);

    PickupDto pickupBook(PickupRequestDto requestDto, UserEntity userEntity);
    PickupDto dropOffBook(PickupRequestDto requestDto, UserEntity userEntity);


//added
CommentResponseDto addComment(Long bookId, CommentRequestDto requestDto, UserEntity userEntity);
    CommentResponseDto replyToComment(String commentId, CommentRequestDto requestDto, UserEntity userEntity);

}
