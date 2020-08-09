<?php
require "init.php";
$name=$_POST["name"];
$gender=$_POST["gender"];
$height=$_POST["height"];
$weight=$_POST["weight"];
$age=$_POST["age"];
$bmr=$_POST["bmr"];

if($gender=='M')
{
$img="http://10.0.2.2/android_use/bmr_calculator/photo_uploaded/male.png";
}
else
{
$img="http://10.0.2.2/android_use/bmr_calculator/photo_uploaded/female.png";
}

$sql_query="insert into users values('DEFAULT','$name','$gender','$age','$height','$weight','$bmr','$img');";

if(mysqli_query($con,$sql_query))
{
echo "Insertion Success...";
}
else
{
echo "Insertion Failed...";
}

?>