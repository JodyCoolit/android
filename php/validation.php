<?php

	$username = $_POST['username'];
	$password = $_POST['password'];
	
	$con = mysqli_connect("www.nmmapp.com", "yeospace_1B14037", "HuangQian2014","yeospace_1B14037"); 
	
	//$con = mysqli_connect("localhost", "root", "","delivery");
	$sql = "select * from users where username ='$username'";
	$result = mysqli_query($con,$sql);
	if (!$result){
	  die('Error: ' . mysqli_error($con));
	  echo 'db error!';
	}
	
	while($row = mysqli_fetch_array($result)){
			if(strcmp($password,$row['password'])===0){// return 0 means equal;
				if($row['role'] == 'admin'){
					echo 'admin';
				}else if($row['role'] == 'staff'){
					echo 'staff';
				}else if($row['role'] == 'courier'){
					echo 'courier';
				}else if($row['role'] == 'user'){
					echo 'user';
				}
			}else{
					echo 'incorrect password';
					//break;
			}
	}
	
	mysqli_close($con);
?>