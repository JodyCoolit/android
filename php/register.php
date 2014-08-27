<?php

	$username = $_POST['username'];
	$password = $_POST['password'];
	$phone = $_POST['phone'];
	$email = $_POST['email'];
	$address = $_POST['address'];
	/* 
	echo 'server response';
	echo 'username : '.$username;
	echo 'password : '.$password;
	echo 'phone : '.$phone;
	echo 'email : '.$email;
	echo 'address : '.$address;   
	*/
	
	$con = mysqli_connect("www.nmmapp.com", "yeospace_1B14037", "HuangQian2014","yeospace_1B14037"); 
	
	//$con = mysqli_connect("localhost", "root", "","delivery");
	$sql = "INSERT INTO users (username,password,role,phone,email,address)VALUES('$username','$password','user','$phone','$email','$address')";
	if (!mysqli_query($con,$sql)) {
	  die('Error: ' . mysqli_error($con));
	  echo 'no';
	}
	echo 'yes';
	mysqli_close($con);
?>