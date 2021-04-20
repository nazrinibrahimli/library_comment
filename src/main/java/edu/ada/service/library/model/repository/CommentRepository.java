package edu.ada.service.library.model.repository;


import edu.ada.service.library.model.entity.CommentEntity;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, String> {

//        finds comments with given bookId
        List<CommentEntity> findByBookId(Long bookId);


}
