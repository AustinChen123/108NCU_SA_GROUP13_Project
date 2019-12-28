package ncu.im3069.Group13.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.Group13.app.Product;
import ncu.im3069.Group13.app.ProductHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/product.do")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProductHelper ph =  ProductHelper.getHelper();
       
    public ProductController() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String id = jsr.getParameter("id_list");
        
        JSONObject resp = new JSONObject();
        /** 判斷該字串是否存在，若存在代表要取回個別會員之資料，否則代表要取回全部資料庫內會員之資料 */
        if (!id.isEmpty()) {
          JSONObject query1=ph.getById(id);
          resp.put("status", "200");
          resp.put("message", "所有商品資料取得成功");
          resp.put("response", query1);
        }
        else {
          JSONObject query = ph.getAll();
          resp.put("status", "200");
          resp.put("message", "所有商品資料取得成功");
          resp.put("response", query);
        }
        
        jsr.response(resp, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int member_id = jso.getInt("member_id");
        String seller_name=jso.getString("seller_name");
        String seller_email=jso.getString("seller_email");
        String seller_fb=jso.getString("seller_fb");
        String name = jso.getString("name");
        String classification = jso.getString("classification");
        boolean product_status = jso.getBoolean("product_status");
        float price=jso.getFloat("price");
        //String image = jso.getString("image");
        String product_overview = jso.getString("product_overview");
        
        
        
        /** 建立一個新的會員物件 */
        Product p = new Product(member_id,seller_name,seller_email,seller_fb,name,classification,product_status,price,product_overview);
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(name.isEmpty() || classification.isEmpty()) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        /** 透過MemberHelper物件的checkDuplicate()檢查該會員電子郵件信箱是否有重複 */
        else {
            /** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
            JSONObject data = ph.create(p);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 新增商品...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
	}
	
	
	
	/**
     * 處理Http Method請求PUT方法（更新）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int id = jso.getInt("id");

        /** 透過傳入之參數，新建一個以這些參數之商品Product物件 */
        Product p = new Product(id);
        
        /** 透過Product物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
        JSONObject data = p.update_verification();
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新商品審核狀態...");
        resp.put("response", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }

}
