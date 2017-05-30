package adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorial.phant.simpletodo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.User;

/**
 * Created by phant on 20-May-17.
 */

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> users;
    private Context context;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    // Step 1 - This interface defines the type of messages I want to communicate to my owner
    public interface OnItemLongClickListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        void OnItemLongClickListener(View itemView, int position);
    }

    // Step 2 - This variable represents the listener passed in by the owning object
    // The listener must implement the events interface and passes messages up to the parent.
    private OnItemLongClickListener ItemLongClickListener;

    // Assign the listener implementing events interface that will receive the events
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.ItemLongClickListener = listener;
    }

    public interface OnItemClickListener{
        void OnItemClickListener(View itemView, int position);
    }

    private OnItemClickListener ItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.ItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final User user = users.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvUsername.setText(user.getTaskname());
        viewHolder.tvPriorityMain.setText(user.getPriority());
        if(user.getPriority().equalsIgnoreCase("HIGH")){
//            viewHolder.tvPriorityMain.setTextColor(Color.parseColor("#FF0000"));
            viewHolder.tvPriorityMain.setTextColor(ContextCompat.getColor(context,R.color.colorHigh));
        }else if(user.getPriority().equalsIgnoreCase("MEDIUM")){
//            viewHolder.tvPriorityMain.setTextColor(Color.parseColor("#009688"));
            viewHolder.tvPriorityMain.setTextColor(ContextCompat.getColor(context,R.color.colorMedium));
        }else{
//            viewHolder.tvPriorityMain.setTextColor(Color.parseColor("#FF6633"));
            viewHolder.tvPriorityMain.setTextColor(ContextCompat.getColor(context,R.color.colorLow));
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final TextView tvUsername;
//        public final TextView tvPriorityMain;
        @BindView(R.id.tvUsername)
        TextView tvUsername;
        @BindView(R.id.tvPriorityMain)
        TextView tvPriorityMain;

        public ViewHolder(final View itemView) {
            super(itemView);
//            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
//            tvPriorityMain = (TextView) itemView.findViewById(R.id.tvPriorityMain);
            ButterKnife.bind(this,itemView);

            // Setup the click listener
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (ItemLongClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            ItemLongClickListener.OnItemLongClickListener(itemView, position);
                        }
                    }
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ItemClickListener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            ItemClickListener.OnItemClickListener(itemView,position);
                        }
                    }
                }
            });
        }
    }
}
