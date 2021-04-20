package edu.ada.service.library.model.mapper;

import edu.ada.service.library.model.entity.BookEntity;
import edu.ada.service.library.model.entity.PickupEntity;
import edu.ada.service.library.model.requestAndResponse.BookDto;


import java.util.List;
import java.util.stream.Collectors;
import edu.ada.service.library.model.requestAndResponse.BookResponseDto;
import edu.ada.service.library.model.requestAndResponse.CommentResponseDto;

import java.util.stream.StreamSupport;

public class BookMapper {
    public static BookDto mapEntityToDto(BookEntity bookEntity) {
        PickupEntity pickupEntity = bookEntity.getCurrentPickup();


        return BookDto.builder().id(bookEntity.getId()).name(bookEntity.getName()).author(bookEntity.getAuthor()).publishDate(bookEntity.getPublishDate()).category(CategoryMapper.mapEntityToDto(bookEntity.getCategoryEntity())).isAvailable(pickupEntity == null).pickerEmail(pickupEntity != null ? pickupEntity.getUserEntity().getEmail() : null).build();
    }

    public static List<BookDto> mapEntitiesToDtos(Iterable<BookEntity> categories) {
        return StreamSupport.stream(categories.spliterator(), false).map(BookMapper::mapEntityToDto).collect(Collectors.toList());
    }

    public static BookResponseDto mapEntityToDetailedDto(BookEntity book, List<CommentResponseDto> comments) {
        PickupEntity pickup = book.getCurrentPickup();

        return BookResponseDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .publishDate(book.getPublishDate())
                .category(CategoryMapper.mapEntityToDto(book.getCategory()))
                .isAvailable(pickup == null)
                .pickerEmail(pickup != null ? pickup.getUser().getEmail() : null)
                .comments(comments)
                .build();
    }


    public static List<BookDto> mapEntitiesToDtos(Iterable<BookEntity> categories) {
        return StreamSupport.stream(categories.spliterator(), false)
                .map(BookMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public static BookResponseDto mapEntityToDetailedDto(BookEntity book, List<CommentResponseDto> comments) {
        PickupEntity pickup = book.getCurrentPickup();

        return BookResponseDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .publishDate(book.getPublishDate())
                .category(CategoryMapper.mapEntityToDto(book.getCategory()))
                .isAvailable(pickup == null)
                .pickerEmail(pickup != null ? pickup.getUser().getEmail() : null)
                .comments(comments) //shows comments
                .build();
    }


}
