<?php
header('Content-Type: application/json');

$json = file_get_contents('php://input');
$obj = json_decode($json, TRUE);
foreach($obj as $item){
   $id= $item->id;
   $titulo= $item->titulo;
   $descripcion= $item->descripcion;
   $imagen= $item->imagen;
   $fecha= $item->fecha;
   $valoracion= $item->valoracion;
   $lanzador= $item->lanzador;
   $preinstalado= $item->preinstalado;

}


//por cada elemento del array
//decodifica ej json
//lo añade a la bd
require_once('connectvars.php');
$con = mysqli_connect(DB_HOST , DB_USER , DB_PASSWORD , DB_NAME , DB_PORT);
$query='insert into juego values ('.$id.','.$titulo.','.$descripcion.','.$imagen.','.$fecha.','.$valoracion.','.$lanzador.','.$preinstalado.')';
$result=mysqli_query($con,$query);


header( "Location: https://gestor-juegos-virginnita.c9users.io/index.php");
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
?>