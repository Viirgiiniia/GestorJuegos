package com.example.dam.gestordejuegos.mvp.vista.vista;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.dam.gestordejuegos.R;
import com.example.dam.gestordejuegos.adaptadores.AdaptadorEdicionDeEntradas;
import com.example.dam.gestordejuegos.contrato.ContratoJuego;
import com.example.dam.gestordejuegos.mvp.vista.presentador.PresentadorJuego;
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

public class Edicion extends AppCompatActivity implements ContratoJuego.InterfazVista {
    //-- El metodo añadir imagen solo sirve para la principal, adaptarlo a cualquiera
    private PresentadorJuego presentador;
    private Juego j;
    private Juego juego;
    private Juego modificado;
    private EditText titulo;
    private EditText descripcion;
    private ImageButton imagen;
    private RatingBar estrellas;
    private Button guardar;
    private Button beT;
    private Button beL;
    private Button beO;
    private ArrayList<Entrada> arrayLi;
    private ArrayList<Entrada> aLogro;
    private ArrayList<Entrada> aOtro;
    private ArrayList<Entrada> aTruco;
    private AdaptadorEdicionDeEntradas aeT;
    private AdaptadorEdicionDeEntradas aeL;
    private AdaptadorEdicionDeEntradas aeO;
    private RecyclerView rvT;
    private RecyclerView rvL;
    private RecyclerView rvO;
    private LinearLayout llT;
    private LinearLayout llO;
    private LinearLayout llL;
    private Toolbar toolbar;
    private UtilFecha fecha;
    private Date hoy;
    private String ruta;
    private Entrada e;
    private boolean luz;
    private boolean nuevaImagen;
    private String orderBy;
    Context context;


    private String userChoosenTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);
        context = this;
        SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
        orderBy = settings.getString("orderBy", orderBy);
        Bundle ex = getIntent().getExtras();
        if (ex != null) {
            j = (Juego) ex.get("juego");
            luz = (boolean) ex.get("luz");
            arrayLi = (ArrayList<Entrada>) ex.get("arrayLi");


        }


        if (j != null) {
            juego = j;
        } else {
            juego = new Juego();
        }
        arrayLi = new ArrayList<Entrada>();
        if (juego != null && juego.getImagen() != null) {
            ruta = juego.getImagen();
        } else {
            ruta = "X";
        }
        nuevaImagen = false;
        presentador = new PresentadorJuego(this);
        arrayLi = presentador.darArray(juego.getId());
        //ocultarReciclersiEsNuevo();
        hoy = new Date();
        fecha = new UtilFecha();
        titulo = (EditText) findViewById(R.id.etTitulo);
        descripcion = (EditText) findViewById(R.id.etDescripcion);
        imagen = (ImageButton) findViewById(R.id.ibImagen);
        estrellas = (RatingBar) findViewById(R.id.estrellas);
        guardar = (Button) findViewById(R.id.ok);
        guardar.setText("GUARDAR");
        beL = (Button) findViewById(R.id.btL);
        llT = (LinearLayout) findViewById(R.id.llTrucos);
        llO = (LinearLayout) findViewById(R.id.llOtros);
        llL = (LinearLayout) findViewById(R.id.llLogros);

        aLogro = new ArrayList();
        aOtro = new ArrayList();
        aTruco = new ArrayList();

        rvT = new RecyclerView(this);
        rvT = (RecyclerView) findViewById(R.id.rvTrucos);
        rvL = new RecyclerView(this);
        rvL = (RecyclerView) findViewById(R.id.rvLogros);
        rvO = new RecyclerView(this);
        rvO = (RecyclerView) findViewById(R.id.rvOtros);

        hacerVisible(luz);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirImagen();
                nuevaImagen = true;
                // HiloCamara hta = new HiloCamara();
                //hta.execute();
            }
        });


        beL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
                SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("orderBy", orderBy);
                editor.commit();
                Intent i = new Intent(v.getContext(), EdicionEntrada.class);
                i.putExtra("tipo", "LOGRO");
                try {
                    i.putExtra("juego", preguardar());
                } catch (Exception e) {
                    i.putExtra("juego", juego);
                }
                i.putExtra("arrayLi", arrayLi);
                startActivity(i);

            }
        });
        beT = (Button) findViewById(R.id.btT);
        beT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                guardar();
                SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("orderBy", orderBy);
                editor.commit();
                Intent i = new Intent(v.getContext(), EdicionEntrada.class);
                i.putExtra("tipo", "TRUCO");
                try {
                    i.putExtra("juego", preguardar());
                } catch (Exception e) {
                    i.putExtra("juego", juego);
                }
                i.putExtra("arrayLi", arrayLi);
                startActivity(i);


            }
        });
        beO = (Button) findViewById(R.id.btO);
        beO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
                SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("orderBy", orderBy);
                editor.commit();
                Intent i = new Intent(v.getContext(), EdicionEntrada.class);
                i.putExtra("tipo", "OTRO");
                try {
                    i.putExtra("juego", preguardar());
                } catch (Exception e) {
                    i.putExtra("juego", juego);
                }
                i.putExtra("arrayLi", arrayLi);
                startActivity(i);
            }
        });

        //arrayLi = new ArrayList();
        //arrayLi = juego.getEntradas();


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Edicion.this, "   CLICK   ", Toast.LENGTH_SHORT).show();

                guardar();

                backHome();//Vuelve a la pantalla principal};
            }

        });


        mostrarJuego();
        System.out.println("TABLAAAAA" + tablaEntradas().toString());
    }

    private void mostrarJuego() {

        if (juego.getId() != 0) {

            repartirEntradaS(arrayLi);

            crearListView();

            titulo.setText(juego.getTitulo());
            estrellas.setRating(juego.getValoracion());
            if (juego.getImagen() != null && juego.getImagen().compareTo("X") != 0) {
                Bitmap b = BitmapFactory.decodeFile(juego.getImagen());
                imagen.setImageBitmap(b);
            }

            descripcion.setText(juego.getDescripcion());
        } else {
            titulo.setHint("Escriba aqui");
        }


    }


    private void backHome() {
        //   Toast.makeText(this, "  go home    ", Toast.LENGTH_SHORT).show();
        SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("orderBy", orderBy);
        editor.commit();

        Intent i = new Intent(this, VistaGJ.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("orderBy", orderBy);
        editor.commit();
    }

    private Juego obtenerJuego() {

        Juego j = new Juego();

        ArrayList<Entrada> e = new ArrayList<>();
        if (titulo.getText() == null || titulo.getText().equals("")) {
            return null;


        } else {
            j.setTitulo(titulo.getText() + "");

            if (descripcion.getText() == null || descripcion.getText().equals("")) {
                j.setDescripcion("");
            } else {
                j.setDescripcion(descripcion.getText() + "");
            }

            Float f = estrellas.getRating();
            j.setValoracion(f.longValue());//cast float to long
            Date d = new Date();
            j.setFecha(fecha.formatDate2(d));
            j.setImagen(ruta);
            if (juego.getPreinstalado() == 1) {
                j.setImagen(juego.getImagen());
            }
            e.addAll(aTruco);
            e.addAll(aLogro);
            e.addAll(aOtro);

            j.setLanzador(juego.getLanzador());

            j.setEntradas(e);

            System.out.println(j.toString());
            return j;
        }


    }

    private Juego preguardar() {

        Juego j = new Juego();

        j.setId(juego.getId());
        if (titulo.getText() == null || titulo.getText().equals("")) {
            j.setTitulo(null);

        } else {
            j.setTitulo(titulo.getText() + "");
        }
        if (descripcion.getText() == null || descripcion.getText().equals("")) {
            j.setDescripcion("");
        } else {
            j.setDescripcion(descripcion.getText() + "");
        }
        Float f = estrellas.getRating();
        j.setValoracion(f.longValue());//cast float to long
        Date d = new Date();
        j.setFecha(fecha.formatDate2(d));
        j.setImagen(ruta);


        j.setLanzador(juego.getLanzador());

        j.setEntradas(juego.getEntradas());

     //   System.out.println("PREGUARDANDOOOO" + j.toString());

        return j;
    }


    private boolean guardarEntrada() {// GUARDA LAS ENTRADAS NUEVAS

        ArrayList arr = obtenerJuego().getEntradas();
        if (arr.size() > juego.getEntradas().size()) {
            return true;
        }
        return false;
    }


    private boolean isModificated(Juego j) {
        if (juego.getEntradas() == j.getEntradas() && juego.getTitulo().compareTo(j.getTitulo()) == 0 && juego.getImagen().compareTo(j.getImagen()) == 0 && j.getValoracion() == juego.getValoracion() && j.getDescripcion().compareTo(juego.getDescripcion()) == 0) {
            //    System.out.println("--------NO SE HA MODIFICADO---------");
            return false;
            //no se ha modificado
        } else if (juego.getId() == 0) {
            return false;
        } else {
            //    System.out.println("-----------SE HA MODIFICADO------------");
            return true;//se ha modificado
        }
    }


    private void crearListView() {

        if (aLogro.size() >= 0) {
            LinearLayoutManager llmL = new LinearLayoutManager(this);
            llmL.setOrientation(LinearLayoutManager.VERTICAL);
            aeL = new AdaptadorEdicionDeEntradas(aLogro);
            aeL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Edicion.this, EdicionEntrada.class);
                    Entrada e = presentador.pasaEntrada(aeL.getDescripcion(position));
                    System.out.println("SOY REBELDE...." + e.toString());
                    intent.putExtra("arrayLi", arrayLi);
                    intent.putExtra("entrada", e);
                    intent.putExtra("juego", juego);
                    startActivity(intent);

                }
            });


            aeL.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    onShowBorrarEntrada(aeL.getDescripcion(position));
                    return false;
                }
            });
            rvL.setAdapter(aeL);
            rvL.setLayoutManager(llmL);

        }
        if (aOtro.size() >= 0) {
            LinearLayoutManager llmO = new LinearLayoutManager(this);
            llmO.setOrientation(LinearLayoutManager.VERTICAL);
            aeO = new AdaptadorEdicionDeEntradas(aOtro);
            aeO.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Edicion.this, EdicionEntrada.class);
                    Entrada e = presentador.pasaEntrada(aeO.getDescripcion(position));
                    intent.putExtra("juego", juego);
                    intent.putExtra("arrayLi", arrayLi);
                    intent.putExtra("entrada", e);
                    System.out.println("entrada en el adaptador" + e.toString());

                    startActivity(intent);
                }
            });
            aeO.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    onShowBorrarEntrada(aeO.getDescripcion(position));
                    return false;
                }
            });

            rvO.setAdapter(aeO);
            rvO.setLayoutManager(llmO);

        }

        if (aTruco.size() >= 0) {
            LinearLayoutManager llmT = new LinearLayoutManager(this);
            llmT.setOrientation(LinearLayoutManager.VERTICAL);

            aeT = new AdaptadorEdicionDeEntradas(aTruco);
            aeT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(Edicion.this, EdicionEntrada.class);
                    Entrada e = presentador.pasaEntrada(aeT.getDescripcion(position));
                    intent.putExtra("juego", juego);
                    intent.putExtra("entrada", e);
                    intent.putExtra("arrayLi", arrayLi);
                    startActivity(intent);
                }
            });
            aeT.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    onShowBorrarEntrada(aeT.getDescripcion(position));
                    return false;
                }
            });
            rvT.setAdapter(aeT);
            rvT.setLayoutManager(llmT);

        }

    }

    private void repartirEntradaS(ArrayList<Entrada> e) {
        if (e.size() > 0) {
            for (int i = 0; i < e.size(); i++) {
                try {
                    switch (e.get(i).getTipo()) {
                        case "LOGRO":
                            aLogro.add(e.get(i));
                            break;
                        case "OTRO":
                            aOtro.add(e.get(i));
                            break;
                        case "TRUCO":
                            aTruco.add(e.get(i));
                            break;
                        default:
                            break;
                    }


                } catch (Exception ex) {
                    break;
                }
            }
        }
    }


    @Override
    public void mostrarJuego(Juego j) {

    }

    @Override
    public void mostrarImagen() {

    }

    @Override
    public ArrayList<Entrada> tablaEntradas() {
        return presentador.tablaEntradas();
    }

    @Override
    public void mostrarConfirmarBorrarItem(Entrada e) {

    }

    @Override
    public void dividirArrayEntradas(ArrayList<Entrada> e) {

    }

    @Override
    public void darBrilloEstrellas() {

    }
    //*** CAMARA ***
    //http://stackoverflow.com/questions/34396539/image-load-in-imageview-from-camera-or-gallery

    private void elegirImagen() {
        final CharSequence[] options = {"Camara", "Galeria", "Cancelar"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(
                Edicion.this);
        builder.setTitle("Añadir Imagen :");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Permisos.checkPermission(Edicion.this);
                if (options[item].equals("Camara") && result) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 5);
                        }
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", f));
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

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
                   // Bitmap bm = resizeImage(bitmap);
                    imagen.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "GestorJuegos" + File.separator + "default";
                    //ruta = path;
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System
                            .currentTimeMillis()) + ".jpg");
                    ruta =file.getAbsolutePath();
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
        ruta = selectedImagePath;

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

        imagen.setImageBitmap(bm);

    }

    class HiloCamara extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            elegirImagen();
            return null;
        }


    }

    public Bitmap resizeImage(Bitmap bm) {

        // cargamos la imagen de origen


        int width = bm.getWidth();
        System.out.println("width = [" + bm.getWidth() + "]");
        int height = bm.getHeight();
        System.out.println("height = [" + bm.getHeight() + "]");
        int newWidth = 300;
        int newHeight = 400;

        // calculamos el escalado de la imagen destino
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // para poder manipular la imagen
        // debemos crear una matriz

        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // volvemos a crear la imagen con los nuevos valores
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0,
                width, height, matrix, true);

        // si queremos poder mostrar nuestra imagen tenemos que crear un
        // objeto drawable y así asignarlo a un botón, imageview...
        return resizedBitmap;

    }


    public void guardar() {
        Juego j = obtenerJuego();
        if (isModificated(j)) {

            j.setId(juego.getId());
            presentador.onSaveJuego(j);
        } else {
            if (j.getTitulo().isEmpty()) {
                Toast.makeText(Edicion.this, "Debe rellenar al menos el titulo", Toast.LENGTH_SHORT).show();
            } else {
                Date d = new Date();
                j.setPreinstalado(0);
                j.setFecha(fecha.formatDate2(d).toString());
                j.setValoracion((long) estrellas.getRating());
                if (ruta.isEmpty() || ruta == null) {
                    ruta = "X";
                }

                j.setImagen(ruta);

                long r = 0;
                r = presentador.onSaveJuego(j);
                System.out.println(r);

                if (r > 0 & j.getId() == 0) {
                    j.setId(r);
                }


            }
        }


    }

    public void onShowBorrarEntrada(String s) {
        presentador.onShowBorrarEntrada(s);
    }


    private void hacerVisible(Boolean ok) {
        if (ok) {
            rvO.setVisibility(View.VISIBLE);
            rvT.setVisibility(View.VISIBLE);
            rvL.setVisibility(View.VISIBLE);
            llO.setVisibility(View.VISIBLE);
            llT.setVisibility(View.VISIBLE);
            llL.setVisibility(View.VISIBLE);
        } else {
            rvO.setVisibility(View.GONE);
            rvT.setVisibility(View.GONE);
            rvL.setVisibility(View.GONE);
            llO.setVisibility(View.GONE);
            llT.setVisibility(View.GONE);
            llL.setVisibility(View.GONE);
        }
    }

}
