const authorizedUserSection = document.getElementById("authorizedUserSection");
const addButton = document.getElementById('addButton');
const isAddButtonHidden = document.getElementById('isAddButtonHidden').value;
const gradeTextArea = document.getElementById('gradeTextArea');
const grade = document.getElementById('grade');
if (grade.value === "0")
    gradeTextArea.textContent = "Вы ещё не оценивали это произведение";
else
    gradeTextArea.textContent = "Ваша оценка: " + grade.value;


if (isAddButtonHidden === 'true'){
    addButton.style.display='none';
    authorizedUserSection.style.display='block'
}else {
    addButton.style.display='block';
    authorizedUserSection.style.display='none'
}

function handleAddButton(message){
    if (message === "Произведение добавленно"){
        addButton.style.display='none';
        authorizedUserSection.style.display='block'
    }

}
function handleDeleteButton(message){
    if (message === "Произведение удалено из избранного"){
        addButton.style.display='block';
        authorizedUserSection.style.display='none'
    }

}
async function fetchAndAddMedia(mediaId, csrf) {

    const requestOptions = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'X-CSRF-Token': csrf
        },
        body: JSON.stringify({"id": mediaId})
    };
    fetch('/index/media/add', requestOptions)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            handleAddButton(data.message)
            ShowToast("success", data.message)
        })
        .catch(error => {
            ShowToast('warning', "Для этого действия вам необходима регистрация на сайте!")
        });
}

async function fetchAndDeleteMedia(mediaId, csrf) {

    const requestOptions = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'X-CSRF-Token': csrf
        },
        body: JSON.stringify({"id": mediaId})
    };
    fetch('/index/media/delete', requestOptions)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            handleDeleteButton(data.message)
            ShowToast("success", data.message)
        })
        .catch(error => {
            ShowToast('warning', "Для этого действия вам необходима регистрация на сайте!")
        });

}

async function fetchAndAssessmentMedia(mediaId, csrf) {
    const grade = document.getElementById('valueSelect');
    if (grade.value === "0"){
        ShowToast('error', "Вам необходимо оценить произведение!")
    }
    const requestOptions = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'X-CSRF-Token': csrf
        },
        body: JSON.stringify({"mediaId": mediaId, "grade": grade.value })
    };
    fetch('/index/media/assessment', requestOptions)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.message === "Произведения нету в избранном")
                ShowToast('success', data.message);
            else {
                ShowToast('success', "Вы оценинили данное произведние на " + data.message);
                gradeTextArea.textContent = "Ваша оценка: " + data.message;
            }
        })
        .catch(error => {
            ShowToast('warning', "Для этого действия вам необходима регистрация на сайте!")
        });

}
function ShowToast(type, message){
    Command: toastr[type](message)

    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": true,
        "progressBar": true,
        "positionClass": "toast-top-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "3500",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
}
