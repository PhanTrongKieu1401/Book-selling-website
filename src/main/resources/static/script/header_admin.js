/**
 * 
 */
//Quá trình đăng nhập
var btnAccount = document.getElementById("btnAccount");
var linkAccountLogin = document.getElementById("login");
var formAccount = document.querySelector(".form__account-action");
var modal = document.querySelector(".modal");

function displayAction(){
    formAccount.style.display = "block";
    setTimeout(function() {
        formAccount.style.display = "none";
    }, 3000);
}

//chuyển trạng thái sang đã đăng xuất
function logout() {
	console.log("đã vào")
    // Xóa tất cả các cookie
    var cookies = document.cookie.split(";");
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
    }
  
    // Xóa tất cả các session
    sessionStorage.clear();
  
    // Xóa tất cả các local storage
    localStorage.clear();
  
    // Chuyển hướng đến trang đăng nhập
    // window.location.href = "login.html";
}