package ncu.im3069.Group13.app;

import org.json.*;
import java.util.Calendar;
import java.time.LocalDateTime;
import java.sql.*;
import java.util.Date;
import java.util.GregorianCalendar;
// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Member
 * Member類別（class）具有會員所需要之屬性與方法，並且儲存與會員相關之商業判斷邏輯<br>
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class Member {
    
    /** id，會員編號 */
    private int id;
    
    /** email，會員電子郵件信箱 */
    private String email;
    
    /** name，會員姓名 */
    private String name;
    
    /** fb_link，臉書連結 */
    private String fb_link;
    
    /** password，會員密碼 */
    private String password;
    
    /** login_datetime，最近登入時間 */
    private Timestamp login_datetime;
    
    /** status，會員之組別 */
    private Boolean status;
    
    
    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MemberHelper mh =  MemberHelper.getHelper();
    
    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立會員資料時，產生一名新的會員
     *
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param name 會員姓名
     */
    public Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        update();
    }

//    /**
//     * 實例化（Instantiates）一個新的（new）Member物件<br>
//     * 採用多載（overload）方法進行，此建構子用於更新會員資料時，產生一名會員同時需要去資料庫檢索原有更新時間分鐘數與會員組別
//     * 
//     * @param id 會員編號
//     * @param email 會員電子信箱
//     * @param password 會員密碼
//     * @param name 會員姓名
//     */
//    public Member(int id, String email, String password, String name) {
//        this.id = id;
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        /** 取回原有資料庫內該名會員之更新時間分鐘數與組別 */
//        getLoginTimesStatus();
//        /** 計算會員之組別 */
//        calcAccName();
//    }
    
    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢會員資料時，將每一筆資料新增為一個會員物件
     *
     * @param id 會員編號
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param name 會員姓名
     * @param login_datetime 更新時間的分鐘數
     * @param status the 會員之組別
     */
    public Member(int id, String email, String name, String fb_link, String password, Boolean status, Timestamp login_datetime) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.fb_link = fb_link;
        this.password = password;
        this.status = status;
        this.login_datetime = login_datetime;
    }
    
    public Member(int id, String email, String name, String fb_link,Boolean status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.fb_link = fb_link;
        this.status=status;
    }
    public Member(int id, String name, String email, String password ,String fb_link ,Boolean status) {
        this.id = id;
        this.name=name;
        this.email = email;
        this.password = password;
        this.fb_link = fb_link;
        this.status=status;
    }
    public Member(String email, String password, String name, String fb_link) {
        this.email = email;
        this.name=name;
        this.password = password;
        this.fb_link = fb_link;
        
    }
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    
    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public int getID() {
        return this.id;
    }

    /**
     * 取得會員之電子郵件信箱
     *
     * @return the email 回傳會員電子郵件信箱
     */
    public String getEmail() {
        return this.email;
    }
    
    /**
     * 取得會員之姓名
     *
     * @return the name 回傳會員姓名
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * 取得會員之臉書連結
     *
     * @return the fb_link 回傳會員臉書連結
     */
    public String getFb_link() {
        return this.fb_link;
    }
    

    /**
     * 取得會員之密碼
     *
     * @return the password 回傳會員密碼
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * 取得最近登入時間
     *
     * @return the login times 回傳最近登入時間
     */
    public Timestamp getLogin_datetime() {
        return this.login_datetime;
    }
    
    /**
     * 取得會員資料之會員審核狀態
     *
     * @return the status 回傳會員審核狀態
     */
    public Boolean getStatus() {
        return this.status;
    }
    
    /**
     * 更新會員資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        
        /** 檢查該名會員是否已經在資料庫 */
        if(this.id != 0) {
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = mh.update(this);
        }
        
        return data;
    }
    
    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("name", getName());
        jso.put("email", getEmail());
        jso.put("fb_link",getFb_link());
        jso.put("password", getPassword());
        jso.put("login_datetime", getLogin_datetime());
        jso.put("status", getStatus());
        
        return jso;
    }
    
      /**
       * 取得資料庫內之更新資料時間分鐘數與會員組別
       *
      */
    private void getLoginTimesStatus() {
//        /** 透過MemberHelper物件，取得儲存於資料庫的更新時間分鐘數與會員組別 */
//        JSONObject data = mh.getLoginTimesStatus(this);
//        /** 將資料庫所儲存該名會員之相關資料指派至Member物件之屬性 */
//        this.login_datetime = new Timestamp(data.getLong("login_datetime"));
//        this.status = data.getString("status");
      }
//    
//    /**
//     * 計算會員之組別<br>
//     * 若為偶數則為「偶數會員」，若為奇數則為「奇數會員」
//     */

}