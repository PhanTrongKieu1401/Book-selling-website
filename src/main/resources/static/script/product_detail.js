/**
 * 
 */
 //biến 
var ipIdPro = document.querySelector('input[id="idPro"]');
var idPro = ipIdPro.value;
var btnEdit = document.getElementById("edit");
var btnSave = document.getElementById("save");
var btnAdd = document.getElementById("add");
const inputs = document.querySelectorAll('input');
var textarea = document.querySelector('textarea');
var select = document.querySelector('select');

//ẩn hiện edit/save/add
if(idPro <= 0){
	btnEdit.style.display = 'none';
	btnSave.style.display = 'none';
	btnAdd.style.display = 'block';
	allowInput();
}
else{
	btnEdit.style.display = 'block';
	btnSave.style.display = 'none';
	btnAdd.style.display = 'none';
}

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
	textarea.removeAttribute('disabled');	
	select.removeAttribute('disabled');
}

//hiển thị select option
var slCategory = document.getElementById("slCategory");
var selectedValue = slCategory.value;
var options = slCategory.options;

for (var i = 0; i < options.length; i++) {
	var option = options[i];
    if (option.value === selectedValue) {
    	option.selected = true;
    	break;
    }
}
function confirmAdd() {
	if (confirm("Bạn có muốn lưu sách không?")) {
        return true;
    } else {
        return false;
    }
}






