package slogup.ssing;

import com.slogup.sgcore.CoreAPIContants;

/**
 * Created by sngjoong on 2016. 11. 27..
 */

public final class SsingAPIMeta extends CoreAPIContants {

    public class Posts {

        public static final String URL = "ssing/posts";

        public class Request {

            public static final String ID = "id";
            public static final String SEARCH_ITEM = "searchItem";
            public static final String TAGS = "tags";
            public static final String GENDER = "gender";
            public static final String LAT = "lat";
            public static final String LNG = "lng";
            public static final String RADIUS = "radius";
            public static final String ORDER_BY = "orderBy";
            public static final String SORT = "sort";
            public static final String LAST = "last";
            public static final String SIZE = "size";
        }

        public class Response {

            public static final String ROWS = "rows";
            public static final String AUTHOR_ID = "authorId";
            public static final String BODY = "body";
            public static final String LOCATION = "location";
            public static final String GENDER = "gender";
            public static final String BIRTH_START = "birthStart";
            public static final String BIRTH_END = "birthEnd";
            public static final String CREATED_AT = "createdAt";
            public static final String UPDATED_AT = "updatedAt";
            public static final String DELETED_AT = "deletedAt";
            public static final String ID = "id";
            public static final String AUTHOR = "author";
            public static final String NICK = "nick";
            public static final String BIRTH = "birth";
            public static final String TOTAL_COUNT = "totalCount";
            public static final String TAG_COUNTS = "tagCounts";
            public static final String POST_ID = "postId";
            public static final String TAG_NAME = "tagName";
            public static final String COUNT = "count";
            public static final String COMMENTS = "comments";
        }


    }
}
