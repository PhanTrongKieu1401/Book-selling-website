/**
 * 
 */
//xác nhận thêm sách vào cart
function confirmAdd() {

	if (confirm("Bạn có muốn thêm sách vào giỏ hàng không?")) {
        return true;
    } else {
		var addButton = document.getElementById('btnAdd');
        addButton.blur();
        return false;
    }
}

//thông báo thành công
function success(){
	alert("Đã thêm vào giỏ hàng thành công!");
}
