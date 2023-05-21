package com.scalefocus.view;

import com.scalefocus.exception.DatabaseConnectivityException;
import com.scalefocus.view.view.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class ViewMapper {

    private static final Logger log = LoggerFactory.getLogger(ViewAccessor.class);

    public List<ViewTown> mapResultSetToViews(ResultSet viewsResultSet) {
        List<ViewTown> viewsList = new ArrayList<>();
        try (viewsResultSet) {
            while (viewsResultSet.next()) {

                String type = viewsResultSet.getString(1);
                String company = viewsResultSet.getString(2);
                double totalRating = viewsResultSet.getDouble(3);
                int totalComment = viewsResultSet.getInt(4);

                ViewTown view = new ViewTown(type, company,totalRating,totalComment);
                viewsList.add(view);
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return viewsList;
    }

    public List<ViewType> mapResultSetToViewType(ResultSet viewsResultSet) {
        List<ViewType> viewsList = new ArrayList<>();
        try (viewsResultSet) {
            while (viewsResultSet.next()) {

                String town = viewsResultSet.getString(1);
                String name = viewsResultSet.getString(2);
                double totalRating = viewsResultSet.getDouble(3);



                ViewType view = new ViewType(town,name,totalRating);
                viewsList.add(view);
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return viewsList;
    }


    public List<ViewBasic> mapResultSetToViewBasics(ResultSet viewBasicsResultSet) {
        List<ViewBasic> viewsList = new ArrayList<>();
        try (viewBasicsResultSet) {
            while (viewBasicsResultSet.next()) {
                String town = viewBasicsResultSet.getString(1);
                String type = viewBasicsResultSet.getString(2);
                String company = viewBasicsResultSet.getString(3);
                double totalRating = viewBasicsResultSet.getDouble(4);

                ViewBasic view = new ViewBasic(town,type, company,totalRating);
                viewsList.add(view);
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return viewsList;
    }




    public List<ViewTown> mapViews(List<ViewTown> views){
        ArrayList<ViewTown> viewList = new ArrayList<>();
        for(ViewTown view : views){
            ViewTown viewObj = new ViewTown(view.getType(),view.getCompany(),view.getTotalRating(), view.getTotalComments());
            viewList.add(viewObj);
        }
        return viewList;
    }

    public List<ViewComments> mapResultSetToViewComments(ResultSet viewCommentsResultSet) {
        List<ViewComments> viewsList = new ArrayList<>();
        try (viewCommentsResultSet) {
            while (viewCommentsResultSet.next()) {

                String company = viewCommentsResultSet.getString(1);
                String user = viewCommentsResultSet.getString(2);
                String content = viewCommentsResultSet.getString(3);
                double rating = viewCommentsResultSet.getDouble(4);
                Timestamp date = viewCommentsResultSet.getTimestamp(5);


                ViewComments view = new ViewComments(company,user,content,rating,date);
                viewsList.add(view);
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return viewsList;
    }


    public List<ViewRate> mapResultSetToViewRates(ResultSet viewBasicsResultSet) {
        List<ViewRate> viewsList = new ArrayList<>();
        try (viewBasicsResultSet) {
            while (viewBasicsResultSet.next()) {
                String type = viewBasicsResultSet.getString(1);
                String company = viewBasicsResultSet.getString(2);
                double rate = viewBasicsResultSet.getDouble(3);

                ViewRate view = new ViewRate(type ,company,rate);
                viewsList.add(view);
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return viewsList;
    }

    public List<ViewCount> mapResultSetToViewCounts(ResultSet viewsResultSet) {
        List<ViewCount> viewsList = new ArrayList<>();
        try (viewsResultSet) {
            while (viewsResultSet.next()) {

                int town= viewsResultSet.getInt(1);
                int type = viewsResultSet.getInt(2);
                int company = viewsResultSet.getInt(3);
                int user = viewsResultSet.getInt(4);
                int comment = viewsResultSet.getInt(5);

                ViewCount view = new ViewCount(town,type,company,user,comment);
                viewsList.add(view);
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return viewsList;
    }
}
