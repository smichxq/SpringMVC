var currentPage = parseInt(window.location.search.replace(/^.*\?p=(\d+).*$/, '$1'));

var buttonNext = document.getElementById('myButtonNext');
var buttonBefore = document.getElementById('myButtonBefore');

if (buttonNext != null) {
    buttonNext.onclick = function () {
        if (isNaN(currentPage)){
            currentPage = 1;
        };
        var newPage = currentPage + 1;
        window.location.href = '/community/home/allusers?p=' + newPage;
    };
};

if (buttonBefore != null) {
    buttonBefore.onclick = function () {
        var newPage = currentPage - 1;
        window.location.href = '/community/home/allusers?p=' + newPage;
    };
};