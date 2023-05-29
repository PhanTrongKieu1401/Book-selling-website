/**
 * 
 */
//cập nhật số lượng
function updateCart(detailBillId) {
	var inputElement = document.getElementById(detailBillId);

	// Kiểm tra xem thẻ input có tồn tại không
	if (inputElement) {
		// Lấy giá trị số lượng hiện tại
		var currentQuantity = parseInt(inputElement.value);

		// Kiểm tra xem người dùng đã nhấp vào nút "+" hay "-" và thực hiện cập nhật số lượng tương ứng
		if (event.target.classList.contains('dec')) {
			// Giảm số lượng đi 1 đơn vị nếu giá trị hiện tại lớn hơn 0
			if (currentQuantity > 1) {
				inputElement.value = currentQuantity - 1;
			}
		} else if (event.target.classList.contains('inc')) {
			// Tăng số lượng lên 1 đơn vị
			inputElement.value = currentQuantity + 1;
		}
	}
	// Gửi yêu cầu cập nhật cart lên server (qua AJAX hoặc giao tiếp với backend) nếu cần thiết
	var xhr = new XMLHttpRequest();
	var url = '/updateQuantity/{index}?quantity=' + inputElement.value;
	var actualUrl = url.replace('{index}', detailBillId);
	xhr.open('POST', actualUrl, true);
	xhr.setRequestHeader('Content-Type', 'application/json');

	// Tạo đối tượng dữ liệu để gửi lên server
	var data = {
		detailBillId: detailBillId,
		quantity: inputElement.value
	};

	// Chuyển đổi dữ liệu thành chuỗi JSON
	var jsonData = JSON.stringify(data);

	xhr.onreadystatechange = function() {
		if (xhr.readyState === XMLHttpRequest.DONE) {
			if (xhr.status === 200) {
				// Xử lý phản hồi từ server (nếu cần)
				var response = JSON.parse(xhr.responseText);
            	//document.getElementById("cartCount").innerText = response.cartCount;
            	var spanTotal = document.querySelectorAll(".total");
            	for(i = 0; i < spanTotal.length; i++){
					spanTotal[i].innerText = response.total;
				}
            	var amountElement = document.getElementById("amount" + detailBillId);
            	amountElement.innerText = response.amount;
			} else {
				// Xử lý lỗi (nếu có)
			}
		}
	};

	// Gửi yêu cầu với dữ liệu JSON
	xhr.send(jsonData);
}

//confirm delete
function confirmDelete(event) {
   event.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết

   if (confirm("Bạn có chắc muốn xóa lựa chọn này?")) {
      // Nếu người dùng xác nhận, chuyển hướng đến liên kết xóa
      window.location.href = event.target.getAttribute("href");
   }
}

//nhắc nhở mua sách 
function validateCartCount(cartCount) {
	if(cartCount <= 0){
		alert("Vui lòng chọn sách để đặt mua!");
	}
    
 }