$(function(){
    //给type绑定点击事件
    $(".type").click(function () {
        $(".type").removeClass("checked");
        $(this).addClass("checked");
    });
    //给color绑定点击事件
    $(".color").click(function () {
        $(".color").removeClass("checked");
        $(this).addClass("checked");
    });
});

//商品数量++
function increase() {
    var value = $("#quantity").val();
    var stock = $("#stock").text();
    value++;
    if(value > stock){
        value = stock;
    }
    $("#quantity").val(value);
}

//商品数量--
function reduce() {
    var value = $("#quantity").val();
    value--;
    if(value == 0){
        value = 1;
    }
    $("#quantity").val(value);
}

//添加购物车
function addCart(){
    var id = $("#productId").val();
    var price = $("#productPrice").val();
    var quantity = $("#quantity").val();
    window.location.href="/cart/add/"+id+"/"+price+"/"+quantity;
}