<?php
require "init.php";
$id=$_POST["id"];
$name=$_POST["name"];
$gender=$_POST["gender"];
$height=$_POST["height"];
$weight=$_POST["weight"];
$age=$_POST["age"];


$sql = "select * from users where id='$id';";
$result=mysqli_query($con,$sql);

if (mysqli_affected_rows($con) ==-1 || mysqli_affected_rows($con)==0) 
{
    echo "Modify failed...no such data id or other error.";
}
else
{
    $row=mysqli_fetch_row($result);
    $flag=0;
    
    if((strlen($name)!=0) and ($name!=$row[1]))
    {
        mysqli_query($con,"update users set name='$name' where id='$id';");
    }
    if((strlen($gender)!=0) and ($gender!=$row[2]))
    {
        mysqli_query($con,"update users set gender='$gender' where id='$id';");
	$flag=1;
    }
    if((strlen($height)!=0) and ($height!=$row[4]))
    {
        mysqli_query($con,"update users set height='$height' where id='$id';");
	$flag=1;
    }
    if((strlen($weight)!=0) and ($weight!=$row[5]))
    {
        mysqli_query($con,"update users set weight='$weight' where id='$id';");
	$flag=1;
    }
    if((strlen($age)!=0) and ($age!=$row[3]))
    {
        mysqli_query($con,"update users set age='$age' where id='$id';");
	$flag=1;
    }
    if($flag!=0)
    {
        $result_new = mysqli_query($con,"select * from users where id='$id';");
	$row_new = mysqli_fetch_row($result_new);
	
	$height_new=$row_new[4];
	$weight_new=$row_new[5];
	$age_new=$row_new[3];
	
	if($row_new[2]=='M')
	{
	    $bmr = 66.0 + (13.7 * $weight_new) + (5.0 * $height_new) - (6.8 * $age_new);
	    $bmr = number_format($bmr, 2, '.', '');
	}
	else
	{
	    $bmr = 655.0 + (9.6 * $weight_new) + (1.8 * $height_new) - (4.7 * $age_new);
	    $bmr = number_format($bmr, 2, '.', '');
	}
	
	mysqli_query($con,"update users set bmr='$bmr' where id='$id';");

    }
    
    echo "Modify Success....";   
}


$con->close();
?>