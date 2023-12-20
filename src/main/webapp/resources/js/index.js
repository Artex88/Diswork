 let selectedItems = sessionStorage.getItem('selectedItems');
    if (selectedItems) {
    let selectedItemsParsed = JSON.parse(selectedItems);

    selectedItemsParsed.forEach(item => {
    if (item.paramName === 'tagIds') {
    item.value.forEach(tagId => {
    $(`input[paramName="tagIds"][value="${tagId}"]`).prop("checked", true);
});
} else {
    $(`li[paramName="${item.paramName}"][value="${item.value}"]`).addClass('selected');
}
});

    checkUrlParametersAndRemoveClass();
}

    document.addEventListener('DOMContentLoaded', function () {
    $("ul.multiple").on('click', 'li input', updateTagParam);

    $("ul.singleton li").on('click', function () {
    let value = $(this).attr('value');
    let paramName = $(this).attr('paramName');
    let selectedItems = JSON.parse(sessionStorage.getItem('selectedItems')) || [];

    let isItemSelected = $(this).hasClass('selected');
    $(this).toggleClass('selected', !isItemSelected);

    if (isItemSelected) {
    removeUrlParameter(paramName);
} else {
    selectedItems = selectedItems.filter(item => item.paramName !== paramName);
    selectedItems.push({ paramName, value });
    addOrUpdateUrlParameter(paramName, value);
}

    sessionStorage.setItem('selectedItems', JSON.stringify(selectedItems));
    window.location.reload();
});
});

    function updateTagParam() {
    let selectedTags = $(".tag-checkbox:checked").map(function () {
    return $(this).val();
}).get();

    addOrUpdateUrlParameter('tagIds', selectedTags.join(','));

    let updatedItems = [{ paramName: 'tagIds', value: selectedTags }];
    sessionStorage.setItem('selectedItems', JSON.stringify(updatedItems));
    window.location.reload();
}

    function checkUrlParametersAndRemoveClass() {
    let selectedItems = JSON.parse(sessionStorage.getItem('selectedItems')) || [];

    selectedItems.forEach(item => {
    if (item.paramName === 'tagIds') {
    let urlParams = new URLSearchParams(window.location.search);
    let tagIdsParam = urlParams.get('tagIds');
    let tagIdsArray = tagIdsParam ? tagIdsParam.split(',').map(Number) : [];

    item.value.forEach(tagId => {
    let selectedCheckBoxElement = $(`input[paramName="tagIds"][value="${tagId}"]`);
    selectedCheckBoxElement.prop("checked", tagIdsArray.includes(parseInt(tagId)));
});
} else {
    let selectedLiElement = $(`li[paramName="${item.paramName}"][value="${item.value}"]`);
    if (selectedLiElement && !urlParameterExists(item.paramName)) {
    selectedLiElement.removeClass('selected');
}
}
});
}
    function urlParameterExists(paramName) {
    let searchParams = new URLSearchParams(window.location.search);
    return searchParams.has(paramName);
}

    function addOrUpdateUrlParameter(key, value) {
    let currentUrl = window.location.href;
    let urlParts = currentUrl.split('?');

    if (urlParts.length > 1) {
    let queryParams = urlParts[1].split('&');
    let paramFound = false;
    let updatedParams = queryParams.map(function (param) {
    let paramParts = param.split('=');
    if (paramParts[0] === key) {
    paramFound = true;
    return key + '=' + value;
}
    return param;
});
    if (!paramFound)
    updatedParams.push(key + '=' + value);
    urlParts[1] = updatedParams.join('&');
} else
    urlParts.push(key + '=' + value);

    let newUrl = urlParts.join('?');
    window.history.replaceState({path: newUrl}, '', newUrl);
}

    function removeUrlParameter(parameterName) {
    let currentUrl = window.location.href;
    let urlParts = currentUrl.split('?');
    if (urlParts.length > 1) {
    let queryParams = urlParts[1].split('&');
    let paramFound = false;
    let updatedParams = queryParams.filter(function (param) {
    let paramParts = param.split('=');
    if (paramParts[0] === parameterName) {
    paramFound = true;
    return false;
}
    return true;
});
    if (paramFound) {
    urlParts[1] = updatedParams.join('&');
    let newUrl = urlParts.join('?');
    window.history.replaceState({path: newUrl}, '', newUrl);
}
}
}