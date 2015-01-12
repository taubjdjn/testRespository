<!-- /* 
 * index.jsp
 *  
 * 1.0
 * 
 * 2014/12/27
 *  
 * Copyright (c) 2014 Le U Uay 
 * 
 * Modification Logs:  
 * DATE             AUTHOR     DESCRIPTION  
 * --------------------------------------------------------  
 * 2014/12/30       UayLU       Create
 */ -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Test Application 1</title>
	<link href="resources/css/bootstrap.min.css" rel="stylesheet">
	<link href="resources/css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="resources/css/style.css" rel="stylesheet">
	
	<script src="resources/js/jquery-2.1.3.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/jquery.bootpag.min.js"></script>
	<script src="resources/js/detectmobilebrowser.js"></script>
	<script type="text/javascript">
	
		
	
	</script>
	<script type="text/javascript">
	 
		$(document).ready(function(){
			
			//load ajax when load page
			getListAnimal("all",1);
			
			//get list animal function
			function getListAnimal(animal,page){
				//set hidden searching animal type
				$('#animalHidden').text(animal);
				//show loading spinner image
				$('#spinner').fadeIn();
				$.ajax({
					type:"GET",
					url:"posts/",
					data: {animal: animal, page: page},
				}).done(function(data){
					//hide loading spinner image when ajax done
					$('#spinner').fadeOut();
					//check valid message form server
					if(data.message){
						$('#message').text(data.message);
						$("tbody").children().remove();
						$("#infor-data").empty();
						$("#page-selection").empty();
					}else{
						//if have no message, jquery set data to table, hide message
						$('#message').text("");
						setDataTable(data.listAnimal);
						setInfoData(data.totalAnimalByType, data.limitPage, data.pageCurrent, data.pageSize);
						setPagination(data.pageSize,animal, page);
					}
				}).fail(function (){
					//hide loading spinner image
					$('#spinner').fadeOut();
					//set message error
					$('#message').text("Have some error--Please try agian later");
					//remove data in table
					$("tbody").children().remove();
					$("#infor-data").empty();
					$("#page-selection").empty();
				});			
			}
			
			$('#buttonDog').click(function(){
				if("dog" != $('#animalHidden').text()){
					$('#animalPreviousHidden').text($('#animalHidden').text());
				}
				getListAnimal("dog",1);
			});
			$('#buttonCat').click(function(){
				if("cat" != $('#animalHidden').text()){
					$('#animalPreviousHidden').text($('#animalHidden').text());
				}
				getListAnimal("cat",1);
			});
			$('#buttonBoth').click(function(){
				if("both" != $('#animalHidden').text()){
					$('#animalPreviousHidden').text($('#animalHidden').text());
				}
				getListAnimal("both",1);
			});
			$('#buttonRefresh').click(function(){
				console.log("animal previous :"+$('#animalPreviousHidden').text());
				var currentAnimal = $('#animalHidden').text();
				getListAnimal($('#animalPreviousHidden').text(),1);
				$('#animalPreviousHidden').text(currentAnimal);
			});
			
			//build hmlt data in table
			function setDataTable(listAnimal){
				$("tbody").children().remove();
				var body = '';
				for (var i=0, size=listAnimal.length; i<size; i++) {

					body += '<tr><td ><img src="'
			  			 + listAnimal[i].url + '" height="30" width="30">'					             
			             + '</td><td>'
			             + listAnimal[i].name
			             + '</td><td>'
			             + listAnimal[i].text
			             + '</td></tr>';
				}

				$('#dataTable').append(body);
			}	
			
			//set information of data in table
			function setInfoData(totalAnimal, limitPage, page, totalPage){
				if(page*limitPage >= totalAnimal){
					$("#infor-data").text("Current page "+ page + " of " + totalPage + ", from "+ (page-1)*limitPage +" to " + totalAnimal + " of " + totalAnimal);
				}else{
					$("#infor-data").text("Current page "+ page + " of " + totalPage + ", from "+ (page-1)*limitPage +" to " + page*limitPage + " of " + totalAnimal);	
				}
				 
			}
			
			//set pagination to div page-selection 
			function setPagination(totalPage, animal, page){
				$('#page-selection').bootpag({
		            total: totalPage,
		            page : page,
		            maxVisible: 5
		        });
			}
			
			//handle click on pagination
			$('#page-selection').bootpag().on("page", function(event, num){
	        	var animal = $('#animalHidden').text();
	        	getListAnimal(animal,num);
	        });
			
			//check browser is mobile or not
			$('#table').ready(function(){
				if($.browser.mobile)
				{
					//set css of table, text-align in mobile 
					$('#table').css('width','100%');
					$('#infor-data').css('text-align','center');
					$('#page-selection').css('text-align','center');
				}
			});
	
		});
	</script>


</head>
<body>

	<div class="container">
		<div class="row" id="header">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 " >
				<h1 style="text-align:center;">Test application 1</h1>
			</div>
		</div>
		<div class="row" id="menu">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 " >
				<input type="button" value="Dog" id="buttonDog" class="button">
				<input type="button" value="Cat" id="buttonCat" class="button">
				<input type="button" value="Dog or Cat" id="buttonBoth" class="button">
			</div>
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 " style="margin-top:20px;">
				<input type="button" value="Push to refresh" id="buttonRefresh" class="button">
			</div>
			<div id="animalHidden" style="display:none;"></div>
			<div id="animalPreviousHidden" style="display:none;"></div>
		</div>
		
		<div class="row"> <p id="message" style="color: red;text-align:center;"></p> </div>
		
		<div class="row" id="table">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 " >
					<div style="height:400px;overflow: auto;">
					<table class="table table-striped table-bordered " id="dataTable" >
						<colgroup>
					    	<col style="width:10%">
					    	<col style="width:30%">
					    	<col style="width:60%">
					    </colgroup>  
					    <thead>
					        <tr>
					            <th >Picture</th>
					            <th >Name</th>
					            <th >Text</th>
					        </tr>
					    </thead>
					   	<tbody>
					   		
					   	</tbody>
					</table>
					</div>
				</div>
			</div>
			<div class="row" id="infor-table">
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 " >
					<div id="infor-data"></div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 " >						
					<div id="page-selection"></div>
				</div>
			</div>
							
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 " >
				<div id="spinner" style="display:none">
					<div id="spinnerContent">
						<img src="resources/img/spinner.gif">
					</div>
					<div id="spinnerExp"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>