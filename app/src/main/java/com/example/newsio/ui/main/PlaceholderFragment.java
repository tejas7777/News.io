package com.example.newsio.ui.main;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsio.R;

import com.example.newsio.ui.main.ApiInterface;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NAME = "section_number";
    private String source_name;

    private PageViewModel pageViewModel;

    private ProgressBar progressBar;
    private TextView textView;
    private RecyclerView recyclerView;
    private String API_KEY = "980675532ea042228f87d24f13e8827f";
    private List<Article> articlelist;
    private View root;
    Context context;

    public static PlaceholderFragment newInstance(String source) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_SECTION_NAME, source);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        if (getArguments() != null) {
            source_name = getArguments().getString(ARG_SECTION_NAME);
            pageViewModel.setName(source_name);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        progressBar = root.findViewById(R.id.progressBar);

        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        SourceFetch sourceFetch = new SourceFetch();
        sourceFetch.execute();
        context = getActivity();
        return root;
    }

    class SourceFetch extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... source) {

            final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseModel> call = apiService.getLatestNews(source_name,API_KEY);

            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.body().getStatus().equals("ok")) {

                        articlelist = response.body().getArticles();
                        Log.d("HERE","Done");
                        Toast.makeText(context,"DONE!",Toast.LENGTH_LONG);
                    }

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Log.e("out", t.toString());
                    Log.d("HERE","ERROR");
                }
            });



            return null;
        }

        @Override
        protected void onPostExecute(String result){
            progressBar.setVisibility(View.GONE);
            RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new RecyclerAdapter(articlelist));
        }
    }

}

