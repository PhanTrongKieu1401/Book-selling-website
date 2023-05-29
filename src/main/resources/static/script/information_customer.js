/**
 * 
 */
var btnEdit = document.getElementById("edit");
var btnSave = document.getElementById("save");
const inputs = document.querySelectorAll('input');
var textarea = document.querySelector('textarea');
//thay đổi edit - > save và cho phép nhập
function changeBtn(){
	btnEdit.style.display = 'none';
	btnSave.style.display = 'block';
	allowInput();
}

//cho phép nhập
function allowInput(){
	inputs.forEach(input =>{
		input.removeAttribute('disabled');
	})	
}

//xác nhận
function confirmAdd() {
	if (confirm("Bạn có muốn lưu thông tin không?")) {
        return true;
    } else {
        return false;
    }
}