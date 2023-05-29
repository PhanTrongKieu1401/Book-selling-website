/**
 * 
 */
//xử lí upload image
var ipImage = document.getElementById("ipFile");
var img = document.getElementById("img");
function showImagePreview() {
	var file = ipImage.files[0];
	const reader = new FileReader();
	reader.onload = function(e){
		img.src = e.target.result;
	}
	reader.readAsDataURL(file);
}