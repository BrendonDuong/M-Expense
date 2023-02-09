package com.jovanovic.stefan.sqlitetutorial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Expense_Adapter extends RecyclerView.Adapter<Expense_Adapter.ExpenseViewHolder> {

    private final Context context;
    private final Activity activity;
    private List<Expense> expenses;

    Expense_Adapter(Activity activity, Context context, List<Expense> expenses) {
        this.activity = activity;
        this.context = context;
        this.expenses = expenses;

    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_1, parent, false);

        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        final Expense expense = expenses.get(position);
        int id = expense.getId();
        String category = expense.getCategory();
        int total = expense.getTotal_expense();
        String time = expense.getUsed_Time();

        // set value to form
        holder.category.setText(category);
        holder.total_expense_txt.setText(String.valueOf(total));
        holder.time.setText(time);
        holder.mainLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //passing parameter values
                Intent intent = new Intent(context, Detail_Expense_Activity.class);
                intent.putExtra("selectedExpense", expense);
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {

        TextView category, total_expense_txt, time;
        LinearLayout mainLayout1;

        ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            total_expense_txt = itemView.findViewById(R.id.total_expense_txt);
            time = itemView.findViewById(R.id.time);
            mainLayout1 = itemView.findViewById(R.id.mainLayout1);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout1.setAnimation(translate_anim);
        }
    }

}
