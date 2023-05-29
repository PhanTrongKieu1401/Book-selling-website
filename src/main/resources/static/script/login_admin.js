/**
 * 
 */
var username = document.getElementById("floatingInput");
var password = document.getElementById("floatingPassword");

//thực hiện login
document.getElementById("form__login").addEventListener("submit", login);
function login(){
    const admin = {
        username: document.getElementById("floatingInput").value,
        password: document.getElementById("floatingPassword").value,
    }
    $.ajax({
        url: '/admin/login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(admin),
        success: function (response) {
            if (response.status === "success") {
	            //xử lí khi tài khoản tồn tại
                localStorage.setItem('admin', JSON.stringify(admin)); //lưu admin vào localStorage
                window.location.href = response.url;
            } else {
                alert("Tài khoản không tồn tại!");
            }
        },
        error: function () {
            alert("Lỗi xảy ra!");
        }
    });
}