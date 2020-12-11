$(document).ready(function () {
    
});


function loadActiveRequests(companyId, userId) {
    $.ajax({
        type: "GET",
        url: "/api/" + companyId + "/" + pageNumber,
        success: function (requestPageList) {
            $("#requests").empty();
            for (let requestDto of requestPageList) {
                $("#requests").append(
                    "<li class=\"job-listing d-block d-sm-flex pb-3 pb-sm-0 align-items-center\">" +
                    "<div class=\"job-listing-about d-sm-flex custom-width w-100 justify-content-between mx-4\">" +
                    "<div class=\"job-listing-position custom-width w-50 mb-3 mb-sm-0\">" +
                    "<h2>" + requestDto.city + ", " + requestDto.streetAddress + "</h2>" +
                    "</div>" +
                    "<div class=\"job-listing-location mb-3 mb-sm-0 custom-width w-30\">" +
                    "<span class=\"icon-room\"></span>" + requestDto.region + " область, " + requestDto.district + " район" +
                    "</div>" +
                    "<div class=\"job-listing-meta\">" +
                    "<span class=\"badge badge-success link\">Обработать</span>" +
                    "</div>" +
                    "</div>" +
                    "</li>"
                );
            }
            $("#pages").empty();
            for (let i = 1; i <= totalPages; i++) {
                $("#pages").append(
                    "<span class=\"ajaxLink" + ((i == pageNumber) ? " currentLink\"" : "\"") +
                    "onclick=\"loadRequests('" + companyId + "', '" + i + "', '" + totalPages + "');\">" +
                    i +
                    "</span>"
                );
            }
        }
    });
}

function processRequest(requestId, userId) {
    $.ajax({
        type: "GET",
        url: "/api/process/" + requestId + "/" + userId,
        success: function (requestPageList) {
            $("#requests").empty();
            for (let requestDto of requestPageList) {
                $("#requests").append(
                    "<li class=\"job-listing d-block d-sm-flex pb-3 pb-sm-0 align-items-center\">" +
                    "<div class=\"job-listing-about d-sm-flex custom-width w-100 justify-content-between mx-4\">" +
                    "<div class=\"job-listing-position custom-width w-50 mb-3 mb-sm-0\">" +
                    "<h2>" + requestDto.city + ", " + requestDto.streetAddress + "</h2>" +
                    "</div>" +
                    "<div class=\"job-listing-location mb-3 mb-sm-0 custom-width w-30\">" +
                    "<span class=\"icon-room\"></span>" + requestDto.region + " область, " + requestDto.district + " район" +
                    "</div>" +
                    "<div class=\"job-listing-meta\">" +
                    "<span class=\"badge badge-success link\">Обработать</span>" +
                    "</div>" +
                    "</div>" +
                    "</li>"
                );
            }
            $("#pages").empty();
            for (let i = 1; i <= totalPages; i++) {
                $("#pages").append(
                    "<span class=\"ajaxLink" + ((i == pageNumber) ? " currentLink\"" : "\"") +
                    "onclick=\"loadRequests('" + companyId + "', '" + i + "', '" + totalPages + "');\">" +
                    i +
                    "</span>"
                );
            }
        }
    });
}