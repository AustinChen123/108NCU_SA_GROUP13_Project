package ncu.im3069.Group13.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.Group13.app.Member;
import ncu.im3069.Group13.app.Order;
import ncu.im3069.Group13.app.OrderHelper;
import ncu.im3069.Group13.app.Product;
import ncu.im3069.Group13.app.ProductHelper;
import ncu.im3069.tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/order.do")
public class OrderController extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    /** ph，ProductHelper 之物件與 Product 相關之資料庫方法（Sigleton） */
    private ProductHelper ph =  ProductHelper.getHelper();

    /** oh，OrderHelper 之物件與 order 相關之資料庫方法（Sigleton） */
	private OrderHelper oh =  OrderHelper.getHelper();

    public OrderController() {
        super();
    }

    /**
     * 處理 Http Method 請求 GET 方法（新增資料）
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);

        /** 取出經解析到 JsonReader 之 Request 參數 */
        String id = jsr.getParameter("id");
        String product_id=jsr.getParameter("product_id_in");
        String seller_email=jsr.getParameter("seller_email");
        String buyer_id=jsr.getParameter("buyer_id");

        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();

        /** 判斷該字串是否存在，若存在代表要取回個別訂單之資料，否則代表要取回全部資料庫內訂單之資料 */
        if (!id.isEmpty()) {
          /** 透過 orderHelper 物件的 getByID() 方法自資料庫取回該筆訂單之資料，回傳之資料為 JSONObject 物件 */
          JSONObject query = oh.getById(id);
          resp.put("status", "200");
          resp.put("message", "單筆訂單資料取得成功");
          resp.put("response", query);
        }
        else if(!product_id.isEmpty()) {
        	/** 透過 orderHelper 物件的 getByProduct_ID() 方法自資料庫取回該筆訂單之資料，回傳之資料為 JSONObject 物件 */
            JSONObject query = oh.getByProduct_Id(product_id);
            resp.put("status", "200");
            resp.put("message", "最新訂單資料取得成功");
            resp.put("response", query);
        }
        else if(!seller_email.isEmpty()) {
        	/** 透過 orderHelper 物件的 getByProduct_ID() 方法自資料庫取回該筆訂單之資料，回傳之資料為 JSONObject 物件 */
            JSONObject query = oh.getBySeller_email(seller_email);
            resp.put("status", "200");
            resp.put("message", "賣家訂單資料取得成功");
            resp.put("response", query);
        }
        else if(!buyer_id.isEmpty()) {
        	/** 透過 orderHelper 物件的 getByProduct_ID() 方法自資料庫取回該筆訂單之資料，回傳之資料為 JSONObject 物件 */
            JSONObject query = oh.getByBuyer_Id(buyer_id);
            resp.put("status", "200");
            resp.put("message", "買家訂單資料取得成功");
            resp.put("response", query);
        }
        else {
          /** 透過 orderHelper 物件之 getAll() 方法取回所有訂單之資料，回傳之資料為 JSONObject 物件 */
          JSONObject query = oh.getAll();
          resp.put("status", "200");
          resp.put("message", "所有訂單資料取得成功");
          resp.put("response", query);
        }

        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
	}

    /**
     * 處理 Http Method 請求 POST 方法（新增資料）
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        /** 取出經解析到 JSONObject 之 Request 參數 */
        int buyer_id=jso.getInt("buyer_id");
        String buyer_name = jso.getString("buyer_name");
        String buyer_email = jso.getString("buyer_email");
        int product_id=jso.getInt("product_id");
        String product_name = jso.getString("product_name");
        String seller_name = jso.getString("seller_name");
        String seller_email = jso.getString("seller_email");
        String seller_fb = jso.getString("seller_fb");
        float total = jso.getFloat("total");

        /** 建立一個新的訂單物件 */
        Order od = new Order(buyer_id, buyer_name, buyer_email,product_id, product_name,seller_name,seller_email,seller_fb,total);

        /** 透過 orderHelper 物件的 create() 方法新建一筆訂單至資料庫 */
        JSONObject data = oh.create(od);
        //創建新訂單外，同時讓商品下架
        boolean on_shelf=true;
        Product p = new Product(product_id,on_shelf);
        ph.updateOn_Shelf(p);

        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 創建新訂單...");
        resp.put("response", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
	}
	public void doPut(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        
	        /** 取出經解析到JSONObject之Request參數 */
	        int id = jso.getInt("id");
	        
	       
	        /** 透過傳入之參數，新建一個以這些參數之會員Order物件 */
	        Order o = new Order(id);
	        
	        /** 透過Order物件的updateStatus()方法至資料庫更新該交易完成狀態，回傳之資料為JSONObject物件 */
	        JSONObject data = oh.updateStatus(o);
	        
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "成功! 完成交易...");
	        resp.put("response", data);
	        
	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	       
	    }
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
            JsonReader jsr = new JsonReader(request);
            JSONObject jso = jsr.getObject();
            
            /** 取出經解析到JSONObject之Request參數 */
            int deleted_id=jso.getInt("deleted_id"); 
            Order o = new Order(deleted_id);
            //刪除新訂單外，同時讓商品重新上架
            boolean on_shelf=false;
            int product_id=jso.getInt("product_re_id"); 
            Product p = new Product(product_id,on_shelf);
            ph.updateOn_Shelf(p);
          
	       
	        JSONObject data = oh.Deleted(o);
	        	
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
		    JSONObject resp = new JSONObject();
		    resp.put("status", "200");
		    resp.put("message", "成功! 刪除此筆交易...");
		    resp.put("response", data);
		        
		    /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
		    jsr.response(resp, response);
	        
        }

}
