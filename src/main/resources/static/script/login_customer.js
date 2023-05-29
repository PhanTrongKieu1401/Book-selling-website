/**
 * 
 */
 function callRegister(){
    window.location.href = "/bookstore/register";
}

//thực hiện login
document.getElementById("form_login").addEventListener("submit", login);
function login(){
    const customer = {
        username: document.getElementById("login_username").value,
        password: document.getElementById("login_password").value,
    }
    $.ajax({
        url: '/customer/login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(customer),
        success: function (response) {
            if (response.status === "success") {
	            //xử lí khi tài khoản tồn tại
	            console.log(customer)
                localStorage.setItem('customer', JSON.stringify(customer)); //lưu customer vào localStorage
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
