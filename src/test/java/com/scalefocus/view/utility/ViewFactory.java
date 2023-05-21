package com.scalefocus.view.utility;


import com.scalefocus.view.view.model.*;

import static com.scalefocus.view.utility.ViewConstants.*;

public final class ViewFactory {

    private ViewFactory() {
        throw new IllegalStateException();
    }

    public static ViewBasic getDefaultViewBasic(){
        return new ViewBasic(VIEW_BASIC_TOWN,VIEW_BASIC_TYPE,VIEW_BASIC_COMPANY,VIEW_BASIC_AVG_RATING);
    }

    public static ViewRate getDefaultViewRate(){
        return new ViewRate(VIEW_RATE_TYPE,VIEW_RATE_COMPANY,VIEW_RATE_RATE);
    }

    public static ViewCount getDefaultViewCount(){
        return new ViewCount(1,1,1,1,1);
    }


    public static ViewTown getDefaultViewTown(){
        return new ViewTown(VIEW_TOWN_TYPE,VIEW_TOWN_COMPANY,VIEW_TOWN_AVG_RATING,VIEW_TOWN_TOTAL_COMMENTS);
    }

    public static ViewComments getDefaultViewComment(){
        return new ViewComments(VIEW_COMMENT_COMPANY,VIEW_COMMENT_USER,VIEW_COMMENT_CONTENT,VIEW_COMMENT_RATING,VIEW_COMMENT_DATE);
    }

    public static ViewType getDefaultViewType(){
        return new ViewType(VIEW_TYPE_TOWN,VIEW_TYPE_NAME,VIEW_TYPE_RATING);
    }





}
