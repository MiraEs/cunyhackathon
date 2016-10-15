package ar.ey.c4q.com.studybuddy.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import ar.ey.c4q.com.studybuddy.R;
import ar.ey.c4q.com.studybuddy.models.StudySession;


public class ActiveStudySessionListAdapter extends RecyclerView.Adapter<StudySessionViewHolder> {


    final Context context;

    private ArrayList<StudySession> data = new ArrayList<>();

    public ActiveStudySessionListAdapter(Context context) {
        this.context = context;
    }


    @Override
    public StudySessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StudySessionViewHolder(context, LayoutInflater.from(context)
                .inflate(R.layout.list_item_study_session, null));
    }

    @Override
    public void onBindViewHolder(StudySessionViewHolder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setData(ArrayList<StudySession> data) {
        this.data = data;
        notifyItemRangeInserted(0, data.size());
    }
}
