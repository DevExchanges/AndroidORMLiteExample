package info.devexchanges.ormlite.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import info.devexchanges.ormlite.R;
import info.devexchanges.ormlite.activity.MainActivity;
import info.devexchanges.ormlite.database.DatabaseManager;
import info.devexchanges.ormlite.model.Cat;
import info.devexchanges.ormlite.model.Kitten;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private MainActivity activity;
    private List<Cat> cats;

    public ExpandableListAdapter(MainActivity context, List<Cat> rows) {
        this.activity = context;
        this.cats = rows;
    }

    @Override
    public Kitten getChild(int groupPosition, int childPosititon) {
        return this.cats.get(groupPosition).getKittens().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getGroup(groupPosition).getKittens().size();
    }

    @Override
    public Cat getGroup(int groupPosition) {
        return this.cats.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return cats.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_child_list, parent, false);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        holder.textView.setText(getChild(groupPosition, childPosition).getName());
        holder.btnEdit.setOnClickListener(onEditKittenListener(getChild(groupPosition, childPosition),
                groupPosition, childPosition));
        holder.btnDelete.setOnClickListener(onDeleteKittenListener(groupPosition, childPosition));

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_header_list, parent, false);
            holder = new HeaderViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        holder.textView.setText(getGroup(groupPosition).getName());
        holder.btnEdit.setOnClickListener(onEditCatListener(getGroup(groupPosition), groupPosition));
        holder.btnDelete.setOnClickListener(onDeleteCatListener(groupPosition));

        return convertView;
    }

    private View.OnClickListener onDeleteKittenListener(final int groupPosition, final int childPosition) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle("Delete?");
                alertDialog.setMessage("Are you sure to delete?");

                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //delete in database
                        DatabaseManager.getInstance().deleteKitten(getChild(groupPosition, childPosition).getId());

                        //update views
                        activity.getDataFromDB();
                    }
                });

                alertDialog.setNegativeButton("Cancel", null);
                alertDialog.show();
            }
        };
    }

    private View.OnClickListener onDeleteCatListener(final int groupPosition) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle("Delete?");
                alertDialog.setMessage("Are you sure to delete?");

                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //delete in database
                        DatabaseManager.getInstance().deleteCat(getGroup(groupPosition).getId());

                        //update views
                        cats.remove(groupPosition);
                        notifyDataSetChanged();
                    }
                });

                alertDialog.setNegativeButton("Cancel", null);
                alertDialog.show();
            }
        };
    }

    private View.OnClickListener onEditCatListener(final Cat cat, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle("EDIT Cat");
                alertDialog.setMessage("Please type a new cat name");

                final EditText input = new EditText(activity);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setText(cat.getName());

                alertDialog.setView(input);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //update database with new cat name
                        cat.setName(input.getText().toString().trim());
                        DatabaseManager.getInstance().updateCat(cat);

                        //update views
                        cats.get(position).setName(input.getText().toString().trim());
                        notifyDataSetChanged();
                    }
                });

                alertDialog.setNegativeButton("Cancel", null);
                alertDialog.show();
            }
        };
    }

    private View.OnClickListener onEditKittenListener(final Kitten kitten, final int groupPos,
                                                      final int childPos) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle("EDIT Kitten");
                alertDialog.setMessage("Please type a new kitten name");

                final EditText input = new EditText(activity);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setText(kitten.getName());

                alertDialog.setView(input);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //update database
                        kitten.setName(input.getText().toString().trim());
                        DatabaseManager.getInstance().updateKitten(kitten);

                        //update views
                        cats.get(groupPos).getKittens().get(childPos).setName(input.getText().toString());
                        notifyDataSetChanged();
                    }
                });

                alertDialog.setNegativeButton("Cancel", null);
                alertDialog.show();
            }
        };
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class ChildViewHolder {
        private TextView textView;
        private View btnEdit;
        private View btnDelete;

        public ChildViewHolder(View v) {
            textView = (TextView) v.findViewById(R.id.kitten_name);
            btnEdit = v.findViewById(R.id.edit);
            btnDelete = v.findViewById(R.id.delete);
        }
    }

    private static class HeaderViewHolder {
        private TextView textView;
        private View btnEdit;
        private View btnDelete;

        public HeaderViewHolder(View v) {
            btnDelete = v.findViewById(R.id.delete);
            textView = (TextView) v.findViewById(R.id.cat_name);
            btnEdit = v.findViewById(R.id.edit);
        }
    }
}
