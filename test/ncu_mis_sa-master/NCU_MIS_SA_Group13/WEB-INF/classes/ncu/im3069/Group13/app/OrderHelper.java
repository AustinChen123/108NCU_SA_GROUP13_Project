package ncu.im3069.Group13.app;

import java.sql.*;
import java.util.*;

import org.json.*;

import ncu.im3069.Group13.util.DBMgr;

public class OrderHelper {
    
    private static OrderHelper oh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private OrderItemHelper oph =  OrderItemHelper.getHelper();
    
    private OrderHelper() {
    }
    
    public static OrderHelper getHelper() {
        if(oh == null) oh = new OrderHelper();
        
        return oh;
    }
    
    public JSONObject create(Order order) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        long id = -1;
        JSONArray opa = new JSONArray();
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "INSERT INTO `missa`.`orders`(`last_name`, `first_name`, `email`, `address`, `phone`, `create`, `modify`)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?)";
            
            /** 取得所需之參數 */
            String first_name = order.getFirstName();
            String last_name = order.getLastName();
            String email = order.getEmail();
            String address = order.getAddress();
            String phone = order.getPhone();
            Timestamp create = order.getCreateTime();
            Timestamp modify = order.getModifyTime();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pres.setString(1, last_name);
            pres.setString(2, first_name);
            pres.setString(3, email);
            pres.setString(4, address);
            pres.setString(5, phone);
            pres.setTimestamp(6, create);
            pres.setTimestamp(7, modify);
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            pres.executeUpdate();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            ResultSet rs = pres.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getLong(1);
                ArrayList<OrderItem> opd = order.getOrderProduct();
                opa = oph.createByList(id, opd);
            }
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("order_id", id);
        response.put("order_product_id", opa);

        return response;
    }
    
    public JSONObject getAll() {
        Order o = null;
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_project`.`orders`";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int id = rs.getInt("id");
                int buyer_id = rs.getInt("buyer_id");
                String buyer_name = rs.getString("buyer_name");
                String buyer_email = rs.getString("buyer_email");
                int product_id = rs.getInt("product_id");
                String product_name = rs.getString("product_name");
                String seller_name = rs.getString("seller_name");
                String seller_email = rs.getString("seller_email");
                String seller_fb = rs.getString("seller_fb");
                float total=rs.getFloat("total");
                Timestamp create_datetime = rs.getTimestamp("create_datetime");
                boolean status=rs.getBoolean("status");
                boolean deleted=rs.getBoolean("deleted");
                
                /** 將每一筆商品資料產生一名新Product物件 */
                o = new Order(id, buyer_id, buyer_name, buyer_email, product_id, product_name, seller_name, seller_email,seller_fb,total,create_datetime,status,deleted);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(o.getOrderData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    
    public JSONObject getById(String order_id) {
        Order o = null;
        /** 記錄實際執行之SQL指令 */
        JSONArray jsa = new JSONArray();
        /** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_project`.`orders` WHERE `orders`.`id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, order_id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int id = rs.getInt("id");
                int buyer_id = rs.getInt("buyer_id");
                String buyer_name = rs.getString("buyer_name");
                String buyer_email = rs.getString("buyer_email");
                int product_id = rs.getInt("product_id");
                String product_name = rs.getString("product_name");
                String seller_name = rs.getString("seller_name");
                String seller_email = rs.getString("seller_email");
                String seller_fb = rs.getString("seller_fb");
                float total=rs.getFloat("total");
                Timestamp create_datetime = rs.getTimestamp("create_datetime");
                boolean status=rs.getBoolean("status");
                boolean deleted=rs.getBoolean("deleted");
                
                /** 將每一筆商品資料產生一名新Product物件 */
                o = new Order(id, buyer_id, buyer_name, buyer_email, product_id, product_name, seller_name, seller_email,seller_fb,total,create_datetime,status,deleted);
                /** 取出該名訂單之資料並封裝至 JSONsonArray 內 */
                jsa.put(o.getOrderData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
}
