package com.taskviewer.api.service;

import com.taskviewer.api.model.Comment;
import com.taskviewer.api.model.CommentNotFoundException;
import com.taskviewer.api.model.Comments;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final Comments comments;

  @Transactional
  @Override
  public Comment with(final Comment comment) {
    log.debug("add comment {}", comment);
    return comment.withId(this.comments.add(comment));
  }

  @Transactional
  @Override
  public Comment update(final Comment comment) {
    log.debug("update comment {} with id {}", comment, comment.id());
    this.comments.update(comment);
    return this.byId(comment.id());
  }

  @Transactional
  @Override
  public void delete(final Long id) {
    log.debug("delete comment with id {}", id);
    this.comments.delete(id);
  }

  @Override
  public Comment byId(final Long id) {
    log.debug("find comment by id {}", id);
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
    log.debug("comment search criteria: {}", criteria);
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
