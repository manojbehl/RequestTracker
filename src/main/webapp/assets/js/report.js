$(function() {
	$("#download").click(function() {
		$("#filterReportTable").tableToCSV();
	});
});

$(function() {
	$("#downloadHits").click(function() {
		$("#hitsReportTable").tableToCSV();
	});
});

 $(document).ready(function() {
				
		//	Initializing calenders
				$("#fromDatepicker").datepicker({
					setDate : "0",
					maxDate : 0,
					dateFormat : "dd/mm/yy",
					defaultDate : +7,
					minDate : new Date(2017, 2, 2)
				});
				$("#toDatepicker").datepicker({
					maxDate : 1,
					dateFormat : "dd/mm/yy",
					minDate : new Date(2017, 2, 2)
				});
				
		//	Get the overall report onload 
				getConversionOverview();

				var today = new Date();
				var dd = today.getDate();
				var mm = today.getMonth() + 1; //January is 0!

				var yyyy = today.getFullYear();
				if (dd < 10) {
					dd = '0' + dd;
				}
				if (mm < 10) {
					mm = '0' + mm;
				}
				var today = dd + '/' + mm + '/' + yyyy;
				
				$("#toDatepicker").val = today;
				

				var date = today;
				
					
				//	load partners in the partner dropdown
				$.getJSON(contextPath+"/partner", function(response) {
					var options = $("#partnerId");
					options.find('option').remove().end();
					if (response.result.length < 1) {
						// alert("inside if ");
						options.append($("<option />").val("-1").text(
								"No Partners found"));
					} else {
						options.append($("<option />").val("0").text(
								"Select All"));
						for (var i = 0; i < response.result.length; i++) {
							options.append($("<option />").val(
									response.result[i].partnerId).text(
									response.result[i].partnerName));
						}
					}
				});
				
				//	load applications in the partner dropdown
				$.getJSON(contextPath+"/apps", function(response) {
					var options = $("#appId");
					options.find('option').remove().end();
					if (response.result.length > 0) {
						options.append($("<option />").val("0").text(
								"Select All"));
						for (var i = 0; i < response.result.length; i++) {
							options.append($("<option />").val(
									response.result[i].appId).text(
									response.result[i].app_Name));
						}
					} else
						options.append($("<option />").val("-1").text(
								"No Application found"));
				});

			
	});
	
 //	called on search button click (on report page) to get a conversion overview report
	getConversionOverview = function() {
		
		var requestPayload = {};
		requestPayload.fromDate = document.getElementById("fromDatepicker").value;
		requestPayload.toDate = document.getElementById("toDatepicker").value;
		requestPayload.partnerId = document.getElementById("partnerId").value;
		requestPayload.appId = document.getElementById("appId").value;

		var filterParams = JSON.stringify(requestPayload);
		$.ajax({
					type : "POST",
					url : contextPath + '/report',
					data : filterParams,
					contentType : 'application/json',
					success : function(data) {
						
					if (data.result != null && data.result.length > 0) {
							
							var recordsCounter = data.result.length;
							if (recordsCounter > 0) {
								var tableBody = $("#filterReportTableBody");
								tableBody.empty();
								for (var i = 0; i < recordsCounter; i++) {
									tableBody.append("<tr>"
													+ "<td width='30%'>" + data.result[i].partnerName + "</td>"
													+ "<td width='40%'>" + data.result[i].appName+ "</td>"
													+ "<td width='30%'><a href='javascript:getConversionDetails("+data.result[i].partnerId +"," + data.result[i].appId + ")'>" + data.result[i].appHits + "</a></td>"
													+ "</tr>");
								}
							}
						} else {

							var tableBody = $("#filterReportTableBody");
							tableBody.empty();
							tableBody.append("<p><b>" + data.message
									+ "</b></p>");
						}

					}

				});
	}

	//	`called on hitCount click to get a conversiondetails report
	getConversionDetails = function(partnerId, appId) {
		
		var conversionPayload = {};
		conversionPayload.conversionTime = document
				.getElementById("fromDatepicker").value;
		conversionPayload.conversionToTime = document
				.getElementById("toDatepicker").value;
		conversionPayload.partnerId = partnerId;
		conversionPayload.appid = appId;
		var tableBody = $("#conversionReportTabbleBody");
		tableBody.empty();
		$("#conversionDetailModal").modal({
			show : true
		});
		
		var payLoad = JSON.stringify(conversionPayload);
		
		$.ajax({
			type : "POST",
			url : contextPath + '/reportData',
			data : payLoad,
			contentType : 'application/json',
			success : function(data) {

				if (data.result != null && data.result.length > 0) {
					
					var recordsCounter = data.result.length;
					if (recordsCounter > 0) {
						for (var i = 0; i < recordsCounter; i++) {
							tableBody.append("<tr><td>"+(i+1)+"</td>"
									+ "<td>" + data.result[i].conversionId + "</td><td>"
									+ data.result[i].ip + "</td><td>"
									+ data.result[i].conversionTime + "</td>"
									+ "</tr>");
						}
					}
				} else {
					tableBody.append("<p><b>" + data.message + "</b></p>");
				}

			}

		});

	}
