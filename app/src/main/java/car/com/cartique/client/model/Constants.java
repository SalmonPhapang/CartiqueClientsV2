package car.com.cartique.client.model;

/**
 * Created by Administrator on 2016/04/06.
 */
public class Constants {
    public static final int DEFAULT_TIMEOUT = 30 * 1000;
    public static final int DEFAULT_RETRIES = 3;
    public static final String IP = "tomcat7app-enfinity.rhcloud.com";
    public static final String GET_DETAILS = "http://" + IP + "/TurnUpServer/TimeLine/getFeedItemDetails";
    public static final String GET_TIMELINE = "http://" + IP + "/TurnUpServer/TimeLine/getTimeLine";
    public static final String GET_EVENT_TIMELINE = "http://" + IP + "/TurnUpServer/TimeLine/getEventTimeLine";
    public static final String GET_UPDATE_FEED = "http://" + IP + "/TurnUpServer/TimeLine/getFeedUpdate";
    public static final String GET_TIMELINE_COUNT = "http://" + IP + "/TurnUpServer/TimeLine/getTimeLineCount";
    public static final String GET_ATTENDED_ITEM = "http://" + IP + "/TurnUpServer/TimeLine/getAttendedClub";
    public static final String GET_PROFILE = "http://" + IP + "/TurnUpServer/TimeLine/getProfile";
    public static final String UPDATE_PROFILE_SHOTS = "http://" + IP + "/TurnUpServer/TimeLine/updateProfileShots";
    public static final String LOAD_MORE = "http://" + IP + "/TurnUpServer/TimeLine/loadMoreFeeds";
    public static final String LOAD_MORE_EVENTS = "http://" + IP + "/TurnUpServer/TimeLine/loadMoreEventFeeds";
    public static final String UPDATE_SHOTS = "http://" + IP + "/TurnUpServer/TimeLine/updateShots";
    public static final String UPDATE_PROFILE = "http://" + IP + "/TurnUpServer/TimeLine/updateProfile";
    public static final String UPDATE_PROFILE_PIC = "http://" + IP + "/TurnUpServer/TimeLine/updateProfilePic";
    public static final String LOGIN = "http://" + IP + "/TurnUpServer/login/dologin";
    public static final String REGISTER = "http://" + IP + "/TurnUpServer/register/doregister";
    public static final String UPDATE_USER_SHOTS = "http://" + IP + "/TurnUpServer/TimeLine/updateUserShots";
    public static final String GET_CLUBS = "http://" + IP + "/TurnUpServer/TimeLine/getClubs";
    public static final String GET_EVENTS = "http://" + IP + "/TurnUpServer/TimeLine/getEvents";
    public static final String GET_SEARCH_ITEM = "http://" + IP + "/TurnUpServer/TimeLine/getSearchItem";
    public static final String SEARCH_LOAD_MORE = "http://" + IP + "/TurnUpServer/TimeLine/loadMore";
    public static final String GET_MAP_LOCATION = "http://" + IP + "/TurnUpServer/TimeLine/getMapLocation";
    public static final String GET_Client_Profile = "http://" + IP + "/TurnUpServer/TimeLine/getClientProfile";
    public static final String INSERT_NEW_FEED = "http://" + IP + "/TurnUpServer/TimeLine/insertNewFeed";
}
