package com.taskviewer.api.web.controller;

import com.taskviewer.api.postgres.PgComment;
import com.taskviewer.api.service.CommentSearchCriteria;
import com.taskviewer.api.service.CommentService;
import com.taskviewer.api.web.rq.RqComment;
import com.taskviewer.api.web.rq.RqCommentUpdate;
import com.taskviewer.api.web.rs.RsComment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService comments;

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

  @PreAuthorize("@securityRulesHandler.canAccessComment(#id)")
  @PutMapping("{id}")
  public RsComment update(
    @PathVariable Long id,
    @RequestBody final RqCommentUpdate request) {
      return new RsComment(
        this.comments.update(
          PgComment.builder()
            .id(id)
            .content(request.content())
            .build()
        )
      );
  }

  @PreAuthorize("@securityRulesHandler.canAccessComment(#id)")
  @DeleteMapping("{id}")
  public void delete(
    @PathVariable final Long id) {
      this.comments.delete(id);
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
