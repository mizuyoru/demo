
$(document).ready(function () {
	getDataTable();
});

function getDataTable(){
	$.getJSON('/api/currency',function(res){
	    $('#table').DataTable({
			data: res,
	        columns: [
	            { data: 'code' ,title: "代碼" },
	            { data: 'name' ,title: "幣別名" },
	            { data: 'rate' ,title: "匯率" },
	        ],
	    })
	})
}

function callAPI(){
	$.getJSON('/api/transferAPI',function(res){
		$("#update").text('更新時間: '+res);
		$('#table').DataTable().destroy();
		getDataTable();
	})
	
}

const InsertCurrency = ()=>{
	const data = {
		"code": $("#code").val(),
		"name": $("#name").val(),
		"rate": $("#rate").val()
	}
	fetch('/api/currency/',{
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(data),
	}).then(function(){
		$('#table').DataTable().destroy();
		getDataTable();
	})
}

const updateCurrency = ()=>{
	const data = {
		"name": $("#name").val(),
		"rate": $("#rate").val()
	}
	fetch('/api/currency/'+$("#code").val(),{
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(data),
	}).then(function(){
		$('#table').DataTable().destroy();
		getDataTable();
	})
}

const deleteCurrency = ()=>{
	fetch('/api/currency/'+$("#code").val(),{
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json'
		},
	}).then(function(){
		$('#table').DataTable().destroy();
		getDataTable();
	})
}