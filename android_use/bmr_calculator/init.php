
<?php


$db_name="bmr_calculator";
$mysql_user="root0";
$mysql_pass="860324";
$server_name="localhost";

$con=mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);

if(!$con)
{
	die("Error in connection".mysqli_connect_error());

}
else
{
	//echo "<br><h3>Connection Success...</h3>";

}


?>
