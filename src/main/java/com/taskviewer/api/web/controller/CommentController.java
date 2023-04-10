package com.taskviewer.api.web.controller;

import com.taskviewer.api.postgres.PgComment;
import com.taskviewer.api.service.CommentSearchCriteria;
import com.taskviewer.api.service.CommentService;
import com.taskviewer.api.service.UserService;
import com.taskviewer.api.web.rq.RqComment;
import com.taskviewer.api.web.rq.RqCommentUpdate;
import com.taskviewer.api.web.rs.RsComment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService comments;
  private final UserService users;

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public RsComment create(@RequestBody final RqComment request) {
    return new RsComment(
      this.comments.with(
        PgComment.builder()
          .task(request.task())
          .username(request.username())
          .content(request.content())
          .build()
      )
    );
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @PutMapping("{id}")
  public RsComment update(
    @AuthenticationPrincipal final User user,
    @PathVariable Long id,
    @RequestBody final RqCommentUpdate request) {
    final boolean can = this.comments.iterate(
        new CommentSearchCriteria(
          this.users
            .byUsername(user.getUsername())
            .id(),
          null
        )
      ).stream()
      .anyMatch(
        comment -> comment.id().equals(id)
      );
    if (can) {
      return new RsComment(
        this.comments.update(
          PgComment.builder()
            .id(id)
            .content(request.content())
            .build()
        )
      );
    } else {
      throw new RqForbiddenException(
        "you can not update someone's else comment"
      );
    }
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @DeleteMapping("{id}")
  public void delete(
    @AuthenticationPrincipal final User user,
    @PathVariable final Long id) {
    final boolean can = this.comments.iterate(
        new CommentSearchCriteria(
          this.users
            .byUsername(user.getUsername())
            .id(),
          null
        )
      )
      .stream()
      .anyMatch(
        comment -> comment.id().equals(id)
      );
    if (can) {
      this.comments.delete(id);
    } else {
      throw new RqForbiddenException(
        "you can't delete someone's else comment"
      );
    }
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @GetMapping
  public List<RsComment> comments(final CommentSearchCriteria criteria) {
    return this.comments.iterate(criteria)
      .stream()
      .map(RsComment::new)
      .toList();
  }
}
