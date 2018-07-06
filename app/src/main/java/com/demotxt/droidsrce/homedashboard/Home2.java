package com.demotxt.droidsrce.homedashboard;

/**
 * Created by Lamanna on 23/05/2018.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.demotxt.droidsrce.homedashboard.Model.Categorie;
import com.demotxt.droidsrce.homedashboard.Storage.ShareUtils;
import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.acl.Group;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Home2 extends AppCompatActivity implements View.OnClickListener{

    String url = "https://ckoipapa.me/";
    static String urlComplement = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);


        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.grouplist_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        groupListAdapter adapter = new groupListAdapter();


        recyclerView.setAdapter(adapter);




        //Create custom interface object and send it to adapter
        //Adapter trigger it when any item view is clicked
        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent camera = new Intent(Home2.this, CameraActivity.class);

                // Puis on lance l'intent !
                startActivity(camera);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }


        });


    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(),"TEst",Toast.LENGTH_SHORT);
    }


    class groupListAdapter extends RecyclerView.Adapter<groupListAdapter.groupCardHoler>{

        private List<Categorie> listGroup;
        private RecyclerViewItemClickListener recyclerViewItemClickListener;

        public groupListAdapter(){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitImageAPI service = retrofit.create(RetrofitImageAPI.class);

            Call<List<Categorie>> call = service.getCategories(ShareUtils.recuperationUser(getApplicationContext()).getToken());

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            try {
                listGroup = call.execute().body();
                notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public groupCardHoler onCreateViewHolder(ViewGroup parent, int viewType) {
            View cell = LayoutInflater.from(Home2.this).inflate(R.layout.cell_home,parent,false);
            groupCardHoler holder= new groupCardHoler(cell);
            return holder;
        }

        @Override
        public void onBindViewHolder(groupCardHoler holder, int position) {
            holder.position=position;
            holder.layoutforGroup(listGroup.get(position));
        }

        @Override
        public int getItemCount() {
            int itemCount = 0;
            if(listGroup != null){
                return listGroup.size();
            }
            return 0;
        }

        public void setOnItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener){
            this.recyclerViewItemClickListener=recyclerViewItemClickListener;
        }



        class groupCardHoler extends RecyclerView.ViewHolder {

            TextView textview1;
            TextView textview2;
            ImageView image;
            public int position=0;

            public groupCardHoler(View cell) {
                super(cell);
                textview1 = (TextView) cell.findViewById(R.id.texthome1);
                textview2 = (TextView) cell.findViewById(R.id.texthome2);
                image = (ImageView) cell.findViewById(R.id.imagehome);


                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //When item view is clicked, trigger the itemclicklistener
                        //Because that itemclicklistener is indicated in MainActivity
                        recyclerViewItemClickListener.onItemClick(v, position);
                    }
                });
                cell.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //When item view is clicked long, trigger the itemclicklistener
                        //Because that itemclicklistener is indicated in MainActivity
                        recyclerViewItemClickListener.onItemLongClick(v, position);
                        return true;
                    }
                });

            }

            public void layoutforGroup(Categorie categorie) {
                textview1.setText(categorie.getDescription());
                getRetrofitImage(categorie.getIllustration());
            }

            void getRetrofitImage(String urlComp) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RetrofitImageAPI service = retrofit.create(RetrofitImageAPI.class);

                String[] parseUrlComplement = urlComp.split("/");

                Call<ResponseBody> call = service.getImageDetails(parseUrlComplement[3], parseUrlComplement[4]);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {

                        try {
                            boolean FileDownloaded = DownloadImage(response.body());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d("onFailure", t.toString());
                    }
                });
            }

            boolean DownloadImage(ResponseBody body) {

                try {
                    Log.d("DownloadImage", "Reading and writing file");
                    InputStream in = null;
                    FileOutputStream out = null;

                    try {
                        in = body.byteStream();
                        out = new FileOutputStream(getExternalFilesDir(null) + File.separator + "Android.jpg");
                        int c;

                        while ((c = in.read()) != -1) {
                            out.write(c);
                        }
                    } catch (IOException e) {
                        Log.d("DownloadImage", e.toString());
                        return false;
                    } finally {
                        if (in != null) {
                            in.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    }

                    int width, height;

                    Bitmap bMap = BitmapFactory.decodeFile(getExternalFilesDir(null) + File.separator + "Android.jpg");
                    width = 2 * bMap.getWidth();
                    height = 3 * bMap.getHeight();
                    Bitmap bMap2 = Bitmap.createScaledBitmap(bMap, width, height, false);
                    image.setImageBitmap(bMap2);

                    return true;

                } catch (IOException e) {
                    Log.d("DownloadImage", e.toString());
                    return false;
                }
            }
        }
    }

    public interface RecyclerViewItemClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
