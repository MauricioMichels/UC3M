
var addProduct = function(argument) {

    var selectValue = $('#accesorios').val();
    var cantidad = $('#cantidad').val();

    var accesorio = accesorios[selectValue];

    createRow(accesorio, cantidad);

    // $("#ProductosSelected").appendTo("<tr>\
    // 	<td>"+accesorio.etiqueta+"</td>\
    // 	<td>"+cantidad+"</td>\
    // 	<td>"+accesorio.precio+"</td>\
    // 	</tr>"
    // );


}



function createRow(accesorio, cantidad) {
    var table = document.getElementById('compra');
    var row = table.insertRow(0);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell2 = row.insertCell(2);
    cell1.innerHTML = accesorio.etiqueta;
    cell2.innerHTML = cantidad;
    cell2.innerHTML = accesorio.precio;
}

function deleteRow() {
    document.getElementById("myTable").deleteRow(0);
}