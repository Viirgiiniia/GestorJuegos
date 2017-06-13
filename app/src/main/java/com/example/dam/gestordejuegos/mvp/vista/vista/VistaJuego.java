package com.example.dam.gestordejuegos.mvp.vista.vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dam.gestordejuegos.R;
import com.example.dam.gestordejuegos.adaptadores.AdaptadorEdicionDeEntradas;
import com.example.dam.gestordejuegos.adaptadores.AdaptadorEntraditas;
import com.example.dam.gestordejuegos.contrato.ContratoJuego;
import com.example.dam.gestordejuegos.mvp.vista.presentador.PresentadorEntrada;
import com.example.dam.gestordejuegos.mvp.vista.presentador.PresentadorJuego;
import com.example.dam.gestordejuegos.pojo.Entrada;
import com.example.dam.gestordejuegos.pojo.Juego;
import com.example.dam.gestordejuegos.util.UtilFecha;

import java.util.ArrayList;


/**
 * Created by dam on 23/3/17.
 */

public class VistaJuego extends AppCompatActivity implements ContratoJuego.InterfazVista {
    private int tieneEntradas;
    private LinearLayout ly;
    private ArrayList<Entrada> arrayLi;
    private ImageView img;
    private Juego juego = new Juego();
    private Entrada entrada = new Entrada();
    private PresentadorJuego presentador;
    private PresentadorEntrada presentadorEntrada;
    private String ruta = "X";
    private RecyclerView rv;
    private ArrayList<Entrada> eOtro;
    private ArrayList<Entrada> eTruco;
    private ArrayList<Entrada> eLogro;
    private RatingBar estrellas;
    private TextView valoracion;
    private TextView descripcion;
    private RecyclerView rvLogro;
    private RecyclerView rvTruco;
    private RecyclerView rvOtro;
    //  private ListView lvLogro;
    //  private ListView lvOtro;
    //  private ListView lvTruco;
    private AdaptadorEdicionDeEntradas aeO;
    private AdaptadorEdicionDeEntradas aeT;
    private AdaptadorEdicionDeEntradas aeL;

    //    private AdaptadorEntraditas aeO;
//    private AdaptadorEntraditas aeT;
//    private AdaptadorEntraditas aeL;
    private FloatingActionButton btLanzar;
    ArrayList<Juego> apps;
    Toolbar mytoolbar;
    String orderBy;


    //PERMISOS
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;
    private static final int MY_PERMISSIONS_WRITE_SYNC_SETTINGS = 4;
    private static final int MY_PERMISSIONS_READ_SYNC_SETTINGS = 5;
    private static final int MY_PERMISSIONS_INTERNET = 6;
    private UtilFecha fecha;
    private boolean borrado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detallejuego);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            juego = (Juego) b.get("juego");
            apps = (ArrayList<Juego>) b.get("array");
            Log.d("id", String.valueOf(juego.getId()));
        }
        SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
        orderBy = settings.getString("orderBy", orderBy);


        arrayLi = new ArrayList<Entrada>();


        presentador = new PresentadorJuego(this);
        if (juego != null) {
            try {
                arrayLi = presentador.darArray(juego.getId());
                System.out.println(arrayLi.toArray().toString());
                juego.setEntradas(presentador.darArray(juego.getId()));
                System.out.println("ENTRADAS DEL JUEGO" + juego.getEntradas().toString());
                System.out.println("ARRAY HERE" + arrayLi.toString());
            } catch (Exception ex) {
                System.out.println("LO INTENTÉ");
            }
        }


        descripcion = (TextView) findViewById(R.id.tvDescripcion);
        RecyclerView.LayoutManager lmT = new LinearLayoutManager(this);
        RecyclerView.LayoutManager lmO = new LinearLayoutManager(this);
        RecyclerView.LayoutManager lmL = new LinearLayoutManager(this);
        rvLogro = new RecyclerView(this);
        rvLogro = (RecyclerView) findViewById(R.id.revLogros);
        rvLogro.setLayoutManager(lmL);
        rvOtro = new RecyclerView(this);
        rvOtro = (RecyclerView) findViewById(R.id.revOtros);
        rvOtro.setLayoutManager(lmO);
        rvTruco = new RecyclerView(this);
        rvTruco = (RecyclerView) findViewById(R.id.revTrucos);
        rvTruco.setLayoutManager(lmT);
        estrellas = (RatingBar) findViewById(R.id.estrellitas);

        estrellas.setIsIndicator(true);
        estrellas.setNumStars(5);
        btLanzar = (FloatingActionButton) findViewById(R.id.btLanzar);
        btLanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzar();
            }
        });
        eOtro = new ArrayList<>();
        eTruco = new ArrayList<>();
        eLogro = new ArrayList<>();
        mostrarJuego(juego);
        System.out.println("VISTA JUEGO---" + juego.toString());
        //  metodo();
        //  System.out.println("TABLAAAAA"+tablaEntradas().toString());
        System.out.println(orderBy);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_juego, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.btEditar) {
            SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("orderBy", orderBy);
            editor.commit();
            Intent i = new Intent(this, Edicion.class);
            i.putExtra("juego", juego);
            i.putExtra("arrayLi", arrayLi);
            i.putExtra("luz", true);
            startActivity(i);
            // Toast.makeText(this, juego.toString(), Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("orderBy", orderBy);
        editor.commit();
    }

    @Override
    public void mostrarJuego(Juego j) {


        if (juego.getImagen() != null) {
            if (!juego.getImagen().equals("X")) {
                img = (ImageView) findViewById(R.id.imagenDetalle);
                Bitmap b = BitmapFactory.decodeFile(juego.getImagen());
                img.setImageBitmap(b);
            }
        }

        descripcion.setText(juego.getDescripcion());

        darBrilloEstrellas(juego);


        getSupportActionBar().setTitle(j.getTitulo());

        mostrarEntradas();


    }


    private void mostrarEntradas() {
        dividirArrayEntradas(arrayLi);
        if (eLogro.size() != 0) {
            aeL = new AdaptadorEdicionDeEntradas(eLogro);
            rvLogro.setAdapter(aeL);
        } else {
            System.out.println("no hay");
        }

        if (eTruco.size() != 0) {
            aeT = new AdaptadorEdicionDeEntradas(eTruco);
            rvTruco.setAdapter(aeT);
        }
        if (eOtro.size() != 0) {
            aeO = new AdaptadorEdicionDeEntradas(eOtro);
            rvOtro.setAdapter(aeO);
        }
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
        if (e.size() > 0) {

            for (int i = 0; i < e.size(); i++) {

                if (e.get(i).getTipo() != null) {
                    switch (e.get(i).getTipo()) {
                        case "LOGRO":
                            eLogro.add(e.get(i));
                            break;
                        case "OTRO":
                            eOtro.add(e.get(i));
                            break;
                        case "TRUCO":
                            eTruco.add(e.get(i));
                            break;
                        default:
                            break;
                    }
                }


            }
        }
    }

    @Override
    public void darBrilloEstrellas() {
        estrellas.setRating(5);
    }


    public void darBrilloEstrellas(Juego juego) {
        estrellas.setRating(juego.getValoracion());
        // valoracion.setText("Nota: " + juego.getValoracion());


    }

    public void lanzar() {
        try {
            String paquete = juego.getLanzador();

            //System.out.println(juego.getLanzador());
            if (paquete.compareTo("") != 0 && paquete != null && !paquete.isEmpty()) {

                Intent intent = getPackageManager().getLaunchIntentForPackage(paquete);

                startActivity(intent);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "El juego no está instalado.", Toast.LENGTH_LONG).show();
        }
    }


}
