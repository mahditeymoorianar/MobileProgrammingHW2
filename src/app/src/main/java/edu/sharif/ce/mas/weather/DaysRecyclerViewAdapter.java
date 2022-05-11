package edu.sharif.ce.mas.weather;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.ce.mas.weather.Model.Day;

public class DaysRecyclerViewAdapter extends RecyclerView.Adapter<DaysRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Day> days;
    private LayoutInflater inflater;

    private Context mContext;

    public DaysRecyclerViewAdapter(Context context, ArrayList<Day> days){

        this.mContext = context;
        this.days = days;
        this.inflater = LayoutInflater.from(context);
    }

    //    /**
//     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
//     * an item.
//     * <p>
//     * This new ViewHolder should be constructed with a new View that can represent the items
//     * of the given type. You can either create a new View manually or inflate it from an XML
//     * layout file.
//     * <p>
//     * The new ViewHolder will be used to display items of the adapter using
//     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
//     * different items in the data set, it is a good idea to cache references to sub views of
//     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
//     *
//     * @param parent   The ViewGroup into which the new View will be added after it is bound to
//     *                 an adapter position.
//     * @param viewType The view type of the new View.
//     * @return A new ViewHolder that holds a View of the given view type.
//     * @see #getItemViewType(int)
//     * @see #onBindViewHolder(ViewHolder, int)
//     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.layout_day, parent, false));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView temperatureTextView, temperatureFeelsLikeTextView, windSpeedTextView;
        ImageView weatherImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            temperatureTextView = itemView.findViewById(R.id.temperatureTextView);
            temperatureFeelsLikeTextView = itemView.findViewById(R.id.temperatureFeelsLikeTextView);
            windSpeedTextView = itemView.findViewById(R.id.windSpeedTextView);
            weatherImageView = itemView.findViewById(R.id.weatherImageView);


        }
    }

    //    /**
//     * Called by RecyclerView to display the data at the specified position. This method should
//     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
//     * position.
//     * <p>
//     * Note that unlike {@link ListView}, RecyclerView will not call this method
//     * again if the position of the item changes in the data set unless the item itself is
//     * invalidated or the new position cannot be determined. For this reason, you should only
//     * use the <code>position</code> parameter while acquiring the related data item inside
//     * this method and should not keep a copy of it. If you need the position of an item later
//     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
//     * have the updated adapter position.
//     * <p>
//     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
//     * handle efficient partial bind.
//     *
//     * @param holder   The ViewHolder which should be updated to represent the contents of the
//     *                 item at the given position in the data set.
//     * @param position The position of the item within the adapter's data set.
//     */
    @Override
    public void onBindViewHolder(@NonNull DaysRecyclerViewAdapter.ViewHolder holder, int position) {
//        holder.subjectItemTextView.setText(subjects.get(position));

        Day currentDay = days.get(position);

        holder.temperatureTextView.setText(currentDay.temperature);
        holder.temperatureTextView.setText(currentDay.temperature_feels_like);
        holder.temperatureTextView.setText(currentDay.wind_speed);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                System.out.println(currentDay.toString());

                Intent detailsActivityIntent = new Intent(mContext, DetailsActivity.class);
                detailsActivityIntent.putExtra("dayIndex", days.indexOf(currentDay));
                mContext.startActivity(detailsActivityIntent);



            }
        });
    }


    @Override
    public int getItemCount() {
        return days.size();
    }
}
