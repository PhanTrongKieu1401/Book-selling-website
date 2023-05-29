/**
 * 
 */
 //xác nhận comment
function confirmComment() {
	if (confirm("Bạn có muốn thêm nhận xét không?")) {
		var commentValue = document.getElementById("comment").value;
        
		if (commentValue.trim() === "") {
			alert("Vui lòng nhập nội dung bình luận");
			return false; 
		}
	
		var starInputs = document.getElementsByName("star");
		var isChecked = false;
	
		for (var i = 0; i < starInputs.length; i++) {
			if (starInputs[i].checked) {
				isChecked = true;
				break;
			}
		}
	
		if (!isChecked) {
			alert("Vui lòng chọn đánh giá");
			return false;
		}
	
		// Nếu không có lỗi, cho phép form được gửi đi
        return true;
    } else {
        return false;
    }
}

//thông báo thành công
function cmtSuccess(){
	alert("Thêm nhận xét thành công!");
}

//hủy comment
function cancleComment() {
	document.getElementById("comment").value = "";
        
	var starInputs = document.getElementsByName("star");
        
	for (var i = 0; i < starInputs.length; i++) {
		starInputs[i].checked = false;
	}
}

