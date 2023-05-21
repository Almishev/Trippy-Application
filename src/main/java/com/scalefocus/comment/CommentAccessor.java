package com.scalefocus.comment;
import com.scalefocus.exception.DatabaseConnectivityException;
import com.scalefocus.exception.IDNotUniqueException;
import com.scalefocus.exception.NotFoundException;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class CommentAccessor {

    private static final Logger log = LoggerFactory.getLogger(CommentAccessor.class);

    private final CommentMapper commentMapper;
    private final HikariDataSource dataSource;

    @Autowired
    public CommentAccessor(CommentMapper commentMapper, HikariDataSource dataSource) {
        this.commentMapper = commentMapper;
        this.dataSource = dataSource;
    }

    public Comment addComment(Comment comment) {
        int commentId;
        final String insertSQL = "insert into comments (content,rating,est_id,user_id) values(?,?,?,?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setDouble(2, comment.getRating());
            preparedStatement.setInt(3, comment.getEstId());
            preparedStatement.setInt(4, comment.getUserId());

            log.debug("Trying to persist new establishments");
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                commentId = rs.getInt(1);
            } else {
                log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
                throw new SQLException("No id retrieved from inserted object");
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        comment.setCommentId(commentId);
        log.info(String.format("Establishment with id %d successfully persisted", commentId));
        return comment;
    }

    public List<Comment> readAllComments() {
        ResultSet resultSet;
        List<Comment> comments;
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM comments order by comment_id");

            comments = commentMapper.mapResultSetToComments(resultSet);
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return comments;
    }

    public Comment readCommentById(int id) {
        ResultSet resultSet;
        List<Comment> comments;

        final String SQL = "SELECT * FROM comments WHERE comment_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            comments = commentMapper.mapResultSetToComments(resultSet);
            if (comments.size() > 1) {
                log.error(String.format("Multiple comments with id %d found in database", id));
                throw new IDNotUniqueException(String.format("Multiple towns with the same id found. Id = %d", id));
            } else if (comments.size() == 0) {
                log.error(String.format("Comment with id %d not found in database", id));
                throw new NotFoundException(String.format("Comment not found with id %d", id));
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return comments.get(0);
    }

    public int deleteComment(int id) {

        final String deleteSQL = "DELETE FROM comments WHERE comment_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

            deleteStatement.setInt(1, id);
            return deleteStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
    }
    public int getUserIdFromComments (int commentId){
        final String StringSQL ="select user_id from comments where comment_Id = ?";
        ResultSet resultSet;
        int n;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(StringSQL)) {
            statement.setInt(1,commentId);
            resultSet = statement.executeQuery();

            n = commentMapper.mapResultSetToInt(resultSet);
            return n;
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }

    }

    public int updateComment(Comment comment) {

        final String UpdateSQL = "UPDATE comments SET content = ? , rating = ? , est_id = ?  WHERE comment_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(UpdateSQL)) {
            updateStatement.setString(1, comment.getContent());
            updateStatement.setDouble(2, comment.getRating());
            updateStatement.setInt(3, comment.getEstId());
            updateStatement.setInt(4, comment.getCommentId());

            return updateStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }

    }


}
