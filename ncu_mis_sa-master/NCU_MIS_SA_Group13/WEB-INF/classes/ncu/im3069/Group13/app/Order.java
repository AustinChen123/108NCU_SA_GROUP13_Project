package ncu.im3069.Group13.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import org.json.*;

public class Order {

    /** id，訂單編號 */
    private int id;
    
    /** buyer_id，買家編號 */
    private int buyer_id;

    /** buyer_name，買家姓名 */
    private String buyer_name;
    
    /** buyer_email，買家email */
    private String buyer_email;
    
    /** product_id，商品編號 */
    private int product_id;

    /** last_name，商品名稱 */
    private String product_name;
    
    /** seller_name，賣家姓名 */
    private String seller_name;
    
    /** seller_email，賣家email */
    private String seller_email;
    
    /** seller_fb，賣家fb */
    private String seller_fb;
    
    /** total，訂單總金額 */
    private float total;
    
    /** create，訂單創建時間 */
    private Timestamp create_datetime;
    
    /** status，訂單是否完成 */
    private boolean status;
    
    /** deleted，訂單是否刪除 */
    private boolean deleted;


    /** oph，OrderItemHelper 之物件與 Order 相關之資料庫方法（Sigleton） */
    private OrderItemHelper oph = OrderItemHelper.getHelper();


    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改訂單資料時，新改資料庫已存在的訂單
     *
     * @param first_name 會員名
     * @param last_name 會員姓
     * @param email 會員電子信箱
     * @param address 會員地址
     * @param phone 會員姓名
     * @param create 訂單創建時間
     * @param modify 訂單修改時間
     */
    public Order(int id, int buyer_id, String buyer_name,  String buyer_email, int product_id,  String product_name,  String seller_name,  String seller_email, String seller_fb, float total, Timestamp create_datetime,boolean status, boolean deleted) {
        this.id = id;
        this.buyer_id=buyer_id;
        this.buyer_name=buyer_name;
        this.buyer_email=buyer_email;
        this.product_id=product_id;
        this.product_name=product_name;
        this.seller_email=seller_email;
        this.seller_name=seller_name;
        this.seller_fb=seller_fb;
        this.total=total;
        this.create_datetime=create_datetime;
        this.status=status;
        this.deleted=deleted;
        getOrderData();
    }

    /**
     * 新增一個訂單產品及其數量
     
    public void addOrderProduct(Product pd, int quantity) {
        this.list.add(new OrderItem(pd, quantity));
    }

    /**
     * 新增一個訂單產品
     
    public void addOrderProduct(OrderItem op) {
        this.list.add(op);
    }

    /**
     * 設定訂單編號
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 取得訂單編號
     *
     * @return int 回傳訂單編號
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * 取得買家id
     *
     * @return int 回傳買家id
     */
    public int getBuyer_Id() {
        return this.id;
    }

    /**
     * 取得買家名字
     *
     * @return String 回傳買家名字
     */
    public String getBuyer_Name() {
        return this.buyer_name;
    }

    /**
     * 取得買家信箱
     *
     * @return String 回傳買家信箱
     */
    public String getBuyer_Email() {
        return this.buyer_email;
    }
    
    /**
     * 取得商品id
     *
     * @return int 回傳商品id
     */
    public int getProduct_Id() {
        return this.product_id;
    }
    
    /**
     * 取得商品名稱
     *
     * @return String 回傳商品名稱
     */
    public String getProduct_Name() {
        return this.product_name;
    }
    
    /**
     * 取得賣家名字
     *
     * @return String 回傳賣家名字
     */
    public String getSeller_Name() {
        return this.seller_name;
    }
    
    /**
     * 取得賣家email
     *
     * @return String 回傳賣家email
     */
    public String getSeller_Email() {
        return this.seller_email;
    }
    
    /**
     * 取得賣家fb
     *
     * @return String 回傳賣家fb
     */
    public String getSeller_Fb() {
        return this.seller_fb;
    }
    
    /**
     * 取得訂單總金額
     *
     * @return String 回傳訂單總金額
     */
    public float getTotal() {
        return this.total;
    }

    /**
     * 取得訂單創建時間
     *
     * @return Timestamp 回傳訂單創建時間
     */
    public Timestamp getCreate_DateTime() {
        return this.create_datetime;
    }

    /**
     * 取得訂單完成或未完成狀態
     *
     * @return boolean 回傳訂單完成或未完成狀態
     */
    public boolean getStatus() {
        return this.status;
    }
    
    /**
     * 取得訂單是否被刪除的狀態
     *
     * @return boolean 回傳訂單是否被刪除的狀態
     */
    public boolean getDeleted() {
        return this.deleted;
    }

    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
    
    public ArrayList<OrderItem> getOrderProduct() {
        return this.list;
    }**/

    /**
     * 從 DB 中取得訂單產品
     
    private void getOrderProductFromDB() {
        ArrayList<OrderItem> data = oph.getOrderProductByOrderId(this.id);
        this.list = data;
    } 
    */

    /**
     * 取得訂單資料
     *
     * @return JSONObject 取得訂單資料
     */
    public JSONObject getOrderData() {
        JSONObject jso = new JSONObject();
        jso.put("id", getId());
        jso.put("buyer_id", getBuyer_Id());
        jso.put("buyer_name", getBuyer_Name());
        jso.put("buyer_email", getBuyer_Email());
        jso.put("product_id", getProduct_Id());
        jso.put("product_name", getProduct_Name());
        jso.put("seller_name", getSeller_Name());
        jso.put("seller_email", getSeller_Email());
        jso.put("seller_fb", getSeller_Fb());
        jso.put("total", getTotal());
        jso.put("create_datetime", getCreate_DateTime());
        jso.put("status", getStatus());
        jso.put("deleted", getDeleted());

        return jso;
    }

    /**
     * 取得訂單產品資料
     *
     * @return JSONArray 取得訂單產品資料
     
    public JSONArray getOrderProductData() {
        JSONArray result = new JSONArray();

        for(int i=0 ; i < this.list.size() ; i++) {
            result.put(this.list.get(i).getData());
        }

        return result;
    }

    /**
     * 取得訂單所有資訊
     *
     * @return JSONObject 取得訂單所有資訊
     
    public JSONObject getOrderAllInfo() {
        JSONObject jso = new JSONObject();
        jso.put("order_info", getOrderData());
        //jso.put("product_info", getOrderProductData());

        return jso;
    }

    /**
     * 設定訂單產品編號
     
    public void setOrderProductId(JSONArray data) {
        for(int i=0 ; i < this.list.size() ; i++) {
            this.list.get(i).setId((int) data.getLong(i));
        }
    } */

}
