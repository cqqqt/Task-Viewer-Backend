package com.taskviewer.api.web.security;

public interface SecurityRules {

  boolean canAccessUser(Long userId);

  boolean canAccessTask(Long taskId);

  boolean canAccessComment(Long commentId);

  boolean hasAuthority(String authorityName);
}
