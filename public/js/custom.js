function sendDeleteRequest(url , rUrl,token) {
    $.ajax({
        url: url,
        method: "DELETE",
        data:{
            csrfToken:token
        },
        success: function () {
            window.location =  rUrl;
        },
        error: function() {
            //window.location.reload();
        }
    });
}

$(document).ready(function() {
    $('#empdata').DataTable({lengthMenu: [5, 10, 20, 30,50]});
} );



