package com.mindorks.placeholderview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 18/08/16.
 */
public class ViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<ViewBinder<ViewResolver>> mViewBinderList;

    public ViewAdapter() {
        mViewBinderList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mViewBinderList.get(position).bindView(holder.itemView);
        mViewBinderList.get(position).getResolver().onResolved();
    }

    @Override
    public int getItemViewType(int position) {
        return mViewBinderList.get(position).getLayoutId();
    }

    @Override
    public int getItemCount() {
        return mViewBinderList.size();
    }

    protected void removeView(int position)throws IndexOutOfBoundsException{
        mViewBinderList.remove(position);
        notifyItemRemoved(position);
    }

    protected <T extends ViewResolver>void addView(T viewResolver)throws IndexOutOfBoundsException{
        mViewBinderList.add(new ViewBinder<ViewResolver>(viewResolver));
        notifyItemInserted(mViewBinderList.size() - 1);
    }

    protected  <T extends ViewResolver>void removeView(T viewResolver)throws IndexOutOfBoundsException{
        int position = -1;
        for(ViewBinder viewBinder : mViewBinderList){
            if(viewBinder.getResolver() == viewResolver){
                position = mViewBinderList.indexOf(viewBinder);
            }
        }
        if(position != -1){
            mViewBinderList.remove(position);
            notifyItemRemoved(position);
        }
    }

    protected <T extends ViewResolver>void addView(int position, T viewResolver)throws IndexOutOfBoundsException{
        mViewBinderList.add(position, new ViewBinder<ViewResolver>(viewResolver));
        notifyItemInserted(position);
    }

}
