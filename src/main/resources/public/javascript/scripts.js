var selectCount = 1;

function addNewCategorySelect(lastSelectId) {
    var select = $("#select_categories");
    var categories_select = $("#select_categories [id^=select]:last").clone(true, true);
    categories_select.attr("id", "select_" + ++selectCount);
    if (selectCount == 2) {
        var div = $("<div/>").addClass("col");
        var button = $("<button type='button'/>").html("-").attr("class", "btn btn-danger").click(deleteSelect);
        div.append(button);
        categories_select.append(div);
    }
    select.append(categories_select);
}

function getLastSelectId() {
    return $("#select_categories [id^=select]:last").attr("id");
}

function deleteSelect() {
    var button = $(this)
    button.parent().parent().remove();
    selectCount--;
}

$(".deleteButton").click(function() {
    $(this).parent().parent().remove();
    selectCount--;
})

