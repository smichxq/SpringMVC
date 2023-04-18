// Get form elements
const form = document.getElementById('login-form');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');
const captchaInput = document.getElementById('captcha');
const captchaImage = document.getElementById('captcha-image');


emailInput.addEventListener("blur", function() { // 给输入框绑定 blur 事件，当输入框失去焦点时触发
    var email = emailInput.value; // 获取输入框中的值
    var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/; // 定义正则表达式
    if (!emailRegex.test(email)) { // 如果输入框中的值不匹配正则表达式
        alert("请输入有效的电子邮件地址！"); // 弹出提示框
    }
});


// Add event listeners to form elements
form.addEventListener('submit', (event) => {
    // Prevent form from submitting
    event.preventDefault();
    // Check if email and password are not empty
    if (emailInput.value.trim() === '') {
        showErrorMessage(emailInput, 'Email is required');
    } else if (!isValidEmail(emailInput.value.trim())) {
        showErrorMessage(emailInput, 'Email is invalid');
    } else {
        hideErrorMessage(emailInput);
    }
    if (passwordInput.value.trim() === '') {
        showErrorMessage(passwordInput, 'Password is required');
    } else {
        hideErrorMessage(passwordInput);
    }
    // Check if captcha is not empty
    if (captchaInput.value.trim() === '') {
        showErrorMessage(captchaInput, 'Captcha is required');
    } else {
        hideErrorMessage(captchaInput);
    }
    // Submit form if all inputs are valid
    if (isValidEmail(emailInput.value.trim()) && passwordInput.value.trim() !== '' && captchaInput.value.trim() !== '') {
        form.submit();
    }
});


captchaImage.addEventListener('click', () => {
    // Generate a new captcha image
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:8080/community/login/kaptcha', true);
    xhr.responseType = 'blob';
    xhr.onload = function() {
        if (this.status === 200) {
            const blob = new Blob([this.response], { type: 'image/png' });
            captchaImage.src = URL.createObjectURL(blob);
        }
    };
    xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
    xhr.send();
});



// Helper functions
function showErrorMessage(input, message) {
    const error = input.nextElementSibling;
    error.innerText = message;
    error.style.display = 'block';
    input.classList.add('is-invalid');
}

function hideErrorMessage(input) {
    const error = input.nextElementSibling;
    error.innerText = '';
    error.style.display = 'none';
    input.classList.remove('is-invalid');
}

function isValidEmail(email) {
    const regex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;
    return regex.test(email);
}
