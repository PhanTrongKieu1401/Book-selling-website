/**
 * 
 */
 //validate
function Validator(options){
	function getParent(element, selector){
		while(element.parentElement){
			if(element.parentElement.matches(selector)){
				return element.parentElement;
			}
			element = element.parentElement;
		}
	}

	var selectorRules = {};

	//thực hiện validate
	function validate(inputElement, rule){
        var errorElement = inputElement.parentElement.querySelector(options.errorSelector);
		var errorMessage;

		//lấy qua các rule của selector
		var rules = selectorRules[rule.selector];
		//lặp qua từng rule
		for (var i = 0; i< rules.length; i++){
			errorMessage = rules[i](inputElement.value);
			if(errorMessage) break;
		}

		if (errorMessage) {
			errorElement.innerText = errorMessage;
			inputElement.classList.add('invalid');
			errorElement.classList.add('invalid-text');
		} else {
			errorElement.innerText = '';
			inputElement.classList.remove('invalid');
			inputElement.classList.remove('invalid-text');
		}
		return !errorMessage;
	}
	
	//lay element của form
	var formElement = document.querySelector(options.form);
	if(formElement){
		//xử lí onsubmit
		formElement.onsubmit = function(e){
			e.preventDefault();
			var isInvalid = true;
			options.rules.forEach(function(rule){
				var inputElement = formElement.querySelector(rule.selector);
				console.log(inputElement);
				var isValid = validate(inputElement, rule);
				console.log(isInvalid);
				if(!isValid){
					isInvalid = false;
				}
			});

			if(isInvalid){
				//submit với js
				if(typeof options.onSubmit === 'function'){
					var enableInputs = formElement.querySelectorAll('.required'); 
					var formValues = Array.from(enableInputs).reduce(function(values, input){
						return (values[input.name] = input.value) && values;
					}, {});
					options.onSubmit(formValues);
				}
				//submit mặc định
				else{
					formElement.submit();
				}
			}
		}

		//lặp qua mỗi rule và xử lí
		options.rules.forEach(function(rule){
			//lưu lại các rule  cho mỗi input
			if(Array.isArray(selectorRules[rule.selector])){
				selectorRules[rule.selector].push(rule.test);
			}
			else{
				selectorRules[rule.selector] = [rule.test];
			}
			
			//di tu form-detail-book chon selector tu phan tu rule
			var inputElements = formElement.querySelectorAll(rule.selector);
			Array.from(inputElements).forEach( function(inputElement){
				//xử lí trường hợp blur ra ngoài
				inputElement.onblur = function(){
					validate(inputElement, rule);
				}
				// inputElement.parentElement.matchs()
				//xử lí mỗi khi người dùng nhập vào input
				inputElement.oninput = function(){
					var errorElement = inputElement.parentElement.querySelector(options.errorSelector);
					errorElement.innerText ='';
					inputElement.parentElement.classList.remove('.invalid');
				}
			});
		});
	}
}

Validator.isRequired = function(selector, message){
	return {
		selector: selector,
		test: function(value) {
			return value.trim() ? undefined: message || "Vui lòng điền thông tin này!";
		}
	}
}
Validator.isEmail = function(selector, message){
	return {
		selector: selector,
		test: function(value) {
			var regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
			return regex.test(value) ? undefined : message || "Trường này phải là email!";
		}
	}
}
Validator.isPhone = function(selector, message){
	return {
		selector: selector,
		test: function(value) {
			var regex = /((09|03|07|08|05)+([0-9]{8})\b)/g;
			return regex.test(value) ? undefined : message || "Số điện thoại không đúng định dạng!";
		}
	}
}
Validator.minLength = function(selector,min, message){
	return {
		selector: selector,
		test: function(value) {
			return value.length >= min ? undefined : message || `Vui lòng nhập tối thiểu ` + min + ` ký tự`;
		}
	}
}
Validator.isConfirmed = function(selector, getConfirmValue, message){
	return {
		selector: selector,
		test: function(value) {
			return value === getConfirmValue() ? undefined : message || "Giá trị nhập vào không chính xác";//nếu có message thì hiện message
		}
	}
}
Validator.isDate = function(selector, message){
	return {
		selector: selector,
		test: function(value) {
			var regex = /^\d{2}-\d{2}-\d{4}$/;
			var date = new Date(value);
			return regex.test(value) || !isNaN(date.getTime()) ? undefined : message || "Vui lòng chọn ngày tháng năm"
			
		}
	}
}
