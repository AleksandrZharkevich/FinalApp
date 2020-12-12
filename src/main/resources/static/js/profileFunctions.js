$(document).ready(function () {
});


function loadActiveRequests(companyId, userId) {
    if (companyId == 0) {
        loadAllActiveRequestsByUser(userId);
    } else {
        loadAllActiveRequestsByUserAndCompany(userId, companyId);
    }
}

function loadAllActiveRequestsByUser(userId) {
    $.ajax({
        type: "GET",
        url: "/api/requests/" + userId,
        success: function(requestsList) {
            loadRequestsOnProfilePage(requestsList);
        }
    });
}

function loadAllActiveRequestsByUserAndCompany(userId, companyId) {
    $.ajax({
        type: "GET",
        url: "/api/requests/" + userId + "/" + companyId,
        success: function(requestsList) {
            loadRequestsOnProfilePage(requestsList);
        }
    });
}

function loadRequestsOnProfilePage(requests) {
    $("#companyRequests").empty();
    for (let requestDto of requests) {
        $("#companyRequests").append(
            "<div class=\"row align-items-center job-item border-bottom pb-3 mb-3 pt-3\">" +
            "<div class=\"col-md-1\">" +
            "<img align=\"center\" width=\"50\" height=\"50\" " +
            "src=\"https://i5.imageban.ru/out/2020/12/12/e43b316568cde6a20cea5fac45b24b29.jpg\" alt=\"Image\" class=\"img-fluid\">" +
            "</div>" +
            "<div class=\"col-md-3\">" +
            "<h5>" + requestDto.streetAddress + "</h5>" +
            "<p class=\"meta\"><strong>" + requestDto.city + "</strong></p>" +
            "</div>" +
            "<div class=\"col-md-3\">" +
            "<p class=\"meta\"><span class=\"icon-room\"></span>" + requestDto.region + " область</p>" +
            "<p class=\"meta\">" + requestDto.district + " район</p>" +
            "</div>" +
            "<div class=\"col-md-4\">" +
            "<p class=\"meta\">" + requestDto.lastname + " " + requestDto.firstname + "</p>" +
            "<p class=\"meta\">" + requestDto.phoneNumber + "</p>" +
            "</div>" +
            "<div class=\"col-md-1 text-md-left\">" +
            "<p><a href=\"/request/process/" + requestDto.id + "\" class=\"meta\"" +
            "style=\"color: blue!important\">Обработать</a></p>" +
            "<p><a href=\"/request/return/" + requestDto.id + "\" class=\"meta\"" +
            "style=\"color: red!important\">Отказаться</a></p>" +
            "</div>" +
            "</div>"
        );
    }
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