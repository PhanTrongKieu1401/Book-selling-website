/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	var listItems = document.querySelectorAll('.list-group-item');

	listItems.forEach(function(item) {
		item.addEventListener('click', function() {
			listItems.forEach(function(item) {
				item.classList.remove('active');
			});

			this.classList.add('active');
			// Lưu trạng thái của phần tử đã chọn vào localStorage
			localStorage.setItem('activeItem', this.id);
		});
	});

	// Lưu trạng thái trước khi trang tải lại
	window.addEventListener('beforeunload', function() {
		var activeItemId = document.querySelector('.list-group-item.active').id;
		localStorage.setItem('activeItem', activeItemId);
	});

	// Khôi phục trạng thái đã lưu từ localStorage khi tải lại trang
	var activeItemId = localStorage.getItem('activeItem');
	if (activeItemId) {
		var activeItem = document.getElementById(activeItemId);
		if (activeItem) {
			activeItem.classList.add('active');
		}
	}
});
