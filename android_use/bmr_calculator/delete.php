<?php
require "init.php";
$name=$_POST["name"];
$age=$_POST["age"];

$sql = "delete from users where name='$name' and age='$age';";
$result=mysqli_query($con,$sql);

if (mysqli_affected_rows($con) ==-1 || mysqli_affected_rows($con)==0) 
{
  echo "Deletion Failed...there is no such content.";
} 
else 
{
  echo "Deletion Success...";
 
}

$con->close();
?>
