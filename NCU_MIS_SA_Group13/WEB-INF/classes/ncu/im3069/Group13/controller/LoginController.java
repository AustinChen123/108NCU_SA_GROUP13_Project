package ncu.im3069.Group13.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.Group13.app.Member;
import ncu.im3069.Group13.app.MemberHelper;
import ncu.im3069.Group13.app.OrderHelper;
import ncu.im3069.Group13.app.ProductHelper;
import ncu.im3069.tools.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class MemberController<br>
 * MemberController類別（class）主要用於處理Member相關之Http請求（Request），繼承HttpServlet
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */
@WebServlet("/api/login.do")
public class LoginController extends HttpServlet {
    
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    /** mh，MemberHelper 之物件與 Product 相關之資料庫方法（Sigleton） */
    private MemberHelper mh =  MemberHelper.getHelper();

    public LoginController() {
        super();
    }
    
    /**
     * 處理Http Method請求POST方法（新增資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        String email = jso.getString("email");
        String password = jso.getString("password");
        
        
        
        /** 建立一個新的會員物件 */
        Member m = new Member(email, password);
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(email.isEmpty() || password.isEmpty()) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        /** 透過MemberHelper物件的checkLogin()檢查該會員是否成功登入 */
        else if (mh.checkLogin(m)) {
            /** 透過MemberHelper物件的getByEmail()方法回傳該email對應之會員資料 */
            JSONObject data = mh.getByEmail(email);
            
            //登入後,更新最新登入時間
            Member m_update_login_time = new Member(email);
            mh.updateLogin_datetime(m_update_login_time);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功登入...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        else {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'登入失敗，請確認帳號密碼！\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
            JsonReader jsr = new JsonReader(request);
            /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
            String id = jsr.getParameter("id");
            
            /** 判斷該字串是否存在，若存在代表要取回個別會員之資料，否則代表要取回全部資料庫內會員之資料 */
            if (id.isEmpty()) {
                /** 透過MemberHelper物件之getAll()方法取回所有會員之資料，回傳之資料為JSONObject物件 */
                JSONObject query = mh.getAll();
                
                /** 新建一個JSONObject用於將回傳之資料進行封裝 */
                JSONObject resp = new JSONObject();
                resp.put("status", "200");
                resp.put("message", "所有會員資料取得成功");
                resp.put("response", query);
        
                /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
                jsr.response(resp, response);
            }
            else {
                /** 透過MemberHelper物件的getByID()方法自資料庫取回該名會員之資料，回傳之資料為JSONObject物件 */
                JSONObject query = mh.getByID(id);
                
                /** 新建一個JSONObject用於將回傳之資料進行封裝 */
                JSONObject resp = new JSONObject();
                resp.put("status", "200");
                resp.put("message", "會員資料取得成功");
                resp.put("response", query);
        
                /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
                jsr.response(resp, response);
            }
        }
   
}