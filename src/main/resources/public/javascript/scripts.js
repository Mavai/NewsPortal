
function addNewSelectForm(divId, btnName) {
    var select = $("#" + divId);
    var categories_select = $("#" + divId + " [id^=select]:last").clone(true, true);
    categories_select.attr("id", "select_" + $("[name='deleteCategory']").length + 1);
    if ($("[name='" + btnName + "']").length == 0) {
        var div = $("<div/>").addClass("col");
        var button = $("<button type='button'/>").html("-").attr("name", btnName).attr("class", "btn btn-danger")
            .click(deleteSelect);
        div.append(button);
        categories_select.append(div);
    }
    select.append(categories_select);
}

function deleteSelect() {
    var button = $(this)
    button.parent().parent().remove();
}

$(".deleteButton").click(function() {
    $(this).parent().parent().remove();
})

