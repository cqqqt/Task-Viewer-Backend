package com.taskviewer.api.web.rs;

import com.taskviewer.api.model.User;
import lombok.Data;

@Data
public class RsUser {

  private String username;
  private String email;
  private String role;
  private String firstname;
  private String lastname;

  public RsUser(final User usr) {
    this(
      usr.username(),
      usr.email(),
      usr.role(),
      usr.firstname(),
      usr.lastname()
    );
  }

  public RsUser(final String usr,
                final String eml,
                final String rl,
                final String first,
                final String last) {
    this.username = usr;
    this.email = eml;
    this.role = rl;
    this.firstname = first;
    this.lastname = last;
  }
}
