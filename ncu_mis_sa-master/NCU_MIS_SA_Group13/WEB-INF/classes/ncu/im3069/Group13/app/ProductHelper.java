package ncu.im3069.Group13.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Calendar;

import org.json.*;

import ncu.im3069.Group13.util.DBMgr;
import ncu.im3069.Group13.app.Product;

public class ProductHelper {
    private ProductHelper() {
        
    }
    
    private static ProductHelper ph;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    public static ProductHelper getHelper() {
        /** Singleton檢查是否已經有ProductHelper物件，若無則new一個，若有則直接回傳 */
        if(ph == null) ph = new ProductHelper();
        
        return ph;
    }
    
    public JSONObject getAll() {
        /** 新建一個 Product 物件之 p變數，用於紀錄每一位查詢回之商品資料 */
    	Product p = null; 
        /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
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
            String sql = "SELECT * FROM `sa_project`.`products` where on_shelf = 0;";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer(rs.next()為一個boolean值)，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int product_id = rs.getInt("id");
                int member_id=rs.getInt("member_id");
                String seller_name=rs.getString("seller_name");
                String seller_email=rs.getString("seller_email");
                String seller_fb=rs.getString("seller_fb");
                String name = rs.getString("name");
                String classificaion=rs.getString("classification");
                Boolean product_status=rs.getBoolean("product_status");
                float price = rs.getFloat("price");
                String product_overview = rs.getString("product_overview");
                String image = rs.getString("image");
                Boolean verification_status=rs.getBoolean("verification_status");
                
                /** 將每一筆商品資料產生一個新Product物件 */
                p = new Product(product_id,member_id,seller_name,seller_email,seller_fb, name,classificaion,product_status, price,product_overview, image,verification_status);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(p.getData());
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
            /**rs=>為結果集；pres=>preparedstatement物件；conn=>connection物件*/
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
    
    public JSONObject deleteByID(int id) {
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
            String sql = "DELETE FROM `sa_project`.`products` WHERE id = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            /** 執行刪除之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
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
        
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
    }

    
    public JSONObject getById(String id) {
        /** 新建一個 Product 物件之 p 變數，用於紀錄每一位查詢回之商品資料 */
        Product p = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        JSONArray jsa = new JSONArray();
        long start_time = System.nanoTime();
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_project`.`products` WHERE `products`.`id` = ?";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            if(rs.next()) {
            	/** 將 ResultSet 之資料取出 */
            	 int product_id = rs.getInt("id");
                 int member_id=rs.getInt("member_id");
                 String seller_name=rs.getString("seller_name");
                 String seller_email=rs.getString("seller_email");
                 String seller_fb=rs.getString("seller_fb");
                 String name = rs.getString("name");
                 String classificaion=rs.getString("classification");
                 Boolean product_status=rs.getBoolean("product_status");
                 float price = rs.getFloat("price");
                 String product_overview = rs.getString("product_overview");
                 String image = rs.getString("image");
                 Boolean verification_status=rs.getBoolean("verification_status");
                 
                 /** 將每一筆商品資料產生一個新Product物件 */
                 p = new Product(product_id,member_id,seller_name,seller_email,seller_fb, name,classificaion,product_status, price,product_overview, image,verification_status);
                 jsa.put(p.getData());           
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
        response.put("row", 1);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    
    public JSONObject getByName(String name) {
        /** 新建一個 Product 物件之 p 變數，用於紀錄每一位查詢回之商品資料 */
        Product p = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        JSONArray jsa = new JSONArray();
        long start_time = System.nanoTime();
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_project`.`products` WHERE `products`.`name` like  ?  and 'on_shelf' = 0";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, '%'+name+'%');
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
            	/** 將 ResultSet 之資料取出 */
                int product_id = rs.getInt("id");
                int member_id=rs.getInt("member_id");
                String seller_name=rs.getString("seller_name");
                String seller_email=rs.getString("seller_email");
                String seller_fb=rs.getString("seller_fb");
                String name_ = rs.getString("name");
                String classificaion=rs.getString("classification");
                Boolean product_status=rs.getBoolean("product_status");
                float price = rs.getFloat("price");
                String product_overview = rs.getString("product_overview");
                String image = rs.getString("image");
                Boolean verification_status=rs.getBoolean("verification_status");
                
                /** 將每一筆商品資料產生一個新Product物件 */
                p = new Product(product_id,member_id,seller_name,seller_email,seller_fb, name_,classificaion,product_status, price,product_overview, image,verification_status);
 
                jsa.put(p.getData());  
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
        response.put("row", 1);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    
    public Product getByClassification(String classification) {
        /** 新建一個 Product 物件之 p 變數，用於紀錄每一位查詢回之商品資料 */
        Product p = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_project`.`products` WHERE `products`.`classification` = ? and on_shelf = 0";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, classification);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
            	/** 將 ResultSet 之資料取出 */
                int product_id = rs.getInt("id");
                String seller_name=rs.getString("seller_name");
                String seller_email=rs.getString("seller_email");
                String seller_fb=rs.getString("seller_fb");
                String name = rs.getString("name");
                String classificaion=rs.getString("classification");
                Boolean product_status=rs.getBoolean("product_status");
                float price = rs.getFloat("price");
                String product_overview = rs.getString("product_overview");
                String image = rs.getString("image");
                Boolean verification_status=rs.getBoolean("verification_status");
                
                /** 將每一筆商品資料產生一個新Product物件 */
                p = new Product(product_id,seller_name,seller_email,seller_fb, name,classificaion,product_status, price,product_overview, image,verification_status);
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

        return p;
    }
    public JSONObject create(Product p) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        //12/27 here
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            /** member_id從cookie取*/
            String sql = 
            		"INSERT INTO `sa_project`.`products`(`member_id`,`seller_name`, `seller_email`,`seller_fb`, `name`, `classification`, `product_status`,`price`,`product_overview`)"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            /** 取得所需之參數 */
            int member_id = p.getMember_id();
            String seller_name=p.getSeller_name();
            String seller_email=p.getSeller_email();
            String seller_fb=p.getSeller_fb();
            String name = p.getName();
            String classificaion=p.getClassification();
            Boolean product_status=p.getProduct_status();
            float price = p.getPrice();
            String product_overview = p.getProduct_overview();
            //String image = p.getImage();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, member_id);
            pres.setString(2, seller_name);
            pres.setString(3, seller_email);
            pres.setString(4, seller_fb);
            pres.setString(5, name);
            pres.setString(6, classificaion);
            pres.setBoolean(7, product_status);
            pres.setFloat(8, price);
            pres.setString(9, product_overview);
            //pres.setString(8, image);
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

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

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("time", duration);
        response.put("row", row);

        return response;
    }
    /**
     * 更新商品資料
     *
     * @param p Product物件
     * @return the JSONObject 回傳SQL指令執行結果與執行之資料
     */
    public JSONObject update(Product p) {
        /** 紀錄回傳之資料 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "Update `sa_project`.`products` SET `name` = ? , `classification`= ? , `` = ? , `product_status` = ?, `price` = ?, `product_overview` = ?`image` = ? WHERE `id` = ?";
            
            /** 取得所需之參數 */
            
            int product_id = p.getInt();
            String name = p.getString();
            String classificaion=p.getString();
            Boolean product_status=p.getBoolean();
            float price = p.getFloat();
            String product_overview = p.getString();
            String image = p.getString();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, name);
            pres.setString(2, classificaion);
            pres.setBoolean(3, product_status);
            pres.setFloat(4, price);
            pres.setString(5, product_overview);
            pres.setString(6, image);
            pres.setInt(7, product_id);
            /** 執行更新之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

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
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    /**
     * 更新商品之審核狀態
     *
     * @param p Product物件
     * @param verification_status 商品審核狀態（Boolean）
     */
    public JSONObject updateStatus(Product p) {      
    	/** 紀錄回傳之資料 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            /** 設成1，商品已由管理員審核 */
            String sql = "Update `sa_project`.`products` SET `verification_status` = 1 WHERE `id` = ?";
            /** 取得會員編號 */
            int id = p.getID();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            /** 執行更新之SQL指令 */
            pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
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
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    /**
     * 更新商品之上架狀態
     *
     * @param p Product物件
     * @param on_shelf 商品上架狀態（Boolean）
     */
    public void updateOn_Shelf(Product p) {      
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            /** 設成1，商品已下架 */
            String sql = "Update `sa_project`.`products` SET `on_shelf` = 1 WHERE `id` = ?";
            /** 取得會員編號 */
            int id = p.getID();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            /** 執行更新之SQL指令 */
            pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
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
    }
}
