package Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haneenalawneh.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Recipes.Recipe;


/**
 * Created by haneenalawneh on 9/20/17.
 */

public class RecpieAdapter extends RecyclerView.Adapter<RecpieAdapter.RecpieViewHolder> {


    final private ListItemClickListener mOnClickListener;

    ArrayList<Recipe> mRecpieItems = new ArrayList<Recipe>();
    Context mContext;


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public RecpieAdapter(ArrayList<Recipe> recipes, ListItemClickListener listener, Context context) {

        mOnClickListener = listener;
        mRecpieItems = recipes;
        mContext = context;
    }


    @Override
    public RecpieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recpie_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecpieViewHolder viewHolder = new RecpieViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecpieAdapter.RecpieViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return mRecpieItems.size();
    }


    class RecpieViewHolder extends RecyclerView.ViewHolder
            implements OnClickListener {

        ImageView recpieView;
        TextView recpieName;
        TextView recpieServings;

        public RecpieViewHolder(View itemView) {
            super(itemView);
            recpieView = itemView.findViewById(R.id.imageView);
            recpieName = itemView.findViewById(R.id.recpie_name);
            recpieServings = itemView.findViewById(R.id.servings);
            itemView.setOnClickListener(this);
        }


        void bind(int listIndex) {
            String recpieNameString = mRecpieItems.get(listIndex).getRecipeName();
            int servings = mRecpieItems.get(listIndex).getRecpieServings();
            recpieName.setText(recpieNameString);
            recpieServings.append(" " + Integer.toString(servings));
            String imageStr = mRecpieItems.get(listIndex).getRecpieImage();
            if (!imageStr.equals("")) {
                Uri u = Uri.parse(imageStr);
                Picasso.with(mContext).load(u).into(recpieView);

            } else {
                switch (listIndex) {
                    case 0:
                        recpieView.setImageResource(R.drawable.recpie1);
                        break;
                    case 1:
                        recpieView.setImageResource(R.drawable.recpie2);
                        break;
                    case 2:
                        recpieView.setImageResource(R.drawable.recpie3);
                        break;
                    case 3:
                        recpieView.setImageResource(R.drawable.recpie4);
                        break;
                    default:
                        recpieView.setImageResource(R.drawable.step);
                        break;

                }

            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
