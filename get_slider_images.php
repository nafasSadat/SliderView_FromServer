<?
require "conn_db.php";

$stmt = $conn->prepare("SELECT id,image_url FROM sample_slider_Table");



$stmt ->execute();

$stmt -> bind_result($id,$image_url);

$products = array();

while($stmt ->fetch()){

    $temp = array();
	$temp["error"]=false;
	$temp['id'] = $id;
    $temp['photo'] = $image_url;

    
    
    array_push($products,$temp);

	}

	echo json_encode($products);

?>