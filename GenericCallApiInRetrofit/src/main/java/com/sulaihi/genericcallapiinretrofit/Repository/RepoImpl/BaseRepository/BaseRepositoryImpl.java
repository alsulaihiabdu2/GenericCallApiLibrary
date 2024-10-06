package com.sulaihi.genericcallapiinretrofit.Repository.RepoImpl.BaseRepository;

import android.content.Context;
import android.util.Log;


import com.google.gson.Gson;
import com.sulaihi.genericcallapiinretrofit.Repository.RepoImpl.BaseRepository.model.ErrorResponse;
import com.sulaihi.genericcallapiinretrofit.Repository.RepoImpl.BaseRepository.model.GeneralResponseMdl;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseRepositoryImpl<R> implements IBaseRepository<R> {


    private static final String TAG = "BaseRepositoryImpl";
    static Context context;

    private static String ResponseIsNull = "خطاء في الارسال و معالجه البيانات ، الاستجابة مرفوضه ، حاول لاحقاُ ! ";

    @Override
    public Callback<GeneralResponseMdl> GetCallBackRepo2(IApiCallBackRepo apiCallBack) {
        return new Callback<GeneralResponseMdl>() {
            @Override
            public void onResponse(@NotNull Call<GeneralResponseMdl> call,@NotNull Response<GeneralResponseMdl> response) {
                Log.e(TAG,"onResponse:  replay - " + response);
                Log.e(TAG,"onResponse:  500Internal Server Error - " + response.code());
                Log.e(TAG,"onResponse:  Internal Server Error - " + response.message());
                try {
                    if (response.isSuccessful()) {
                        Log.e(TAG,"onResponse-isSuccess = " + response.body().toString());

                        if (response.body() != null) {
                            Log.e(TAG,"onResponse-isSuccess = " + response.body().toString());
                            Log.e(TAG,"onResponse-Message = " + response.body().getMessage());
                            Log.e(TAG,"onResponse-Cookie = " + response.headers().get("Set-Cookie"));
                          /*  if (response.body().isSuccess()) {
                                if (response.body().getEntity() instanceof LoginResponse || response.body().getEntity() instanceof RegisterResponse) {
                                    ManageRegisterAndLoginData.SetRefreshToken(Objects.requireNonNull(response.headers().get("Set-Cookie")),context);
                                    ManageRegisterAndLoginData.SaveData(context,response.body().getEntity());
                                }
                            }*/
                            apiCallBack.onCallback(response.body());
                        } else {
                            assert response.body() != null;
                            Log.e(TAG,"onResponse:  >>  response.body(isnull)   = " + response.body().toString());
                            // apiCallBack.onCallback(response.body());
                            apiCallBack.onCallback(new GeneralResponseMdl<>(false
                                    ,String.valueOf(response.code()),
                                    ResponseIsNull,null));
                        }
                    } else {
                        Log.e(TAG,"onResponse-isFailed = " + response.code());
                        Log.e(TAG,"onResponse-isFailed =  Response.error " + Response.error(response.code(),response.errorBody()));

                        if (response.code() == 500) {
                            apiCallBack.onCallback(new GeneralResponseMdl<>(false,
                                    String.valueOf(response.code()),
                                    response.message()
                                    ,null));
                        } else {
                            // Extract and handle error messages from the error response
                            assert response.errorBody() != null;
                            ErrorResponse errorResponse = getErrorEntity(response.errorBody().string());
                            if (errorResponse != null) {
                                String errorMessage = errorResponse.GetErrorsMessage();
                                Log.e(TAG,"onResponse: .bdy >>  errorMessage   = " + errorMessage);
                                Log.e(TAG,"onResponse: .bdy >>  errorCode   = " + errorResponse.getStatus());
                                Log.e(TAG,"onResponse: .bdy >>  response.code    = ===========" + response.code() + " - " + response.message());
                                apiCallBack.onCallback(new GeneralResponseMdl<>(false,
                                        String.valueOf(response.code()),
                                        errorMessage
                                        ,null));
                            } else {
                                Log.e(TAG,"onResponse: .----------> errorBody >>  errorMessage   = " + response.message());
                                Log.e(TAG,"onResponse: .----------> errorBody >>  code   = " + response.code());

                                apiCallBack.onCallback(new GeneralResponseMdl<>(false,
                                        String.valueOf(response.code()),
                                        response.message()
                                        ,null));
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG," Exception : " + e.getMessage());
                    Log.e(TAG," Exception   + response.code(): " + response.code());
                    Log.e(TAG," Exception :+ response.message(): " + response.message());
                    apiCallBack.onCallback(new GeneralResponseMdl<>(false
                            ,String.valueOf(response.code()),
                            e.getMessage() + response.message()
                            ,null));
                }
            }

            @Override
            public void onFailure(@NotNull Call<GeneralResponseMdl> call,@NotNull Throwable t) {
                Log.e(TAG," alsulaihi accept:  response in disposable " + t.getMessage());
                Log.e(TAG,"onFailure: " + t.getMessage());
                Log.e(TAG,"onFailure: call -  " + call.request());
                apiCallBack.onCallback(new GeneralResponseMdl<>(false,"404",
                        t.getMessage()
                        ,null));
            }
        };
    }

    public static <R> BaseRepositoryImpl<R> GetInstance(Context... _context) {
        context = _context != null && _context.length > 0 ? _context[0] : null;
        return new BaseRepositoryImpl<R>();
    }

    public interface IApiCallBackRepo<R> {
        void onCallback(GeneralResponseMdl<R> generalResponseMdl);
    }

    private ErrorResponse getErrorEntity(String errorBodyString) {
        // Create a Gson instance
        Gson gson = new Gson();
        // Deserialize the error body string to an ErrorResponse object
        ErrorResponse errorResponse = gson.fromJson(errorBodyString,ErrorResponse.class);
        return errorResponse;
    }
}
