package com.sulaihi.genericcallapiinretrofit.Repository.RepoImpl.BaseRepository;


import com.sulaihi.genericcallapiinretrofit.Repository.RepoImpl.BaseRepository.model.GeneralResponseMdl;

import retrofit2.Callback;

public interface IBaseRepository<R> {
    <R> Callback<GeneralResponseMdl<R>> GetCallBackRepo2(BaseRepositoryImpl.IApiCallBackRepo<R> apiCallBack);

     static <R> BaseRepositoryImpl<R> GetInstance() {
        return null;
    }
}
