package com.slogup.sgcore;

/**
 * Created by sngjoong on 16. 8. 22..
 */
public class CoreAPIContants {

    public static String RootUrl = "http://52.198.33.208:8080";
    public static String RootUrlPostFix = "/api/";

    public class SessionParams {

        public class POST {

            public static final String LOGIN_TYPE = "type";
            public static final String USER_ID = "uid";
            public static final String PASSWORD = "secret";
            public static final String PLAT_FORM = "platform";
            public static final String DEVICE = "device";
            public static final String APP_VERSION = "version";
            public static final String PUSH_TOKEN = "token";
        }
    }

    public class Session {

        public static final String URL = "accounts/session";

        public class Request {

        }

    }

    public class SocialSesstion {

        public static final String URL = "accounts/social-session";

        public class Request {

            public static final String PROVIDER = "provider";
            public static final String PID = "pid";
            public static final String ACCESS_TOKEN = "accessToken";
            public static final String PLAT_FORM = "platform";
            public static final String DEVICE = "device";
            public static final String BROWSER = "browser";
            public static final String VERSION = "version";
            public static final String TOKEN = "token";
        }

        public class Response {

        }
    }

    public class User {

        public static final String URL = "accounts/users";


        public static final String SIGNUP_TYPE = "type";
        public static final String PROVIDER = "provider";
        public static final String USER_ID = "uid";
        public static final String PASSWORD = "secret";
        public static final String NICK = "nick";
        public static final String AID = "aid";
        public static final String APASSWORD = "apass";
        public static final String NAME = "name";
        public static final String GENDER = "gender";
        public static final String BIRTH_Y = "birthYear";
        public static final String BIRTH_M = "birthMonth";
        public static final String BIRTH_D = "birthDay";
        public static final String COUNTRY = "country";
        public static final String LANGUAGE = "language";
        public static final String AGREED_EMAIL = "agreedEmail";
        public static final String AGREED_PHONE_NUM = "agreedPhoneNum";
        public static final String PLAT_FORM = "platform";
        public static final String DEVICE = "device";
        public static final String VERSION = "version";
        public static final String TOKEN = "token";
        public static final String EMAIL = "email";
        public static final String PHONE_NUM = "phoneNum";
        public static final String ROLE = "role";
        public static final String IS_VERIFIED_EMAIL = "isVerifiedEmail";
        public static final String IS_REVIEWED = "isReviewed";
        public static final String AGREED_TERMS_AT = "agreedTermsAt";
        public static final String PROFILE_ID = "profileId";
        public static final String CREATED_AT = "createdAt";
        public static final String UPDATED_AT = "updatedAt";
        public static final String DELETED_AT = "deletedAt";
        public static final String PASS_UPDATED_AT = "passUpdatedAt";
        public static final String ID = "id";



    }

    public class Meta {


    }

    public class Unique {

        public static final String URL = "accounts/unique";

        public class GET {

            public class Key {

                public static final String KEY = "key";
                public static final String VALUE = "value";
            }

            public class Value {

                public static final String EMAIL = "email";
                public static final String NICK = "nick";
                public static final String AID = "aid";
                public static final String PHONE_NUM = "phoneNum";
            }
        }
    }


    public class SenderPhone {

        public static final String URL = "accounts/sender-phone";

        public class POST {

            public class Key {

                public static final String PHONE_NUM = "phoneNum";
                public static final String TYPE = "type";

            }

            public class Value {

                public static final String PHONE_SIGNUP = "phoneSignup";
                public static final String PHONE_FIND_PASS = "phoneFindPass";
                public static final String PHONE_FIND_ID = "phoneFindId";
                public static final String PHONE_ADDING = "phoneAdding";
                public static final String PHONE_LOGIN = "phoneLogin";
                public static final String PHONE_CHANGE = "phoneChange";
            }
        }
    }

    public class AuthPhone {

        public static final String URL = "accounts/auth-phone";

        public class POST {

            public class Key {

                public static final String TYPE = "type";
                public static final String TOKEN = "token";
            }

            public class Value {

                public static final String PHONE_ADDING = "phoneAdding";
                public static final String PHONE_CHANGE = "phoneChange";
                public static final String PHONE_FIND_ID = "phoneFindId";
                public static final String PHONE_FIND_PASS = "phoneFindPass";

            }
        }
    }


}
