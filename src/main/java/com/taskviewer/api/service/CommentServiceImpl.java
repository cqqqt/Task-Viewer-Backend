package com.taskviewer.api.service;

import com.taskviewer.api.model.Comment;
import com.taskviewer.api.model.CommentNotFoundException;
import com.taskviewer.api.model.Comments;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final Comments comments;

  @Transactional
  @Override
  public Comment with(final Comment comment) {
    return comment.withId(
      this.comments.add(comment)
    );
  }

  @Transactional
  @Override
  public Comment update(final Comment comment) {
    this.comments.update(comment);
    return this.byId(comment.id());
  }

  @Transactional
  @Override
  public void delete(final Long id) {
    this.comments.delete(id);
  }

  @Override
  public Comment byId(final Long id) {
    return this.comments.byId(id)
      .orElseThrow(
        () ->
          new CommentNotFoundException(
            "Comment with id %s is not found"
              .formatted(
                id
              )
          )
      );
  }

  @Override
  public List<Comment> iterate(final CommentSearchCriteria criteria) {
    if (criteria.user() != null && criteria.task() != null) {
      return this.comments.iterate(criteria.user(), criteria.task());
    }
    if (criteria.task() != null) {
      return this.comments.byTask(criteria.task());
    }
    if (criteria.user() != null) {
      return this.comments.byUser(criteria.user());
    }
    throw new CriteriaIsNotSpecifiedException(
      "Search Criteria is not specified"
    );
  }
}
