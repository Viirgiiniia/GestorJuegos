package com.example.dam.gestordejuegos.mvp.vista.vista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.dam.gestordejuegos.R;
import com.example.dam.gestordejuegos.contrato.ContratoEntrada;
import com.example.dam.gestordejuegos.mvp.vista.presentador.PresentadorEntrada;
import com.example.dam.gestordejuegos.pojo.Entrada;
import com.example.dam.gestordejuegos.pojo.Juego;
import com.example.dam.gestordejuegos.util.Permisos;
import com.example.dam.gestordejuegos.util.UtilFecha;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dam on 2/5/17.
 */

public class EdicionEntrada extends AppCompatActivity implements ContratoEntrada.InterfazVista {
    Entrada e;
    Button guardar;
    Button cancelar;
    ImageButton btImagen;
    String imagen = "X";
    EditText contenido;
    UtilFecha fecha;
    String tipo;
    Long id, idjuego;
    Juego juego;

    Entrada entrada;
    Date d = new Date();
    PresentadorEntrada presentador;
    ArrayList<Entrada> nuevo;
String orderBy;

   /* this.idJuego = idJuego;
    this.idEntrada = idEntrada;
    this.tipo = tipo;
    this.descripcion = descripcion;
    this.imagen = imagen;
    this.fechaCrea = fechaCrea;
    this.fechaModi = fechaModi;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_entrada);
        SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
        orderBy = settings.getString("orderBy", orderBy);
        Bundle ex = getIntent().getExtras();
        if (ex != null) {
            entrada = (Entrada) ex.get("entrada");
            tipo = (String) ex.get("tipo");
            juego = (Juego) ex.get("juego");
            nuevo = (ArrayList<Entrada>) ex.get("arrayLi");

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        guardar = (Button) findViewById(R.id.btAceptar);
        cancelar = (Button) findViewById(R.id.btCancel);
        btImagen = (ImageButton) findViewById(R.id.ibEntrada);
        contenido = (EditText) findViewById(R.id.etEntrada);
        presentador = new PresentadorEntrada(this);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entrada en = new Entrada();
                if (entrada != null) {
                    System.out.println("--------------" + entrada.toString() + "---------------------");

                    en.setDescripcion(contenido.getText().toString());
    //                System.out.println("esta es la descripcion: " + en.getDescripcion());
                    en.setFechaModi(fecha.formatDate2(d));
    //                System.out.println("fecha mod" + en.getFechaModi());
                    en.setFechaCrea(entrada.getFechaCrea());
    //                System.out.println("fecha creea" + en.getFechaCrea());
                    en.setImagen(imagen);
    //                System.out.println("img" + en.getImagen());
                    en.setTipo(entrada.getTipo());
    //                System.out.println("tipo" + en.getTipo());
                    en.setIdEntrada(entrada.getIdEntrada());
                    en.setIdJuego(juego.getId());

                    //  presentador.onDeleteEntrada(entrada);
                    System.out.println(":::::::" + presentador.onSaveEntrada(en) + ":::::::");
                    System.out.println("hola , soy una entrada..." + en.toString());
                    nuevo.remove(entrada);
                    nuevo.add(en);


                } else {
                    System.out.println("Entro abajooooooooooooooooooo");
                    en.setIdJuego(juego.getId());
                    en.setDescripcion(contenido.getText().toString());
                    en.setFechaCrea(fecha.formatDate2(d));
                   // System.out.println("afecha:" + e.getFechaCrea());
                    en.setFechaModi(null);
                    if (imagen == null || imagen.isEmpty()) {
                        imagen = "X";
                    }
                    en.setImagen(imagen);
                    en.setTipo(tipo);
                    long r = 0;
                    r = presentador.onSaveEntrada(en);
                    en.setIdEntrada(r);
                    if (r > 0 && en.getIdEntrada() == 0) {
                        en.setIdEntrada(r);
                    }
                    System.out.println("hola , soy TU NUEVA entrada..." + en.toString());
                    nuevo.add(en);
                }

                Intent i = new Intent(v.getContext(), Edicion.class);
                i.putExtra("juego", juego);
                i.putExtra("entrada", en);
                i.putExtra("arrayLi", nuevo);
                System.out.println("++++*"+nuevo.toString());
                i.putExtra("luz", true);
                SharedPreferences settings = getSharedPreferences("perfil" ,MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("orderBy",orderBy );
                editor.commit();

                startActivity(i);

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences("perfil" ,MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("orderBy",orderBy );
                editor.commit();

                Entrada e = null;
                Intent i = new Intent(v.getContext(), Edicion.class);
                i.putExtra("juego", juego);
                i.putExtra("entrada", e);
                i.putExtra("luz", true);
                //   i.putExtra("vengoDe", vengoDe);
                startActivity(i);

            }
        });

        btImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirImagen();
            }
        });

        if (entrada != null) {
            if (entrada.getDescripcion() != null) {


                contenido.setText(entrada.getDescripcion());

                if (entrada.getImagen() != null && entrada.getImagen().compareTo("X") != 0) {
                    entrada.mostrarImagen(btImagen);
                } else {
                }

            }
        }
        System.out.println(orderBy);
    }

    private void elegirImagen() {
        final CharSequence[] options = {"Camara", "Galeria", "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(
                this);
        builder.setTitle("Añadir Imagen :");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Permisos.checkPermission(EdicionEntrada.this);
                if (options[item].equals("Camara") && result) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Galeria") && result) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancelar")) {

                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                // onCaptureImageResult(data);
                try {

                    File f = new File(Environment.getExternalStorageDirectory()
                            .toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    btImagen.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    imagen = path;
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System
                            .currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                onSelectFromGalleryResult(data);
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);
        imagen = selectedImagePath;

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        btImagen.setImageBitmap(bm);

    }

    @Override
    public void mostrarEntrada(Entrada e) {
        if (e.getIdEntrada() != 0) {
            contenido.setText(e.getDescripcion());
            if (e.getImagen() != null) {
                Bitmap bm = BitmapFactory.decodeFile(e.getImagen());
                btImagen.setImageBitmap(bm);
            }
        } else {
            contenido.setHint("Escriba aqui su entrada...");
        }
    }
}
