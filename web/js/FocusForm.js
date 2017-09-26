
function FocusForm() {
    var elementsForm = document.getElementById(arguments[0]).elements;
    for (var i = 0; i < elementsForm.length; i++) {
        if (elementsForm[i].tagName.toLowerCase() !== "fieldset")
        {
            if (elementsForm[i].value === "")
            {
                elementsForm[i].focus();
                break;
            }
        }
    }
}

