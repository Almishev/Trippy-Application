package com.scalefocus.view.utility;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class ViewConstants {

    private ViewConstants() {
        throw new IllegalStateException();
    }

    public static final String VIEW_BASIC_TOWN = "Kartagen";
    public static final String VIEW_BASIC_TYPE = "Hotel";
    public static final String VIEW_BASIC_COMPANY = "Blue scy";
    public static final double VIEW_BASIC_AVG_RATING = 4.00;

    public static final String VIEW_COMMENT_COMPANY = "Red earth";
    public static final String VIEW_COMMENT_USER = "man@abv.bg";
    public static final String VIEW_COMMENT_CONTENT = "Yaba daba du";
    public static final double VIEW_COMMENT_RATING = 3.00;
    public static final Timestamp VIEW_COMMENT_DATE = Timestamp.valueOf(LocalDateTime.now());

    public static final String VIEW_RATE_TYPE = "bar";
    public static final String VIEW_RATE_COMPANY = "Cheers";
    public static final double VIEW_RATE_RATE = 5.00;

    public static final String VIEW_TYPE_TOWN = "Rome";
    public static final String VIEW_TYPE_NAME = "Tiber";
    public static final double VIEW_TYPE_RATING = 1.00;

    public static final String VIEW_TOWN_TYPE = "restaurant";
    public static final String VIEW_TOWN_COMPANY = "Zoo";
    public static final double VIEW_TOWN_AVG_RATING=2.00;
    public static final int VIEW_TOWN_TOTAL_COMMENTS =2;


}
