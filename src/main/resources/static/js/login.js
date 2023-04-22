// Get form elements
const form = document.getElementById('login-form');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');
const captchaInput = document.getElementById('captcha');
const captchaImage = document.getElementById('captcha-image');
const domain = "localhost:8080";

// var script = document.createElement('script');
// script.src = '/webjars/jquery/3.6.0/jquery.min.js';
// script.type = 'text/javascript';
// document.getElementsByTagName('head')[0].appendChild(script);




$(document).ready(function() {
    $('#login-form').submit(function(event) {
        event.preventDefault(); // 防止表单默认提交行为

        if (emailInput.value.trim() === '') {
            showErrorMessage(emailInput, '请输入邮箱');
        } else if (!isValidEmail(emailInput.value.trim())) {
            showErrorMessage(emailInput, '请输入正确的邮箱');
        } else {
            hideErrorMessage(emailInput);
        }
        if (passwordInput.value.trim() === '') {
            showErrorMessage(passwordInput, '请输入密码');
        } else {
            hideErrorMessage(passwordInput);
        }
        // Check if captcha is not empty
        if (captchaInput.value.trim() === '') {
            showErrorMessage(captchaInput, '请输入验证码');
        } else {
            hideErrorMessage(captchaInput);
        }
        // Submit form if all inputs are valid
        if (isValidEmail(emailInput.value.trim()) && passwordInput.value.trim() !== '' && captchaInput.value.trim() !== '') {
            // form.submit();

            // 通过Ajax发送表单数据
            $.ajax({
                url: 'http://' + domain + form.getAttribute("action"), // 表单提交的URL
                type: 'POST', // 表单提交的方法
                data: $(this).serialize(), // 表单数据
                success: function (response) {
                    //将返回的html直接加载
                    document.write(response);
                    // response.getResponseHeader("REDIRECT");
                    // window.location.href =
                    // 处理表单提交后的响应
                    //获取响应的html中的元素内容
                    console.log(this.url);
                    console.log(response);
                    // 将响应的HTML字符串解析为HTML格式，并将其中的错误信息提取出来
                    var elementContent = $.parseHTML(response);
                    var msg = "";
                    $(elementContent).each(function(index, element) {
                        if (element.id === "errMsg") {
                            msg = element.textContent;
                            return false; // 停止遍历
                        }
                    });
                    // console.log(msg);


                    // var errMsg = $(elementContent).find("#errMsg").text();
                    // console.log(errMsg);

                    // var elementContent = $(response).find("#errMsg").html();  // 从响应中获取id为"errMsg"的div元素的内容
                    // var elementContent = $('#errMsg').text();
                    // console.log(elementContent);
                    showAlert(msg);

                },
                error: function (xhr, status, error) {
                    // 处理错误
                    showAlert("错误" + error.toString());
                }
            });
        }


    });
});



function showAlert(message) {
    // 创建提醒框
    var alertBox = document.createElement('div');
    alertBox.setAttribute('id', 'alert-box');
    alertBox.innerText = message;

    //创建容器
    var alertContainer = document.createElement('div');
    alertContainer.setAttribute("id","alert-container");

    // 将提醒框添加到容器中
    alertContainer.appendChild(alertBox);

    //将容器添加在html
    var Container = document.getElementById('container');
    Container.appendChild(alertContainer);

    // 渐变显示提醒框
    setTimeout(function() {
        alertBox.style.opacity = 1;
    }, 100);

    // 5秒后渐变隐藏提醒框
    setTimeout(function() {
        alertBox.style.opacity = 0;
        setTimeout(function() {
            alertContainer.removeChild(alertBox);
        }, 300);
    }, 1000);

    setTimeout(function() {
        alertBox.style.opacity = 0;
        setTimeout(function() {
            Container.removeChild(alertContainer);
        }, 300);
    }, 1000);
}

// 使用示例
// showAlert('这是一个提醒框！');



emailInput.addEventListener("blur", function() { // 给输入框绑定 blur 事件，当输入框失去焦点时触发
    var email = emailInput.value; // 获取输入框中的值
    var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/; // 定义正则表达式
    if (!emailRegex.test(email)) { // 如果输入框中的值不匹配正则表达式
        showErrorMessage(emailInput,"请输入有效的电子邮件地址！"); // 弹出提示框
    }
    else {
        hideErrorMessage(emailInput);
    }

});


// Add event listeners to form elements
// form.addEventListener('submit', (event) => {
//     // Prevent form from submitting
//     event.preventDefault();
//     // Check if email and password are not empty
//     if (emailInput.value.trim() === '') {
//         showErrorMessage(emailInput, 'Email is required');
//     } else if (!isValidEmail(emailInput.value.trim())) {
//         showErrorMessage(emailInput, 'Email is invalid');
//     } else {
//         hideErrorMessage(emailInput);
//     }
//     if (passwordInput.value.trim() === '') {
//         showErrorMessage(passwordInput, 'Password is required');
//     } else {
//         hideErrorMessage(passwordInput);
//     }
//     // Check if captcha is not empty
//     if (captchaInput.value.trim() === '') {
//         showErrorMessage(captchaInput, 'Captcha is required');
//     } else {
//         hideErrorMessage(captchaInput);
//     }
//     // Submit form if all inputs are valid
//     if (isValidEmail(emailInput.value.trim()) && passwordInput.value.trim() !== '' && captchaInput.value.trim() !== '') {
//         form.submit();
//     }
// });


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
