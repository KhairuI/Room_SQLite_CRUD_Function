package com.example.sqlite_room_crud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements Filterable {

    private List<Student> users;
    private List<Student> usersSearch;

    public UserAdapter(List<Student> users) {
        this.users = users;
        usersSearch= new ArrayList<>(users);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.idTextView.setText("ID: "+String.valueOf(users.get(position).getStudentId()));
        holder.nameTextView.setText("Name: "+users.get(position).getStudentName());
        holder.markTextView.setText("Mark: "+users.get(position).getStudentMark());
        holder.genderTextView.setText("Gender: "+users.get(position).getStudentGender());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    //this is for search data
    @Override
    public Filter getFilter() {
        return userFilter;
    }

    private Filter userFilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Student> filterStudent= new ArrayList<>();

            if(constraint== null || constraint.length()==0){
                filterStudent.addAll(usersSearch);
            }
            else {

                String filterPattern= constraint.toString().toLowerCase().trim();
                for (Student student: usersSearch){
                    if(student.getStudentName().toLowerCase().contains(filterPattern)||student.getStudentGender().toLowerCase().contains(filterPattern)||
                    student.getStudentMark().toLowerCase().contains(filterPattern)){
                        filterStudent.add(student);
                    }
                }
            }

            FilterResults results= new FilterResults();
            results.values= filterStudent;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            users.clear();
            users.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView idTextView;
        private AppCompatTextView nameTextView;
        private AppCompatTextView markTextView;
        private AppCompatTextView genderTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idTextView= itemView.findViewById(R.id.studentId);
            nameTextView= itemView.findViewById(R.id.studentName);
            markTextView= itemView.findViewById(R.id.studentMark);
            genderTextView= itemView.findViewById(R.id.studentGender);
        }
    }
}
