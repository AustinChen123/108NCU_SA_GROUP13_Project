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
// 更新會員列表表格
function updateitem(data) {
    $("#items").empty();
    var table_html = '';
    $.each(data, function(index, value) {
        table_html += '<div class="item">';
        table_html += '<img class = "item_shortimg" src='+value['image']+ '/>';
        table_html += '<table class="item_shortinfo">';
        table_html += '<tr>' + '</td>商品名稱' + '</td>';
        table_html += '<td>' + value['name'] + '</td>'+'</tr>';
        table_html += '<tr>'+'<td>商品狀態'+ '</td>';
        table_html += '<td>' + value['product_status'] + '</td>'+'</tr>';
        table_html += '<tr>'+'<td>商品價格'+ '</td>';
        table_html += '<td>' + value['price'] + '</td>'+'</tr>';
        table_html += '</table>';
        // 尚未連結id
        table_html += '<a class="check_item" href="#item_info"></a>';
        table_html += '</div>';
        table_html += '<span class = "hidden">'+value['id']+'</span>';
        table_html += '<span class = "hidden">'+value['member_id']+'</span>';
        table_html += '<span class = "hidden">'+value['seller_name']+'</span>';
        table_html += '<span class = "hidden">'+value['product_overview']+'</span>';
    })

    $("#items").append(table_html);
}
$(document).ready(function() {
    getAllproduct();
});