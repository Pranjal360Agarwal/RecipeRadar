package com.example.appfoodie;

import android.content.Context;

import com.example.appfoodie.Listeners.InstructionsListener;
import com.example.appfoodie.Listeners.RandomRecipeResponseListener;
import com.example.appfoodie.Listeners.RecipeDetailsListener;
import com.example.appfoodie.Models.InstructionsResponse;
import com.example.appfoodie.Models.RandomRecipeApiResponse;
import com.example.appfoodie.Models.RecipeDetailsResponse;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {

    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }


    public void  getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags){
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe("fe339556c7b04db3b616a89cf35b6300","10", tags); //od 1-100
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });

    }

    public void getRecipeDetails(RecipeDetailsListener listener, int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key));

        call.enqueue(new Callback<RecipeDetailsResponse>() {
             @Override
             public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                 if(!response.isSuccessful()){
                 listener.didError(response.message());
                 return;
             }
                 listener.didFetch(response.body(), response.message());
        }
        @Override
        public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
            listener.didError(t.getMessage());
        }
    });
    }

    public void getInstructions( InstructionsListener listener,int id){
        CallInstructions callInstructions = retrofit.create(CallInstructions.class);
        Call<List<InstructionsResponse>> call = callInstructions.callInstructions(id, context.getString(R.string.api_key));

        call.enqueue(new Callback<List<InstructionsResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionsResponse>> call, Response<List<InstructionsResponse>> response) {

                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<InstructionsResponse>>call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }


     private interface CallRandomRecipes {
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags")List<String> tags
                );
     }

    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
         Call<RecipeDetailsResponse> callRecipeDetails(
                 @Path("id") int id,
                 @Query("apiKey") String apiKey
        );
     }


    private interface CallInstructions{
         @GET("recipes/{id}/analyzedInstructions")
         Call<List<InstructionsResponse>>callInstructions(
            @Path("id") int id,
            @Query("apiKey") String apiKey
         );
     }
}
