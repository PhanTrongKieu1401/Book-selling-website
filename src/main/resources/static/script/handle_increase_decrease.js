/**
 * 
 */
 //xử lí tăng giảm số lượng
document.addEventListener("DOMContentLoaded", function() {
	  var decreaseButton = document.querySelector(".dec");
	  var increaseButton = document.querySelector(".inc");
	  var quantityInput = document.querySelector(".pro-qty input");

	  decreaseButton.addEventListener("click", function() {
		    var currentValue = parseInt(quantityInput.value);
		    if (currentValue > 1) {
			      quantityInput.value = currentValue - 1;
		    }
	  });

	  increaseButton.addEventListener("click", function() {
		    var currentValue = parseInt(quantityInput.value);
		    quantityInput.value = currentValue + 1;
	  });
});


