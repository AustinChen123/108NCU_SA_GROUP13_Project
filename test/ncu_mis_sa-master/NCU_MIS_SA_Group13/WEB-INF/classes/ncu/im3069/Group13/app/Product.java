package ncu.im3069.Group13.app;

import org.json.*;

public class Product {

    /** member_id，賣家編號 */
    private int member_id;
    
    /** seller_name，賣家名字 */
    private String seller_name;
    
    /** seller_email，賣家email*/
    private String seller_email;
    
    /** seller_email，賣家fb*/
    private String seller_fb;
    
    /** id，產品編號 */
    private int id;

    /** name，產品名字 */
    private String name;
    
    /** classification，產品分類 */
    private String classification;
    
    /** product_status，產品全新二手狀態 */
    private Boolean product_status;

    /** price，產品價格 */
    private float price;
    
    /** product_overview，產品簡述*/
   	private String product_overview;

    /** image，產品圖片 */
    private String image;

    /** verification_status，產品審核狀態 */
    private Boolean verification_status;
    
    /** on_shelf，產品是否售出或尚在架上 */
    private Boolean on_shelf;
    
    /** ph，ProductHelper之物件與Product相關之資料庫方法（Sigleton） */
    private ProductHelper ph =  ProductHelper.getHelper();
   

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增產品時
     *
     * @param id 產品編號
     */
	public Product(int id) {
		this.id = id;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增產品時
     *
     * @param name 產品名稱
     * @param price 產品價格
     * @param image 產品圖片
     */
	public Product(String name, float price, String image) {
		this.name = name;
		this.price = price;
		this.image = image;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改產品時
     *
     * @param id 產品編號
     * @param name 產品名稱
     * @param price 產品價格
     * @param image 產品圖片
     * @param describe 產品敘述
     */
	public Product(int id, String name, float price, String image, String product_overview) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.product_overview = product_overview;
	}
	public Product(int id,int member_id,String seller_name,String seller_email,String seller_fb, String name,String classificaion, Boolean product_status, float price, String product_overview, String image, Boolean verification_status) {
		this.id = id;
		this.member_id=member_id;
		this.seller_name=seller_name;
		this.seller_email=seller_email;
		this.seller_fb=seller_fb;
		this.name = name;
		this.classification = classificaion;
		this.product_status=product_status;
		this.price = price;
		this.product_overview = product_overview;
		this.image = image;
		this.verification_status=verification_status;
	}
	public Product(int member_id,String seller_name,String seller_email,String seller_fb,String name,String classification, Boolean product_status, float price, String product_overview) {
		this.member_id=member_id;
		this.seller_name=seller_name;
		this.seller_email=seller_email;
		this.seller_fb=seller_fb;
		this.name = name;
		this.classification = classification;
		this.product_status=product_status;
		this.price = price;
		this.product_overview = product_overview;
		
	}
	
	
	
	
	/**
     * 取得賣家編號
     *
     * @return int 回傳賣家編號
     */
	public int getMember_id() {
		return this.member_id;
	}
	
	/**
     * 取得賣家名字
     *
     * @return String 回傳賣家名字
     */
	public String getSeller_name() {
		return this.seller_name;
	}
	
	/**
     * 取得賣家email
     *
     * @return String 回傳賣家email
     */
	public String getSeller_email() {
		return this.seller_email;
	}
	
	/**
     * 取得賣家fb
     *
     * @return String 回傳賣家fb
     */
	public String getSeller_fb() {
		return this.seller_fb;
	}
	
    /**
     * 取得產品編號
     *
     * @return int 回傳產品編號
     */
	public int getID() {
		return this.id;
	}

    /**
     * 取得產品名稱
     *
     * @return String 回傳產品名稱
     */
	public String getName() {
		return this.name;
	}
	
	/**
     * 取得產品分類
     *
     * @return String 回傳產品分類
     */
	public String getClassification() {
		return this.classification;
	}
	
	/**
     * 取得產品全新二手狀態
     *
     * @return String 回傳產品全新二手狀態
     */
	public Boolean getProduct_status() {
		return this.product_status;
	}

    /**
     * 取得產品價格
     *
     * @return double 回傳產品價格
     */
	public float getPrice() {
		return this.price;
	}
	
	/**
     * 取得產品敘述
     *
     * @return String 回傳產品敘述
     */
	public String getProduct_overview() {
		return this.product_overview;
	}

    /**
     * 取得產品圖片
     *
     * @return String 回傳產品圖片
     */
	public String getImage() {
		return this.image;
	}
	
	/**
     * 取得產品審核狀態
     *
     * @return String 回傳產品審核狀態
     */
	public Boolean getVerification_status() {
		return this.verification_status;
	}
	
	/**
     * 取得產品販售狀態
     *
     * @return Boolean 回傳產品販售狀態(為已賣出或販售中)
     */
	public Boolean getOn_shelf() {
		return this.on_shelf;
	}
	
	/**
     * 更新商品審核資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update_verification() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        
        /** 檢查該名商品是否已經在資料庫 */
        if(this.id != 0) {
            /** 透過MemberHelper物件，更新目前之商品資料置資料庫中 */
            data = ph.updateStatus(this);
        }
        
        return data;
    }

    

    /**
     * 取得產品資訊
     *
     * @return JSONObject 回傳產品資訊
     */
	public JSONObject getData() {
        /** 透過JSONObject將該項產品所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("member_id", getMember_id());
        jso.put("seller_name", getSeller_name());
        jso.put("seller_email", getSeller_email());
        jso.put("seller_fb", getSeller_fb());
        jso.put("id", getID());
        jso.put("name", getName());
        jso.put("classification", getClassification());
        jso.put("product_status", getProduct_status());
        jso.put("price", getPrice());
        jso.put("product_overview", getProduct_overview());
        jso.put("image", getImage());
        jso.put("verifacation_status", getVerification_status());
      
        return jso;
    }
}
