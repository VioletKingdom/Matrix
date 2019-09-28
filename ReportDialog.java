package com.laioffer.matrix;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
private RecyclerView mRecyclerView;
private ReportRecyclerViewAdapter mRecyclerViewAdapter;


import androidx.annotation.NonNull;
private int cx;
private int cy;


public ReportDialog(@NonNull Context context) {
   this(context, R.style.MyAlertDialogStyle);
}

public ReportDialog(@NonNull Context context, int themeResId) {
   super(context, themeResId);
}

public static ReportDialog newInstance(Context context, int cx, int cy) {

   ReportDialog dialog = new ReportDialog(context, R.style.MyAlertDialogStyle);
   dialog.cx = cx;
   dialog.cy = cy;
   return dialog;
}

private void showDialog(String label, String prefillText) {
   int cx = (int) (fabReport.getX() + (fabReport.getWidth() / 2));
   int cy = (int) (fabReport.getY()) + fabReport.getHeight() + 56;
   dialog = ReportDialog.newInstance(getContext(), cx, cy);
   dialog.show();
}



public class ReportDialog extends Dialog {

   public ReportDialog(@NonNull Context context) {
       this(context, R.style.MyAlertDialogStyle);
   }
  
   public ReportDialog(@NonNull Context context, int themeResId) {
       super(context, themeResId);
   }

 
@Override
protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   final View dialogView = View.inflate(getContext(), R.layout.dialog, null);
   requestWindowFeature(Window.FEATURE_NO_TITLE);
   setContentView(dialogView);
   getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

   //set up animation
   setOnShowListener(new DialogInterface.OnShowListener() {
       @Override
       public void onShow(DialogInterface dialogInterface) {
           animateDialog(dialogView, true);
       }
   });

   setOnKeyListener(new DialogInterface.OnKeyListener() {
       @Override
       public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
           if (i == KeyEvent.KEYCODE_BACK) {
               animateDialog(dialogView, false);
               return true;
           }
           return false;
       }
   });

   setupRecyclerView(dialogView);
}


   setOnKeyListener(new DialogInterface.OnKeyListener() {
       @Override
       public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
           if (i == KeyEvent.KEYCODE_BACK) {
               animateDialog(dialogView, false);
               return true;
           }
           return false;
       }
   });
}
   
private void setupRecyclerView(View dialogView) {
   mRecyclerView = dialogView.findViewById(R.id.recycler_view);
   mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
   mRecyclerViewAdapter = new ReportRecyclerViewAdapter(getContext(), Config.listItems);
   mRecyclerViewAdapter.setClickListener(new ReportRecyclerViewAdapter
           .OnClickListener() {
       @Override
       public void setItem(String item) {
           // for switch item
       }
   });

   mRecyclerView.setAdapter(mRecyclerViewAdapter);
}


private void animateDialog(final View dialogView, boolean open) {
   final View view = dialogView.findViewById(R.id.dialog);
   int w = view.getWidth();
   int h = view.getHeight();

   int endRadius = (int) Math.hypot(w, h);
   if (open) {
       Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);
       view.setVisibility(View.VISIBLE);
       revealAnimator.setDuration(500);
       revealAnimator.start();
   } else {
       Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

       anim.addListener(new AnimatorListenerAdapter() {
           @Override
           public void onAnimationEnd(Animator animation) {
               super.onAnimationEnd(animation);
               view.setVisibility(View.INVISIBLE);
               dismiss();
           }
       });
       anim.setDuration(500);
       anim.start();
   }
}

}
