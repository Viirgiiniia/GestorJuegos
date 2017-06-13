
 <?php  require_once('connectvars.php');
   $con = mysqli_connect(DB_HOST , DB_USER , DB_PASSWORD , DB_NAME , DB_PORT);
   $query ="select * from juego";
   $result = mysqli_query($con,$query) or die ('Bad query'.$query);
 ?>
<!DOCTYPE html>

<html lang="es">
  <head>
    
    <meta charset="utf-8" />
    <title>Gestor de Juegos </title>
    <link rel="stylesheet" href="estilos.css" type="text/css" media="all"/>
  </head>
  <body>
   
                                                  
    <div class="cabecera">
      <h1>GESTOR DE JUEGOS</h1>
      <?php echo'Hola!';?>
    </div>
    <div class="contenido">
        
        
  <?php  
     $cont=4;
    while($row=mysqli_fetch_array($result2)){
        if($cont>3){
            echo'<tr>';
        }
        
        echo' <div class="juego">
                <div class="imagen"><img src="ing/estrella.png" alt="Upss"/></div>
                <div class="titulo">'.$row['titulo'].'</div>
                <div class="fechaValor">
                    <div class="fecha">'.$row['fecha'].'</div>
                    <div class="valoracion">'.$row['valoracion'].'<img src="img/estrella.png" alt="*" style="width:25px;height:25px;"></div>
                </div>
            </div>';
        
        
        if($cont>3){
            echo'</tr>';
            $cont=1;
        }else {
            $cont=$cont+1;
        }
    } 
  ?>    
  
  
  
  
  </div>                                 

  <div class="pie"> © 2017 VGR - Todos los derechos reservados.</div>
</body>
</html>