function getAllproduct() {
    // 發出POST的GET請求取得所有商品
    $.ajax({
            type: "GET",
            url: "api/product.do",
            crossDomain: true,
            cache: false,
            dataType: 'json',
            timeout: 5000,
            success: function (response) {
                if(response.status == 200){
                    updateitem(response.response.data);
                    // updateSQLTable(response.response);
                }
                console.log(response);
            },
            error: function () {
                alert("無法連線到伺服器！");
            }
    });
}
// 更新商品列表
function updateitem(data) {
    $(".items").empty();
    var table_html = '';
    $.each(data, function(index, value) {
        table_html += '<div class="item">';
        table_html += '<img class = "item_shortimg" src='+value['image']+ '/>';
        table_html += '<table class="item_shortinfo">';
        table_html += '<tr>' + '<td>商品名稱' + '</td>';
        table_html += '<td>' + value['name'] + '</td>'+'</tr>';
        table_html += '<tr>'+'<td>商品狀態'+ '</td>';
        table_html += '<td>' + value['product_status'] + '</td>'+'</tr>';
        table_html += '<tr>'+'<td>商品價格'+ '</td>';
        table_html += '<td>' + value['price'] + '</td>'+'</tr>';
        table_html += '</table>';
        // 尚未連結id
        table_html += '<a class="check_item" href="?id='+ value['id']+'#item_info'+'"></a>';
        table_html += '</div>';
    })

    $("#items").append(table_html);
}
function get_item(product_id) {
    var data_object = {
        "product_id": product_id,
    };

    // 將JSON格式轉換成字串
    var data_string = JSON.stringify(data_object);
    $.ajax({
        type: "POST",
        url: "api/product.do",
        crossDomain: true,
        cache: false,
        data = data_string,
        dataType: 'json',
        timeout: 5000,
        success: function (response) {
            if(response.status == 200){
                check_item(response.response.data);
                // updateSQLTable(response.response);
            }
            console.log(response);
        },
        error: function () {
            alert("無法連線到伺服器！");
        }
});

    $("#items").append(table_html);
}
function get_id_from_url() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var id = url.searchParams.get("id");
    return id;
});

    $("#items").append(table_html);
}
function check_item(data) {
    $("#item_info > div").empty();
    var table_html = '';
    $.each(data, function(index, value) {
        table_html += '<img class = "item_img" src='+value['image']+ '/>';
        table_html += '<table">';
        table_html += '<tr> <td class = "left">賣家</td>';
        table_html += '<td class = "right">' + value['seller_name'] + '</td>'+'</tr>';
        table_html += '<tr> <td class = "left">賣家ID</td>';
        table_html += '<td class = "right">' + value['member_id'] + '</td>'+'</tr>';
        table_html += '<tr> <td class = "left">商品名稱</td>';
        table_html += '<td class = "right">' + value['name'] + '</td>'+'</tr>';
        table_html += '<tr> <td class = "left">商品分類</td>';
        table_html += '<td class = "right">' + value['classification'] + '</td>'+'</tr>';
        table_html += '<tr> <td class = "left">商品狀態</td>';
        table_html += '<td class = "right">' + value['product_status'] + '</td>'+'</tr>';
        table_html += '<tr> <td class = "left">商品價格</td>';
        table_html += '<td class = "right">' + value['price'] + '</td>'+'</tr>';
        table_html += '<tr> <td class = "left">商品描述</td>';
        table_html += '<td class = "right">' + value['product_overview'] + '</td>'+'</tr>';
        table_html += '</table>';
        // table_html += '<a class="check_item" href="#item_info?id='+ value['id']+'"></a>';
    })
// <div class="item_box">
//     <img class = "item_img" src="statics/img/item1.jpg" />
//     <table>
//         <tr>
//             <td class="left">商品名稱</td>
//             <td class="right">腳踏車</td>
//         </tr>
//         <tr>
//             <td class="left">商品分類</td>
//             <td class="right">交通工具</td>
//         </tr>
//         <tr>
//             <td class="left">商品狀態</td>
//             <td class="right">二手</td>
//         </tr>
//         <tr>
//             <td class="left">售價</td>
//             <td class="right">1500元</td>
//         </tr>
//         <tr>
//             <td class="left">商品描述</td>
//             <td class="right last">上課代步用，正常使用痕跡</td>
//         </tr>
//     </table>
//     <button class="buy_btn"id="buy_btn">購買商品</button>
// </div>
    $("#items").append(table_html);
}
$(document).ready(function() {
    getAllproduct();
});