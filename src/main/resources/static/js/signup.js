
// 获取账号输入框元素
// var accountInput = document.querySelector('input[name="account"]');
// var accountTip = document.querySelector('#accountTip');

var nameInput = document.getElementById('name');

var nameTip = document.getElementById('nameTip');

// 监听账号输入框的变化事件
const accountInput = document.getElementById('account');
const accountTip = document.getElementById('accountTip');

accountInput.addEventListener('input', function() {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/community/login/signupacc', true);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                accountTip.textContent = response.message;
                accountTip.classList.toggle('error', !response.success);
                accountTip.classList.toggle('success', response.success);
            }
        }
    };
    xhr.send('account=' + encodeURIComponent(accountInput.value));
});








nameInput.addEventListener('input', function() {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/community/login/signupname', true);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                nameTip.textContent = response.message;
                nameTip.classList.toggle('error', !response.success);
                nameTip.classList.toggle('success', response.success);
            }
        }
    };
    xhr.send('name=' + encodeURIComponent(nameInput.value));
});

// // 获取表单和用于显示响应信息的元素
// var form = document.querySelector('form');
// var responseDiv = document.querySelector('#response');
//
// // 监听表单提交事件
// form.addEventListener('submit', function(event) {
//     event.preventDefault(); // 阻止表单默认提交事件
//
//     // 获取表单中的值
//     var account = document.querySelector('#account').value;
//     var password = document.querySelector('#password').value;
//     var name = document.querySelector('#name').value;
//     var age = document.querySelector('#age').value;
//
//     // 发送Ajax请求到服务器
//     var xhr = new XMLHttpRequest();
//     xhr.open('POST', '/community/login/signup');
//     xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
//     xhr.onreadystatechange = function() {
//         if (xhr.readyState === 4 && xhr.status === 200) {
//             // 获取响应信息
//             var response = JSON.parse(xhr.responseText);
//             var message = response.message;
//
//             // 设置响应信息
//             responseDiv.innerHTML = message;
//         }
//     };
//     xhr.send('account=' + encodeURIComponent(account) + '&password=' + encodeURIComponent(password) + '&name=' + encodeURIComponent(name) + '&age=' + encodeURIComponent(age));
// });


