package com.example.dam.gestordejuegos.mvp.vista.vista;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.dam.gestordejuegos.R;
import com.example.dam.gestordejuegos.adaptadores.AdaptadorRvJuego;
import com.example.dam.gestordejuegos.cliente.Conexion;
import com.example.dam.gestordejuegos.contrato.ContratoBaseDeDatos;
import com.example.dam.gestordejuegos.contrato.ContratoMain;
import com.example.dam.gestordejuegos.dialogo.DialogoBorrar;
import com.example.dam.gestordejuegos.dialogo.OnBorrarDialogListener;
import com.example.dam.gestordejuegos.mvp.vista.presentador.PresentadorGJ;
import com.example.dam.gestordejuegos.pojo.Entrada;
import com.example.dam.gestordejuegos.pojo.Icono;
import com.example.dam.gestordejuegos.pojo.Juego;
import com.example.dam.gestordejuegos.util.Json;
import com.example.dam.gestordejuegos.util.UtilFecha;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class VistaGJ extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ContratoMain.InterfazVista, OnBorrarDialogListener, LoaderManager.LoaderCallbacks<Cursor> {
    private PresentadorGJ presentador;
    private DrawerLayout dl;
    private NavigationView nv;
    private AdaptadorRvJuego adaptador;
    private Juego juego;
    private RecyclerView rv;
    private RecyclerView.LayoutManager lym;
    private ArrayList borrar;
    private ArrayList<Entrada> entradas;
    private long aBorrar;
    private String orderBy = null;
    private Conexion conn;
    private Json json;
    //  private final String vengoDe = "VISTAGJ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        juego = new Juego();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_nav);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navview);
        navigationView.setNavigationItemSelectedListener(this);

        presentador = new PresentadorGJ(this);

        if (presentador.totalJuegos() == 0) {
            anadirAppsTelefono();
        }

        RecyclerView rv = (RecyclerView) findViewById(R.id.rvPrincipal);
        rv.setLayoutManager(new GridLayoutManager(this, 2));

        SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
        if (orderBy == null) {
            orderBy = "fecha";
        }
        orderBy = settings.getString("orderBy", orderBy);

        adaptador = new AdaptadorRvJuego(this, presentador.getCursorOrdenado(orderBy), getIconos());

        adaptador.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(VistaGJ.this, VistaJuego.class);
                juego = presentador.darJuego(id);
                //  System.out.println("789789"+presentador.darJuego(id).toString());
                presentador.onEditarJuego(position);
                System.out.println(position + juego.toString());

                intent.putExtra("juego", juego);
                startActivity(intent);
            }
        });

        adaptador.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // Toast.makeText(VistaGJ.this, "estoy en .."+position+"ssoy.."+id, Toast.LENGTH_SHORT).show();
                DialogoBorrar borra = DialogoBorrar.newInstance(juego);
                borra.show(getSupportFragmentManager(), "Dialogo borrar");

                aBorrar = id;
                return true;
            }
        });

        rv.setAdapter(adaptador);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentador.onAgregarJuego();
            }
        });
        getSupportLoaderManager().initLoader(1, null, this);
        System.out.println(orderBy);

        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            System.out.println("\n--CONECTADO--\n");
        }


    }

    public Juego pasarJuego() {
        return juego;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        System.out.println("AAATRRRRAAASSS");
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.ordenaFecha) {
            Toast.makeText(this, "fecha", Toast.LENGTH_SHORT).show();
            orderBy = ContratoBaseDeDatos.TablaJuego.FECHA + " desc ";
            adaptador.swapCursor(presentador.recargar(orderBy));
            adaptador.notifyDataSetChanged();


        } else if (id == R.id.ordenatitulo) {
            Toast.makeText(this, "titulo", Toast.LENGTH_SHORT).show();
            orderBy = ContratoBaseDeDatos.TablaJuego.TITULO + " asc ";
            adaptador.swapCursor(presentador.recargar(orderBy));
            adaptador.notifyDataSetChanged();

        } else if (id == R.id.ordenaPuntuacion) {
            Toast.makeText(this, "valoracion", Toast.LENGTH_SHORT).show();
            orderBy = ContratoBaseDeDatos.TablaJuego.VALORACION + " desc ";
            adaptador.swapCursor(presentador.recargar(orderBy));
            adaptador.notifyDataSetChanged();

        } else if (id == R.id.recargar) {


            AsyncTask tarea = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    System.out.println("abcde");
                    conn = new Conexion();
                    JSONArray json_arr=null;
                    try {
                   json_arr =obtenerArrayJuegos(getJuegos(presentador.getCursorOrdenado(orderBy)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("fallo al crear");
                    }
                    return conn.downloadUrl("https://gestor-juegos-virginnita.c9users.io/conexionApp.php", json_arr.toString());
                }

                @Override
                protected void onPostExecute(Object o) {
                    //super.onPostExecute(o);
                    System.out.println((String) "---TAREA----" + o);
                }


            };
            tarea.execute();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void mostrarAgregarJuego() {
        Intent i = new Intent(this, Edicion.class);
        i.putExtra("luz", false);
        startActivity(i);

    }

    @Override
    public void mostrarJuegos(Cursor c) {
        adaptador.changeCursor(c);

    }


    @Override
    public void mostrarEditarJuego(Juego j) {
        SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("orderBy", orderBy);
        editor.commit();
        System.out.println("JUEGO-- -- " + j.toString());
        Intent i = new Intent(this, VistaJuego.class);
        Bundle b = new Bundle();
        b.putParcelable("juego", juego);
        i.putExtras(b);
        startActivity(i);

    }


    @Override
    public void mostrarBorrarJuego(Juego n) {//Llama a DialogoBorrar
        System.out.println("JUEGO-- borrar-- " + n.toString());
        DialogoBorrar fragmentBorrar = DialogoBorrar.newInstance(n);
        fragmentBorrar.show(getSupportFragmentManager(), "Dialogo borrar");

    }

    @Override
    public void onBorrarPossitiveButtonClick(Juego j) {

        presentador.onDeleteJuego(aBorrar);
        aBorrar = 0;
    }//lo borra si le da a ACEPTAR

    @Override
    public void onBorrarNegativeButtonClick() {//no hace nada si le da CANCELAR

    }


    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(), ContratoBaseDeDatos.CONTENT_URI_JUEGO, null, null, null, orderBy);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        adaptador.swapCursor((Cursor) data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        try {
            adaptador.swapCursor(presentador.recargar(orderBy));
        } catch (Exception e) {
            adaptador.swapCursor(null);
        }
    }


    private void anadirAppsTelefono() {
        presentador.aniadirApps(getListaAplicaciones());

    }

    private int totalAppsDelTelf() {
        int total = getListaAplicaciones().size();
        return total;
    }

    private ArrayList<Juego> getListaAplicaciones() {

        ArrayList<Juego> lista = new ArrayList<>();

        PackageManager gestorPaquetes = getPackageManager();

        List<ApplicationInfo> aplicaciones = gestorPaquetes.getInstalledApplications(0);

        for (ApplicationInfo infoAplicacion : aplicaciones) {

            if (getPackageManager().getLaunchIntentForPackage(infoAplicacion.packageName) != null) {
                File archivo = new File(infoAplicacion.sourceDir);
                String nombre;

                if (!archivo.exists()) {
                    nombre = infoAplicacion.packageName;

                } else {
                    CharSequence label = infoAplicacion.loadLabel(getPackageManager());
                    if (label != null) {
                        nombre = label.toString();
                    } else {
                        nombre = infoAplicacion.packageName;
                    }

                }
                Drawable icono = getIcono(infoAplicacion);
                Drawable ico = infoAplicacion.loadIcon(getPackageManager());


                String ic = infoAplicacion.toString();
                String paquete = infoAplicacion.packageName;
                Juego app = new Juego(nombre, paquete, icono.toString());
                Date d = new Date();

                app.setPreinstalado(0);
                app.setFecha(UtilFecha.formatDate2(d));
                app.setDescripcion("Aplicacion preinstalada , descripcion no disponible");

                if (paquete.compareTo("com.example.dam.gestordejuegos") != 0) {
                    lista.add(app);
                }
            }

        }

        return lista;
    }

    public ArrayList<Icono> getIconos() {

        ArrayList<Icono> lista = new ArrayList<>();

        PackageManager gestorPaquetes = getPackageManager();

        List<ApplicationInfo> aplicaciones = gestorPaquetes.getInstalledApplications(0);

        for (ApplicationInfo infoAplicacion : aplicaciones) {

            if (getPackageManager().getLaunchIntentForPackage(infoAplicacion.packageName) != null) {
                File archivo = new File(infoAplicacion.sourceDir);

                Drawable icono;
                String app;
                if (!archivo.exists()) {

                    icono = ContextCompat.getDrawable(this, android.R.drawable.sym_def_app_icon);
                    app = infoAplicacion.packageName;

                } else {

                    icono = getIcono(infoAplicacion);
                    app = infoAplicacion.loadLabel(getPackageManager()).toString();


                }
                Icono ic = new Icono(icono, app);

                lista.add(ic);

            }

        }
        return lista;
    }


    private Drawable getIcono(ApplicationInfo info) {
        File archivo = new File(info.sourceDir);
        if (archivo.exists()) {

            return info.loadIcon(getPackageManager());
        } else {
            return ContextCompat.getDrawable(this, android.R.drawable.sym_def_app_icon);
        }
    }

    private JSONArray obtenerArrayJuegos(ArrayList<Juego> lista) throws JSONException {
      //  Json js = new Json();
        JSONArray arr = new JSONArray();
       // JSONObject obj = new JSONObject();
        for (int i = 0; i < lista.size(); i++) {
              Json js = new Json();
              String cad =js.juego_json(lista.get(i));
            JSONObject obj = new JSONObject();
              obj.put("juego",cad);
              arr.put(obj);
        }



        return arr;
    }


    private ArrayList getJuegos(Cursor c) {
        ArrayList<Juego> lista = new ArrayList();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Juego j = Juego.getJuego(c);

            lista.add(j);

            c.moveToNext();
        }
        return lista;
    }


}


