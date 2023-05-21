package com.scalefocus.comment;

public class CommentDto {

   private String content;
   private double rating;
   private int estId;
   private int userId;

   public CommentDto() {
   }

   public CommentDto(String content, double rating, int estId, int userId) {
      this.content = content;
      this.rating = rating;
      this.estId = estId;
      this.userId = userId;
   }

}
