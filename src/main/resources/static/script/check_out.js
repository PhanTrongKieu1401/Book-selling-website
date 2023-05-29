/**
 * 
 */
//xác nhận đặt sách
function confirmBook() {
	if (confirm("Bạn có muốn đặt đơn không?")) {
        return true;
    } else {
        return false;
    }
}

//thực hiện
document.getElementById('form__inf').addEventListener("submit", book);
function book(){
	var nameInput = document.getElementById("name");
    var addressInput = document.getElementById("address");
    var phoneInput = document.getElementById("phone");
    var emailInput = document.getElementById("email");
	var noteInput = document.getElementById("note");
	
    var name = nameInput.value.trim();
    var address = addressInput.value.trim();
    var phone = phoneInput.value.trim();
    var email = emailInput.value.trim();
	var note = noteInput.value.trim();

    if (name === '' || address === '' || phone === '' || email === '') {
        alert("Vui lòng điền đầy đủ thông tin!");
        return;
    }
	const bill = {
		name: name,
		address : address,
		phone : phone,
		email : email,
		note : note,
	}
    $.ajax({
        url: '/confirm',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(bill),
        success: function (response) {
            if (response.status === "success") {
	            alert("Chúc mừng bạn đặt đơn thành công!");
	            window.location.href = response.url;
            } else{
				if(response.status === "failure"){
                	alert("Đặt sách không thành công! Vui lòng thử lại!");
                	location.reload();
                }
            } 
        },
        error: function () {
            alert("Lỗi xảy ra!");
        }
    });
}