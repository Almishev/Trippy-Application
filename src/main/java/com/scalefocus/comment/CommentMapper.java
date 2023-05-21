package com.scalefocus.comment;

import com.scalefocus.exception.DatabaseConnectivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    private static final Logger log = LoggerFactory.getLogger(CommentAccessor.class);

    public List<Comment> mapResultSetToComments(ResultSet commentsResultSet) {
        List<Comment> commentsList = new ArrayList<>();
        try (commentsResultSet) {
            while (commentsResultSet.next()) {
                int id = commentsResultSet.getInt(1);
                String content = commentsResultSet.getString(2);
                double rating = commentsResultSet.getDouble(3);
                int estId  = commentsResultSet.getInt(4);
                int userId  = commentsResultSet.getInt(5);
                Timestamp createOn=commentsResultSet.getTimestamp(6);
                Timestamp editOn=commentsResultSet.getTimestamp(7);
                Comment comment = new Comment(id,content, rating, estId,userId,createOn,editOn);
                commentsList.add(comment);
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return commentsList;
    }


    public Integer mapResultSetToInt(ResultSet commentResultSet){
        int status = 0;
        try (commentResultSet){
            while (commentResultSet.next()) {
                status = commentResultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return status;
    }



}
