package com.taskviewer.api.web.security.jwt;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.Tasks;
import com.taskviewer.api.service.CommentSearchCriteria;
import com.taskviewer.api.service.CommentService;
import com.taskviewer.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("securityRulesHandler")
@RequiredArgsConstructor
public class SecurityRulesHandler {

  private final Tasks tasks;
  private final UserService users;
  private final CommentService comments;

  public boolean canAccessUser(final Long userId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
    return userDetails.getUser().id().equals(userId) || hasAuthority("ADMIN");
  }

  public boolean canAccessTask(final Long taskId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    Task task = tasks.task(taskId);
    return task.username().equals(userDetails.getUsername()) || hasAuthority("ADMIN");
  }

  public boolean canAccessComment(final Long commentId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    final boolean can = this.comments.iterate(
        new CommentSearchCriteria(
          this.users
            .byUsername(userDetails.getUsername())
            .id(),
          null
        )
      )
      .stream()
      .anyMatch(
        comment -> comment.id().equals(commentId)
      );
    return can;
  }

  public boolean hasAuthority(String authorityName) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authorityName);
    return userDetails.getAuthorities().contains(authority);
  }
}
