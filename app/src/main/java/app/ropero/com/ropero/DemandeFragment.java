package app.ropero.com.ropero;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

/**
 * Created by noellodou on 27/07/2017.
 */

public class DemandeFragment extends Fragment {
    private FloatingActionButton btn_pref;
    private FlowLayout llBtnPref;
    public DemandeFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demande, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.demande_list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(new CourseListAdapter(new CoursesProvider().readCourses()));




       // btn_pref = (FloatingActionButton) view.findViewById(R.id.btn_pref);
       // llBtnPref = (FlowLayout) view.findViewById(R.id.lin1);
      /*  btn_pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());
                final EditText edittext = new EditText(getActivity());
                alertDialogBuilder.setMessage("Nom produit");
                alertDialogBuilder.setTitle("Produits");

                alertDialogBuilder.setView(edittext);

                alertDialogBuilder.setPositiveButton("Validez", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String pref = "#"+edittext.getText().toString().trim();
                        Button btn = new Button(getContext());
                        btn.setText(pref);
                        btn.setTextColor(getResources().getColor(R.color.colorChoice));
                        btn.setTextSize(15);
                        // btn.setHeight(20);

                        btn.setBackgroundResource(R.drawable.backchoice2);
                        FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(15,15,0,0);
                        btn.setLayoutParams(lp);
                        llBtnPref.addView(btn);

                    }
                });

                alertDialogBuilder.setNegativeButton("Annulez", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alertDialogBuilder.show();
            }
        });*/
        return view;
    }

    class CourseListAdapter extends RecyclerView.Adapter<CoursesViewHolder> {
        private final List<Courses> courses;

        CourseListAdapter(List<Courses> courses) {
            this.courses = courses;
        }

        @Override
        public CoursesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            final View v = layoutInflater.inflate(R.layout.demande_card, viewGroup, false);
            return new CoursesViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CoursesViewHolder courseViewHolder, int i) {
            courseViewHolder.courseTitle.setText(courses.get(i).getName());
        }

        @Override
        public int getItemCount() {
            return courses.size();
        }
    }

    class CoursesViewHolder extends RecyclerView.ViewHolder {
        TextView courseTitle;

        CoursesViewHolder(View itemView) {
            super(itemView);
            courseTitle = (TextView) itemView.findViewById(R.id.course_title);
        }
    }
}
