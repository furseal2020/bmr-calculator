<?php
require "init.php";
$id=$_POST["id"];
$sql="select * from users where id='$id';";

$result=mysqli_query($con,$sql);
$response=array();

while($row=mysqli_fetch_array($result))
{
array_push($response,array("id"=>$row[0],"name"=>$row[1],"gender"=>$row[2],"age"=>$row[3],"height"=>$row[4],"weight"=>$row[5],"bmr"=>$row[6],"img"=>$row[7]));
}

echo json_encode(array("server_response"=>$response));

mysqli_close($con);
?>