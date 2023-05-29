/**
 * 
 */
//swap login-register
function callLogin(){
    window.location.href = "/bookstore/login";
}

//xác nhận đăng ký
function confirmRegister(){
	if(confirm("Bạn có muốn đăng ký tài khoản không?")){
		return true;
	}
	else{
		return false;
	}
}

//thực hiện register
document.getElementById('form_register').addEventListener("submit", register);
function register(){
	var usernameInput = document.getElementById("register_username");
    var passwordInput = document.getElementById("register_password");
	
	var username = usernameInput.value().trim();
	var password = passwordInput.value().trim();
    const customer = {
        username: username,
        password: password,
    }
    $.ajax({
        url: '/bookstore/customer/register',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(customer),
        success: function (response) {
            if (response.status === "success") {
	            confirm("Đăng kí tài khoản thành công!");
	            window.location.href = "/bookstore/login";
            } else{
				if(response.status === "failure"){
                	alert("Đăng kí tài khoản không thành công!");
                	location.reload();
                }
                else{
					alert("Tên đăng nhập đã tồn tại!");
					//location.reload();
				}
            } 
        },
        error: function () {
            alert("Lỗi xảy ra!");
        }
    });
}