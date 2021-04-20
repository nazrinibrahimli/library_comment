package edu.ada.service.library.service.impl;

import edu.ada.service.library.controller.ErrorHandler;
import edu.ada.service.library.exceptions.InvalidPickupException;
import edu.ada.service.library.exceptions.NotExistsException;
import edu.ada.service.library.model.entity.*;
import edu.ada.service.library.model.mapper.BookMapper;
import edu.ada.service.library.model.mapper.CategoryMapper;
import edu.ada.service.library.model.mapper.CommentMapper;

import edu.ada.service.library.model.mapper.PickupMapper;
import edu.ada.service.library.model.repository.BookRepository;
import edu.ada.service.library.model.requestAndResponse.*;

import java.util.ArrayList;
import edu.ada.service.library.model.repository.CategoryRepository;
import edu.ada.service.library.model.repository.PickupRepository;
import edu.ada.service.library.model.repository.CommentRepository;
import edu.ada.service.library.service.LibraryService;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service



public class LibraryServiceImpl implements LibraryService {
    protected static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);




    private BookRepository bookRepository;

    private CategoryRepository categoryRepository;
    private PickupRepository pickupRepository;
    private CommentRepository commentRepository;

    public LibraryServiceImpl(
            BookRepository bookRepository, CategoryRepository categoryRepository, PickupRepository pickupRepository, CommentRepository commentRepository
    ) {
          this.bookRepository = bookRepository;
           this.categoryRepository = categoryRepository;
        this.pickupRepository = pickupRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CategoryDto> listCategories() {
        log.info(" ");
        Iterable<CategoryEntity> categories = categoryRepository.findAll();
        log.info(" ");
        return CategoryMapper.mapEntitiesToDtos(categories);
    }

    @Override
    public List<BookDto> listBooks() {
        log.info(" ");
        var books = bookRepository.findAll();
        log.info(" ");
        return BookMapper.mapEntitiesToDtos(books);
    }

    @Override
    public List<BookDto> searchBooks(SearchBookParams params) {
        log.info(" ");

        List<BookEntity> bookEntities;
        if (params.getSearchAndOr() == SearchAndOr.AND) {
            bookEntities = bookRepository.findAllByNameContainingAndCategory_idAndAuthorContaining(
                    params.getName(),
                    params.getCategoryId(),
                    params.getAuthor()
            );
        } else {
            bookEntities = bookRepository.findAllByNameContainingOrCategory_idOrAuthorContaining(
                    params.getName(),
                    params.getCategoryId(),
                    params.getAuthor()
            );
        }

        log.info(" ");
        return BookMapper.mapEntitiesToDtos(bookEntities);
    }

    @Override
    public List<PickupDto> listCurrentPickups(UserEntity userEntity) {
        log.info(" ");

        var pickups = pickupRepository.findByUserAndDropOffFalse(userEntity);

        log.info(" ");
        return PickupMapper.mapEntitiesToDtos(pickups);
    }

    @Override
    public List<PickupDto> listPickupAndDropOffs(UserEntity userEntity) {
        log.info(" ");

        var pickups = pickupRepository.findByUser(userEntity);

        log.info(" ");
        return PickupMapper.mapEntitiesToDtos(pickups);
    }

    @Override
    public PickupDto pickupBook(PickupRequestDto requestDto, UserEntity userEntity) {
        log.info(" ");

        BookEntity bookEntity = bookRepository
                .findById(requestDto.getBookId())
                .orElseThrow(() -> new NotExistsException(" "));

        PickupEntity pickupEntity = pickupRepository.findByBookAndDropOffFalse(bookEntity);

        if (pickupEntity != null) {
            if (pickupEntity.getUserEntity().getId().equals(userEntity.getId())) {
                throw new InvalidPickupException(" ");
            }
            throw new InvalidPickupException(" ");
        }

        var newPickup = PickupEntity
                .builder().bookEntity(bookEntity).userEntity(userEntity).build();

        pickupRepository.save(newPickup);

        log.info(" ");
        return PickupMapper.mapEntityToDto(newPickup);
    }

    @Override
    public PickupDto dropOffBook(PickupRequestDto requestDto, UserEntity userEntity) {
        log.info(" ");

        BookEntity bookEntity = bookRepository
                .findById(requestDto.getBookId()).orElseThrow(() -> new NotExistsException("Book cannot be found"));

        PickupEntity pickupEntity = pickupRepository.findByBookAndUserAndDropOffFalse(bookEntity, userEntity);

        if (pickupEntity == null) {
            throw new InvalidPickupException("error");
        }
        pickupEntity.setDropOff(true);
        pickupRepository.save(pickupEntity);
        log.info(" ");
        return PickupMapper.mapEntityToDto(pickupEntity);
    }




    //NEW ADDED FOR ASSIGNMENT 4



    @Override
    public BookResponseDto getBookById(Long bookId) {
        log.info(" ");
//if there is a book with this id (bookId)
        BookEntity book = bookRepository.findById(bookId).orElseThrow(() -> new NotExistsException("Book could not be found "));

        //if yes
        log.info("Book found ");


        //finds the corresponding comment for the book within all comments
        var comments = commentRepository.findByBookId(bookId);

        log.info(" ");
        return BookMapper.mapEntityToDetailedDto(book, CommentMapper.mapEntitiesToDtos(comments));
    }

    @Override
    public CommentResponseDto addComment(Long bookId, CommentRequestDto requestDto, UserEntity user) {
//        log.info("");

        BookEntity book = bookRepository
                .findById(bookId).orElseThrow(() -> new NotExistsException("book doesnt exist"));

        log.info("Book found");
//with email, book, and text we create ne comment
        var newComment = CommentEntity.builder()
                .comment_author_name(user.getEmail()).book(book).comment_content(requestDto.getComment_content()).build();
        commentRepository.save(newComment); //saves new comment



        return CommentMapper.mapEntityToDto(newComment);
        return null;
    }

    @Override
    public CommentResponseDto replyToComment(String commentId, CommentRequestDto requestDto, UserEntity userEntity) {
        log.info(" ");
//if the comment we want to reply is exist or not
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new NotExistsException("Comment could not be found"));

        log.info("Comment found");

        var newComment = CommentEntity.builder()
                .comment_author_name(userEntity.getEmail()).book(comment.getBook()).comment_content(requestDto.getComment_content()).build();

        if (comment.getReplies() == null) {
            comment.setReplies(new ArrayList<>());
        }
        comment.getReplies().add(newComment);
        commentRepository.save(comment); //save new comment


        return CommentMapper.mapEntityToDto(newComment);




        return null;
    }





}