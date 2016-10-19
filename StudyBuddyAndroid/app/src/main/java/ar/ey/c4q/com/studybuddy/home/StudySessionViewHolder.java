package ar.ey.c4q.com.studybuddy.home;


import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ar.ey.c4q.com.studybuddy.R;
import ar.ey.c4q.com.studybuddy.models.StudySession;

public class StudySessionViewHolder extends RecyclerView.ViewHolder {

    private final Context context;

    private ImageView image;

    private TextView topic;

    private TextView locationName;

    private TextView start;

    private TextView end;

    private TextView distance;

    private SessionItemClickListener clickListener;

    public StudySessionViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        image = (ImageView) itemView.findViewById(R.id.image);
        topic = (TextView) itemView.findViewById(R.id.topic);
        locationName = (TextView) itemView.findViewById(R.id.location_name);
        start = (TextView) itemView.findViewById(R.id.start_time);
        end = (TextView) itemView.findViewById(R.id.end_time);
        distance = (TextView) itemView.findViewById(R.id.distance);
    }

    @SuppressLint("DefaultLocale")
    public void setData(final StudySession session, final SessionItemClickListener clickListener) {
        Picasso.with(context).load(session.getImageUrl()).into(image);
        topic.setText(session.getTopicOfStudy());
        locationName.setText(session.getLocationName());
        distance.setText(String.format("%s miles", session.getDistance()));
        start.setText(session.getDateStart());
        end.setText(session.getDateEnd());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClickSessions(session);
            }
        });
    }

    interface SessionItemClickListener {

        void onClickSessions(StudySession session);
    }
}
